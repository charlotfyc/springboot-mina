package com.example.demo.mina;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ct = new ClassPathXmlApplicationContext("config/spring-mvc.xml");
		ct.close();
	}
}