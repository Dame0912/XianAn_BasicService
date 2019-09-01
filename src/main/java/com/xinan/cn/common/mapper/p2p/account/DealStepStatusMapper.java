package com.xinan.cn.common.mapper.p2p.account;

import com.xinan.cn.common.bean.entities.p2p.account.DealStepStatus;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DealStepStatusMapper extends Mapper<DealStepStatus> {


    /**
     * 查询对账步骤表
     */
    List<DealStepStatus> selectAllBySkuId(Long skuId);

}