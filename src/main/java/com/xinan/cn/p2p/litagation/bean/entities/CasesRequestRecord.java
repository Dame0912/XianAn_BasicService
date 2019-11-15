package com.xinan.cn.p2p.litagation.bean.entities;

import lombok.Data;

import java.util.Date;

@Data
public class CasesRequestRecord {
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 请求类型
     */
    private Integer requestType;
    /**
     * 状态0等结果1失败2成功
     */
    private Integer status;
    /**
     * 响应码
     */
    private String responseCode;
    /**
     * 响应码结果
     */
    private String responseCodeMsg;
    /**
     * 请求数据
     */
    private String requestData;
    /**
     * 响应数据
     */
    private String responseData;
    /**
     * 业务id
     */
    private String businessId;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
