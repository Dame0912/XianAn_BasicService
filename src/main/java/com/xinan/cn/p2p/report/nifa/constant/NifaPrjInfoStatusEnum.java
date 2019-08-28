package com.xinan.cn.p2p.report.nifa.constant;

/**
 * 
 * <概述>：项目状态
 *
 * @author lyq
 *
 */
public enum NifaPrjInfoStatusEnum {
    IN_PREHEAT("01", "募集前(01)"), 
    IN_SALE("02", "募集中(02)"), 
    IN_REPAY("03", "还款中(03)"), 
    NORMAL_PAY_OFF("04", "正常还款已结清(04)"), 
    OVERDUE_PAY_OFF("05", "逾期还款已结清(05)"), 
    RAISE_FAIL("06", "流标(06)"), 
    PRJ_CANCEL("07", "项目取消(07)"), 
    OVERDUE("08", "逾期未结清(08)");

    public final String prjStatus;

    public final String desc;

    NifaPrjInfoStatusEnum(String prjStatus, String desc) {
        this.prjStatus = prjStatus;
        this.desc = desc;
    }

    public static String getDesc(String prjStatus) {
        for (NifaPrjInfoStatusEnum prjInfoStatusEnum : NifaPrjInfoStatusEnum.values()) {
            if (prjInfoStatusEnum.prjStatus.equals(prjStatus)||(Integer.parseInt(prjInfoStatusEnum.prjStatus))==(Integer.parseInt(prjStatus))) {
                return prjInfoStatusEnum.desc;
            }
        }
        return null;
    }
}
