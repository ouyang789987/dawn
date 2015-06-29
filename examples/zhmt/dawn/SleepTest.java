package zhmt.dawn;

import kilim.Pausable;
import kilim.Scheduler;
import kilim.Task;

public class SleepTest extends Task{
	//覆盖execute,注意这里的Pausable异常，这个异常必须抛出，不能捕捉（实际上也不会捕捉到这个异常）
	@Override
	public void execute() throws Pausable, Exception {
		System.out.println("I am tired.");
		sleepOneSecond();
		//1秒后打印
		System.out.println("I am refreshed.");
	}
	
	//sleepOneSecond调用了sleep，而sleep是可暂停的，所以，sleepOneSecond抛出了Pausable异常
	private void sleepOneSecond() throws Pausable{
		sleep(1000);
	}
	
	public static void main(String[] args) {
		//创建调度器，并启动
		Scheduler sch = new Scheduler();
		sch.start();
		
		
		//new 我们写的Task
		SleepTest task = new SleepTest();
		//让task在sch中执行
		task.startOn(sch);
		
	}
}
