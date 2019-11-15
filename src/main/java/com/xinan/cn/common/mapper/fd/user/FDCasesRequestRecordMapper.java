package com.xinan.cn.common.mapper.fd.user;

import com.xinan.cn.p2p.litagation.bean.entities.CasesRequestRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FDCasesRequestRecordMapper {

    int insert(CasesRequestRecord casesRequestRecord);

    int update(CasesRequestRecord casesRequestRecord);

}
