package com.xinan.cn.p2p.test.repay.bean.entities;

import lombok.Data;

import java.util.Date;

@Data
public class LoanOpAssetLoan {
    private long id;
    /**
     * 借款id
     */
    private long loanId;
    /**
     * 第三方放款用编号
     */
    private String loanApplyNo;
    /**
     * 贷款业务号
     */
    private String loanBizNo;
    /**
     * 信托合同编号
     */
    private String contractNo;
    private String applyId;
    private String loanUserId;
    private long relatedLoanId;
    private int raiseMode;
    private long mobile;
    private String moneyPlanId;
    private String rateConfigId;
    private String projectId;
    private String assetSideId;
    private String moneySideId;
    private String creditSideId;
    private int packMode;
    private int loanType;
    private int productType;
    private long loanAmount;
    private long arrivalMoney;
    private String title;
    private String description;
    private int stage;
    private int periods;
    private int periodsType;
    private int interestType;
    private int status;
    private int repayType;
    private long finalRepayDate;
    private long maxRepayDate;
    private int fixedRepayDate;
    private long lendTime;
    private int overdueGracePeriod;
    private int transitDays;
    private String skuTitle;
    private int skuSkuRate;
    private long canRepayOffLine;
    private long serviceFeeSingle;
    private long channelFeeSingle;
    private long insuranceFeeSingle;
    private int skuBondsType;
    private int skuPlatformDiscountType;
    private long skuStartRaiseTime;
    private long skuEndRaiseTime;
    private long skuMinInvestAmount;
    private String insuranceNo;
    private int insureType;
    private Date createdAt;
    private Date updatedAt;
    private String moneySideCode;
    private long planId;//计划id
    private String projectNo;//项目编号
}
