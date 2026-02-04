package com.xjy.shujia.exception;

import com.xjy.shujia.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public Result handleCustomException(ServiceException serviceException) {
        return Result.error(serviceException.getCode(),serviceException.getMsg());
    }
}
