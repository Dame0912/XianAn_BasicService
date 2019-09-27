package com.xinan.cn.common.mapper.p2p.account;

import com.xinan.cn.common.bean.entities.p2p.account.AccountFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountFlowMapper {

    List<AccountFlow> selectAll();

    AccountFlow selectByPrimaryKey(Long flowId);

    AccountFlow selectByBusiness(@Param("actId") long actId, @Param("subActType") int subActType, @Param("flowType") int flowType, @Param("businessId") String businessId);

}