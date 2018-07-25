package com.he.util;

import com.he.constant.ConCode;
import com.he.constant.Constant;
import com.he.constant.FunCode;
import com.he.constant.InConCode;

/**
 * 编码工具类
 * 
 * @author fyc
 *
 */
public class EncodeFrame {

	/**
	 * 
	 * @param mac
	 *            设备Mac
	 * @param function
	 *            控制 or 查询
	 * @param data
	 *            数据域
	 * @return
	 */
	public static byte[] decode(String mac, String function, String data) {
		// 如果为查询操作
		if (function.equals(Constant.OPERATE_QUERY)) {
			return getOutFrame(mac, getInFrame(data, InConCode.READ_A));
		}
		// 如果为控制操作
		else if (function.equals(Constant.OPERATE_CONTROL)) {
			return getOutFrame(mac, getInFrame(data, InConCode.WRITE_A));
		} else {
			return null;
		}
	}

	/**
	 * 组装内帧
	 * 
	 * @param strData
	 *            数据域
	 * @param inConCode
	 *            控制功能码
	 * @return
	 */
	protected static byte[] getInFrame(String strData, byte inConCode) {
		// 数据域长度
		int dataLen = strData.length() / 2;
		// 内帧长度
		int len = 6 + dataLen;
		byte[] inFrame = new byte[len];
		// 内帧帧头
		System.arraycopy(Constant.INSIDE_HEAD, 0, inFrame, 0, 2);
		// 内帧长度
		inFrame[2] = (byte) len;
		// 内帧控制功能码
		inFrame[3] = inConCode;
		// 内帧数据域
		System.arraycopy(Hex.hexStr2Bytes(strData), 0, inFrame, 4, dataLen);
		// 校验码
		inFrame[len - 2] = FrameUtil.makeChecksum(inFrame);
		// 内帧帧尾
		inFrame[len - 1] = Constant.INSIDE_END;
		return inFrame;
	}

	/**
	 * 
	 * @param strMac
	 * @param inFrame
	 * @return
	 */
	protected static byte[] getOutFrame(String strMac, byte[] inFrame) {
		// 内帧长度
		int inLen = inFrame.length;
		// 外帧长度
		int len = 19 + inLen;
		byte[] outFrame = new byte[len];
		// 外帧帧头
		System.arraycopy(Constant.OUTER_HEAD, 0, outFrame, 0, 2);
		// 外帧长度
		System.arraycopy(FrameUtil.int2Bytes(len), 0, outFrame, 2, 2);
		//
		// outFrame[4] = 0x0;
		// outFrame[5] = 0x0;
		// mac
		System.arraycopy(Hex.hexStr2Bytes(strMac), 0, outFrame, 4, 8);
		// 预留字段
		outFrame[12] = Constant.OUTER_REMAIN;
		// 外帧控制码
		outFrame[13] = ConCode.DOWN_REQ;
		// 外帧功能码
		outFrame[14] = FunCode.SEND;

		// 内帧长度
		System.arraycopy(FrameUtil.int2Bytes(inLen), 0, outFrame, 15, 2);
		// 内帧
		System.arraycopy(inFrame, 0, outFrame, 17, inLen);
		// 校验码
		outFrame[len - 2] = FrameUtil.makeChecksum(outFrame);
		// 内帧帧尾
		outFrame[len - 1] = Constant.OUTER_END;
		return outFrame;
	}

	public static void main(String[] args) {
		System.out.println(Hex.bytesToHex(decode("8655330301414300", "control", "030002")));
		
	}
}
