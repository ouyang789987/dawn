package zhmt.dawn.concurrent.interthread;

import kilim.Scheduler;

public class TestCallableTask {
	public static void main(String[] args) {
		Scheduler sch = new Scheduler();
		sch.start();

		String ret = new CallableTask<String>() {
			public String execute() throws kilim.Pausable, Exception {
				System.out.println("I want to take a sleep");
				sleep(2000);
				System.out.println("sleep done");
				return "hello world";
			};
		}.startOn(sch);
		System.out.println(ret);
	}
}
