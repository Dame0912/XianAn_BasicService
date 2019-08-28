package com.xinan.cn.p2p.test.repay.mapper;

import com.xinan.cn.p2p.test.repay.bean.entities.LoanOpCoreLoanSkuMapping;

public interface LoanOpCoreLoanSkuMapper {
    LoanOpCoreLoanSkuMapping getByLoanId(Long loanId);
}
