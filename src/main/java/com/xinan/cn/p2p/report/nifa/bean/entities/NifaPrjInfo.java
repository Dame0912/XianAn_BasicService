package com.xinan.cn.p2p.report.nifa.bean.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;

/**
 * NIFA中互金 项目信息
 *
 * @author lyq
 */
@Data
public class NifaPrjInfo implements Serializable {
	private static final long serialVersionUID = 4759788336646193925L;
	@Id
    @Column
	private Long id;

	@Column
    private String prjNum;

	@Column
    private String prjStatus;

	@Column
    private String batchNum;

	@Column
    private String nonce;

	@Column
    private String type;

	@Column
    private String data;

	@Column
    private Integer status;

	@Column
    private String errorMsg;

	@Column
    private Date createdAt;

	@Column
    private Date updatedAt;

	@Column
    private Long deletedAt;

}
