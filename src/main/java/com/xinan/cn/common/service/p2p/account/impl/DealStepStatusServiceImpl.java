package com.xinan.cn.common.service.p2p.account.impl;

import com.xinan.cn.common.bean.entities.p2p.account.DealStepStatus;
import com.xinan.cn.common.mapper.p2p.account.DealStepStatusMapper;
import com.xinan.cn.common.service.p2p.account.intf.DealStepStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 对账步骤
 *
 * @Author lyq
 **/
@Service
public class DealStepStatusServiceImpl implements DealStepStatusService {
    @Autowired
    private DealStepStatusMapper dealStepStatusMapper;

    @Override
    public List<DealStepStatus> getDealStepStatusInfoBySkuId(Long skuId) {
        return dealStepStatusMapper.selectAllBySkuId(skuId);
    }

}
