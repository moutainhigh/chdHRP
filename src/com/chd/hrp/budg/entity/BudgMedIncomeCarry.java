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
 * 医疗收入预算结转
 * @Table:
 * BUDG_MED_INCOME_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgMedIncomeCarry implements Serializable {

	
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
	 * 年度
	 */
	private String year;
	
	/**
	 * 月份
	 */
	private String month;
	
	/**
	 * 是否结转
	 */
	private Integer is_carried;
	

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
	* 设置 年度
	* @param value 
	*/
	public void setYear(String value) {
		this.year = value;
	}
	
	/**
	* 获取 年度
	* @return String
	*/
	public String getYear() {
		return this.year;
	}
	/**
	* 设置 月份
	* @param value 
	*/
	public void setMonth(String value) {
		this.month = value;
	}
	
	/**
	* 获取 月份
	* @return String
	*/
	public String getMonth() {
		return this.month;
	}
	/**
	* 设置 是否结转
	* @param value 
	*/
	public void setIs_carried(Integer value) {
		this.is_carried = value;
	}
	
	/**
	* 获取 是否结转
	* @return Integer
	*/
	public Integer getIs_carried() {
		return this.is_carried;
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