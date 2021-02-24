package com.chengq.chengq.exception;

import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

public class SigException extends ServiceException {

    public SigException(AbstractBaseExceptionEnum exception) {
        super(exception);
    }

    public SigException(Integer code, String errorMessage) {
        super(code, errorMessage);
    }

    @Override
    public Throwable fillInStackTrace() {
        //覆写fillInStackTrace()，防止出现性能低效，不记录堆栈信息。
        return this;
    }

}
