package com.xinan.cn.p2p.litagation.bean.nifa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 企业查询请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyQueryRequest extends LawQueryBasic implements Serializable {
    /**
     * 名称（3des 加密后做 base64 编码）
     */
    public String name;
}
