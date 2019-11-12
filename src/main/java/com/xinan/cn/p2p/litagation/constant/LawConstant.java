package com.xinan.cn.p2p.litagation.constant;

import com.xinan.cn.common.bean.PageParam;

/**
 * 涉诉查询常量
 */
public interface LawConstant {

    /**
     * 天数
     */
    int DAYS_LIMIT = 7;

    int COUNT_LIMIT = 5;

    String ZERO = "0";

    String ONE = "1";

    interface LawLoanQueryPage {
        String BEGIN = "begin";
        String END = "end";
        int MAX_SIZE = 50;
    }

    /**
     * 借款人类型
     */
    interface LawIdentityTypeConst {
        /**
         * 身份证
         */
        String ID_CARD = "1";
    }


    /**
     * 借款人类型
     */
    interface LawLoanerTypeConst {
        /**
         * 自然人
         */
        String NATURAL = "1";

        /**
         * 企业
         */
        String COMPANY = "2";
    }

    /**
     * 响应码
     */
    interface LawResponseCodeConst {
        String CODE_0000 = "0000";
        String CODE_0000_CN = "查询成功";
        String CODE_0001 = "0001";
        String CODE_0001_CN = "请求成功，但未查询到信息";
        String CODE_9999 = "9999";
        String CODE_9999_CN = "系统异常";
    }

    /**
     * 请求报文相关常量
     */
    interface LawRequestConst {
        /**
         * 个人标识：姓名
         * 企业标识：企业名称
         */
        String NAME = "name";

        /**
         * 身份证号标识
         */
        String CARD_NO = "cardNo";

        /**
         * 签名标识
         */
        String SIGN = "sign";

        /**
         * 行业类型：P2P互联网金融
         */
        String INDUSTR_TYPE = "01";

        /**
         * 产品类型：01：默认产品； 02：2C 产品； 03：2B 产品
         */
        String PRODUCT_TYPE = "01";

        /**
         * 颁发的授权 key
         */
        String APP_KEY = "b19dbbaac5dd48ddabcae13a6dddc1a6";

        /**
         * 签名 key
         */
        String APP_SECRET = "lkUa094Z9q4BkLUZwdLI4shH";

        /**
         * 个人查询地址
         */
        String NATRUAL_PATH = "http://auth.nifa.org.cn:9082/litigation-service/v1.0/service/verify/people";

        /**
         * 企业查询地址
         */
        String ORG_PATH = "http://auth.nifa.org.cn:9082/litigation-service/v1.0/service/verify/org";
    }

    interface LawResponseConst{

        String COUNT = "count";
    }

}
