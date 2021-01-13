package com.chengq.chengq.Enums;

public enum ResponseEnums {
    SUCCESS("200", "操作成功"),
    ERROR("600001", "操作失败"),; //尽量报错前面添加项目端口号,后续排查问题方便


    private String respCode;
    private String respMsg;


    ResponseEnums(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public String getCode() {
        return respCode;
    }

    public String getMsg() {
        return respMsg;
    }
}