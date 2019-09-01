package com.xinan.cn.common.bean.dto.p2p.asset;

import com.xinan.cn.common.constants.SymbolsConst;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 资产信息展示
 */
@Data
public class LoanSimpleInfoVO implements Serializable {
    private Integer loanBaseId;//用户借款Id
    private String prjName;//项目名称
    private String realName;//用户姓名
    private String userName;//用户名
    private String loanName;//借款类型名称
    private Integer loanStatus;//借款状态
    private String p2pLoanId;//资产Id
    private String p2pSkuId;//标的Id
    private String p2pPlanId;//计划Id
    private String p2pInvestPlanIds;//投资回款计划Id
    private String fdLoanId;//房贷资产Id
    private String fdSkuId;//房贷标的Id
    private String fdPlanId;//房贷计划Id
    private String fdInvestPlanId;//房贷投资回款计划Id

    public List<Long> allPlanIds() {
        List<Long> allPlanIds = new ArrayList<>();
        String[] investPlanIds = p2pInvestPlanIds.split(SymbolsConst.VERTICAL_SEPARATOR);
        for (String investPlanId : investPlanIds) {
            allPlanIds.add(Long.parseLong(investPlanId));
        }
        allPlanIds.add(Long.parseLong(p2pPlanId));
        allPlanIds.add(Long.parseLong(fdPlanId));
        allPlanIds.add(Long.parseLong(fdInvestPlanId));
        return allPlanIds;
    }
}
