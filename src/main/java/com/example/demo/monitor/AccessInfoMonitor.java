package com.example.demo.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @date
 */
@Component
@Aspect
public class AccessInfoMonitor {

    private final static Logger logger= LoggerFactory.getLogger(AccessInfoMonitor.class);

    long startTime;


    @Pointcut("execution(public * com.example.demo.controller.*.*(..))")
    public void accessInfo(){};

    @Before("accessInfo()")
    public void before(JoinPoint joinPoint){
        startTime=System.currentTimeMillis();
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String  methodName = joinPoint.getSignature().getName();
        MethodSignature methodSignature= (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        ServletRequestAttributes requestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //URL
        String requestURL = request.getRequestURL().toString();
        //Mtehod
        String method = request.getMethod();
        //IP
        String remoteAddr = request.getRemoteAddr();
        logger.info("请求URL："+requestURL);
        logger.info("请求Method:"+method);
        logger.info("请求IP："+remoteAddr);
        logger.info("请求参数：");
        for(int i=0;i<parameterNames.length;i++){
            logger.info(parameterNames[i]+":"+args[i]);
        }
    }

    @AfterReturning(pointcut = "accessInfo()" ,returning = "ret")

    public void afterReturning(Object ret) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();

        logger.info("请求返回信息："+objectMapper.writeValueAsString(ret));
        logger.info("请求耗时："+(System.currentTimeMillis()-startTime)+"ms");
        logger.info("..................................................................................................");

    }


    @Around("accessInfo()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object o=null;
        logger.info("..................................................................................................");
        o = proceedingJoinPoint.proceed();
        return o;

    }
}
