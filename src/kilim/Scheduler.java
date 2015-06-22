/* Copyright (c) 2006, Sriram Srinivasan
 *
 * You may distribute this software under the terms of the license 
 * specified in the file "License"
 */

package kilim;

import java.util.concurrent.ConcurrentLinkedDeque;

import zhmt.dawn.NonpausableTask;
import zhmt.dawn.CascadeTimerWheel;
import zhmt.dawn.TimerExe;

/**
 * This is a basic FIFO Executor. It maintains a list of runnable tasks and
 * hands them out to WorkerThreads. Note that we don't maintain a list of all
 * tasks, but we will at some point when we introduce monitoring/watchdog
 * services. Paused tasks are not GC'd because their PauseReasons ought to be
 * registered with some other live object.
 * 
 */
public class Scheduler {

	public WorkerThread coreThread;
	protected boolean shutdown = false;
	CascadeTimerWheel<TimerExe> runnableTasks;
	ConcurrentLinkedDeque<TimerExe> outsideTasks = new ConcurrentLinkedDeque<TimerExe>();

	public static final ThreadLocal<Scheduler> tls = new ThreadLocal<Scheduler>();

	public Scheduler() {
		coreThread = new WorkerThread(this);
	}

	public void start() {
		coreThread.start();
	}

	/**
	 * Schedule a task to run. It is the task's job to ensure that it is not
	 * scheduled when it is runnable.
	 */
	public void schedule(Task t) {
		if (tls.get() == null)
			outsideTasks.add(t);
		else
			runnableTasks.add(t);
		//		assert t.running == true : "Task " + t
		//				+ " scheduled even though running is false";
	}

	public void schedule(NonpausableTask t) {
		if (tls.get() == null)
			outsideTasks.add(t);
		else
			runnableTasks.add(t);
	}

	public void shutdown() {
		shutdown = true;
		//		if (defaultScheduler == this) {
		//			defaultScheduler = null;
		//		}
		coreThread.notify();
	}

	public boolean isShutdown() {
		return shutdown;
	}

	public void dump() {
		System.out.println(runnableTasks);
		//      for (WorkerThread w: allThreads) {
		//          w.dumpStack();
		//      }
	}
}
