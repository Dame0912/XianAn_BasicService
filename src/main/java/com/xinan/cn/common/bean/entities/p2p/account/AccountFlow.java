package com.xinan.cn.common.bean.entities.p2p.account;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AccountFlow implements Serializable {
    private static final long serialVersionUID = 5323805364650032821L;
    /**
     * 流水id
     */
    private Long flowId;

    /**
     * 账户id
     */
    private Long actId;

    /**
     * 账户类型 0投资账户，1借款账户
     */
    private Integer subActType;

    /**
     * 对方账户标识
     */
    private Long anotherActId;

    /**
     * 流水金额
     */
    private Long amount;

    /**
     * 流水类型
     */
    private Integer flowType;

    /**
     * 交易类型
     */
    private Integer tradeType;
    /**
     * 类型描述
     */
    private String flowDesc;

    /**
     * 流水描述
     */
    private String description;

    /**
     * 该流水是否显示0不显示1显示
     */
    private int showFlag;

    /**
     * 相关标的id
     */
    private Long skuId;

    /**
     * 业务id
     */
    private String businessId;

    /**
     * 期数
     */
    private String items;

    /**
     * 0处理中1处理失败2成功-1待处理
     */
    private int status;

    /**
     * 结果描述
     */
    private String retMsg;

    /**
     * 结果码
     */
    private String retCode;

    /**
     * 总资产
     */
    private Long totalAsset;

    /**
     * 银行方相关id
     */
    private String bankOrderId;

    private Long createdAt;

    private Date updatedAt;

    /**
     * 最终结果时间
     */
    private Date resultAt;

    /**
     * 交易前快照
     */
    private String beforeActInfo;

    /**
     * 交易后快照
     */
    private String afterActInfo;

    /**
     * 扩展用
     */
    private String extendReserve;

    /**
     * 扩展字段
     */
    private String detail;

}