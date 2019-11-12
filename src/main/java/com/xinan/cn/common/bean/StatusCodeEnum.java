package com.xinan.cn.common.bean;


public enum StatusCodeEnum {
    ok(0, "正常"),
    DEFAULT_ERROR(-1, "系统繁忙，此时请开发者稍候再试"),
    CORE_ILLEGAL_ARGUMENT_ERROR(42001, "参数异常"),
    CORE_ILLEGAL_SKU_NO_ERROR(42002, "标的不存在"),
    CORE_ILLEGAL_SKU_STATUS_ERROR(42003, "该状态下的标的不允许投资"),
    CORE_ILLEGAL_MIN_INVEST_AMOUNT_ERROR(42004, "投资金额不能小于起投金额"),
    CORE_ILLEGAL_A_SKU_AMOUNT_ERROR(42005, "投资金额大于剩余可投金额"),
    UNKNOWN_ERROR(40002, "未知错误"),
    ASSET_ILLEGAL_ARGUMENT_ERROR(32001, "参数异常"),
    UNKNOWN_OTHER_FEE_TYPE(32002, "未知的其他费类型"),
    UNKNOWN_REPAY_TYPE(32003, "未知的还款类型"),
    CANNOT_PREPAY(32004, "不允许提前还款"),
    INSUFFICIENT_BALANCE(32005, "余额不足"),
    DATA_CANCEL(400, "数据作废"),
    CANNOT_REPAY(32004, "不允许还款"),;

    private int code;
    private String msg;

    StatusCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
