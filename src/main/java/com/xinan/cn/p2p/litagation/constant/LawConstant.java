package com.xinan.cn.p2p.litagation.constant;

/**
 * 涉诉查询常量
 */
public interface LawConstant {

    String ZERO = "0";

    String ONE = "1";

    /**
     * 天数
     */
    int DAYS_LIMIT = 7;

    /**
     * 重复次数
     */
    int COUNT_LIMIT = 5;

    interface LawApiResponceCodeConst {

        /**
         * 成功
         */
        String CODE_0000 = "0000";
        String CODE_0000_CN = "成功";

        /**
         * 参数违反接口约定：
         * 请求参数不合法
         * 返回参数不合法
         */
        String CODE_9997 = "9997";
        String CODE_9997_CN = "请求中互金查询异常";

        /**
         * 参数违反接口约定：
         * 请求参数不合法
         * 返回参数不合法
         */
        String CODE_9998 = "9998";
        String CODE_9998_CN = "参数违反接口约定";

        /**
         * 系统未定义异常
         */
        String CODE_9999 = "9999";
        String CODE_9999_CN = "系统未定义异常";
    }

    interface LawReqRecodeConst {
        /**
         * 等待
         */
        Integer WAITING = 0;
        /**
         * 失败
         */
        Integer FAIL = 1;
        /**
         * 成功
         */
        Integer SUCCESS = 2;
    }

    /**
     * 分页常量
     */
    interface LawLoanQueryPage {
        /**
         * 开始标识
         */
        String BEGIN = "begin";

        /**
         * 结束标识
         */
        String END = "end";

        /**
         * 每页最大数量
         */
        Integer MAX_SIZE = 50;
    }

    /**
     * 证件类型
     */
    interface LawCardTypeConst {
        /**
         * 身份证
         */
        Integer IDENTITY_CARD = 1;

        /**
         * 营业执照
         */
        Integer BUSINESS_LICENSE = 2;
    }

    /**
     * 借款人类型
     */
    interface LawUserTypeConst {
        /**
         * 个人
         */
        Integer NATURAL = 1;

        /**
         * 企业
         */
        Integer COMPANY = 2;
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

    /**
     * 响应报文相关常量
     */
    interface LawResponseConst {
        String COUNT = "count";
    }

}
