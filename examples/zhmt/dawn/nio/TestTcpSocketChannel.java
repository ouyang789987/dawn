package zhmt.dawn.nio;

import kilim.Pausable;
import kilim.Scheduler;
import kilim.Task;
import zhmt.dawn.nio.buffer.ScalableDirectBuf;

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
