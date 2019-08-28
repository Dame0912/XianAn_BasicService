package com.xinan.cn.p2p.report.nifa.bean;

import com.xinan.cn.p2p.report.nifa.constant.SerialNum;

import lombok.Data;

/**
 * 项目信息
 */
@Data
public class ProjectInfo {

    @SerialNum(ordinal = 1)
    private String projectName;

    @SerialNum(ordinal = 2)
    private String projectNo;

    @SerialNum(ordinal = 3)
    private String platformNo;

    @SerialNum(ordinal = 4)
    private String projectDesc;

    @SerialNum(ordinal = 5)
    private String projectUrl;

    @SerialNum(ordinal = 6)
    private String borrowUse;

    @SerialNum(ordinal = 7)
    private String borrowAmount;

    @SerialNum(ordinal = 8)
    private String borrowPeriodUnit;

    @SerialNum(ordinal = 9)
    private String period;

    @SerialNum(ordinal = 10)
    private String borrowRate;

    @SerialNum(ordinal = 11)
    private String valueDate;

    @SerialNum(ordinal = 12)
    private String repayType;

    @SerialNum(ordinal = 13)
    private String repayTypeDesc;

    @SerialNum(ordinal = 14)
    private String projectStatus;

    @SerialNum(ordinal = 15)
    private String startRaiseTime;

    @SerialNum(ordinal = 16)
    private String repaymentGuarantee;

    @SerialNum(ordinal = 17)
    private String paymentSource;

    @SerialNum(ordinal = 18)
    private String riskEvaluation;

    @SerialNum(ordinal = 19)
    private String otherFee;

    @SerialNum(ordinal = 20)
    private String contractTemplateNo;

    @SerialNum(ordinal = 21)
    private String lenderTips;

    @SerialNum(ordinal = 22)
    private String borrowerType;

}
