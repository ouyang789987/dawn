package zhmt.dawn.util;

import java.lang.reflect.Field;
import java.nio.ByteOrder;

public class UnsafeUtil {
	public static final sun.misc.Unsafe unsafe;
	public static final boolean isBigEndian = ByteOrder.nativeOrder().equals(
			ByteOrder.BIG_ENDIAN);

	static {
		sun.misc.Unsafe tmp = null;
		try {
			Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			tmp = (sun.misc.Unsafe) f.get(null);
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		unsafe = tmp;
	}

	public static short shortToBigEndian(int data) {
		if (isBigEndian)
			return (short) data;
		return reverseShort(data);
	}

	private static short reverseShort(int data) {
		int ret = (data << 8) | (data >>> 8);
		return (short) ret;
	}

	public static int intToBigEndian(int data) {
		if (isBigEndian) {
			return data;
		}
		return reverseInt(data);
	}

	public static int littleEndianToHostInt(int data) {
		if (isBigEndian) {
			return reverseInt(data);
		}
		return data;
	}

	public static int hostIntToLittleEndian(int data) {
		if (isBigEndian) {
			return reverseInt(data);
		}
		return data;
	}

	public static int intToLittleEndian(int data) {
		if (isBigEndian) {
			return reverseInt(data);
		}
		return data;
	}

	public static int reverseInt(int data) {
		int ret = (data << 24) | ((data << 8) & 0xFF0000)
				| ((data >> 8) & 0xFF00) | (data >>> 24);
		return ret;
	}

	public static long longToBigEndian(long data) {
		if (isBigEndian) {
			return data;
		}
		return reverseLong(data);
	}

	private static long reverseLong(long data) {
		long ret = (data << 56) | ((data << 40) & 0x00FF000000000000L)
				| ((data << 24) & 0x0000FF0000000000L)
				| ((data << 8) & 0x000000FF00000000L)
				| ((data >>> 8) & 0x00000000FF000000L)
				| ((data >>> 24) & 0x0000000000FF0000L)
				| ((data >>> 56) & 0x000000000000FF00L) | (data >>> 56);
		return ret;
	}

	public static void main(String[] args) {
		System.out.println(isBigEndian);
	}
}
