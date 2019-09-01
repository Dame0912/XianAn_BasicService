package com.xinan.cn.common.mapper.p2p.financial;

import com.xinan.cn.common.bean.entities.p2p.financial.P2PUserInfo;
import tk.mybatis.mapper.common.Mapper;

public interface P2PUserInfoMapper extends Mapper<P2PUserInfo> {

    P2PUserInfo getUserInfo(Long actId);
}
