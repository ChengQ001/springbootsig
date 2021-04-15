package com.chengq.chengq.Enums;

public enum CoreExceptionEnum implements AbstractBaseExceptionEnum {

    SUCCESS("200","成功"),


    ALI_OSS_ERROR("10000011", "删除失败，该分类还有已关联的客户！"),;


    private String code;
    private String message;

    CoreExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
