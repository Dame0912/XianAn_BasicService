package com.xinan.cn.common.bean.entities.fd.core;

import lombok.Data;

@Data
public class FDLoanSkuMapping {
    private int id;
    private long loanId; // 借款id
    private long skuId; // 借款id对应标的id
    private String createdAt;
    private String updatedAt;
    private long deletedAt;//删除时间
}
