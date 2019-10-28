package com.xinan.cn.p2p.report.nifa.mapper;

import com.xinan.cn.p2p.report.nifa.bean.entities.NifaPrjRequestData;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface NifaPrjRequestDataMapper extends Mapper<NifaPrjRequestData> {

    /**
     * 查询报送信息
     *
     * @param requestIds
     */
    List<NifaPrjRequestData> queryReqDataList(@Param("requestIds") List<Long> requestIds);
}
