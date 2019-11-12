package com.xinan.cn.p2p.litagation.bean.entities;

import lombok.Data;

import java.util.Date;

@Data
public class CasesLoanerInfo {

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 名称
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
     * 是否涉案 0： 未涉案； 1： 涉案
     */
    private Integer hasCases;

    /**
     * 是否失信被执行人  0： 否； 1： 是
     */
    private Integer isSxbzxr;

    /**
     * 民事案件总数
     */
    private Integer civilCountTotal;

    /**
     * 刑事案件总数
     */
    private Integer criminalCountTotal;

    /**
     * 行政案件总数
     */
    private Integer administrativeCountTotal;

    /**
     * 作为被告涉案总数
     */
    private Integer countBeiGao;

    /**
     * 作为被告已结案件总数
     */
    private Integer countJieBeiGao;

    /**
     * 作为原告涉案总数
     */
    private Integer countYuanGao;

    /**
     * 作为第三人涉案总数
     */
    private Integer countOther;

    /**
     * 作为被告涉案金额等级   0-20 等级
     */
    private Integer moneyBeiGao;

    /**
     * 作为被告已结案件金额等级  0-20 等级
     */
    private Integer moneyJieBeiGao;

    /**
     * 作为原告涉案金额等级    0-20 等级
     */
    private Integer moneyYuanGao;

    /**
     * 作为第三人涉案金额等级   0-20 等级
     */
    private Integer moneyOther;

    /**
     * 涉案时间分布,   全部立案日期年份分布（列表及其数量）
     */
    private String larqStat;

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
    private Date deletedAt;
}
