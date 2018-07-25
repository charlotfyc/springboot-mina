package com.he.util;

public class Hex {

	/**
	 * 将byte[] 转换为 16进制字符串
	 * 
	 * @param bytes
	 *            byte[]
	 * @param begin
	 *            开始位置 包含头
	 * @param end
	 *            结束位置 不包含尾
	 * @return
	 */
	public static String bytesToHex(byte[] bytes, int begin, int end) {
		StringBuilder hexBuilder = new StringBuilder(2 * (end - begin));
		try {
			for (int i = begin; i < end; i++) {
				hexBuilder.append(Character.forDigit((bytes[i] & 0xF0) >> 4, 16)); // 转化高四位
				hexBuilder.append(Character.forDigit((bytes[i] & 0x0F), 16)); // 转化低四位
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hexBuilder.toString().toUpperCase();
	}

	/**
	 * 将byte[]反转后 转换为 16进制字符串
	 * 
	 * @param bytes
	 *            byte[]
	 * @param begin
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return
	 */
	public static String reverseBytesToHex(byte[] bytes, int begin, int end) {
		StringBuilder hexBuilder = new StringBuilder(2 * (end - begin));
		try {
			for (int i = end - 1; i >= begin; i--) {
				hexBuilder.append(Character.forDigit((bytes[i] & 0xF0) >> 4, 16)); // 转化高四位
				hexBuilder.append(Character.forDigit((bytes[i] & 0x0F), 16)); // 转化低四位
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hexBuilder.toString().toUpperCase();
	}

	/**
	 * 将byte[] 转换为 16进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		int len = bytes.length;
		StringBuilder hexBuilder = new StringBuilder(2 * len);
		try {
			for (int i = 0; i < len; i++) {
				hexBuilder.append(Character.forDigit((bytes[i] & 0xF0) >> 4, 16)); // 转化高四位
				hexBuilder.append(Character.forDigit((bytes[i] & 0x0F), 16)); // 转化低四位
			}
		} catch (Exception e) {
			System.out.println("in bytesToHex");
			e.printStackTrace();
		}
		return hexBuilder.toString().toUpperCase();
	}

	/**
	 * 16进制字符串转换为 Byte[]
	 * 
	 * @param hexStr
	 *            16进制字符串
	 * @return byte[]
	 */
	public static byte[] hexStr2Bytes(String hexStr) {
		int length = hexStr.length() / 2;
		byte[] bytes = new byte[length];
		int index = 0;// 起始位
		for (int i = 0; i < length; i++) {
			bytes[i] = (byte) Integer.parseInt(hexStr.substring(index, index + 2), 16);
			index += 2;
		}
		return bytes;
	}

	/**
	 * 反转16进制字符串后转int值
	 * 
	 * @param hexStr
	 * @return
	 */
	public static int reverseHexStrToInt(String hexStr) {
		String realHex = hexStr.substring(2, 4) + hexStr.substring(0, 2);
		return Integer.parseInt(realHex, 16);
	}

	/**
	 * 反转16进制字符串
	 * 
	 * @param hexStr
	 *            需要反转的16进制字符串
	 * @return 反转后的16进制字符串
	 */
	public static String reverseHexStr(String hexStr) {
		if (hexStr.length() % 2 != 0) {
			return null;
		}
		String rtn = "";
		for (int i = 1; i <= hexStr.length() / 2; i++) {
			int beginIndex = hexStr.length() - i * 2;
			rtn += hexStr.substring(beginIndex, beginIndex + 2);
		}
		return rtn;
	}

	public static void main(String[] args) {
		String bytes = "5A86553303014143C0A5";
		byte[] b = hexStr2Bytes(bytes);
		System.out.println(bytesToHex(b, 1, b.length - 2));
	}
}
