package com.xinan.cn.common.mapper.fd.core;

import com.xinan.cn.common.bean.entities.fd.core.FDLoanSkuMapping;

public interface FDLoanSkuMapper {
    FDLoanSkuMapping getByLoanId(Long loanId);
}
