package com.he.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.apache.commons.lang3.ArrayUtils;

import com.example.demo.pojo.DevSta;
import com.example.demo.pojo.InFrame;
import com.example.demo.pojo.OutFrame;


/**
 * 解析数据帧工具类
 * 
 * @author fyc
 *
 */
public class RunxinDecodeFrame {
	/** BYTE 类 */
	private final static byte[] BYTE = { 0x1, 0x2, 0x3, 0x5, 0x8, 0xA, 0xF, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15 };
	/** INT 类 */
	private final static byte[] INT = { 0xC };
	/** 时间 类 */
	private final static byte[] TIME = { 0x4, 0x9 };
	/** 数字类 10倍 */
	private final static byte[] NUMBER_10 = { 0x6, 0xB, 0xD };
	/** 数字类 100倍 */
	private final static byte[] NUMBER_100 = { 0x7, 0xE };

	/** 数字类 10倍 */
	private final static BigDecimal BG_10 = new BigDecimal(10);
	/** 数字类 100倍 */
	private final static BigDecimal BG_100 = new BigDecimal(100);

//	private final static String WEB_URL = "http://121.40.242.158:8080/device/add?mac=%s&devicetype=%s";

	/**
	 * 解析
	 * 
	 * @param byte[]
	 * @return OutFrame
	 */
	public static OutFrame analyseFrame(byte[] bytes) {
		int len = bytes.length;
		if (len >= 28) {
			OutFrame outFrame = new OutFrame();
			// outFrame.setHead(Hex.bytesToHex(bytes, 0, 2));
			outFrame.setLen(FrameUtil.bytes2Int(bytes, 3, 2));
			outFrame.setMac(Hex.bytesToHex(bytes, 4, 12).substring(0, 15));
			outFrame.setChannelCode(bytes[12]);
			outFrame.setCtrCode(bytes[13]);
			outFrame.setFunCode(bytes[14]);
			outFrame.setInLen(FrameUtil.bytes2Int(bytes, 16, 15));
			InFrame inFrame = new InFrame();
			// inFrame.setHead(Hex.bytesToHex(bytes, 17, 19));
			inFrame.setLen(bytes[19]);
			inFrame.setCtrFunCode(bytes[20]);
			byte[] data = new byte[len - 25];
			System.arraycopy(bytes, 21, data, 0, len - 25);
			inFrame.setData(data);
			inFrame.setCheckCode(bytes[len - 4]);
			inFrame.setEnd(bytes[len - 3]);
			outFrame.setInframe(inFrame);
			outFrame.setCheckCode(bytes[len - 2]);
			outFrame.setEnd(bytes[len - 1]);
			return outFrame;
		}
		return null;
	}

	/**
	 * 解码
	 * 
	 * @param data[3]
	 *            长度为3
	 * @param devSta
	 * @return
	 * @throws SecurityException
	 * @throws Exception
	 */
	public static void decode(byte[] data, DevSta devSta) throws Exception {
		String key = Hex.bytesToHex(data, 0, 1);
		Class<? extends Object> clazz = devSta.getClass();
		if (ArrayUtils.contains(BYTE, data[0])) {
			if (data[0] == 0x1) {
			}
			Method method = clazz.getMethod("setData" + key, Byte.class);
			method.invoke(devSta, (byte) (data[1] & 0xFF));
		} else if (ArrayUtils.contains(INT, data[0])) {
			Method method = clazz.getMethod("setData" + key, Integer.class);
			method.invoke(devSta, data[1] | (data[2] << 8));
		} else if (ArrayUtils.contains(TIME, data[0])) {
			Method method = clazz.getMethod("setData" + key, String.class);
			String hour = data[1] + "";
			String minute = data[2] + "";
			if (data[1] < 10) {
				hour = "0" + hour;
			}
			if (data[2] < 10) {
				minute = "0" + minute;
			}
			method.invoke(devSta, hour + ":" + minute);
		} else if (ArrayUtils.contains(NUMBER_10, data[0])) {
			Method method = clazz.getMethod("setData" + key, BigDecimal.class);
			BigDecimal bg = new BigDecimal(FrameUtil.bytes2Int(data[1], data[2]));
			method.invoke(devSta, bg.divide(BG_10));
		} else if (ArrayUtils.contains(NUMBER_100, data[0])) {
			Method method = clazz.getMethod("setData" + key, BigDecimal.class);
			BigDecimal bg = new BigDecimal(FrameUtil.bytes2Int(data[1], data[2]));
			method.invoke(devSta, bg.divide(BG_100));
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println((0xFFFFFFFB & 0xFF));
		System.out.println(0xFFFFFFFB);
	}
}
