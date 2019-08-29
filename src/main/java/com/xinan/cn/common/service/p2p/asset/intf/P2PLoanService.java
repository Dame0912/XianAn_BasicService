package com.xinan.cn.common.service.p2p.asset.intf;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;

public interface P2PLoanService {
    /**
     * 获取P2P的简单资产信息用户展示
     *
     * @param skuId
     */
    LoanSimpleInfoVO getLoanSimpleInfo(Long skuId);
}
