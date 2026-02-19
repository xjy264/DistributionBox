package com.xjy.shujia.exception;

import com.xjy.shujia.common.Constants;
import com.xjy.shujia.common.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public Result handleCustomException(ServiceException serviceException, HttpServletResponse response) {
        if (Constants.CODE_403.equals(serviceException.getCode())) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        } else if (Constants.CODE_401.equals(serviceException.getCode())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return Result.error(serviceException.getCode(),serviceException.getMsg());
    }
}
