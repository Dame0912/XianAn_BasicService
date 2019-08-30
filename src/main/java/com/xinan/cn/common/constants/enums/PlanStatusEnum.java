package com.xinan.cn.common.constants.enums;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public enum PlanStatusEnum {
    WAIT(0, "等待还款"),
    FAILED(10, "还款失败"),
    SYSTEM_FAILED(14, "系统还款失败(不计罚息)"),
    REPAY_PROCESSING(20, "还款处理中"),
    SYSTEM_REPAY_PROCESSING(21, "系统还款处理中(不计罚息)"),
    SUCCESS(30, "还款成功"),
    PART_SUCCESS(32, "部分还款成功"),
    ACCELERATE(51, "已加速到期"),
    PRE(52, "已提前还款"),
    ADJUST_DONE(53, "已债转");

    private static final Set<Integer> PROCESSING_SET = ImmutableSet.of(REPAY_PROCESSING.getCode(), SYSTEM_REPAY_PROCESSING.code);
    private static final Set<Integer> CLEARED_SET = ImmutableSet.of(SUCCESS.getCode(), ACCELERATE.getCode(), PRE.getCode());
    private static final Set<Integer> EXCLUDE_SET = ImmutableSet.of(ACCELERATE.getCode(), PRE.getCode(), ADJUST_DONE.getCode());
    private int code;
    private String msg;

    PlanStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static boolean isClear(int code) {
        return CLEARED_SET.contains(code);
    }

    public static boolean isExclude(int code) {
        return EXCLUDE_SET.contains(code);
    }

    public static boolean isProcessing(int code) {
        return PROCESSING_SET.contains(code);
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
