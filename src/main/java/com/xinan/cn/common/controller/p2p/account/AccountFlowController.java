package com.xinan.cn.common.controller.p2p.account;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinan.cn.common.bean.entities.p2p.account.AccountFlow;
import com.xinan.cn.common.mapper.p2p.account.AccountFlowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class AccountFlowController {

    @Autowired
    private AccountFlowMapper accountFlowMapper;

    /**
     * 获取冻结金额为0的流水
     * @return
     */
    @ResponseBody
    @RequestMapping("aa.do")
    public List<Long>  getAccountFlow(){
        List<AccountFlow> accountFlows = accountFlowMapper.selectAll();
        log.info("总数：" + accountFlows.size());
        //List<AccountFlow> accountFlows = new ArrayList<>();
        //accountFlows.add(accountFlowMapper.selectByPrimaryKey(1059398965432426496L));
        List<Long> transfer = transfer(accountFlows);
        Long lastFlowId = accountFlows.get(accountFlows.size() - 1).getFlowId();
        log.info("last:" + lastFlowId);
        transfer.add(lastFlowId);
        log.info("总的--> " + JSON.toJSONString(transfer));
        return transfer;
    }

    private List<Long> transfer(List<AccountFlow> accountFlows){
        List<Long> list = new ArrayList<>();
        for (AccountFlow accountFlow : accountFlows) {
            String beforeActInfo = accountFlow.getBeforeActInfo();
            String afterActInfo = accountFlow.getAfterActInfo();

            JSONObject before = JSONObject.parseObject(beforeActInfo);
            JSONObject after = JSONObject.parseObject(afterActInfo);

            Integer beforeFrozenBalance = 1;
            Integer afterFrozenBalance = 1;
            if(null != before || null != after){
                beforeFrozenBalance = (Integer)before.get("frozenBalance");
                afterFrozenBalance = (Integer)after.get("frozenBalance");
            }
            if(beforeFrozenBalance == 0 || afterFrozenBalance == 0){
                log.info("单个-->" + accountFlow.getFlowId());
                list.add(accountFlow.getFlowId());
            }
        }
        return list;
    }
}
