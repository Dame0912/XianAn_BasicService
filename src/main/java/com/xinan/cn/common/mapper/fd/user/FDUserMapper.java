package com.xinan.cn.common.mapper.fd.user;

import com.xinan.cn.p2p.litagation.bean.LawLoanerInfo;
import com.xinan.cn.p2p.litagation.bean.entities.CasesLoanerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FDUserMapper {

    List<LawLoanerInfo> batchQueryNatrualLoaner(@Param("lawLoanerInfos") List<LawLoanerInfo> lawLoanerInfos);

    List<LawLoanerInfo> batchQueryCompanyLoaner(@Param("lawLoanerInfos") List<LawLoanerInfo> lawLoanerInfos);

    int batchInsert(@Param("casesLoanerInfoList") List<CasesLoanerInfo> casesLoanerInfoList);

    int batchUpdateNaturals(@Param("casesLoanerInfoList") List<CasesLoanerInfo> casesLoanerInfoList);

    int batchUpdateCompanys(@Param("casesLoanerInfoList") List<CasesLoanerInfo> casesLoanerInfoList);

}
