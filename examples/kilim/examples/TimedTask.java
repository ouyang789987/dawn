///* Copyright (c) 2006, Sriram Srinivasan
// *
// * You may distribute this software under the terms of the license 
// * specified in the file "License"
// */
//
//package kilim.examples;
//
//import kilim.ExitMsg;
//import kilim.Mailbox;
//import kilim.Pausable;
//import kilim.Task;
//
///**
// * Creates lots of tasks that print stuff, sleep, then wake up and print more.
// * 
// * [compile] javac -d ./classes TimedTask.java [weave] java kilim.tools.Weave -d
// * ./wlasses kilim.examples.TimedTask [run] java -cp
// * ./wlasses:./classes:$CLASSPATH kilim.examples.TimedTask
// * 
// * @author sriram@malhar.net
// */
//public class TimedTask extends Task {
//	static final Mailbox<String> exitmb = new Mailbox<String>();
//	
//	public static void main(String[] args) throws Exception {
//		System.setProperty("kilim.Scheduler.numThreads", "1");
//		final int numTasks = (args.length > 0) ? Integer.parseInt(args[0]) : 10000000;
//		
//
//		new Task() {
//			@Override
//			public void execute() throws Pausable, Exception {
//				for (int i = 0; i < numTasks; i++) {
//					new TimedTask().start();//.informOnExit(exitmb);
//					exitmb.putb(i+"");
//					//Task.yield();
//					
//				}
//			}
//		}.start();
//
//		new Task(){
//			public void execute() throws Pausable ,Exception {
//				for (int i = 0; i < numTasks; i++) {
//					exitmb.getb();
//					//Task.yield();
//				}
//				System.exit(0);
//			}
//			
//		}.start();
//	}
//
//	public void execute() throws Pausable {
//		int a =1 + 3;
//		int b =a +2;
//		d(a,b);
//		//System.out.println("Task #" + id() + " going to sleep ...");
////		Task.sleep(1000);
//		//System.out.println("           Task #" + id() + " waking up");
//	}
//	
//	public int d(int a , int b){
//		return a+b;
//	}
//}
