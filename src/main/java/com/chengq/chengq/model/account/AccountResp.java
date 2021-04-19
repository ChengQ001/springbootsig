package com.chengq.chengq.model.account;

import cn.hutool.core.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chengq.chengq.Enums.Enums;
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
        return Optional.ofNullable(EnumUtil.likeValueOf(Enums.StatusEnum.class,
                status)).map(x -> x.toString()).orElse("");
    }
}
