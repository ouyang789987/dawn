/* Copyright (c) 2006, Sriram Srinivasan
 *
 * You may distribute this software under the terms of the license 
 * specified in the file "License"
 */

package zhmt.dawn;

//import kilim.Mailbox;
import kilim.Pausable;
import kilim.Scheduler;
import kilim.Task;

/**
 * Spawn a task, communicate through a shared mailbox. The task's termination is
 * knowm through another mailbox.
 * 
 * The structure of this class is not much different from a Thread version that
 * uses PipedInput/OutputStreams (Task instead of Thread, execute() instead of
 * run(), and typed, buffered mailboxes instead of pipes.
 * 
 * [compile] javac -d ./classes SimpleTask.java [weave] java kilim.tools.Weave
 * -d ./classes kilim.examples.SimpleTask [run] java -cp
 * ./classes:./classes:$CLASSPATH kilim.examples.SimpleTask
 */
public class TestScheduler extends Task {

	public static void main(String[] args) throws Exception {
		final Scheduler sch = new Scheduler();

		for (int i = 0; i < 6000; i++) {
			TestScheduler s = new TestScheduler();
			s.startOn(sch);
		}
		
		Task t = new Task() {
			public void execute() throws Pausable, Exception {
				while (true) {
					acc = 0;
					sleep(1000);
					System.out.println("spd:"+acc);
				}
			}
		};
		t.startOn(sch);

		sch.start();

		Thread.sleep(1000000);
	}

	static long acc = 0;

	/**
	 * The entry point. mb.get() is a blocking call that yields the thread
	 * ("pausable")
	 */

	public void execute() throws Pausable {
		for (int i = 0; i < 100000000; i++) {
//			System.out.println("hi..." + i);
			acc++;
			sleep(1);
		}
	}
}
