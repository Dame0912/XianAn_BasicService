package com.xinan.cn.p2p.report.nifa.bean.dto;

import com.xinan.cn.common.bean.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 中互金报送记录数据(含请求数据)
 *
 * @Author lyq
 **/
@Data
public class NifaReqRecordDataDTO {
    private String id;

    /**
     * 项目编号
     */
    private String prjNumList;

    /**
     * 总条数
     */
    private Integer totalNum;

    /**
     * 类型，31:项目+借款人/法人信息; 32:项目状态或信息变更
     */
    private String type;

    /**
     * 状态，-1失败 2成功
     */
    private Integer status;

    /**
     * 请求数据
     */
    private String requestData;

    /**
     * 响应数据
     */
    private String responseData;

    /**
     * 失败原因
     */
    private String errorMsg;

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
    private String deletedAt;

}
