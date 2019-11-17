package com.xinan.cn;

import com.xinan.cn.common.mapper.fd.asset.FDLoanMapper;
import com.xinan.cn.common.mapper.fd.user.FDUserMapper;
import com.xinan.cn.common.utils.DateUtil;
import com.xinan.cn.p2p.litagation.bean.dto.LawLoanerBasicInfo;
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
        List<LawLoanerBasicInfo> LawLoanerBasicInfos = new ArrayList<>();
        LawLoanerBasicInfo LawLoanerBasicInfo1 = new LawLoanerBasicInfo();
        LawLoanerBasicInfo1.setName("黄尧");
        LawLoanerBasicInfo1.setCardId("340403198403270618");
        LawLoanerBasicInfos.add(LawLoanerBasicInfo1);

        LawLoanerBasicInfo LawLoanerBasicInfo2 = new LawLoanerBasicInfo();
        LawLoanerBasicInfo2.setName("余俊");
        LawLoanerBasicInfo2.setCardId("340104196511142059");
        LawLoanerBasicInfos.add(LawLoanerBasicInfo2);

        LawLoanerBasicInfo LawLoanerBasicInfo3 = new LawLoanerBasicInfo();
        LawLoanerBasicInfo3.setName("张建平");
        LawLoanerBasicInfo3.setCardId("340103198707052517");
        LawLoanerBasicInfos.add(LawLoanerBasicInfo3);

        LawLoanerBasicInfo LawLoanerBasicInfo4 = new LawLoanerBasicInfo();
        LawLoanerBasicInfo4.setName("张玉霞");
        LawLoanerBasicInfo4.setCardId("340103196912263541");
        LawLoanerBasicInfos.add(LawLoanerBasicInfo4);

        System.out.println(LawLoanerBasicInfos);

        List<LawLoanerBasicInfo> queryLawLoanerBasicInfos = fdUserMapper.batchQueryNatrualLoaner(LawLoanerBasicInfos);

        System.out.println(queryLawLoanerBasicInfos);
    }
}
