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


public class TestScheduler extends Task {

	public static void main(String[] args) throws Exception {
		final Scheduler sch = new Scheduler();
		sch.start();

		new Task(){
			public void execute() throws Pausable ,Exception {
				
				for (int i = 0; i < 6000; i++) {
					TestScheduler s = new TestScheduler();
					s.start();
				}
				
				new Task() {
					public void execute() throws Pausable, Exception {
						while (true) {
							acc = 0;
							sleep(1000);
							System.out.println("spd:"+acc); 
						}
					}
				}.start();
				
			};
		}.startOn(sch);
		
	}

	static long acc = 0;

	public void execute() throws Pausable {
		for (int i = 0; i < 100000000; i++) {
			acc++;
			sleep(1);
		}
	}
}
