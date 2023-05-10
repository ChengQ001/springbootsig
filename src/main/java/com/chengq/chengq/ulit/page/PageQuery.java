package com.chengq.chengq.ulit.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PageQuery implements Serializable {
    @ApiModelProperty(value = "每页的条数", example = "10")
    @NotNull(message = "分页页码数不能为空")
    public Long pageSize = 10L;

    @ApiModelProperty(value = "页编码(第几页)", example = "1")
    @NotNull(message = "分页码不能为空")
    public Long pageIndex = 1L;


    public PageQuery() {
    }

    public PageQuery(Long pageSize, Long currentPage) {
        this.pageSize = pageSize;
        this.pageIndex = currentPage;
    }
}
