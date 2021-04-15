package com.chengq.chengq.ulit;

import com.chengq.chengq.Enums.CoreExceptionEnum;
import com.chengq.chengq.ulit.ResponseModel;

import java.io.Serializable;

public class ResponseHelper implements Serializable {
    public static <T> ResponseModel<T> succeed(T model) {
        return succeed(model, CoreExceptionEnum.SUCCESS.getMessage(), CoreExceptionEnum.SUCCESS.getCode());
    }

    public static <T> ResponseModel<T> succeed(T datas, String msg, String code) {
        return new ResponseModel<T>(datas, msg, code);
    }
}
