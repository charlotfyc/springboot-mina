package com.he.constant;

/**
 * 内帧功能控制码常量
 * 
 * @version 1.0
 */
public class InConCode {

	/**
	 * 下行读A类组合帧 09
	 */
	public static final byte READ_A = 0x9;
	/**
	 * 下行写A类组合帧 19
	 */
	public static final byte WRITE_A = 0x19;
	/**
	 * 上行回应A类组合帧 C9
	 */
	public static final byte UP_RESP_A = 0xFFFFFFC9;
	/**
	 * 上行控制 D9
	 */
	public static final byte UP_CON = 0xFFFFFFD9;
	/**
	 * 主动上报 98
	 */
	public static final byte UP_REPORT = 0xFFFFFF98;
	/**
	 * 设备离线 A9
	 */
	public static final byte OFF_LINE = 0xFFFFFFA9;

}
