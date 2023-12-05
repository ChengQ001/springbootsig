package com.chengq.businessmodule.model.base;

import lombok.Data;

@Data
public class PaginationVO {
    private long currentPage;
    private long pageSize;
    private int total;

    public PaginationVO(long currentPage, long pageSize, long total) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = (int) total;
    }
}
