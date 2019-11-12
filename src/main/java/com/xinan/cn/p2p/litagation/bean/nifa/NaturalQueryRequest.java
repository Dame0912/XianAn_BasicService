package com.xinan.cn.p2p.litagation.bean.nifa;

import lombok.Data;

import java.io.Serializable;

/**
 * 自然人查询请求参数
 */
@Data
public class NaturalQueryRequest extends LawQueryBasic implements Serializable {
    /**
     * 名称（3des 加密后做 base64 编码）
     */
    public String name;
    /**
     * 身份证号（3des 加密后做 base64 编码）
     */
    public String cardNo;
}
