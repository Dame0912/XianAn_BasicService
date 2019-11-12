package com.xinan.cn.common.bean;

import java.io.Serializable;

public class WeiyanException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 702283275358603586L;
    private StatusCodeEnum statusCodeEnum;

    @Deprecated
    public WeiyanException() {
    }

    public WeiyanException(Throwable cause) {
        super(cause);
    }

    public WeiyanException(StatusCodeEnum statusCodeEnum) {
        super(statusCodeEnum.getMsg());
        this.statusCodeEnum = statusCodeEnum;
    }

    public WeiyanException(StatusCodeEnum statusCodeEnum, Throwable cause) {
        super(statusCodeEnum.getMsg(), cause);
        this.statusCodeEnum = statusCodeEnum;
    }

    public WeiyanException(StatusCodeEnum statusCodeEnum, String msg) {
        super(msg);
        this.statusCodeEnum = statusCodeEnum;
    }

    public static WeiyanException getDefault() {
        return new WeiyanException(StatusCodeEnum.UNKNOWN_ERROR);
    }

    public StatusCodeEnum getStatusCodeEnum() {
        return statusCodeEnum;
    }

    public void setStatusCodeEnum(StatusCodeEnum statusCodeEnum) {
        this.statusCodeEnum = statusCodeEnum;
    }
}
