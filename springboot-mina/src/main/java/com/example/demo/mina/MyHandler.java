package com.example.demo.mina;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.pojo.DevSta;
import com.example.demo.pojo.InFrame;
import com.example.demo.pojo.OutFrame;
import com.he.constant.ConCode;
import com.he.util.Hex;
import com.he.util.RunxinDecodeFrame;

public class MyHandler extends IoHandlerAdapter {

	// @Autowired
	// private DevStaMapper devStaMapper;

	// private final int IDLE = 120;// (单位s)
	// private final Logger LOG = Logger.getLogger(MyHandler.class);
	protected static final Logger LOG = LoggerFactory.getLogger(MyHandler.class);

	public static ConcurrentHashMap<String, IoSession> sessionsConcurrentHashMap = new ConcurrentHashMap<String, IoSession>();

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		if (session.containsAttribute("mac")) {
			String mac = (String) session.getAttribute("mac");
			LOG.error(mac + " 异常 ：" + cause.getMessage());
			cause.printStackTrace();
		}
		session.closeOnFlush();
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		byte[] bytes = (byte[]) message;
		int len = bytes.length;
		// LOG.warn("message ： " + Hex.bytesToHex(bytes));
		// 上报帧
		if (len > 16) {
			OutFrame outFrame = RunxinDecodeFrame.analyseFrame(bytes);
			if (outFrame != null) {// 数据可正常解析且完整
				byte ctrCode = outFrame.getCtrCode();// 控制码
				if (ctrCode == ConCode.UP_REPORT) {// 主动上报 80
					InFrame inFrame = outFrame.getInframe();// 内帧
					byte[] data = inFrame.getData();// 内帧数据域
					// LOG.warn("data ： " + Hex.bytesToHex(data));
					String mac = outFrame.getMac();
					DevSta devSta = null;
					if (session.containsAttribute("devSta")) {
						devSta = (DevSta) session.getAttribute("devSta");
					} else {
						devSta = new DevSta();
						devSta.setMac(mac);
					}
					byte[] byteTem = new byte[3];
					for (int i = 0; i < data.length; i = i + 3) {
						System.arraycopy(data, i, byteTem, 0, 3);
						RunxinDecodeFrame.decode(byteTem, devSta);
					}
					session.setAttribute("devSta", devSta);
					session.setAttribute("mac", mac);
					sessionsConcurrentHashMap.put(mac, session);
				} else if (ctrCode == ConCode.UP_REP) {// 上行回应 C0
					session.setAttribute("back", outFrame);
				}
			}
		} else {// 注册帧
			if (len == 11 && bytes[0] == 0x5A && bytes[len - 1] == 0xFFFFFFA5) {
				String mac = Hex.bytesToHex(bytes, 1, len - 2);
				mac = mac.substring(0, mac.length() - 1);
				session.setAttribute("mac", mac);
				session.setAttribute("register", bytes);
				sessionsConcurrentHashMap.put(mac, session);
				InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
				LOG.error("mac " + mac + " ip : " + address.getHostString());
			}
		}
		byte[] register = (byte[]) session.getAttribute("register");
		if (register != null) {
			session.write(register);
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		byte[] bytes = (byte[]) message;
		String data = Hex.bytesToHex(bytes);
		if (session.containsAttribute("mac")) {
			String mac = (String) session.getAttribute("mac");
			 LOG.warn("向 " + mac + " 发送数据 : " + data);
		} else {
			InetSocketAddress address = (InetSocketAddress) session.getRemoteAddress();
			 LOG.warn("向 " + address.getHostString() + " : " + address.getPort() + " 发送数据: " + data);
		}
		 LOG.warn("发送数据 : " + data);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// session.getConfig().setIdleTime(IdleStatus.READER_IDLE, IDLE);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		if (session.containsAttribute("mac")) {
			String mac = (String) session.getAttribute("mac");
			IoSession mapSession = sessionsConcurrentHashMap.get(mac);
			if (session == mapSession) {
				sessionsConcurrentHashMap.remove(mac);
				LOG.warn(mac + " 离线");
			}
		}
		session.closeOnFlush();
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// 空闲时即会调用KeepAliveMessageFactory里的getRequest方法
		if (session.containsAttribute("mac")) {
			LOG.warn(session.getAttribute("mac") + " 闲置");
		}
		session.closeOnFlush();
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}
}