package com.chengq.chengq.exception;

public interface AbstractBaseExceptionEnum {
    /**
     * 获取异常的状态码
     * @return
     */
    String getCode();

    /**
     * 获取异常的提示信息
     */
    String getMessage();
}
