package com.chengq.chengq.model.account;

import cn.stylefeng.roses.core.page.PageQuery;
import cn.stylefeng.roses.core.util.ToolUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AccountPageReq查询对象", description = "用户对象")
public class AccountPageReq extends PageQuery {
    @ApiModelProperty(value = "名字", example = "1")
    private String username;

    @Override
    public String checkParam() {
        String checkResult = "";
        if (ToolUtil.isEmpty(username)) {
            checkResult += "【名字不能为空】";
        }
        return checkResult;
    }
}
