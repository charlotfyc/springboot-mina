package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/*
 * springboot 启动类
 * 定时任务加注解@EnableScheduling
 */
@EnableScheduling  
@SpringBootApplication
public class SpringbootStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootStartApplication.class, args);
	}
}
