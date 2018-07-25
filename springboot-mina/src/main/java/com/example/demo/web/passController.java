package com.example.demo.web;

import org.apache.mina.core.session.IoSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.mina.MyHandler;
import com.example.demo.pojo.DevSta;
import com.he.util.EncodeFrame;

import net.sf.json.JSONObject;


/**
 * 
 * @author fyc 透传数据
 */
@RestController
@RequestMapping(value = "/pass", produces = "application/json; charset=utf-8")
public class passController {
	
	/**
	 * 获取设备运行状态
	 * 
	 * @param mac
	 * @param bytes
	 * @return
	 */
	@RequestMapping(value = "getState")
	public JSONObject getState(String mac) {
		JSONObject json = new JSONObject();
		if (mac != null && MyHandler.sessionsConcurrentHashMap.containsKey(mac)) {
			try {
				IoSession session = MyHandler.sessionsConcurrentHashMap.get(mac);
				if (session.containsAttribute("devSta")) {
					json.put("resCode", 0);
					json.put("devSta", session.getAttribute("devSta"));
				} else {
					json.put("resCode", 1);
					json.put("errMsg", "N0_MSG");
				}
			} catch (Exception e) {
//				LOG.error(mac + " getState 异常 ： " + e.getMessage());
			}
		} else {
			json.put("resCode", 2);
			json.put("errMsg", "N0_ONLINE");
		}
		return json;
	}

	/**
	 * 控制设备
	 * 
	 * @param mac
	 *            设备mac
	 * @param data
	 *            数据域
	 * @return
	 */
	@RequestMapping(value = "control")
	public JSONObject control(String mac, String data) {
//		LOG.error("mac : " + mac + "========data : " + data);
		JSONObject json = new JSONObject();
		if (mac != null && MyHandler.sessionsConcurrentHashMap.containsKey(mac)) {
			byte[] bytes = EncodeFrame.decode(mac + "0", "control", data);
			IoSession session = MyHandler.sessionsConcurrentHashMap.get(mac);
			session.write(bytes);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
//				LOG.error(mac + " control 异常 ： " + e.getMessage());
			}
			if (session.containsAttribute("devSta")) {
				DevSta devSta = (DevSta) session.getAttribute("devSta");
				json.put("devSta", devSta);
				json.put("resCode", 0);
			} else {
				json.put("resCode", 1);
				json.put("errMsg", "N0_MSG");
			}
		} else {
			json.put("errMsg", "N0_ONLINE");
			json.put("resCode", 2);
		}
		return json;
	}
}
