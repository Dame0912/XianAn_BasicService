package com.xinan.cn.common.mapper.plan;

import com.xinan.cn.common.bean.entities.plan.Money;

public interface MoneyMapper {
    /**
     * 插入数据
     */
    Integer insertOne(Money money);

    /**
     * 删除
     */
    Integer deleteOne();
}
