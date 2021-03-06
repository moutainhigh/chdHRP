﻿
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 9905 绩效函数参数栈
 * @Table:
 * PRM_FUN_STACK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmFunStack implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 账套编码
	 */
	private String copy_code;
	
	/**
	 * 指标代码
	 */
	private String target_code;
	
	/**
	 * 参数代码
	 */
	private String fun_para_code;
	
	/**
	 * 参数数据
	 */
	private String fun_para_value;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 指标代码
	* @param value 
	*/
	public void setTarget_code(String value) {
		this.target_code = value;
	}
	
	/**
	* 获取 指标代码
	* @return String
	*/
	public String getTarget_code() {
		return this.target_code;
	}
	/**
	* 设置 参数代码
	* @param value 
	*/
	public void setFun_para_code(String value) {
		this.fun_para_code = value;
	}
	
	/**
	* 获取 参数代码
	* @return String
	*/
	public String getFun_para_code() {
		return this.fun_para_code;
	}
	/**
	* 设置 参数数据
	* @param value 
	*/
	public void setFun_para_value(String value) {
		this.fun_para_value = value;
	}
	
	/**
	* 获取 参数数据
	* @return String
	*/
	public String getFun_para_value() {
		return this.fun_para_value;
	}
	
	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}
}