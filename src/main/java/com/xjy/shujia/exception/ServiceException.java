package com.xjy.shujia.exception;

import lombok.Getter;

/**
 * 自定制异常类
 */
@Getter
public class ServiceException extends RuntimeException{
    private String code;
    private String msg;

    public ServiceException(String code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
