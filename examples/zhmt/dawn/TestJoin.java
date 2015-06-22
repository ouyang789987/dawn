/* Copyright (c) 2006, Sriram Srinivasan
 *
 * You may distribute this software under the terms of the license 
 * specified in the file "License"
 */

package zhmt.dawn;

import java.util.LinkedList;
import java.util.List;

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
public class TestJoin extends Task {
	public static void main(String[] args) throws Exception {
		final Scheduler sch = new Scheduler();

		new Task() {
			public void execute() throws Pausable, Exception {
				List<Task> list = new LinkedList<Task>();
				for (int i = 0; i < 2; i++) {
					TestJoin s = new TestJoin();
					list.add(s);
					s.startOn(sch);
				}

				System.out.println("joining one " + list.get(0).id);
				list.get(0).join();
				System.out.println("join one done " + list.get(0).id);
				sleep(5000);

				for (Task task : list) {
					task.join();
				}
				System.out.println("ddddon");
			};
		}.startOn(sch);

		sch.start();

		Thread.sleep(1000000);
	}

	static long acc = 0;


	public void execute() throws Pausable {
		for (int i = 0; i < 3; i++) {
			System.out.println("hi..." + getCurrentTask());
			acc++;
			sleep(1000);
		}

		System.out.println("one done " + getCurrentTask());
	}
}
