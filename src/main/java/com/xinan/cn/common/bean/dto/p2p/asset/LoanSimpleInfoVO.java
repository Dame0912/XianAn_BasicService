package com.xinan.cn.common.bean.dto.p2p.asset;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoanSimpleInfoVO implements Serializable {
    private String prjName;//项目名称
    private String loanUserName;//贷款用户姓名
    private String loanName;//借款类型名称
    private Integer loanStatus;//借款状态
    private String p2pLoanId;
    private String p2pSkuId;
    private String p2pPlanId;
    private String p2pInvestPlanIds;
    private String fdLoanId;
    private String fdSkuId;
    private String fdPlanId;
    private String fdInvestPlanId;
}
