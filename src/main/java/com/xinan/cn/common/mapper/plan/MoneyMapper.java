package com.xinan.cn.common.mapper.plan;

import com.xinan.cn.common.bean.entities.plan.Money;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MoneyMapper {

    List<Money> queryOverdueMoney(@Param("planId") Long planId, @Param("period") Integer period);
    /**
     * 插入数据
     */
    Integer insertOne(Money money);

    /**
     * 删除
     */
    Integer deleteOne(Money money);
}
