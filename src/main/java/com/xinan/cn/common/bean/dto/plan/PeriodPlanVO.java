package com.xinan.cn.common.bean.dto.plan;

import lombok.Data;

/**
 * 每期计划展示
 *
 * @Author lyq
 **/
@Data
public class PeriodPlanVO {
    private Integer period;//期数
    private String planRepayDate;//计划还款日期
    private Integer p2pPlanStatus;//计划状态
    private Integer p2pIsOverdue;//是否逾期
    private Integer p2pClaimStatus;//代偿状态
    private Integer fdPlanStatus;//计划状态
    private Integer fdIsOverdue;//是否逾期
    private Integer fdClaimStatus;//代偿状态
}
