package com.example.demo.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.he.util.Hex;

public class MyKeepAliveMessageFactory implements KeepAliveMessageFactory {

//	private final Logger LOG = Logger.getLogger(MyKeepAliveMessageFactory.class);

	/** 心跳包内容 */
	// private static final byte[] HEARTBEATREQUEST = {0x5A,0x5A};
	// private static final byte[] HEARTBEATRESPONSE = {0x5A,0x5A};

	@Override
	public boolean isRequest(IoSession session, Object message) {
		if (message instanceof String) {
			return false;
		} else {
			byte[] bytes = (byte[]) message;
			if (bytes.length == 9 && bytes[0] == 0x5A && bytes[8] == (byte) 0xA5) {
				// if (!session.containsAttribute("mac")) {
				// String mac = Hex.bytesToHex(bytes, 1, 7);
				// session.setAttribute("mac", mac);
				// MyHandler.sessionsConcurrentHashMap.put(mac, session);
				// LOG.warn(mac + " 上线");
				// } else {
				// LOG.warn(session.getAttribute("mac") + " 心跳");
				// }
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public boolean isResponse(IoSession session, Object message) {
		// LOG.warn("响应心跳包信息: " + message);
		// 被动心跳直接返回false
		return false;
	}

	@Override
	public Object getRequest(IoSession session) {
		// LOG.warn("请求预设信息: " + HEARTBEATREQUEST);
		// return HEARTBEATREQUEST;
		// byte[] bytes = { 0x5A, 0 };
		return null;
	}

	@Override
	public Object getResponse(IoSession session, Object request) {
		// LOG.warn("响应预设信息: " + HEARTBEATRESPONSE);
		// /** 返回预设语句 */
		// 返回相同的心跳
		byte[] bytes = (byte[]) request;
		if (!session.containsAttribute("mac")) {
			String mac = Hex.bytesToHex(bytes, 1, 7);
			session.setAttribute("mac", mac);
			MyHandler.sessionsConcurrentHashMap.put(mac, session);
//			LOG.warn(mac + " 上线");
		} else {
//			LOG.warn(session.getAttribute("mac") + " 心跳");
		}
		return bytes;
	}
}