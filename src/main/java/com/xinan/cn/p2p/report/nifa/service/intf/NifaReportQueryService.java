package com.xinan.cn.p2p.report.nifa.service.intf;

import com.xinan.cn.p2p.report.nifa.bean.NifaReportQueryParamVO;

import java.util.Map;

/**
 * 项目报送信息查询
 *
 * @author lyq
 *
 */
public interface NifaReportQueryService {

    /**
     *  分页查询项目报送信息
     * @param requestParam
     */
    Map<String,Object> queryReportInfoPage(NifaReportQueryParamVO requestParam);
}
