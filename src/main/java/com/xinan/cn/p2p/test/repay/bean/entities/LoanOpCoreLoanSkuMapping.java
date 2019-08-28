package com.xinan.cn.p2p.test.repay.bean.entities;

import lombok.Data;

@Data
public class LoanOpCoreLoanSkuMapping {
    private int id;
    private long loanId; // 借款id
    private long skuId; // 借款id对应标的id
    private String createdAt;
    private String updatedAt;
    private long deletedAt;//删除时间
}
