package com.xinan.cn.common.bean.entities.p2p.trading;

import lombok.Data;

import java.util.Date;

@Data
public class P2PPaybackPlan {
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
