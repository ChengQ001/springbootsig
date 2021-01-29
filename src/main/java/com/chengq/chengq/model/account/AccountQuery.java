package com.chengq.chengq.model.account;

import cn.stylefeng.roses.core.page.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AccountQuery查询对象", description = "用户对象")
public class AccountQuery extends PageQuery {

    @ApiModelProperty(value = "用户", example = "1")
    private String  like_username;
}
