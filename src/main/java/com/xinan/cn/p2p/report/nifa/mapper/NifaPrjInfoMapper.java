package com.xinan.cn.p2p.report.nifa.mapper;

import com.xinan.cn.p2p.report.nifa.bean.NifaReportQueryParamVO;
import com.xinan.cn.p2p.report.nifa.bean.entities.NifaPrjInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface NifaPrjInfoMapper extends Mapper<NifaPrjInfo> {

    /**
     * 查询总条数
     *
     * @param requestParam
     */
    Long queryCount(NifaReportQueryParamVO requestParam);

    /**
     * 查询报送信息
     *
     * @param requestParam
     */
    List<NifaPrjInfo> queryShowPage(NifaReportQueryParamVO requestParam);
}
