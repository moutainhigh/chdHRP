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
 * 科室职称平均工资
 * @Table:
 * BUDG_DEPT_TITLE_AVG_WAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgDeptTitleAvgWage implements Serializable {

	
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
	 * 部门ID
	 */
	private Long dept_id;
	
	/**
	 * 职称编码
	 */
	private String title_code;
	
	/**
	 * 工资项目编码
	 */
	private String wage_item_code;
	
	/**
	 * 金额
	 */
	private Double amount;
	

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
	* 设置 职称编码
	* @param value 
	*/
	public void setTitle_code(String value) {
		this.title_code = value;
	}
	
	/**
	* 获取 职称编码
	* @return String
	*/
	public String getTitle_code() {
		return this.title_code;
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
	* 设置 金额
	* @param value 
	*/
	public void setAmount(Double value) {
		this.amount = value;
	}
	
	/**
	* 获取 金额
	* @return Double
	*/
	public Double getAmount() {
		return this.amount;
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