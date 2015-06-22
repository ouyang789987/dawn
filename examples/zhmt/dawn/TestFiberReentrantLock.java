package zhmt.dawn;

import zhmt.dawn.concurrent.FiberReentrantLock;
import kilim.Pausable;
import kilim.Scheduler;
import kilim.Task;

public class TestFiberReentrantLock {
	public static void main(String[] args) {
//		testNormal();
		testTimeout();
	}

	static void testTimeout() {
		Scheduler sch = new Scheduler();

		new Task() {
			@Override
			public void execute() throws Pausable, Exception {
				final FiberReentrantLock lock = new FiberReentrantLock();
				//task one will be excuted firstly, so hold the lock for 1000ms
				new Task() {
					public void execute() throws Pausable, Exception {
						System.out.println(this + "1 try get lock");
						lock.lock();
						System.out.println(this + "1 got lock ");
						sleep(1000);
						lock.release();
						System.out.println(this + "1 release lock ");
					};
				}.start();

				//task 2 will timeout .
				new Task() {
					public void execute() throws Pausable, Exception {
						System.out.println(this + "2 try get lock");
						lock.tryLock(400);
						System.out.println(this + "2 got lock ");
						sleep(1000);
						lock.release();
						System.out.println(this + "2 release lock ");
					};
				}.start();
				
				//task 3 wait 2000ms ,and got the lock finally
				new Task() {
					public void execute() throws Pausable, Exception {
						System.out.println(this + "3 try get lock");
						lock.tryLock(2000);
						System.out.println(this + "3 got lock ");
						sleep(1000);
						lock.release();
						System.out.println(this + "3 release lock ");
						
						
						new Task() {
							public void execute() throws Pausable, Exception {
								System.out.println(this + "4 try get lock");
								lock.tryLock(400);
								System.out.println(this + "4 got lock ");
								sleep(2000);
								lock.release();
								System.out.println(this + "4 release lock ");
							};
						}.start();
						
						new Task() {
							public void execute() throws Pausable, Exception {
								System.out.println(this + "5 try get lock");
								lock.tryLock(400);
								System.out.println(this + "5 got lock ");
								sleep(2000);
								lock.release();
								System.out.println(this + "5 release lock ");
							};
						}.start();
					};
				}.start();

			}
		}.startOn(sch);

		sch.start();
	}

	static void testNormal() {
		Scheduler sch = new Scheduler();

		new Task() {
			@Override
			public void execute() throws Pausable, Exception {

				final FiberReentrantLock lock = new FiberReentrantLock();

				for (int i = 0; i < 3; i++) {
					new Task() {
						public void execute() throws Pausable, Exception {
							System.out.println(this + " try get lock");
							lock.lock();
							System.out.println(this + " got lock ");
							sleep(1000);
							lock.release();
							System.out.println(this + " release lock ");
						};
					}.start();
				}

			}
		}.startOn(sch);

		sch.start();
	}
}
