package com.example.demo.pojo;

import java.util.Arrays;

public class OutFrame {

	// 外帧帧头
	private byte[] head;
	// 外帧长度
	private int len;
	// 设备mac
	private String mac;
	// 外帧逻辑通道码
	private byte channelCode;
	// 外帧控制码
	private byte ctrCode;
	// 外帧功能码
	private byte funCode;
	// 内帧长度
	private int inLen;
	// 内帧
	private InFrame inframe;
	// 外帧校验码
	private byte checkCode;
	// 外帧帧尾
	private byte end;

	public byte[] getHead() {
		return head;
	}

	public void setHead(byte[] head) {
		this.head = head;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public byte getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(byte channelCode) {
		this.channelCode = channelCode;
	}

	public byte getCtrCode() {
		return ctrCode;
	}

	public void setCtrCode(byte ctrCode) {
		this.ctrCode = ctrCode;
	}

	public byte getFunCode() {
		return funCode;
	}

	public void setFunCode(byte funCode) {
		this.funCode = funCode;
	}

	public int getInLen() {
		return inLen;
	}

	public void setInLen(int inLen) {
		this.inLen = inLen;
	}

	public InFrame getInframe() {
		return inframe;
	}

	public void setInframe(InFrame inframe) {
		this.inframe = inframe;
	}

	public byte getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(byte checkCode) {
		this.checkCode = checkCode;
	}

	public byte getEnd() {
		return end;
	}

	public void setEnd(byte end) {
		this.end = end;
	}

	public static void main(String[] args) {
		byte[] bytes = { 0x0, 0x5A };
		System.out.println(Arrays.toString(bytes));
	}
}
