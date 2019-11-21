package com.xinan.cn;

import com.alibaba.fastjson.JSON;
import com.xinan.cn.common.utils.DateUtil;
import com.xinan.cn.p2p.litagation.bean.dto.LawApiQueryRequest;
import com.xinan.cn.p2p.litagation.bean.dto.LawApiQueryResponse;
import com.xinan.cn.p2p.litagation.constant.LawCasesConstant;
import com.xinan.cn.p2p.litagation.service.intf.LawInfoQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@ActiveProfiles("sit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class LawTests {
    @Autowired
    private LawInfoQueryService lawInfoQueryService;

    @Test
    public void lawInfoQueryService_test1() {
        long start = System.currentTimeMillis();
        boolean result = lawInfoQueryService.verifyAllUnclearLawInfo(DateUtil.getCurMillis());
        System.out.println("***" + result + "," + (System.currentTimeMillis() - start));
    }

    @Test
    public void lawInfoQueryService_queryLoanerCase() {
        LawApiQueryRequest lawApiQueryRequest_natural = new LawApiQueryRequest();
        lawApiQueryRequest_natural.setName("花蕊");
        lawApiQueryRequest_natural.setUserType(LawCasesConstant.LawUserTypeConst.NATURAL);
        lawApiQueryRequest_natural.setUserId(1194092145406124032L);
        lawApiQueryRequest_natural.setCardType(LawCasesConstant.LawCardTypeConst.IDENTITY_CARD);
        lawApiQueryRequest_natural.setCardId("342623199209123457");
        lawApiQueryRequest_natural.setBusinessId(UUID.randomUUID().toString().replace("-", ""));

        LawApiQueryRequest lawApiQueryRequest_company = new LawApiQueryRequest();
        lawApiQueryRequest_company.setName("合肥市包河区唐玲日用百货商行");
        lawApiQueryRequest_company.setUserType(LawCasesConstant.LawUserTypeConst.COMPANY);
        lawApiQueryRequest_company.setUserId(1157179532118335488L);
        lawApiQueryRequest_company.setCardType(LawCasesConstant.LawCardTypeConst.BUSINESS_LICENSE);
        lawApiQueryRequest_company.setCardId("92340111MA2TXF7E7K");
        lawApiQueryRequest_company.setBusinessId(UUID.randomUUID().toString().replace("-", ""));

        LawApiQueryResponse lawApiQueryResponse = lawInfoQueryService.queryLoanerCase(lawApiQueryRequest_natural);
        System.out.println(JSON.toJSONString(lawApiQueryResponse));
    }

}
