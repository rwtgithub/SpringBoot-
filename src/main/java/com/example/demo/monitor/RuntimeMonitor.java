package com.example.demo.monitor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date
 */
@Component
@Aspect
public class RuntimeMonitor {

    private final static Logger logger= LoggerFactory.getLogger(RuntimeMonitor.class);

    @Pointcut("execution(public * com.example.demo.service.*.*(..))")
    public void exe(){};

    @Around("exe()")

    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object o= null;
        long startTime=System.currentTimeMillis();
        o=proceedingJoinPoint.proceed();

        logger.info("类名："+proceedingJoinPoint.getSignature().getDeclaringTypeName());
        logger.info("方法名"+proceedingJoinPoint.getSignature().getName());
        logger.info("执行时间："+(System.currentTimeMillis()-startTime)+"ms");

        return  o;


    }
}
