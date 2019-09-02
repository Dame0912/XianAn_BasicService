package com.xinan.cn;

import com.xinan.cn.common.bean.entities.plan.PeriodPlan;
import com.xinan.cn.common.mapper.plan.PeriodPlanMapper;
import com.xinan.cn.common.mapper.plan.PlanConfigMapper;
import com.xinan.cn.common.utils.DateUtil;
import com.xinan.cn.common.utils.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicServiceApplicationTests {

    @Autowired
    private PlanConfigMapper planConfigMapper;

    @Test
    public void test_planConfig_update() {
        Map<String, Object> param = new HashMap<>();
        String repayDate = "1571500800000";
        Long lastCheckTime = DateUtil.getDateMillis(DateUtil.getAddDate(new Date(Long.parseLong(repayDate)), -1));
        param.put("lastCheckTime", lastCheckTime);
        param.put("isAllowAheadRepay", 1);
        List<Long> allPlanIds = Arrays.asList(1163716734587977728L, 1163717618481336320L, 1163717618481336321L, 1163716733743239168L, 1163716735103877120L);
        param.put("planIds", allPlanIds);
        int count = planConfigMapper.batchUpdateLastCheckTime(param);
        System.out.println("count:" + count);
        if (count != allPlanIds.size()) {
            throw new RuntimeException("AbstractRepay.updateLastCheckTime异常,需要更新的条目数:" + allPlanIds.size() + ", 实际更新:" + count);
        }
    }

    @Autowired
    private PeriodPlanMapper periodPlanMapper;

    @Test
    public void test02() {
        PeriodPlan periodPlan = new PeriodPlan();
        periodPlan.setPeriod(2);
        periodPlan.setPlanId(1167275875053441024L);
        List<Integer> overduePeriods = new ArrayList<>();
        while (!overduePeriods.contains(2)) {
            overduePeriods = periodPlanMapper.queryOverdueAndNoRepayWithPeriod(periodPlan);
        }
        System.out.println(overduePeriods);
    }

    @Test
    public void test03() {
        PlanConfigMapper planConfigMapper = SpringContextUtil.getBean(PlanConfigMapper.class);
        System.out.println(planConfigMapper);
    }

}
