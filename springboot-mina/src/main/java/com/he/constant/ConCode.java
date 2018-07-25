package com.he.constant;

/**
 * 外帧控制码
 * 
 */
public class ConCode {
	/**
	 * 下行请求帧 00
	 */
	public static final byte DOWN_REQ = 0x0;
	/**
	 * 上行回应帧 C0
	 */
	public static final byte UP_REP = 0xFFFFFFC0;

	/**
	 * 主动上报帧 80
	 */
	public static final byte UP_REPORT = 0xFFFFFF80;
}
