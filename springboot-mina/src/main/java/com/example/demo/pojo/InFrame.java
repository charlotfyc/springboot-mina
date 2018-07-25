package com.example.demo.pojo;

public class InFrame {

	// 内帧帧头
	private byte[] head;
	// 内帧长度
	private byte len;
	// 内帧控制功能码
	private byte ctrFunCode;
	// 内帧数据域
	private byte[] data;
	// 内帧校验码
	private byte checkCode;
	// 内帧帧尾
	private byte end;

	public byte[] getHead() {
		return head;
	}

	public void setHead(byte[] head) {
		this.head = head;
	}

	public byte getLen() {
		return len;
	}

	public void setLen(byte len) {
		this.len = len;
	}

	public byte getCtrFunCode() {
		return ctrFunCode;
	}

	public void setCtrFunCode(byte ctrFunCode) {
		this.ctrFunCode = ctrFunCode;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
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
}
