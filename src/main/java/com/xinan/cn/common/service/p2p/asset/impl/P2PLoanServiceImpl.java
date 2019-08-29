package com.xinan.cn.common.service.p2p.asset.impl;

import com.xinan.cn.common.bean.dto.p2p.asset.LoanSimpleInfoVO;
import com.xinan.cn.common.bean.entities.fd.asset.FDLoan;
import com.xinan.cn.common.bean.entities.fd.core.FDInvest;
import com.xinan.cn.common.bean.entities.fd.core.FDLoanSkuMapping;
import com.xinan.cn.common.bean.entities.p2p.asset.P2PLoan;
import com.xinan.cn.common.bean.entities.p2p.financial.P2PLoansBase;
import com.xinan.cn.common.bean.entities.p2p.trading.P2PPaybackPlan;
import com.xinan.cn.common.constants.SymbolsConst;
import com.xinan.cn.common.mapper.fd.asset.FDLoanMapper;
import com.xinan.cn.common.mapper.fd.core.FDInvestMapper;
import com.xinan.cn.common.mapper.fd.core.FDLoanSkuMapper;
import com.xinan.cn.common.mapper.p2p.asset.P2PLoanMapper;
import com.xinan.cn.common.mapper.p2p.financial.P2PLoansBaseMapper;
import com.xinan.cn.common.mapper.p2p.trading.P2PPaybackPlanMapper;
import com.xinan.cn.common.service.p2p.asset.intf.P2PLoanServiceIntf;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class P2PLoanServiceImpl implements P2PLoanServiceIntf {

    @Autowired
    private P2PLoansBaseMapper p2pLoansBaseMapper;
    @Autowired
    private P2PLoanMapper p2pLoanMapper;
    @Autowired
    private P2PPaybackPlanMapper p2pPaybackPlanMapper;
    @Autowired
    private FDLoanMapper fdLoanMapper;
    @Autowired
    private FDLoanSkuMapper fdLoanSkuMapper;
    @Autowired
    private FDInvestMapper fdInvestMapper;


    @Override
    public LoanSimpleInfoVO getLoanSimpleInfo(Long skuId) {
        P2PLoansBase p2pLoansBase = p2pLoansBaseMapper.getBySkuId(skuId);
        P2PLoan p2pLoan = p2pLoanMapper.getByLoanId(p2pLoansBase.getLoanId());
        List<P2PPaybackPlan> p2pPaybackPlanList = p2pPaybackPlanMapper.findListBySkuId(skuId);
        List<Long> p2pInvestPlanIdList = p2pPaybackPlanList.stream().map(P2PPaybackPlan::getPlanId).collect(Collectors.toList());
        String p2pInvestPlanIds = StringUtils.join(p2pInvestPlanIdList, SymbolsConst.JOIN_SYMBOL);
        FDLoan fdLoan = fdLoanMapper.getByApplyId(String.valueOf(p2pLoansBase.getLoanApplyNo()));
        FDLoanSkuMapping fdLoanSkuMapping = fdLoanSkuMapper.getByLoanId(fdLoan.getLoanId());
        FDInvest fdInvest = fdInvestMapper.getBySkuId(fdLoanSkuMapping.getSkuId());
        Long phpInvestPlanId = fdInvest.getPlanId();

        LoanSimpleInfoVO simpleLoanInfo = new LoanSimpleInfoVO();
        simpleLoanInfo.setPrjName(p2pLoansBase.getSkuName());
        simpleLoanInfo.setLoanUserName(p2pLoansBase.getLoanUserName());
        simpleLoanInfo.setLoanName(p2pLoan.getLoanName());
        simpleLoanInfo.setLoanStatus(p2pLoan.getLoanStatus());
        simpleLoanInfo.setP2pLoanId(String.valueOf(p2pLoan.getLoanId()));
        simpleLoanInfo.setP2pSkuId(String.valueOf(skuId));
        simpleLoanInfo.setP2pPlanId(String.valueOf(p2pLoan.getPlanId()));
        simpleLoanInfo.setP2pInvestPlanIds(p2pInvestPlanIds);
        simpleLoanInfo.setPhpLoanId(String.valueOf(fdLoan.getLoanId()));
        simpleLoanInfo.setPhpSkuId(String.valueOf(fdLoanSkuMapping.getSkuId()));
        simpleLoanInfo.setPhpPlanId(String.valueOf(fdLoan.getPlanId()));
        simpleLoanInfo.setPhpInvestPlanId(String.valueOf(phpInvestPlanId));
        return simpleLoanInfo;
    }
}
