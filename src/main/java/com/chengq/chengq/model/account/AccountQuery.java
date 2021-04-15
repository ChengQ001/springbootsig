package com.chengq.chengq.model.account;

import com.chengq.chengq.ulit.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountQuery extends PageQuery {

    @ApiModelProperty(value = "名字", example = "zhiling")
    private String username;
}
