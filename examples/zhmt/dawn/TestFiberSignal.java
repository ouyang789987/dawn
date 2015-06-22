package zhmt.dawn;

import zhmt.dawn.concurrent.FiberSignal;
import kilim.Scheduler;
import kilim.Task;

public class TestFiberSignal {
	public static void main(String[] args) {
		Scheduler sch = new Scheduler();

		new Task() {
			public void execute() throws kilim.Pausable, Exception {
				final FiberSignal sig = new FiberSignal();
				for (int i = 0; i < 2; i++) {
					new Task() {
						public void execute() throws kilim.Pausable, Exception {
							System.out.println(getCurrentTask()+" waiting");
							sig.waitForSignal();
							System.out.println(getCurrentTask()+" waken");
						};
					}.start();
				}
				new Task() {
					public void execute() throws kilim.Pausable, Exception {
						sleep(1000);
						System.out.println("signal 1");
						sig.signalFirst();
						sleep(1000);
						System.out.println("signal 2");
						sig.signalFirst();
					};
				}.start();
			};
		}.startOn(sch);

		sch.start();
	}
}
