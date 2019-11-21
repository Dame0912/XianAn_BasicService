package com.xinan.cn.p2p.litagation.bean.nifa;

import com.xinan.cn.p2p.litagation.constant.LawCasesConstant;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 诉讼查询公用参数
 */
@Data
public class LawCasesQueryBasic implements LawCasesConstant, Serializable {

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

    public LawCasesQueryBasic() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
        String clientTime = simpleDateFormat.format(date);
        this.appKey = LawRequestConst.APP_KEY;
        this.transactionNo = UUID.randomUUID().toString().replace("-", "");
        this.productType = LawRequestConst.PRODUCT_TYPE;
        this.industrType = LawRequestConst.INDUSTR_TYPE;
        this.clientTime = clientTime;
    }

}
