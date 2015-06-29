package zhmt.dawn.concurrent.interthread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import kilim.Pausable;
import kilim.Scheduler;
import kilim.Task;

public class TestThreadPollCallableTask {
	public static void main(String[] args) {
		final Scheduler sch = new Scheduler();
		sch.start();

		final ThreadPoolExecutor th = new ThreadPoolExecutor(1, 2, 1000,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

		new Task() {
			@Override
			public void execute() throws Pausable, Exception {
				String ret = new ThreadPoolCallableTask<String>(){
					@Override
					public String execute() throws Exception {
						return "hi";
					}
				}.startOn(th);
				
				System.out.println(ret);
			}
		}.startOn(sch);
	}
}
