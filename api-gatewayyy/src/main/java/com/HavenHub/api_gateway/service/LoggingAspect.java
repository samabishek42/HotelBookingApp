package com.HavenHub.api_gateway.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

      private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

      // Intercepting all methods in HotelController
      @Around("execution(* com.HavenHub.api_gateway.controller.HotelController.*.*(..))")
      public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
            // Log before method starts
            logger.info("Method {} is about to start.", joinPoint.getSignature());

            // Proceed with the method execution
            Object proceed = joinPoint.proceed();

            // Log after method ends
            logger.info("Method {} has finished executing.", joinPoint.getSignature());

            return proceed;
      }
}




