package com.xinan.cn.common.bean;

import org.apache.ibatis.session.RowBounds;
import org.springframework.util.Assert;

import java.io.Serializable;

public class PageParam<T> implements Serializable {
    public static final int MAX_SIZE = 200;
    private static final long serialVersionUID = 2132756100849364983L;
    private int pageNo = 1;
    private int pageSize = 50;
    private T data;

    public PageParam() {
    }

    public static <T> PageParam<T> of(T data) {
        Assert.notNull(data, "参数不能为空");
        PageParam<T> pageParam = new PageParam();
        pageParam.setData(data);
        return pageParam;
    }

    public static long getSerialVersionUID() {
        return 2132756100849364983L;
    }

    public RowBounds getRowBounds() {
        Assert.isTrue(this.getPageSize() <= 200, "page size must be less than 200");
        Assert.isTrue(this.getPageSize() > 0, "page size must be positive");
        Assert.isTrue(this.getPageNo() > 0, "page no must be positive");
        return new RowBounds(this.getPageSize() * (this.getPageNo() - 1), this.getPageSize());
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
