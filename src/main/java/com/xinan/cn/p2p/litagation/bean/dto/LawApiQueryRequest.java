package com.xinan.cn.p2p.litagation.bean.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LawApiQueryRequest extends LawLoanerBasicInfo implements Serializable {

    /**
     * 请求标识，32 位， 不可重复
     */
    private String businessId;

}
