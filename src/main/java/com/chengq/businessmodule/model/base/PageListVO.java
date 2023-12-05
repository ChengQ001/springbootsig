package com.chengq.businessmodule.model.base;

import lombok.Data;

import java.util.List;

@Data
public class PageListVO<T> {
    private List<T> list;
    PaginationVO pagination;

    public PageListVO(){

    }

    public PageListVO(List<T> list, long currentPage, long pageSize, long total) {
        this.list = list;
        this.pagination = new PaginationVO(currentPage, pageSize, total);
    }
}
