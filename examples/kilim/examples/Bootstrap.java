//package kilim.examples;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//import kilim.Mailbox;
//import kilim.PauseReason;
//import kilim.Task;
//
//public class Bootstrap {
//	// auth rpc , coroutine based
//	// game rpc , coroutine based
//	// gate
//	// mem cluster,
//	static class Pa implements PauseReason{
//		long start;
//		Pa(){
//			start = System.currentTimeMillis();
//		}
//		@Override
//		public boolean isValid(Task t) {
//			
//			return System.currentTimeMillis()-start < 1000;
//		}
//		
//	}
//	public static void main(String[] args) {
//		final Mailbox<Object> gamecommands = new Mailbox<Object>();
//		Mailbox<Object> rpcevent = new Mailbox<Object>();
//		final int N = 10;
//		final int T = 1;
//		final long start = System.currentTimeMillis();
//
//		System.setProperty("kilim.Scheduler.numThreads", "1");
//
//		final AtomicInteger at = new AtomicInteger(0);
//		for (int ii = 0; ii < T; ii++) {
//			final int iii = ii;
//			new Task() {
//				public void execute() throws kilim.Pausable, Exception {
//					for (int i = 0; i < N; i++) {
////						gamecommands.put(iii+":" + at.incrementAndGet());
////						Task.sleep(1000);
////						 System.out.println("-"+i);
//						 long start = System.currentTimeMillis();
//						yield();
//						pause(new Pa());
//						System.out.println(System.currentTimeMillis() - start);
//					}
//					System.exit(0);
//				}
//			}.start();
//		}
//
////		new Task() {
////			public void execute() throws kilim.Pausable, Exception {
////				int num = N*T;
////				for (int i = 0; i < num; i++) {
////					 System.out.println(gamecommands.get());
//////					gamecommands.get();
//////					yield();
////				}
////				System.out.println(1.0 * num / (System.currentTimeMillis() - start)
////						* 1000);
//////				System.exit(0);
////			}
////		}.start();
//	}
//}
