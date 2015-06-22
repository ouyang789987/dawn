package zhmt.dawn.nio.buffer;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import org.junit.Test;


public class ScalableDirectBufTest {
	@Test
	public void testWriteToChannel() throws IOException {
		String str = "abccc电风扇";
		for (int i = 0; i < 1024; i++) {
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(i + 1));

			buf.wbyte(Byte.MAX_VALUE);
			buf.wbyte(Byte.MIN_VALUE);
			buf.wshort(Short.MAX_VALUE);
			buf.wshort(Short.MIN_VALUE);
			buf.wint(Integer.MAX_VALUE);
			buf.wint(Integer.MIN_VALUE);
			buf.wfloat(Float.MAX_VALUE);
			buf.wfloat(Float.MIN_VALUE);
			buf.wlong(Long.MAX_VALUE);
			buf.wlong(Long.MIN_VALUE);
			buf.wdouble(Double.MAX_VALUE);
			buf.wdouble(Double.MIN_VALUE);
			buf.wutf(str);

			RandomAccessFile aFile = new RandomAccessFile("a.txt", "rw");
			FileChannel inChannel = aFile.getChannel();
			while (buf.readable() > 0)
				buf.writeTo(inChannel);
			inChannel.close();
			aFile.close();

			ScalableBuf buf2 = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(i + 1));
			aFile = new RandomAccessFile("a.txt", "rw");
			inChannel = aFile.getChannel();
			long n = buf2.readFrom(inChannel);
			while (n > 0) {
				n = buf2.readFrom(inChannel);
			}
			inChannel.close();
			aFile.close();

