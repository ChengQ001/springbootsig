package com.chengq.businessmodule.model.account;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;


@Data
public class UserReq implements Serializable {


    @NotBlank(message = "用户名字不能为空")
    private String username;
    @NotBlank(message = "用户密码不能为空")
    private Double password;



}
