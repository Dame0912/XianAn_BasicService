package com.xinan.cn.p2p.litagation.bean.dto;

import com.xinan.cn.p2p.litagation.bean.nifa.LawDetailResponse;
import lombok.Data;

import java.io.Serializable;

@Data
public class LawApiQueryResponse implements Serializable {

    /**
     * 请求标识，32 位， 不可重复
     */
    private String businessId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 响应码
     */
    private String responseCode;

    /**
     * 响应信息
     */
    private String responseMsg;

    /**
     * 详情
     */
    private LawDetailResponse data;

}
