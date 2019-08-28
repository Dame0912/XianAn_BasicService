package com.xinan.cn.p2p.test.repay.bean.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class P2pFinancialLoansBase {
    @Id
    @Column
    private long loanId;
    @Column
    private long loanApplyNo;
    @Column
    private long skuId;
    @Column
    private String loanNumber;
    @Column
    private long loanUserId;
}
