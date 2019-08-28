package com.xinan.cn.p2p.report.nifa.bean.dto;

import com.xinan.cn.p2p.report.nifa.bean.NaturalInfo;
import com.xinan.cn.p2p.report.nifa.bean.ProjectInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrjNaturalDTO {
	private ProjectInfo projectInfo;
	private NaturalInfo naturalInfo;
}
