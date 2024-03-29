package com.chengq.businessmodule.model.base;


import com.chengq.Enums.CoreExceptionEnum;

import java.io.Serializable;

public class ResponseHelper implements Serializable {
    public static <T> ResponseModel<T> succeed(T model) {
        return succeed(model, CoreExceptionEnum.SUCCESS.getMessage(), CoreExceptionEnum.SUCCESS.getCode());
    }

    public static <T> ResponseModel<T> succeed(T datas, String msg, String code) {
        return new ResponseModel<T>(datas, msg, code);
    }
}
