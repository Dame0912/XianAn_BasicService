package com.xinan.cn.p2p.test.repay.mapper;

import com.xinan.cn.p2p.test.repay.bean.entities.CorePlanMoney;

public interface CorePlanMoneyMapper {
    /**
     * 插入数据
     */
    Integer insertOne(CorePlanMoney money);

    /**
     * 删除
     */
    Integer deleteOne();
}
