package com.xinan.cn.p2p.report.nifa.service.intf;

import com.xinan.cn.p2p.report.nifa.bean.NifaReportQueryParamVO;
import com.xinan.cn.p2p.report.nifa.bean.dto.NifaReqRecordDataDTO;

import java.util.List;
import java.util.Map;

/**
 * 项目报送信息查询
 *
 * @author lyq
 *
 */
public interface NifaReportQueryService {

    /**
     *  分页查询项目报送项目信息
     * @param requestParam
     */
    Map<String,Object> queryPrjInfoPage(NifaReportQueryParamVO requestParam);

    /**
     *  分页查询报送请求记录信息
     * @param param
     */
    List<NifaReqRecordDataDTO> queryReqRecordPage(Map<String, String > param);
}
