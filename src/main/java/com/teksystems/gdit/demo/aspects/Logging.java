package com.teksystems.gdit.demo.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {

  // Logging aspect for Controller classes
  @Pointcut("execution(public * com.teksystems.gdit.demo.controller.*Controller.*(..))")
  public void controllersPointcut() {}

  @Around(value = "controllersPointcut()")
  public Object controllersAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    return getObject(joinPoint);
  }

  // Logging aspect for Service classes
  @Pointcut("execution(public * com.teksystems.gdit.demo.service.*Service.*(..))")
  public void servicesPointcut() {}

  @Around(value = "servicesPointcut()")
  public Object servicesAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    return getObject(joinPoint);
  }

  // Logging aspect for Client classes
  @Pointcut("execution(public * com.teksystems.gdit.demo.client.*Client.*(..))")
  public void clientsPointcut() {}

  @Around(value = "clientsPointcut()")
  public Object clientsAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    return getObject(joinPoint);
  }

  /**
   * Private method that will be called every time a method is intercepted @Before (before being moved to
   * memory stack) and @After (right after proceeding action) executions.
   */
  private Object getObject(ProceedingJoinPoint joinPoint) throws Throwable {
    Logger log = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    log.info("START: {} {}()", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());
    Object result = joinPoint.proceed();
    log.info("END: {} {}()", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());
    return result;
  }
}
