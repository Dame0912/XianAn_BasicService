package com.xinan.cn.common.bean.entities.p2p.account;

import lombok.Data;

import java.util.Date;

@Data
public class DealStepStatus {
    private Integer id;
    private Long actId;//账户id
    private Integer subActType;//子账户类型
    private String dealType;//交易类型
    private String businessId;//业务id
    private Long skuId;//标的id
    private String item;//期数
    private String step;//当前步骤
    private Integer stepStatus;//当前步骤状态
    private String requestData;//请求数据
    private Integer status;//总状态, 0处理中; 1失败; 2成功
    private Date createdAt;
    private Date updatedAt;
    private Integer retryTimes;//重试次数
    private Integer errorCode;
    private String errorMsg;
}