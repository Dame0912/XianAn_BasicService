package com.xinan.cn.common.constants.enums;

public enum ClaimStatusEnum {
  UNKNOWN(999, "未知"),
  CLAIM_NOT_TRIGGER(0, "未触发代偿"),
  CLAIM_TRIGGERED(10, "已触发代偿"),
  CLAIM_PROCESSING(11, "代偿处理中"),
  CLAIM_SUCCESS(12, "代偿成功"),
  CLAIM_FAILED(14, "代偿失败"),;
  private int code;
  private String msg;

  public static boolean isAllowChange(ClaimStatusEnum preStatus, ClaimStatusEnum newStatus) {
    if (preStatus == newStatus) {
      return false;
    }

    if (preStatus == ClaimStatusEnum.CLAIM_SUCCESS) {
      return false;
    }

    if (preStatus == ClaimStatusEnum.CLAIM_NOT_TRIGGER) {
      return newStatus == ClaimStatusEnum.CLAIM_TRIGGERED;
    }

    if (preStatus == ClaimStatusEnum.CLAIM_TRIGGERED) {
      return newStatus == ClaimStatusEnum.CLAIM_PROCESSING || newStatus == ClaimStatusEnum.CLAIM_NOT_TRIGGER;
    }

    if (preStatus == ClaimStatusEnum.CLAIM_PROCESSING) {
      return newStatus == ClaimStatusEnum.CLAIM_SUCCESS || newStatus == ClaimStatusEnum.CLAIM_FAILED;
    }

    if (preStatus == ClaimStatusEnum.CLAIM_FAILED) {
      return newStatus == ClaimStatusEnum.CLAIM_PROCESSING;
    }
    return false;
  }

  public static ClaimStatusEnum getByCode(int code) {
    for (ClaimStatusEnum claimStatusEnum : values()) {
      if (claimStatusEnum.getCode() == code) {
        return claimStatusEnum;
      }
    }
    return UNKNOWN;
  }

  ClaimStatusEnum(int code, String msg) {
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
