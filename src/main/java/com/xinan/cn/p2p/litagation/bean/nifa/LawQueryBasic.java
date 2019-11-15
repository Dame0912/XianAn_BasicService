package com.xinan.cn.p2p.litagation.bean.nifa;

import lombok.Data;

import java.io.Serializable;

/**
 * 诉讼查询公用参数
 */
@Data
public class LawQueryBasic implements Serializable {
    /**
     * 颁发的授权 key
     */
    public String appKey;

    /**
     * 请求的标识， 32 位， 不可重复
     */
    public String transactionNo;

    /**
     * 产品类型， 请参考附录， 必填
     */
    public String productType;

    /**
     * 行业类型， 请参考附录， 必填
     */
    public String industrType;

    /**
     * 请求时间， 格式： yyyyMMddHHmmss
     */
    public String clientTime;

}
