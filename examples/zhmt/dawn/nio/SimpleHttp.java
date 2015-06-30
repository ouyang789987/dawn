package zhmt.dawn.nio;

import java.nio.charset.Charset;

import zhmt.dawn.nio.buffer.ScalableDirectBuf;
import kilim.Pausable;
import kilim.Scheduler;
import kilim.Task;

public class SimpleHttp extends Task {
	public static void main(String[] args) {
		Scheduler sch = new Scheduler();
		sch.start();

		SimpleHttp task = new SimpleHttp();
		task.startOn(sch);
	}

	/**
	 * 自己要清楚，这段代码是在调度器中执行的
	 */
	@Override
	public void execute() throws Pausable, Exception {
		//创建buffer
		ScalableDirectBuf buf = ScalableDirectBuf.allocateFromTlsCache();

		//准备http请求数据，这里是访问首页
		byte[] req = "GET / HTTP/1.0\r\n\r\n".getBytes();
		buf.wbytes(req, 0, req.length);

		//创建连接
		TcpClientChannel ch = new TcpClientChannel("www.baidu.com", 80, false);

		//发送http请求
		ch.writeAll(buf);

		//接收所有响应数据,直到收到EOF，因为这里用的是HTTP1.0，所以，很快就能收到EOF
		int n = ch.readSome(buf);
		while (n >= 0) {
			n = ch.readSome(buf);
		}

		//把结果转成字符串，并打印
		String ret = buf.rstr((int) buf.readable(), Charset.forName("utf8"));
		System.out.println("===========================");
		System.out.println(ret);
		System.out.println("===========================");

		//释放buffer
		buf.release();
		//关闭连接
		ch.close();
	}
}
