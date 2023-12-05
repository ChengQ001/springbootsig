package com.chengq.businessmodule.model.account;

import cn.hutool.core.util.EnumUtil;
import com.chengq.Enums.ChengQEnums;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
public class AccountResp implements Serializable {


    private Integer id;

    private String username;

    private Double balance;


    private Integer status;

    public String getStatusDesc() {
        return Optional.ofNullable(EnumUtil.likeValueOf(ChengQEnums.StatusEnum.class,
                status)).map(x -> x.toString()).orElse("");
    }
}
