package zhmt.dawn.nio.buffer;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.junit.Test;

import zhmt.dawn.util.UnsafeUtil;

public class ScalableDirectBufTest {
	private static Charset cs = Charset.forName("utf8");
	@Test
	public void testWriteToChannel() throws IOException {
		String str = "abccc电风扇";
		int strlen = str.getBytes(cs).length;
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
			buf.wstr(str,cs);

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
			assertTrue(buf2.rstr(strlen,cs).equals(str));
		}
	}

	@Test
	public void testWriteToBuf() {
		String str = "abccc电风扇";
		int strlen = str.getBytes(cs).length;
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
			buf.wstr(str,cs);

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
			assertTrue(buf2.rstr(strlen,cs).equals(str));
		}
	}
	
	@Test
	public void testShortByteOrder() {
		{
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(1));
			buf.wshort((short)1);
			int i = 0;
			assertTrue(buf.gbyte(i++) == 0);
			assertTrue(buf.gbyte(i++) == 1);
		}
		{
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(10));
			buf.wshort((short)1);
			int i = 0;
			assertTrue(buf.gbyte(i++) == 0);
			assertTrue(buf.gbyte(i++) == 1);
		}
		{
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(1));
			buf.setByteOrder(ByteOrder.LITTLE_ENDIAN);
			buf.wshort((short)1);
			int i = 0;
			assertTrue(buf.gbyte(i++) == 1);
			assertTrue(buf.gbyte(i++) == 0);
		}
		{
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(10));
			buf.setByteOrder(ByteOrder.LITTLE_ENDIAN);
			buf.wshort((short)1);
			int i = 0;
			assertTrue(buf.gbyte(i++) == 1);
			assertTrue(buf.gbyte(i++) == 0);
		}
	}

	@Test
	public void testIntByteOrder() {
		{
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(1));
			buf.wint(0x01020304);
			int i = 0;
			assertTrue(buf.gbyte(i++) == 1);
			assertTrue(buf.gbyte(i++) == 2);
			assertTrue(buf.gbyte(i++) == 3);
			assertTrue(buf.gbyte(i++) == 4);
		}
		{
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(10));
			buf.wint(0x01020304);
			int i = 0;
			assertTrue(buf.gbyte(i++) == 1);
			assertTrue(buf.gbyte(i++) == 2);
			assertTrue(buf.gbyte(i++) == 3);
			assertTrue(buf.gbyte(i++) == 4);
		}
		{
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(1));
			buf.setByteOrder(ByteOrder.LITTLE_ENDIAN);
			buf.wint(0x01020304);
			int i = 0;
			assertTrue(buf.gbyte(i++) == 4);
			assertTrue(buf.gbyte(i++) == 3);
			assertTrue(buf.gbyte(i++) == 2);
			assertTrue(buf.gbyte(i++) == 1);
		}
		{
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(10));
			buf.setByteOrder(ByteOrder.LITTLE_ENDIAN);
			buf.wint(0x01020304);
			int i = 0;
			assertTrue(buf.gbyte(i++) == 4);
			assertTrue(buf.gbyte(i++) == 3);
			assertTrue(buf.gbyte(i++) == 2);
			assertTrue(buf.gbyte(i++) == 1);
		}
	}
	
	@Test
	public void testLongByteOrder() {
		long data = 0x0102030405060708L;
		{
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(1));
			buf.wlong(data);
			int i = 0;
			assertTrue(buf.gbyte(i++) == 1);
			assertTrue(buf.gbyte(i++) == 2);
			assertTrue(buf.gbyte(i++) == 3);
			assertTrue(buf.gbyte(i++) == 4);
			assertTrue(buf.gbyte(i++) == 5);
			assertTrue(buf.gbyte(i++) == 6);
			assertTrue(buf.gbyte(i++) == 7);
			assertTrue(buf.gbyte(i++) == 8);
		}
		{
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(10));
			buf.wlong(data);
			int i = 0;
			assertTrue(buf.gbyte(i++) == 1);
			assertTrue(buf.gbyte(i++) == 2);
			assertTrue(buf.gbyte(i++) == 3);
			assertTrue(buf.gbyte(i++) == 4);
			assertTrue(buf.gbyte(i++) == 5);
			assertTrue(buf.gbyte(i++) == 6);
			assertTrue(buf.gbyte(i++) == 7);
			assertTrue(buf.gbyte(i++) == 8);
		}
		{
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(1));
			buf.setByteOrder(ByteOrder.LITTLE_ENDIAN);
			buf.wlong(data);
			int i = 0;
			assertTrue(buf.gbyte(i++) == 8);
			assertTrue(buf.gbyte(i++) == 7);
			assertTrue(buf.gbyte(i++) == 6);
			assertTrue(buf.gbyte(i++) == 5);
			assertTrue(buf.gbyte(i++) == 4);
			assertTrue(buf.gbyte(i++) == 3);
			assertTrue(buf.gbyte(i++) == 2);
			assertTrue(buf.gbyte(i++) == 1);
		}
		{
			ScalableBuf buf = new ScalableDirectBuf(
					new BlockFactory.CachedDirectFactory(10));
			buf.setByteOrder(ByteOrder.LITTLE_ENDIAN);
			buf.wlong(data);
			int i = 0;
			assertTrue(buf.gbyte(i++) == 8);
			assertTrue(buf.gbyte(i++) == 7);
			assertTrue(buf.gbyte(i++) == 6);
			assertTrue(buf.gbyte(i++) == 5);
			assertTrue(buf.gbyte(i++) == 4);
			assertTrue(buf.gbyte(i++) == 3);
			assertTrue(buf.gbyte(i++) == 2);
			assertTrue(buf.gbyte(i++) == 1);
		}
	}
	
	@Test
	public void testShortBound(){
		ScalableBuf buf = new ScalableDirectBuf(
				new BlockFactory.CachedDirectFactory(10));
		buf.wshort(Short.MAX_VALUE);
		System.out.println(Short.MAX_VALUE);
		assertTrue(buf.rshort() == Short.MAX_VALUE);
		
	}

	@Test
	public void testFormat() {
		String str = "abccc电风扇";
		int strlen = str.getBytes(cs).length;
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
			buf.wstr(str,cs);

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
			assertTrue(buf.rstr(strlen,cs).equals(str));

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
			buf.wstr(str,cs);

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
			assertTrue(buf.rstr(strlen,cs).equals(str));
		}
	}

}
