package com.xinan.cn.common.bean.entities.p2p.financial;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class P2PLoansBase {
    private int id;
    private long loanUserId;
    private String loanUserName;
    private long loanId;
    private long loanApplyNo;
    private long skuId;
    private String loanNumber;
    private String loanName;
    private String skuName;
    private int loanType;
    private int loanStatus;
}
