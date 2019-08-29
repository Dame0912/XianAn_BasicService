package com.xinan.cn.p2p.report.nifa.bean;

import com.xinan.cn.common.bean.Page;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询报送的请求参数
 *
 * @Author lyq
 **/
@Data
public class NifaReportQueryParamVO extends Page implements Serializable {
    /**
     * 项目编号
     */
    private String prjNum;

    /**
     * 报送状态
     */
    private String status;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}
