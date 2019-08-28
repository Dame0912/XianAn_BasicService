package com.xinan.cn.p2p.report.nifa.bean.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * NIFA中互金请求记录
 *
 * @author lyq
 */
@Data
public class NifaPrjRequestRecord implements Serializable {
	private static final long serialVersionUID = 547115141324079349L;

	private Long id;

	/**
	 * 总条数
	 */
	private Integer totalNum;

	/**
	 * 批次号
	 */
	private String batchNum;

	/**
	 * 类型，31:项目+借款人/法人信息; 32:项目状态或信息变更
	 */
	private String type;

	/**
	 * 状态，-1失败 2成功
	 */
	private Integer status;

	/**
	 * 请求数据
	 */
	private String requestData;

	/**
	 * 响应数据
	 */
	private String responseData;

	/**
	 * 失败原因
	 */
	private String errorMsg;

	/**
	 * 创建时间
	 */
	private Date createdAt;

	/**
	 * 更新时间
	 */
	private Date updatedAt;

	/**
	 * 删除时间
	 */
	private Long deletedAt;

}