			assertTrue(buf2.rbyte() == Byte.MAX_VALUE);
			assertTrue(buf2.rbyte() == Byte.MIN_VALUE);
			assertTrue(buf2.rshort() == Short.MAX_VALUE);
			assertTrue(buf2.rshort() == Short.MIN_VALUE);
			assertTrue(buf2.rint() == Integer.MAX_VALUE);
			assertTrue(buf2.rint() == Integer.MIN_VALUE);
			assertTrue(buf2.rfloat() == Float.MAX_VALUE);
			assertTrue(buf2.rfloat() == Float.MIN_VALUE);
			assertTrue(buf2.rlong() == Long.MAX_VALUE);
			assertTrue(buf2.rlong() == Long.MIN_VALUE);
			assertTrue(buf2.rdouble() == Double.MAX_VALUE);
			assertTrue(buf2.rdouble() == Double.MIN_VALUE);
			assertTrue(buf2.rutf(str.length() * 2).equals(str));
		}
	}

	@Test
	public void testWriteToBuf() {
		String str = "abccc电风扇";
		for (int i = 0; i < 1024; i++) {
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(i + 1));

			buf.wbyte(Byte.MAX_VALUE);
			buf.wbyte(Byte.MIN_VALUE);
			buf.wshort(Short.MAX_VALUE);
			buf.wshort(Short.MIN_VALUE);
			buf.wint(Integer.MAX_VALUE);
			buf.wint(Integer.MIN_VALUE);
			buf.wfloat(Float.MAX_VALUE);
			buf.wfloat(Float.MIN_VALUE);
			buf.wlong(Long.MAX_VALUE);
			buf.wlong(Long.MIN_VALUE);
			buf.wdouble(Double.MAX_VALUE);
			buf.wdouble(Double.MIN_VALUE);
			buf.wutf(str);

			ScalableBuf buf2 = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(i + 1));
			buf.writeTo(buf2);

			assertTrue(buf2.rbyte() == Byte.MAX_VALUE);
			assertTrue(buf2.rbyte() == Byte.MIN_VALUE);
			assertTrue(buf2.rshort() == Short.MAX_VALUE);
			assertTrue(buf2.rshort() == Short.MIN_VALUE);
			assertTrue(buf2.rint() == Integer.MAX_VALUE);
			assertTrue(buf2.rint() == Integer.MIN_VALUE);
			assertTrue(buf2.rfloat() == Float.MAX_VALUE);
			assertTrue(buf2.rfloat() == Float.MIN_VALUE);
			assertTrue(buf2.rlong() == Long.MAX_VALUE);
			assertTrue(buf2.rlong() == Long.MIN_VALUE);
			assertTrue(buf2.rdouble() == Double.MAX_VALUE);
			assertTrue(buf2.rdouble() == Double.MIN_VALUE);
			assertTrue(buf2.rutf(str.length() * 2).equals(str));
		}
	}

	@Test
	public void testFormat() {
		String str = "abccc电风扇";
		for (int i = 0; i < 1024; i++) {

			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(i + 1));

			buf.wbyte(Byte.MAX_VALUE);
			buf.wbyte(Byte.MIN_VALUE);
			buf.wshort(Short.MAX_VALUE);
			buf.wshort(Short.MIN_VALUE);
			buf.wint(Integer.MAX_VALUE);
			buf.wint(Integer.MIN_VALUE);
			buf.wfloat(Float.MAX_VALUE);
			buf.wfloat(Float.MIN_VALUE);
			buf.wlong(Long.MAX_VALUE);
			buf.wlong(Long.MIN_VALUE);
			buf.wdouble(Double.MAX_VALUE);
			buf.wdouble(Double.MIN_VALUE);
			buf.wutf(str);

			assertTrue(buf.rbyte() == Byte.MAX_VALUE);
			assertTrue(buf.rbyte() == Byte.MIN_VALUE);
			assertTrue(buf.rshort() == Short.MAX_VALUE);
			assertTrue(buf.rshort() == Short.MIN_VALUE);
			assertTrue(buf.rint() == Integer.MAX_VALUE);
			assertTrue(buf.rint() == Integer.MIN_VALUE);
			assertTrue(buf.rfloat() == Float.MAX_VALUE);
			assertTrue(buf.rfloat() == Float.MIN_VALUE);
			assertTrue(buf.rlong() == Long.MAX_VALUE);
			assertTrue(buf.rlong() == Long.MIN_VALUE);
			assertTrue(buf.rdouble() == Double.MAX_VALUE);
			assertTrue(buf.rdouble() == Double.MIN_VALUE);
			assertTrue(buf.rutf(str.length() * 2).equals(str));

			buf.compact();

			buf.wbyte(Byte.MAX_VALUE);
			buf.wbyte(Byte.MIN_VALUE);
			buf.wshort(Short.MAX_VALUE);
			buf.wshort(Short.MIN_VALUE);
			buf.wint(Integer.MAX_VALUE);
			buf.wint(Integer.MIN_VALUE);
			buf.wfloat(Float.MAX_VALUE);
			buf.wfloat(Float.MIN_VALUE);
			buf.wlong(Long.MAX_VALUE);
			buf.wlong(Long.MIN_VALUE);
			buf.wdouble(Double.MAX_VALUE);
			buf.wdouble(Double.MIN_VALUE);
			buf.wutf(str);

			assertTrue(buf.rbyte() == Byte.MAX_VALUE);
			assertTrue(buf.rbyte() == Byte.MIN_VALUE);
			assertTrue(buf.rshort() == Short.MAX_VALUE);
			assertTrue(buf.rshort() == Short.MIN_VALUE);
			assertTrue(buf.rint() == Integer.MAX_VALUE);
			assertTrue(buf.rint() == Integer.MIN_VALUE);
			assertTrue(buf.rfloat() == Float.MAX_VALUE);
			assertTrue(buf.rfloat() == Float.MIN_VALUE);
			assertTrue(buf.rlong() == Long.MAX_VALUE);
			assertTrue(buf.rlong() == Long.MIN_VALUE);
			assertTrue(buf.rdouble() == Double.MAX_VALUE);
			assertTrue(buf.rdouble() == Double.MIN_VALUE);
			assertTrue(buf.rutf(str.length() * 2).equals(str));
		}
	}

}
