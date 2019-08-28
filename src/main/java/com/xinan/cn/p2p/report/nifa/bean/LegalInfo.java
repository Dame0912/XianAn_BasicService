package com.xinan.cn.p2p.report.nifa.bean;

import com.xinan.cn.p2p.report.nifa.constant.SerialNum;

import lombok.Data;

/**
 * 借款人信息（法人/组织）
 */
@Data
public class LegalInfo {

    @SerialNum(ordinal = 1)
    private String projectNo;

    @SerialNum(ordinal = 2)
    private String platformNo;

    @SerialNum(ordinal = 3)
    private String companyName;

    @SerialNum(ordinal = 4)
    private String registeredCapital;

    @SerialNum(ordinal = 5)
    private String registeredAddress;

    @SerialNum(ordinal = 6)
    private String establishedTime;

    @SerialNum(ordinal = 7)
    private String legalRepresentative;

    @SerialNum(ordinal = 8)
    private String corporateCreditInfo;

    @SerialNum(ordinal = 9)
    private String industryInvolved;

    @SerialNum(ordinal = 10)
    private String incomeAndLiabilities;

    @SerialNum(ordinal = 11)
    private String creditReportInfo;

    @SerialNum(ordinal = 12)
    private String otherLoanInfo;

    @SerialNum(ordinal = 13)
    private String shareholderInfo;

    @SerialNum(ordinal = 14)
    private String contributedCapital;

    @SerialNum(ordinal = 15)
    private String officeLocation;

    @SerialNum(ordinal = 16)
    private String businessArea;

    @SerialNum(ordinal = 17)
    private String unifiedSocialCreditCode;

}
