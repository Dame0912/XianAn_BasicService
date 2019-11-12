package com.xinan.cn.p2p.litagation.service.intf;

public interface LawInfoQueryService {

    /**
     * 核验所有未结清借款人的诉讼信息
     *
     * @param time 请求时间
     */
    boolean verifyAllUnclearLawInfo(long time) throws Exception;
}
