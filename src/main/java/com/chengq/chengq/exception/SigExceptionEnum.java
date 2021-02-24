package com.chengq.chengq.exception;


import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 鉴权相关的错误异常
 *
 * @author kangxinghua
 * @date 2018-08-26-下午3:19
 */
public enum SigExceptionEnum implements AbstractBaseExceptionEnum {


    ERROR(60000, "异常"),ERROR_TOKEN(60001, "token非法"),

    ;


    private int code;
    private String message;

    SigExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
