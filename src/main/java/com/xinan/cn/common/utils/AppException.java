package com.xinan.cn.common.utils;

/**
 * 自定义业务异常
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = -926779998461056212L;
    /**
     * 错误码
     */
    protected String errorCode;
    /**
     * 错误额外信息。例如：用户存放，成功、失败、异常这3个状态枚举
     */
    protected String tradeMassage;

    public AppException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public AppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode 错误码
     * @param cause     错误异常
     */
    public AppException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode 错误码
     * @param message   错误信息
     * @param cause     错误异常
     */
    public AppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode    错误码
     * @param message      错误信息
     * @param tradeMassage 错误额外信息
     */
    public AppException(String errorCode, String message, String tradeMassage) {
        super(message);
        this.errorCode = errorCode;
        this.tradeMassage = tradeMassage;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getTradeMassage() {
        return this.tradeMassage;
    }

    public void setTradeMassage(String tradeMassage) {
        this.tradeMassage = tradeMassage;
    }
}