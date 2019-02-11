package com.example.demo.ExceptionHandle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @date
 */
@Component
@ControllerAdvice
public class ServerExceptionHandler {
    private final static Logger logger= LoggerFactory.getLogger(ServerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object cxustomerHandler(Exception e){
        logger.info("...........................................................");
        logger.info("统一异常处理");
        logger.info("...........................................................");
        return "服务运行异常";
    }

}
