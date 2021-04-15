package com.chengq.chengq.ulit;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageQuery implements Serializable {
    @ApiModelProperty(
            value = "每页的条数",
            example = "10"
    )
    public Long pageSize = 10L;

    @ApiModelProperty(
            value = "页编码(第几页)",
            example = "1"
    )
    public Long currentPage = 1L;


    public PageQuery() {
    }

    public PageQuery(Long pageSize, Long currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }
}
