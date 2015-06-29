package zhmt.dawn;

import kilim.Pausable;
import kilim.Scheduler;
import kilim.Task;

//继承kilim.Task
public class HelloWorld extends Task{
	//覆盖execute,注意这里的Pausable异常，这个异常必须抛出，不能捕捉（实际上也不会捕捉到这个异常）
	@Override
	public void execute() throws Pausable, Exception {
		System.out.println("hello world");
	}
	
	public static void main(String[] args) {
		//创建调度器，并启动
		Scheduler sch = new Scheduler();
		sch.start();
		
		
		//new 我们写的Task
		HelloWorld task = new HelloWorld();
		//让task在sch中执行
		task.startOn(sch);
		
		System.out.println("task started.");
	}
}
