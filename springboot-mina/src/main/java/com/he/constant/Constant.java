package com.he.constant;

/**
 * 
 * 协议解析常量类
 * 
 * @version 1.0
 */
public class Constant {
	/**
	 * 外帧帧头 5A5C
	 */
	public static final byte[] OUTER_HEAD = { 0x5A, 0x5C };
	/**
	 * 外帧帧尾 A5
	 */
	public static final byte OUTER_END = 0xFFFFFFA5;
	/**
	 * 内帧帧头 DFFD
	 */
	public static final byte[] INSIDE_HEAD = { 0xFFFFFFDF, 0xFFFFFFFD };
	/**
	 * 内帧帧尾 DE
	 */
	public static final byte INSIDE_END = 0xFFFFFFDE;
	/**
	 * 外帧预留字段 01
	 */
	public static final byte OUTER_REMAIN = 0x1;
	/**
	 * 主动上报操作 submit
	 */
	public static final String OPERATE_SUBMIT = "submit";
	/**
	 * 查询操作 query
	 */
	public static final String OPERATE_QUERY = "query";
	/**
	 * 控制操作 control
	 */
	public static final String OPERATE_CONTROL = "control";

}
