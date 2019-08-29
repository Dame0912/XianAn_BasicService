package com.xinan.cn.common.mapper.p2p.financial;

import com.xinan.cn.common.bean.entities.p2p.financial.P2PLoansBase;
import tk.mybatis.mapper.common.Mapper;

public interface P2PLoansBaseMapper extends Mapper<P2PLoansBase> {

    P2PLoansBase getBySkuId(Long skuId);
}
