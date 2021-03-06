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
 * 工资变动
 * @Table:
 * BUDG_WAGE_CHANGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgWageChange implements Serializable {

	
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
	 * 部门ID
	 */
	private Long dept_id;
	
	/**
	 * 职工ID
	 */
	private Long emp_id;
	
	/**
	 * 工资项目编码
	 */
	private String wage_item_code;
	
	/**
	 * 变动前
	 */
	private Double change_before;
	
	/**
	 * 变动后
	 */
	private Double change_after;
	

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
	* 设置 部门ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 部门ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 职工ID
	* @param value 
	*/
	public void setEmp_id(Long value) {
		this.emp_id = value;
	}
	
	/**
	* 获取 职工ID
	* @return Long
	*/
	public Long getEmp_id() {
		return this.emp_id;
	}
	/**
	* 设置 工资项目编码
	* @param value 
	*/
	public void setWage_item_code(String value) {
		this.wage_item_code = value;
	}
	
	/**
	* 获取 工资项目编码
	* @return String
	*/
	public String getWage_item_code() {
		return this.wage_item_code;
	}
	/**
	* 设置 变动前
	* @param value 
	*/
	public void setChange_before(Double value) {
		this.change_before = value;
	}
	
	/**
	* 获取 变动前
	* @return Double
	*/
	public Double getChange_before() {
		return this.change_before;
	}
	/**
	* 设置 变动后
	* @param value 
	*/
	public void setChange_after(Double value) {
		this.change_after = value;
	}
	
	/**
	* 获取 变动后
	* @return Double
	*/
	public Double getChange_after() {
		return this.change_after;
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