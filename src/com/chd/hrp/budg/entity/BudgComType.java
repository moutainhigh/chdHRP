﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 部件类型表
 * @Table:
 * BUDG_COM_TYPE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgComType implements Serializable {

	
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
	private Object copy_code;
	
	/**
	 * 部件代码
	 */
	private Object com_type_code;
	
	/**
	 * 部件名称
	 */
	private Object com_type_name;
	
	/**
	 * input:下拉框 text:文本框 date:日期框
	 */
	private Object com_type_nature;
	
	/**
	 * 部件说明
	 */
	private Object com_type_note;
	

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
	public void setCopy_code(Object value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return Object
	*/
	public Object getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 部件代码
	* @param value 
	*/
	public void setCom_type_code(Object value) {
		this.com_type_code = value;
	}
	
	/**
	* 获取 部件代码
	* @return Object
	*/
	public Object getCom_type_code() {
		return this.com_type_code;
	}
	/**
	* 设置 部件名称
	* @param value 
	*/
	public void setCom_type_name(Object value) {
		this.com_type_name = value;
	}
	
	/**
	* 获取 部件名称
	* @return Object
	*/
	public Object getCom_type_name() {
		return this.com_type_name;
	}
	/**
	* 设置 input:下拉框 text:文本框 date:日期框
	* @param value 
	*/
	public void setCom_type_nature(Object value) {
		this.com_type_nature = value;
	}
	
	/**
	* 获取 input:下拉框 text:文本框 date:日期框
	* @return Object
	*/
	public Object getCom_type_nature() {
		return this.com_type_nature;
	}
	/**
	* 设置 部件说明
	* @param value 
	*/
	public void setCom_type_note(Object value) {
		this.com_type_note = value;
	}
	
	/**
	* 获取 部件说明
	* @return Object
	*/
	public Object getCom_type_note() {
		return this.com_type_note;
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