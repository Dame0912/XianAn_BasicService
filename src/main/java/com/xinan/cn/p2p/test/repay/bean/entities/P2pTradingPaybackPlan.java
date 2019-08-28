package com.xinan.cn.p2p.test.repay.bean.entities;

import lombok.Data;

import java.util.Date;

@Data
public class P2pTradingPaybackPlan {
    private int id;
    private long investId;
    private long userId;
    private long skuId;
    private long planId;
    private long transferId;
    private int transferPeriod;
    private Date updatedAt;
    private Date createdAt;
}
