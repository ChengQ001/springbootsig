package com.chengq.chengq.tools;

import com.chengq.chengq.Enums.ResponseEnums;

import java.io.Serializable;

public class ResponseHelper implements Serializable {


    public static <T> ResponseModel<T> succeed(T model, String msg) {
        return succeed(model, ResponseEnums.SUCCESS.getCode() , msg);
    }

    public static <T> ResponseModel<T> succeed(T model) {
        return succeed(model, ResponseEnums.SUCCESS.getCode(), ResponseEnums.SUCCESS.getMsg());
    }

    public static <T> ResponseModel<T> succeed(T datas, String code, String msg) {
        return new ResponseModel<T>(datas, code, msg);
    }

    public static <T> ResponseModel<T> failed2Message(String msg) {
        return failedWith(null, ResponseEnums.ERROR.getCode(), msg);
    }

    public static <T> ResponseModel<T> failed(T model, String msg) {
        return failedWith(model, ResponseEnums.ERROR.getCode(), msg);
    }
    public static <T> ResponseModel<T> failed(T model) {
        return failedWith(model, ResponseEnums.ERROR.getCode(), ResponseEnums.ERROR.getMsg());
    }


    public static <T> ResponseModel<T> failedWith(T datas, String code, String msg) {
        return new ResponseModel<>(datas, code, msg);
    }

}
