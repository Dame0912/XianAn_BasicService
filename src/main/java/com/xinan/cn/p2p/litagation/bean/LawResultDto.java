package com.xinan.cn.p2p.litagation.bean;

import com.xinan.cn.p2p.litagation.bean.nifa.LawDetailResponse;
import lombok.Data;

import java.io.Serializable;

@Data
public class LawResultDto implements Serializable {
    private String code;
    private String message;
    private String requestId;
    private String result;
    private LoanBasicInfo loanBasicInfo;
    private LawDetailResponse lawDetailResponse;
}
