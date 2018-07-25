package com.he.util;

/**
 * 
 * @author ypc
 *
 */
public class FrameUtil {

	/**
	 * 16进制累加和校验
	 * 
	 * @param data
	 * @return
	 */
	/*
	 * protected static String makeChecksum(String data) { if (data == null ||
	 * data.equals("")) { return ""; } int total = 0; int len = data.length(); int
	 * num = 0; while (num < len) { String s = data.substring(num, num + 2); //
	 * System.out.println("s=" + s); total += Integer.parseInt(s, 16); num = num +
	 * 2; // System.out.println("total=" + total); }
	 * 
	 * //用256求余最大是255，即16进制的FF
	 * 
	 * int mod = total % 256; String hex = Integer.toHexString(mod); len =
	 * hex.length(); // 如果不够校验位的长度，补0,这里用的是两位校验 if (len < 2) { hex = "0" + hex; }
	 * return hex; }
	 */

	/**
	 * 16进制累加和校验
	 * 
	 * @param bytes
	 * @return
	 */
	protected static byte makeChecksum(byte[] bytes) {
		byte sum = 0;
		for (int i = 0; i < bytes.length; i++) {
			sum += bytes[i];
		}
		return sum;
	}

	/**
	 * int 转 byte[2]
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] int2Bytes(int value) {
		byte[] src = new byte[2];
		src[1] = (byte) ((value >> 8) & 0xFF);
		src[0] = (byte) (value & 0xFF);
		return src;
	}

	/**
	 * byte[2] 转 int
	 * 
	 * @param src
	 * @return
	 */
	public static int bytes2Int(byte[] src) {
		return (int) ((src[0] & 0xFF) | ((src[1] & 0xFF) << 8));
	}

	public static int bytes2Int(byte hi, byte low) {
		return (int) ((hi & 0xFF) | ((low & 0xFF) << 8));
	}

	/**
	 * byte[n] 转 int
	 * 
	 * @param src
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int bytes2Int(byte[] src, int begin, int end) {
		return (int) ((src[begin] & 0xFF) | ((src[end] & 0xFF) << 8));
	}

	// 测试
	public static void main(String[] args) {
		System.out.println(makeChecksum(new byte[] { (byte) 0xAC, (byte) 0xAA, 0x2 }));
	}
}
