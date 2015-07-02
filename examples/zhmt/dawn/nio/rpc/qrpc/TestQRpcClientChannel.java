package zhmt.dawn.nio.rpc.qrpc;

import kilim.Pausable;
import kilim.Scheduler;
import kilim.Task;
import zhmt.dawn.nio.TcpChannel;
import zhmt.dawn.nio.TcpClientChannel;
import zhmt.dawn.nio.TcpServer;
import zhmt.dawn.nio.buffer.ScalableDirectBuf;

public class TestQRpcClientChannel extends QRpcClientChannel {
	public TestQRpcClientChannel(String ip, int port, boolean autoReconnect) {
		super(ip, port, autoReconnect);
	}

	@Override
	protected void serial(Object req, ScalableDirectBuf buf) {
		Long data = (Long) (req);
		buf.wlong(data);
	}

	@Override
	protected long isPackReceived(ScalableDirectBuf buf, long startRindex) {
		if (buf.wi() - startRindex >= 8)
			return startRindex + 8;
		return -1;
	}

	@Override
	protected Object deserial(ScalableDirectBuf buf) {
		Object rsp = buf.rlong();
		return rsp;
	}

	static long seq = 1;

	public static void main(String[] args) {
		Scheduler sch = new Scheduler();

//		new Task() {
//			@Override
//			public void execute() throws Pausable, Exception {
//				System.out.println("boostrap..");
//				TcpServer server = new TcpServer("0.0.0.0", 10000) {
//					@Override
//					protected void onAccepted(TcpChannel ch) {
//						processConnection(ch);
//					}
//				};
//				server.start();
//			}
//		}.startOn(sch);

		new Task() {
			public void execute() throws kilim.Pausable, Exception {
				final TestQRpcClientChannel client = new TestQRpcClientChannel(
						"127.0.0.1", 10000, true);
				final long N = 100000000;
				int T = 10000;

				for (int n = 0; n < T; n++) {
					new Task() {
						public void execute() throws kilim.Pausable, Exception {
							for (long i = 0; i < N; i++) {
								try {
									long tmp = seq++;
									Object rsp = client.request(tmp);
									if(!rsp.equals(tmp))
									{
										System.out.println("error ........");
									}
									//																		System.out.println(rsp + " \t " + this);
									//																		sleep(1000);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						};
					}.start();
				}

				new Task() {
					public void execute() throws Pausable, Exception {
						while (true) {
							long tmp = seq;
							sleep(1000);
							System.out.println(seq-tmp);
						}
					};
				}.start();

			};
		}.startOn(sch);

		sch.start();
	}

	static void processConnection(final TcpChannel ch) {
		new Task() {
			@Override
			public void execute() throws Pausable, Exception {
				System.out.println("reading.");
				ScalableDirectBuf buf = ScalableDirectBuf
						.allocateFromTlsCache();
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
