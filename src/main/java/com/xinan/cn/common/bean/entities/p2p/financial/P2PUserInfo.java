package com.xinan.cn.common.bean.entities.p2p.financial;

import lombok.Data;

/**
 * @Description 用户信息表
 * @Author lyq
 **/
@Data
public class P2PUserInfo {
    private long uuid;//账户id
    private String userName;//登录名
    private String mobile;//手机号
    private String realName;//真实姓名
}
