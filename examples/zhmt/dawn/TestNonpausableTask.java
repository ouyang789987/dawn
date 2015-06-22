package zhmt.dawn;

import kilim.Pausable;
import kilim.Scheduler;
import kilim.Task;

public class TestNonpausableTask extends NonpausableTask {

	public static void main(String[] args) throws Exception {
		final Scheduler sch = new Scheduler();

		Task t = new Task() {
			public void execute() throws Pausable, Exception {

				for (int i = 0; i < 60000; i++) {
					TestNonpausableTask s = new TestNonpausableTask();
					s.start(System.currentTimeMillis());
				}

				while (true) {
					acc = 0;
					sleep(1000);
					System.out.println("spd:" + acc);
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

	//	public void execute() throws Pausable {
	//		for (int i = 0; i < 100000000; i++) {
	////			System.out.println("hi..." + i);
	//			acc++;
	//			sleep(1);
	//		}
	//	}

	@Override
	public void execute() {
		this.start(System.currentTimeMillis() + 1);
		acc++;
	}
}