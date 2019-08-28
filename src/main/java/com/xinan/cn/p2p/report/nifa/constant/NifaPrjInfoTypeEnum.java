package com.xinan.cn.p2p.report.nifa.constant;

/**
 * 
 * <概述>：项目类型
 *
 * @author lyq
 *
 */
public enum NifaPrjInfoTypeEnum {
    PRJ_NATURAL("1", "项目 + 自然人"), PRJ_LEGAL("2", "项目 + 法人"), PRJ_CHANGE("3", "项目信息变更");

    public final String type;

    public final String desc;

    NifaPrjInfoTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDesc(String type) {
        for (NifaPrjInfoTypeEnum prjInfoType : NifaPrjInfoTypeEnum.values()) {
            if (prjInfoType.type.equals(type)) {
                return prjInfoType.desc;
            }
        }
        return null;
    }
}
