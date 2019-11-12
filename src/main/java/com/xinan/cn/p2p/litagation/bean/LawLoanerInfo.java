package com.xinan.cn.p2p.litagation.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 诉讼人员基本信息
 */
@Data
public class LawLoanerInfo implements Serializable {

    /**
     * 名称
     * 个人：姓名
     * 企业：企业名称
     */
    private String loanerName;

    /**
     * 借款人类型    1:自然人；2:企业
     */
    private String loanerType;

    /**
     * 证件类型  1:身份证
     */
    private String identityType;

    /**
     * 证件号码
     */
    private String identityNo;

    /**
     * 最新查询时间
     */
    private Date lastRequestDate;

}
