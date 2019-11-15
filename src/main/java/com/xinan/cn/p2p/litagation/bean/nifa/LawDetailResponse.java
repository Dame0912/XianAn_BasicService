package com.xinan.cn.p2p.litagation.bean.nifa;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class LawDetailResponse implements Serializable {

    /**
     * 是否涉案 0： 未涉案； 1： 涉案
     */
    @JSONField(name = "has_cases")
    private Integer hasCases;

    /**
     * 是否失信被执行人  0： 否； 1： 是
     */
    @JSONField(name = "is_sxbzxr")
    private Integer isSxbzxr;

    /**
     * 民事案件总数
     */
    @JSONField(name = "civil_count_total")
    private Integer civilCountTotal;

    /**
     * 刑事案件总数
     */
    @JSONField(name = "criminal_count_total")
    private Integer criminalCountTotal;

    /**
     * 行政案件总数
     */
    @JSONField(name = "administrative_count_total")
    private Integer administrativeCountTotal;

    /**
     * 作为被告涉案总数
     */
    @JSONField(name = "count_beigao")
    private Integer countBeiGao;

    /**
     * 作为被告已结案件总数
     */
    @JSONField(name = "count_jie_beigao")
    private Integer countJieBeiGao;

    /**
     * 作为原告涉案总数
     */
    @JSONField(name = "count_yuangao")
    private Integer countYuanGao;

    /**
     * 作为第三人涉案总数
     */
    @JSONField(name = "count_other")
    private Integer countOther;

    /**
     * 作为被告涉案金额等级   0-20 等级
     */
    @JSONField(name = "money_beigao")
    private Integer moneyBeiGao;

    /**
     * 作为被告已结案件金额等级  0-20 等级
     */
    @JSONField(name = "money_jie_beigao")
    private Integer moneyJieBeiGao;

    /**
     * 作为原告涉案金额等级    0-20 等级
     */
    @JSONField(name = "money_yuangao")
    private Integer moneyYuanGao;

    /**
     * 作为第三人涉案金额等级   0-20 等级
     */
    @JSONField(name = "money_other")
    private Integer moneyOther;

    /**
     * 涉案时间分布,   全部立案日期年份分布（列表及其数量）
     */
    @JSONField(name = "larq_stat")
    private String larqStat;

}
