package com.xinan.cn.bean;

import lombok.Data;

@Data
public class Page {
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 当前页码
     */
    private Integer currentPage;

    private Integer begin;

    private Integer end;

    public Integer getBegin() {
        return (this.currentPage - 1) * this.pageSize;
    }

    public Integer getEnd() {
        return pageSize;
    }
}
