package com.xinan.cn.p2p.report.nifa.constant;

public interface NifaConstant {

	/**
	 * 竖线分隔符
	 */
	String VERTICAL_SEPARATOR = "\\|";

	/**
	 * 项目 + 借款人
	 */
	String PRJ_NATURAL = "1";

	/**
	 * 项目 + 法人
	 */
	String PRJ_LEGAL = "2";

	/**
	 * 项目状态或信息变更
	 */
	String PRJ_CHANGE = "3";

	/**
	 * 报文体KEY：借款项目信息 Key
	 */
	String PRJ_INFO_KEY = "REPAYPROJECT";

	/**
	 * 报文体内部：借款人(自然人)信息 Key
	 */
	String PRJ_NATURAL_KEY = "REPAYMENINFO";

	/**
	 * 报文体内部：借款人(法人/组织)信息 Key
	 */
	String PRJ_LEGAL_KEY = "REPAYMEN_LEGAL";

	/**
	 * 报文体内部：借款项目状态或信息变更 Key
	 */
	String PRJ_CHANGE_KEY = "PLATFORMDEBTTRANSFERPROJECT";

}
