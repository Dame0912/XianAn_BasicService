package com.xinan.cn.common.mapper.p2p.asset;

import com.xinan.cn.common.bean.entities.p2p.asset.P2PLoan;

public interface P2PLoanMapper {

    P2PLoan getByLoanId(Long loanId);
}
