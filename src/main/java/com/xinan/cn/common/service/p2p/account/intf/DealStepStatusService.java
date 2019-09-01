package com.xinan.cn.common.service.p2p.account.intf;

import com.xinan.cn.common.bean.entities.p2p.account.DealStepStatus;

import java.util.List;

public interface DealStepStatusService {

    List<DealStepStatus> getDealStepStatusInfoBySkuId(Long skuId);
}
