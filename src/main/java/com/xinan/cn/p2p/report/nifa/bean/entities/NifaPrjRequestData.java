package com.xinan.cn.p2p.report.nifa.bean.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * NIFA请求响应数据
 *
 * @author lyq
 */
@Data
public class NifaPrjRequestData implements Serializable {
    private static final long serialVersionUID = -1498302524088367118L;

    private Long id;

    /**
     * 请求id
     */
    private Long requestId;

    /**
     * 请求数据
     */
    private String requestData;

    /**
     * 响应数据
     */
    private String responseData;

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
    private Long deletedAt;
}
