package com.example.demo.service.Impl;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class TimeServiceImpl {
	
	@Scheduled(cron = "0 * * * * ?")
	public void test() {
		System.out.println(new Date());
	}
}
