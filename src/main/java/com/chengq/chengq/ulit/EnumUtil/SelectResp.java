package com.chengq.chengq.ulit.EnumUtil;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "枚举返回对象")
public class SelectResp implements Serializable{
    private String id;
    private String text;
    private String description;
}
