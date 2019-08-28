package com.xinan.cn.p2p.report.nifa.bean.dto;

import java.util.Map;

import lombok.Data;

/**
 * 报送信息
 * @author lyq
 */
@Data
public class NifaPrjRepInfoDTO {
    private String id;
    /**
     * 项目编号
     */
    private String prjNum;
    /**
     * 项目状态
     */
    private String prjStatus;
    /**
     * 项目类型
     */
    private String type;
    /**
     * 报送状态
     */
    private String status;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 创建时间
     */
    private String createdAt;
    /**
     * 更新时间
     */
    private String updatedAt;
    /**
     * 报送内容
     */
    private Map<String, Object> reportData;

}
