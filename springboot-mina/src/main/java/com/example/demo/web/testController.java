package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.model.UserRepository;


/**
 * 
 * @author fyc 透传数据
 */
@RestController
@RequestMapping(value = "/test", produces = "application/json; charset=utf-8")
public class testController {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 获取设备运行状态
	 * 
	 * @param mac
	 * @param bytes
	 * @return
	 */
	@RequestMapping(value = "test1")
	public User getState(User user) {
		System.out.println("ssssssssssssssssssssssssssssss");
//		return userRepository.findByName(user.getName());
		System.out.println(userRepository.save(user));
		return null;
	}
	@RequestMapping(value = "test2")
	public Object getState2(String name,Pageable pageable) {
		return userRepository.findByName(name, pageable);
	}
	
	@RequestMapping(value = "test3")
	public Object test3(String token,User user) {
		return user;
	}
}
