package com.xinan.cn;

import com.xinan.cn.common.mapper.fd.asset.FDLoanMapper;
import com.xinan.cn.common.mapper.fd.user.FDUserMapper;
import com.xinan.cn.p2p.litagation.bean.LawLoanerInfo;
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

    @Test
    public void fdLoanMapper_test(){
        List<LawLoanerInfo> allUnclearLoanerInfo = fdLoanMapper.getAllUnclearLoanerInfo();
        System.out.println(allUnclearLoanerInfo);
    }


    @Test
    public void fdUserMapper_test() {
        List<LawLoanerInfo> lawLoanerInfos = new ArrayList<>();
        LawLoanerInfo lawLoanerInfo1 = new LawLoanerInfo();
        lawLoanerInfo1.setLoanerName("黄尧");
        lawLoanerInfo1.setIdentityNo("340403198403270618");
        lawLoanerInfos.add(lawLoanerInfo1);

        LawLoanerInfo lawLoanerInfo2 = new LawLoanerInfo();
        lawLoanerInfo2.setLoanerName("余俊");
        lawLoanerInfo2.setIdentityNo("340104196511142059");
        lawLoanerInfos.add(lawLoanerInfo2);

        LawLoanerInfo lawLoanerInfo3 = new LawLoanerInfo();
        lawLoanerInfo3.setLoanerName("张建平");
        lawLoanerInfo3.setIdentityNo("340103198707052517");
        lawLoanerInfos.add(lawLoanerInfo3);

        LawLoanerInfo lawLoanerInfo4 = new LawLoanerInfo();
        lawLoanerInfo4.setLoanerName("张玉霞");
        lawLoanerInfo4.setIdentityNo("340103196912263541");
        lawLoanerInfos.add(lawLoanerInfo4);

        System.out.println(lawLoanerInfos);

        List<LawLoanerInfo> queryLawLoanerInfos = fdUserMapper.batchQueryNatrualLoaner(lawLoanerInfos);

        System.out.println(queryLawLoanerInfos);
    }
}
