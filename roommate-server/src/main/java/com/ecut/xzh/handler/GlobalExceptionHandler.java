package com.ecut.xzh.handler;


import com.ecut.xzh.exception.BaseException;
import com.ecut.xzh.exception.ServiceException;
import com.ecut.xzh.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result serviceException(ServiceException e) {
        return Result.error(e.getCode(), e.getMessage());
    }




    /**
     * 用户已存在异常处理
     * @param ex
     * @return
     */
/*    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")) {
            *//*String[] split = message.split(" ");
            String username = split[2];*//*
            String username = message.split(" ")[2];
            String msg = username + MessageConstant.ALREADY_EXITS;
            return Result.error(msg);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    } */


}












