package com.xinan.cn.common.constants.enums;

public enum OverdueStatusEnum {
  UNKNOWN(999, "未知"),
  NOT_OVERDUE(0, "未逾期"),
  IS_OVERDUE(1, "已逾期"),
  ;
  private int code;
  private String msg;

  OverdueStatusEnum(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
