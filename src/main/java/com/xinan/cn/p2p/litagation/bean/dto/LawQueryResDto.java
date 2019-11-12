package com.xinan.cn.p2p.litagation.bean.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LawQueryResDto implements Serializable {

    /**
     * 查询日期
     */
    private String queryDate;

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
}
