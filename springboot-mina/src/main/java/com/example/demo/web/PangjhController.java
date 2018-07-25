package com.example.demo.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
 
@RestController
public class PangjhController {
	
	@Autowired
	private StringRedisTemplate template;
    
    @RequestMapping("/setValue")
    public String setValue(){
    	if(!template.hasKey("shabao")){
    		template.opsForValue().append("shabao", "我是傻宝");
    		return "使用redis缓存保存数据成功";
    	}else{
    		template.delete("shabao");
    		return "key已存在";
    	}
    }
    
    @RequestMapping("/getValue")
    public String getValue(){
    	
    	if(!template.hasKey("shabao")){
    		return "key不存在，请先保存数据";
    	}else{
    		String shabao = template.opsForValue().get("shabao");//根据key获取缓存中的val 
    		return "获取到缓存中的数据：shabao="+shabao;
    	}
    }
 
}