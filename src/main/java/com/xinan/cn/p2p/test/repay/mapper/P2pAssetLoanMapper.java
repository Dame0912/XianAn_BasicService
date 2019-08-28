package com.xinan.cn.p2p.test.repay.mapper;

import com.xinan.cn.p2p.test.repay.bean.entities.P2pAssetLoan;

public interface P2pAssetLoanMapper {

    P2pAssetLoan getByLoanId(Long loanId);
}
