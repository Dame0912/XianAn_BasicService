package com.xinan.cn.p2p.test.repay.bean.entities;

import lombok.Data;

import java.util.Date;

/**
 * core-plan 计划配置表
 */
@Data
public class CorePlanConfig {
    /**
     * 计划id
     */
    private Long planId;
    /**
     * 业务id
     */
    private String businessId;
    /**
     * 调用方标识
     */
    private String requestSource;
    /**
     * 本金
     */
    private Long amount;
    /**
     * 还款方式
     */
    private Integer paymentType;
    /**
     * 计息方式
     */
    private Integer interestType;
    /**
     * 期数
     */
    private Integer periods;
    /**
     * 每期天数
     */
    private Integer periodDays;
    /**
     * 一年天数
     */
    private Integer yearDays;
    /**
     * 一月天数
     */
    private Integer monthDays;
    /**
     * 固定还款日
     */
    private Integer repayDay;
    /**
     * 首期最小天数
     */
    private Integer firstPeriodMinDays;
    /**
     * 末期最小天数
     */
    private Integer finalPeriodMinDays;
    /**
     * 起息时间
     */
    private Long interestActiveTime;
    /**
     * 最晚还款时间
     */
    private Long maxRepayTime;
    /**
     * 是否包含第一天
     */
    private Integer isInvolveStartDate;
    /**
     * 是否包含最后一天
     */
    private Integer isInvolveEndDate;
    /**
     * 首期还款日期类型
     */
    private Integer firstPeriodRepayDayType;
    /**
     * 末期还款日期类型
     */
    private Integer finalPeriodRepayDayType;
    /**
     * 尾差处理类型
     */
    private Integer balanceAdjustType;
    /**
     * 还款计划生成方式
     */
    private Integer repayPlanGeneMode;
    /**
     * 末期计息方式
     */
    private Integer endPeriodInterestType;
    /**
     * 是否允许提前还当期
     */
    private Integer isAllowPreRepayCurrent;
    /**
     * 是否允许罚息减免
     */
    private Integer isAllowPenaltyDeduct;
    /**
     * 逾期罚息宽限期
     */
    private Integer penaltyGraceDays;
    /**
     * 上次检查时间
     */
    private Long lastCheckTime;
    /**
     * 代偿宽限期
     */
    private Integer claimGraceDays;
    /**
     * 还款日是否跳过节假日
     */
    private Integer isSkipFestival;
    /**
     * 节假日是否计算罚息
     */
    private Integer IsCalcPenaltyInFestival;
    /**
     * 是否允许提前结清
     */
    private Integer isAllowPreRepay;
    /**
     * 提前结清最小已还清期数
     */
    private Integer preRepayMinPeriod;
    /**
     * 当期利息是否按照实际使用天数计算
     */
    private Integer isCalcPreRepayInterestByUsedDays;
    /**
     * 当期居间服务费是否按照实际使用天数计算
     */
    private Integer isCalcCurrentCenterServiceByUsedDays;
    /**
     * 提前结清其他费用收取方式
     */
    private Integer preRepayOtherFeeRepayType;
    /**
     * 提前结清违约金计算方法
     */
    private Integer preRepayCalcMethod;
    /**
     * 提前结清收取额外利息期数
     */
    private Integer preRepayAdditionalInterestPeriod;
    /**
     * 是否允许加速到期
     */
    private Integer isAllowAcc;
    /**
     * 加速到期利息收取方式
     */
    private Integer accInterestCharge;
    /**
     * 加速到期利息收取具体金额
     */
    private Long accFixedInterestAmount;
    /**
     * 加速到期条件类型
     */
    private Integer accCondition;
    /**
     * 加速到期条件参数
     */
    private Integer accArg;
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


    /**
     * 是否允许超前正常还款 is_allow_ahead_repay
     */
    private Integer isAllowAheadRepay;

}
