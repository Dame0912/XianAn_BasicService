package com.xinan.cn.common.mapper.fd.user;

import com.xinan.cn.p2p.litagation.bean.dto.LawApiQueryRequest;
import com.xinan.cn.p2p.litagation.bean.dto.LawLoanerBasicInfo;
import com.xinan.cn.p2p.litagation.bean.entities.CasesLoanerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FDUserMapper {

    CasesLoanerInfo queryOne(LawApiQueryRequest lawApiQueryRequest);

    int insertOne(CasesLoanerInfo casesLoanerInfo);

    int updateOne(CasesLoanerInfo casesLoanerInfo);

    List<LawLoanerBasicInfo> batchQueryNatrualLoaner(@Param("lawLoanerBasicInfoList") List<LawLoanerBasicInfo> lawLoanerBasicInfoList);

    List<LawLoanerBasicInfo> batchQueryCompanyLoaner(@Param("lawLoanerBasicInfoList") List<LawLoanerBasicInfo> lawLoanerBasicInfoList);

    int batchInsert(@Param("casesLoanerInfoList") List<CasesLoanerInfo> casesLoanerInfoList);

    int batchUpdateNaturals(@Param("casesLoanerInfoList") List<CasesLoanerInfo> casesLoanerInfoList);

    int batchUpdateCompanys(@Param("casesLoanerInfoList") List<CasesLoanerInfo> casesLoanerInfoList);

}
