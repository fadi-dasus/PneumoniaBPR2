package com.bachelor.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TracingAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(TracingAspect.class);
	
	
	@Before("execution(* getFoldersPath())")
	public void entering(JoinPoint joinPoint) {
		System.out.println("//////////////////////////Hello From Aspect Oriented Programming //////////////////////////////////////////////////");
		LOGGER.info("methodEntered Recieved: " + joinPoint.getStaticPart().getSignature().toLongString());

	}
	

}
