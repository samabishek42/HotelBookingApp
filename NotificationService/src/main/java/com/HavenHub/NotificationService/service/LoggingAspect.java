package com.HavenHub.NotificationService.service;

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
      @Around("execution(* com.HavenHub.NotificationService.controller.NotificationController.*(..))")
      public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
            long start = System.currentTimeMillis();

            try {
                  Object proceed = joinPoint.proceed();  // Proceed with method execution
                  long executionTime = System.currentTimeMillis() - start;
                  logger.info("Executed {} in {} ms", joinPoint.getSignature(), executionTime);
                  return proceed;
            } catch (Throwable e) {
                  logger.error("Exception in {}: {}", joinPoint.getSignature(), e.getMessage());
                  throw e; // Re-throw the exception after logging
            }
      }

}
