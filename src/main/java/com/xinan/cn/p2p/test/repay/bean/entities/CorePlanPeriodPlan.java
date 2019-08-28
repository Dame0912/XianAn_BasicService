package com.xinan.cn.p2p.test.repay.bean.entities;

import lombok.Data;

import java.util.Date;

/**
 * core-plan 每期计划表
 */
@Data
public class CorePlanPeriodPlan {
    /**
     * 计划id
     */
    private Long planId;
    /**
     * 期数
     */
    private Integer period;
    /**
     * 计划还款日期
     */
    private Long planRepayDate;
    /**
     * 计划状态
     */
    private Integer planStatus;
    /**
     * 来源
     */
    private String requestSource;
    /**
     * 逾期宽限期
     */
    private Integer overdueGracePeriod;
    /**
     * 代偿宽限期
     */
    private Integer claimGracePeriod;
    /**
     * 是否逾期
     */
    private Integer isOverdue;
    /**
     * 代偿状态
     */
    private Integer claimStatus;
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
