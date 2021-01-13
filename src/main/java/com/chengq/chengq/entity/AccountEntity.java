package com.chengq.chengq.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "account")//指定表名
public class AccountEntity {
//    @TableId(value = "id", type = IdType.AUTO)//指定自增策略
    private Integer id;
//    @TableField(value = "username")
    private String username;
//    @TableField(value = "balance")
    private Double balance;
}
