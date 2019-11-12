package com.xinan.cn.p2p.litagation.bean;

import lombok.Data;

/**
 * 借款基本信息
 */
@Data
public class LoanBasicInfo {
    /**
     * 项目编号
     */
    private String project_no;

    /**
     * 用户ID
     */
    private String act_id;

    /**
     * 用户类型：企业，个人
     */
    private String act_type;

    /**
     * 名称
     * 个人：姓名
     * 企业：企业名称
     */
    private String act_name;

    /**
     * 证件号
     * 个人：身份证号
     */
    private String identity_no;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 借款产品
     */
    private String loan_type;

    /**
     * 客户经理
     */
    private String created_user;

    /**
     * 借款金额
     */
    private String loan_amount;

    /**
     * 到期日
     */
    private String final_return_date;
}
