package com.xinan.cn.common.bean;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 6817540581567602754L;
    private long total;
    private List<T> data;

    public PageResult() {
    }

    public static <T> PageResult<T> of(long total, List<T> data) {
        PageResult<T> pageResult = new PageResult();
        pageResult.setData(data);
        pageResult.setTotal(total);
        return pageResult;
    }

    public static long getSerialVersionUID() {
        return 6817540581567602754L;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("total", this.total).add("data", this.data).toString();
    }
}

