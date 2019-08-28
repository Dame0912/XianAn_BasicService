package com.xinan.cn.p2p.report.nifa.bean;

import com.xinan.cn.p2p.report.nifa.constant.SerialNum;

import lombok.Data;

/**
 * 项目状态或信息变更
 */
@Data
public class ProjectChangeInfo {

    @SerialNum(ordinal = 1)
    private String projectNo;

    @SerialNum(ordinal = 2)
    private String platformNo;

    @SerialNum(ordinal = 3)
    private String projectStatus;

    @SerialNum(ordinal = 4)
    private String fundApplicationState;

    @SerialNum(ordinal = 5)
    private String businessAndFinance;

    @SerialNum(ordinal = 6)
    private String repaymentCapacity;

    @SerialNum(ordinal = 7)
    private String overdueSituation;

    @SerialNum(ordinal = 8)
    private String lawsuits;

    @SerialNum(ordinal = 9)
    private String administrativePenalty;

    @SerialNum(ordinal = 10)
    private String valueDate;

    @SerialNum(ordinal = 11)
    private String endRaiseTime;

}
