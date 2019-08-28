package com.xinan.cn.p2p.report.nifa.constant;

/**
 * 
 * <概述>：项目报送状态
 *
 * @author lyq
 *
 */
public enum NifaReportStatusEnum {
	FAIL(-1, "失败"), WATING(0, "待处理"), PROCESSING(1, "处理中"), SUCCESS(2, "成功");

	public final int reportStatus;

	public final String desc;

	NifaReportStatusEnum(int reportStatus, String desc) {
		this.reportStatus = reportStatus;
		this.desc = desc;
	}

	public static String getDesc(int reportStatus) {
		for (NifaReportStatusEnum prjReportStatus : NifaReportStatusEnum.values()) {
			if (prjReportStatus.reportStatus == reportStatus) {
				return prjReportStatus.desc;
			}
		}
		return null;
	}
}
