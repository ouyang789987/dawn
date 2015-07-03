Kilim v1.0
Copyright (c) 2006 Sriram Srinivasan.
(kilim _at_ malhar.net)
======================================================================

This software is released under an MIT-style licesne (please see the
License file). 

Please see docs/manual.txt and docs/kilim_ecoop08.pdf for a brief
introduction.

This software depends on the ASM bytecode library (v. 4.x)

To build, you can either run "build.sh" on Unix or ant from the top
directory. Run "test.sh" or "ant test" to test.

To run an example, type (say)
  java -cp ./classes:$CLASSPATH kilim.examples.Chain 10

Please send comments, queries, code fixes, constructive criticisms to 
kilim _at_ malhar.net



dawn v0.0.1
======================================================================

系列教程

http://blog.csdn.net/zhmt/article

Code with nio in simplest way:

如何运行本工程以及例子：

1.jdk1.7以上
2.这是个eclipse工程
3.在写完代码后，运行之前要执行ant脚本weaver.xml，进行代码织入。
4.再正常运行代码
5.每次编辑代码后，执行之前都要运行weaver.xml

看一下我们的hello world：这是一个echo server，运行后，你可以
telnet 127.0.0.1 10000，输入一些数据回车查看结果。

这里首先要创建一个调度器，Scheduler，并启动。然后在调度器中初始化
server。这里的处理方法是：
接收到连接后，启动一个Task来处理此连接。
处理单个连接的方式，和我们读取文件 或者普通socket一样，read一些数据，
然后写出去。更多示例参考examples。

public class TestTcpSocketChannel {
	static long count = 0; 
	public static void main(String[] args) {
		Scheduler sch = new Scheduler();

		new Task() {
			@Override
			public void execute() throws Pausable, Exception {
				System.out.println("boostrap..");
				TcpServer server = new TcpServer("0.0.0.0", 10000) {
					@Override
					protected void onAccepted(TcpChannel ch) {
						processConnection(ch);
					}
				};
				server.start();
			}
		}.startOn(sch);

//		new Task() {
//			public void execute() throws Pausable, Exception {
//				while (true) {
//					sleep(1000);
//					System.out.println(count);
//				}
//			};
//		}.startOn(sch);
		
		sch.start();
	}

	static void processConnection(final TcpChannel ch) {
		new Task() {
			@Override
			public void execute() throws Pausable, Exception {
				System.out.println("reading.");
				ScalableDirectBuf buf = ScalableDirectBuf.allocateFromTlsCache();
				int n = 0;
				while (true) {
					n = ch.readSome(buf);
					if (n > 0) {
						ch.writeAll(buf);
					}
					count++;
					buf.compact();
				}
			}
		}.start();
	}
}


关于benchmark：
zhmt.dawn.nio.TestTcpClient既有服务器，也有客户端，可以直接运行查看运行效率：
我在macbook air上测试的数据是：47500response/秒。

