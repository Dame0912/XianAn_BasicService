package com.xinan.cn.p2p.litagation.enums;


/**
 * 查询中互金诉讼结果响应码
 */
public enum LawResCodeEnum {

    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数错误"),
    APPKEY_NOT_EXIST(2, "appKey不存在"),
    APPKEY_FORBIDDEN(3, "appKey已被禁用"),
    LICENCE_DATA_ERROR(4, "licence验证失败,数据签名错误"),
    DECODE_DATA_ERROR(5, "解密数据出错"),
    APP_NO_AUTH(6, "服务未授权"),
    LICENCE_TOKEN_INVALID(7, "licence验证失败,访问令牌无效"),
    APP_MAX_TIMES(8, "服务超最大使用次数"),
    IP_NO_AUTH(13, "IP地址未授权"),
    NETWORK_TRANSACTION_ERROR(99, "网络或交易异常"),
    NAME_ILLEGAL(101, "名称不合法（1~40 位）"),
    CARDNO_ILLEGAL(102, "证件号不合法（身份证号：18位加尾号校验）"),
    CALL_METHOD_ERROR(998, "调用方法出错"),
    SERVER_INTERNAL_ERROR(999, "服务器内部错误");

    public final int code;

    public final String codeDesc;

    LawResCodeEnum(int code, String codeDesc) {
        this.code = code;
        this.codeDesc = codeDesc;
    }

    public static String getCodeMsg(int code) {
        for (LawResCodeEnum law : LawResCodeEnum.values()) {
            if (law.code == code) {
                return law.codeDesc;
            }
        }
        return "";
    }

}
