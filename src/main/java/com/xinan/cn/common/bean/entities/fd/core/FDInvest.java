package com.xinan.cn.common.bean.entities.fd.core;

import lombok.Data;

import java.util.Date;

@Data
public class FDInvest {
    private int id;
    private long investId; // 投资id
    private long skuId; // 投资对应的skuId
    private long investUserId; // 投资人uid
    private String moneySideCode; // 资金方代码
    private String rateConfigId; // 费率id
    private String projectId; // 项目id
    private int productType; // 标的大类
    private long investAmount; // 投资总额
    private int periods; // 期数
    private int periodsType; // 总天数
    private int interestType; // 计息方式
    private int status; // 状态
    private int stage; // 状态
    private int fixedRepayDate; // 状态
    private int returnType; // 回款方式
    private int source; // 投资来源
    private String channel; // 投资渠道
    private long valueDateTime; // 起息时间
    private Date createdAt;
    private Date updatedAt;
    private long maxPaybackDate;
    private long planId;//计划核心id
}
