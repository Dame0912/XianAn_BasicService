package com.xinan.cn.p2p.report.nifa.bean.dto;

import com.xinan.cn.p2p.report.nifa.bean.LegalInfo;
import com.xinan.cn.p2p.report.nifa.bean.ProjectInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrjLegalDTO {
	private ProjectInfo projectInfo;
	private LegalInfo legalInfo;
}
