package com.xinan.cn.common.bean.entities.plan;

import lombok.Data;

import java.util.Date;

/**
 * core-plan 金额表
 */
@Data
public class Money {
    /**
     * 计划id
     */
    private Long planId;
    /**
     * 期数
     */
    private Integer period;
    /**
     * 应还金额
     */
    private Long planAmount;
    /**
     * 尾差
     */
    private Long balance;
    /**
     * 实还金额
     */
    private Long actualAmount;
    /**
     * 费用类型
     */
    private Integer moneyType;
    /**
     * 上次还款时间
     */
    private Long lastRepayTime;
    /**
     * 扣除方向
     */
    private Integer receiverType;
    /**
     * 生成日志
     */
    private String generateLog;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;
    /**
     * 删除时间
     */
    private Long deletedAt;
}
