package com.xinan.cn;

import com.xinan.cn.common.mapper.fd.asset.FDLoanMapper;
import com.xinan.cn.common.mapper.fd.user.FDUserMapper;
import com.xinan.cn.common.utils.DateUtil;
import com.xinan.cn.p2p.litagation.bean.entities.CasesLoanerInfo;
import com.xinan.cn.p2p.litagation.service.intf.LawInfoQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("sit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class LawTests {
    @Autowired
    private FDUserMapper fdUserMapper;
    @Autowired
    private FDLoanMapper fdLoanMapper;
    @Autowired
    private LawInfoQueryService lawInfoQueryService;

    @Test
    public void lawInfoQueryService_test1() {
        long start = System.currentTimeMillis();
        boolean result = lawInfoQueryService.verifyAllUnclearLawInfo(DateUtil.getCurMillis());
        System.out.println("***" + result + "," + (System.currentTimeMillis() - start));
    }

    @Test
    public void caseMapper_test(){
        List<CasesLoanerInfo> insertCases = new ArrayList<>();
        CasesLoanerInfo loanerInfo1 = new CasesLoanerInfo();
        loanerInfo1.setUserId(1L);
        loanerInfo1.setName("1");
        insertCases.add(loanerInfo1);

        CasesLoanerInfo loanerInfo2 = new CasesLoanerInfo();
        loanerInfo2.setUserId(2L);
        loanerInfo2.setName("2");
        insertCases.add(loanerInfo2);
        fdUserMapper.batchInsert(insertCases);
    }

    @Test
    public void fdUserMapper_test() {
        List<LawAssetLoanerInfo> lawAssetLoanerInfos = new ArrayList<>();
        LawAssetLoanerInfo lawAssetLoanerInfo1 = new LawAssetLoanerInfo();
        lawAssetLoanerInfo1.setName("黄尧");
        lawAssetLoanerInfo1.setCardId("340403198403270618");
        lawAssetLoanerInfos.add(lawAssetLoanerInfo1);

        LawAssetLoanerInfo lawAssetLoanerInfo2 = new LawAssetLoanerInfo();
        lawAssetLoanerInfo2.setName("余俊");
        lawAssetLoanerInfo2.setCardId("340104196511142059");
        lawAssetLoanerInfos.add(lawAssetLoanerInfo2);

        LawAssetLoanerInfo lawAssetLoanerInfo3 = new LawAssetLoanerInfo();
        lawAssetLoanerInfo3.setName("张建平");
        lawAssetLoanerInfo3.setCardId("340103198707052517");
        lawAssetLoanerInfos.add(lawAssetLoanerInfo3);

        LawAssetLoanerInfo lawAssetLoanerInfo4 = new LawAssetLoanerInfo();
        lawAssetLoanerInfo4.setName("张玉霞");
        lawAssetLoanerInfo4.setCardId("340103196912263541");
        lawAssetLoanerInfos.add(lawAssetLoanerInfo4);

        System.out.println(lawAssetLoanerInfos);

        List<LawAssetLoanerInfo> queryLawAssetLoanerInfos = fdUserMapper.batchQueryNatrualLoaner(lawAssetLoanerInfos);

        System.out.println(queryLawAssetLoanerInfos);
    }
}
