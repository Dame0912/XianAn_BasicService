package com.xinan.cn.p2p.litagation.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LawLoanerBasicInfo implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 名称
     * 个人：姓名
     * 企业：企业名称
     */
    private String name;

    /**
     * 类型   1: 个人； 2: 企业
     */
    private Integer userType;

    /**
     * 证件类型  1:身份证； 2:营业执照
     */
    private Integer cardType;

    /**
     * 证件号码
     */
    private String cardId;

    /**
     * 最新查询时间
     */
    private Date lastRequestDate;
}
