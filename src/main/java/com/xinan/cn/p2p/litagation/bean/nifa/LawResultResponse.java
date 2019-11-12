package com.xinan.cn.p2p.litagation.bean.nifa;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 涉诉信息查询响应
 */
@Data
public class LawResultResponse implements Serializable {
    /**
     * 状态码 0:成功 非 0:失败， 详情参照错误码定义
     */
    @JSONField(name = "code")
    private String code;

    /**
     * 可选， 出错时返回错误信息， 详情参照业务详细返回描述
     */
    @JSONField(name = "message")
    private String message;

    /**
     * 请求 Id， 32 位
     */
    @JSONField(name = "requestId")
    private String requestId;

    /**
     * 查询结果(0:成功， 1:未查到)
     */
    @JSONField(name = "result")
    private String result;

    /**
     * 查询结果， 详见 5： 相关字段说明表
     */
    @JSONField(name = "data")
    private String data;
}
