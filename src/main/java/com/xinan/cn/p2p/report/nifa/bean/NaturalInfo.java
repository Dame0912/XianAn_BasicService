package com.xinan.cn.p2p.report.nifa.bean;

import com.xinan.cn.p2p.report.nifa.constant.SerialNum;

import lombok.Data;

/**
 * 借款人信息（自然人）
 */
@Data
public class NaturalInfo {

    @SerialNum(ordinal = 1)
	private String projectNo;

    @SerialNum(ordinal = 2)
    private String platformNo;

    @SerialNum(ordinal = 3)
    private String borrowerName;

    @SerialNum(ordinal = 4)
    private String documentType;

    @SerialNum(ordinal = 5)
    private String idNo;

    @SerialNum(ordinal = 6)
    private String jobNature;

    @SerialNum(ordinal = 7)
    private String otherLoanInfo;

    @SerialNum(ordinal = 8)
    private String creditReportInfo;

    @SerialNum(ordinal = 9)
    private String overdueNum;

    @SerialNum(ordinal = 10)
    private String overdueAmount;

    @SerialNum(ordinal = 11)
    private String incomeAndLiabilities;

}
