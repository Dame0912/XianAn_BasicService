package com.xinan.cn.p2p.litagation.service.intf;

import com.xinan.cn.p2p.litagation.bean.dto.LawApiQueryRequest;
import com.xinan.cn.p2p.litagation.bean.dto.LawApiQueryResponse;

public interface LawInfoQueryService {

    /**
     * 核验所有未结清借款人的诉讼信息
     *
     * @param time 请求时间
     */
    boolean verifyAllUnclearLawInfo(long time);

    /**
     * 根据借款人信息查询其诉讼信息
     *
     * @param lawApiQueryRequest 请求
     * @return LawApiQueryResponse 响应
     */
    LawApiQueryResponse queryLoanerCase(LawApiQueryRequest lawApiQueryRequest);
}
