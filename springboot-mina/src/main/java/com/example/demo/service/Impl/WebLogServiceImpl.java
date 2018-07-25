package com.example.demo.service.Impl;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.sf.json.JSONObject;

@Aspect
@Service
public class WebLogServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(WebLogServiceImpl.class);

	// private static String[] types = { "java.lang.Integer", "java.lang.Double",
	// "java.lang.Float", "java.lang.Long",
	// "java.lang.Short", "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
	// "java.lang.String", "int",
	// "double", "long", "short", "byte", "boolean", "char", "float" };

	@Pointcut("execution(public * com.example.demo.web..*.*(..))")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		String httpMethod = request.getMethod();
		String classMethod = request.getMethod();

		// 记录下请求内容
		// logger.info("URL : " + request.getRequestURL().toString());
		// logger.info("HTTP_METHOD : " + request.getMethod());
		// logger.info("IP : " + request.getRemoteAddr());
		// logger.info("CLASS_METHOD : " +
		// joinPoint.getSignature().getDeclaringTypeName() + "."
		// + joinPoint.getSignature().getName());
		// logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

		String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
		JSONObject json = new JSONObject();
		Object[] paramValues = joinPoint.getArgs();
		for (int i = 0; i < paramNames.length; i++) {
			json.put(paramNames[i], paramValues[i]);
		}
		String param = json.toString();
		logger.error(param);
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		logger.error("RESPONSE : " + JSONObject.fromObject(ret));
	}
}
