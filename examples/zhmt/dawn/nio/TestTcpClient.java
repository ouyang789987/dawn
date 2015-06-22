package zhmt.dawn.nio;

import kilim.Pausable;
import kilim.Scheduler;
import kilim.Task;
import zhmt.dawn.nio.buffer.ScalableDirectBuf;

public class TestTcpClient {
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
		
		startClient(sch);
		
		
		sch.start();
	}

	static void startClient(Scheduler sch) {
		for (int nn = 0; nn < 200; nn++) {
			new Task() {
				public void execute() throws Pausable, Exception {
					TcpClientChannel client = new TcpClientChannel("127.0.0.1",
							10000, true);
					ScalableDirectBuf buf = ScalableDirectBuf
							.allocateFromTlsCache();
					for (int i = 0; i < 1000000; i++) {
						try {
							buf.skipr(buf.readable());
							buf.compact();

							buf.wint(i);
							client.writeAll(buf);
							client.readSome(buf);
							buf.rint();
							//						System.out.println(buf.rint());
							count++;
							//sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
			}.startOn(sch);
		}

		new Task() {
			public void execute() throws Pausable, Exception {
				while (true) {
					long tmp = count;
					sleep(1000);
					System.out.println(count-tmp);
				}
			};
		}.startOn(sch);
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
					buf.compact();
				}
			}
		}.start();
	}

}
