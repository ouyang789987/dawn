/* Copyright (c) 2006, Sriram Srinivasan
 *
 * You may distribute this software under the terms of the license 
 * specified in the file "License"
 */

package kilim;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import zhmt.dawn.NonpausableTask;
import zhmt.dawn.CascadeTimerWheel;
import zhmt.dawn.TimerExe;
import zhmt.dawn.TimerExe.Type;
import zhmt.dawn.nio.NioMainLoop;

public class WorkerThread extends Thread {
	Task runningTask;
	/**
	 * A list of tasks that prefer to run only on this thread. This is used by
	 * kilim.ReentrantLock and Task to ensure that lock.release() is done on the
	 * same thread as lock.acquire()
	 */
	Scheduler scheduler;
	static AtomicInteger gid = new AtomicInteger();
	public int numResumes = 0;

	WorkerThread(Scheduler ascheduler) {
		super("KilimWorker-" + gid.incrementAndGet());
		scheduler = ascheduler;
	}

	public void run() {
		scheduler.runnableTasks = new CascadeTimerWheel<>();
		Scheduler.tls.set(scheduler);
		NioMainLoop nioLoop = NioMainLoop.getTlMainLoop();
		
		CascadeTimerWheel<TimerExe> mainQ =scheduler.runnableTasks; 
		ConcurrentLinkedDeque<TimerExe> outsideQ = scheduler.outsideTasks; 
		try {
			while (true) {
				if (scheduler.isShutdown())
					throw new ShutdownException();
				
				//consume tasks
				TimerExe obj = mainQ.pollFirst();
				if (obj != null) {
					if (obj.getType() == Type.Task) {
						Task t = (Task) obj;
						runningTask = t;
						t._runExecute(this);
						runningTask = null;
					} else {
						NonpausableTask t = (NonpausableTask) obj;
						try {
							t.execute();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					//offer tasks
					if (mainQ.canTick()) {
						mainQ.tick();
					} else {
						while ((obj = outsideQ.pollFirst()) != null) {
							mainQ.add(obj);
						}
						int ioNum = nioLoop
								.loopOnce(mainQ.tickPeriod);
						if (ioNum <= 10) {
							Thread.sleep(1);
						}
					}
				}
			}
		} catch (ShutdownException se) {
			// nothing to do.
		} catch (OutOfMemoryError ex) {
			System.err.println("Out of memory");
			System.exit(1);
		} catch (Throwable ex) {
			ex.printStackTrace();
			System.err.println(runningTask);
		}
		runningTask = null;
	}

	public Task getCurrentTask() {
		return runningTask;
	}
}
