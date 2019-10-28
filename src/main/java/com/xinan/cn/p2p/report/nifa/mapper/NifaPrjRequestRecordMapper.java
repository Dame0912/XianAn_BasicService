package com.xinan.cn.p2p.report.nifa.mapper;

import com.xinan.cn.p2p.report.nifa.bean.NifaReportQueryParamVO;
import com.xinan.cn.p2p.report.nifa.bean.entities.NifaPrjRequestRecord;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface NifaPrjRequestRecordMapper extends Mapper<NifaPrjRequestRecord> {

    /**
     * 查询总条数
     *
     * @param requestParam
     */
    Long queryCount(NifaReportQueryParamVO requestParam);

    /**
     * 查询报送信息
     *
     * @param param
     */
    List<NifaPrjRequestRecord> queryShowPage(Map<String, String > param);
}
