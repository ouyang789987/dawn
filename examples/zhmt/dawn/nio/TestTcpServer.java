package zhmt.dawn.nio;

import kilim.Pausable;
import kilim.Scheduler;
import kilim.Task;

public class TestTcpServer {
	/**
	 * telnet to server, connections will be closed.
	 * @param args
	 */
	
	public static void main(String[] args) {
		Scheduler sch = new Scheduler();

		new Task() {
			@Override
			public void execute() throws Pausable, Exception {
				TcpServer server = new TcpServer("0.0.0.0", 10000) {
					@Override
					protected void onAccepted(TcpChannel ch) {
						ch.close();
					}
				};
				server.start();
			}
		}.startOn(sch);

		sch.start();
	}
}
