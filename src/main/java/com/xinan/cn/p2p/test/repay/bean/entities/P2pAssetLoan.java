package com.xinan.cn.p2p.test.repay.bean.entities;

import lombok.Data;

import java.util.Date;
@Data
public class P2pAssetLoan {
    private Integer id;
    private Long loanId;
    private String projectNo;
    private Long planId;
    private Long loanUserId;
    private Integer loanUserType;
    private String loanName;
    private Integer loanType;
    private Integer loanStatus;
    private String statusName;
    private Long loanAmount;
    private Long lendAmount;
    private Long raiseFullTime;
    private Long loanYearRate;
    private Integer duration;
    private Integer durationType;
    private Integer repayType;
    private Long lendTime;
    private Long guaranteeActId;
    private Long claimActId;
    private Long platActId;
    private Date createdAt;
    private Date updatedAt;
}
