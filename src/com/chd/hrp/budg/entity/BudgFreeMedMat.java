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
 * tabledesc
 * @Table:
 * BUDG_FREE_MED_MAT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgFreeMedMat implements Serializable {

	
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
	 * 月
	 */
	private String month;
	
	/**
	 * 部门ID
	 */
	private Long dept_id;
	
	/**
	 * 物资类别ID
	 */
	private Long mat_type_id;
	
	/**
	 * 业务量预算（门诊）
	 */
	private Double work_budg_out;
	
	/**
	 * 业务量预算（住院）
	 */
	private Double work_budg_in;
	
	/**
	 * 业务量预算（检查）
	 */
	private Double work_budg_check;
	
	/**
	 * 业务量支出配比（门诊）
	 */
	private Double work_cost_rate_out;
	
	/**
	 * 业务量支出配比（住院）
	 */
	private Double work_cost_rate_in;
	
	/**
	 * 业务量支出配比（检查）
	 */
	private Double work_cost_rate_check;
	
	/**
	 * 计算值
	 */
	private Double count_value;
	
	/**
	 * 调整比例
	 */
	private Double adj_rate;
	
	/**
	 * 支出预算
	 */
	private Double cost_budg;
	
	/**
	 * 说明
	 */
	private String remark;
	

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
	* 设置 月
	* @param value 
	*/
	public void setMonth(String value) {
		this.month = value;
	}
	
	/**
	* 获取 月
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
	* 设置 物资类别ID
	* @param value 
	*/
	public void setMat_type_id(Long value) {
		this.mat_type_id = value;
	}
	
	/**
	* 获取 物资类别ID
	* @return Long
	*/
	public Long getMat_type_id() {
		return this.mat_type_id;
	}
	/**
	* 设置 业务量预算（门诊）
	* @param value 
	*/
	public void setWork_budg_out(Double value) {
		this.work_budg_out = value;
	}
	
	/**
	* 获取 业务量预算（门诊）
	* @return Double
	*/
	public Double getWork_budg_out() {
		return this.work_budg_out;
	}
	/**
	* 设置 业务量预算（住院）
	* @param value 
	*/
	public void setWork_budg_in(Double value) {
		this.work_budg_in = value;
	}
	
	/**
	* 获取 业务量预算（住院）
	* @return Double
	*/
	public Double getWork_budg_in() {
		return this.work_budg_in;
	}
	/**
	* 设置 业务量预算（检查）
	* @param value 
	*/
	public void setWork_budg_check(Double value) {
		this.work_budg_check = value;
	}
	
	/**
	* 获取 业务量预算（检查）
	* @return Double
	*/
	public Double getWork_budg_check() {
		return this.work_budg_check;
	}
	/**
	* 设置 业务量支出配比（门诊）
	* @param value 
	*/
	public void setWork_cost_rate_out(Double value) {
		this.work_cost_rate_out = value;
	}
	
	/**
	* 获取 业务量支出配比（门诊）
	* @return Double
	*/
	public Double getWork_cost_rate_out() {
		return this.work_cost_rate_out;
	}
	/**
	* 设置 业务量支出配比（住院）
	* @param value 
	*/
	public void setWork_cost_rate_in(Double value) {
		this.work_cost_rate_in = value;
	}
	
	/**
	* 获取 业务量支出配比（住院）
	* @return Double
	*/
	public Double getWork_cost_rate_in() {
		return this.work_cost_rate_in;
	}
	/**
	* 设置 业务量支出配比（检查）
	* @param value 
	*/
	public void setWork_cost_rate_check(Double value) {
		this.work_cost_rate_check = value;
	}
	
	/**
	* 获取 业务量支出配比（检查）
	* @return Double
	*/
	public Double getWork_cost_rate_check() {
		return this.work_cost_rate_check;
	}
	/**
	* 设置 计算值
	* @param value 
	*/
	public void setCount_value(Double value) {
		this.count_value = value;
	}
	
	/**
	* 获取 计算值
	* @return Double
	*/
	public Double getCount_value() {
		return this.count_value;
	}
	/**
	* 设置 调整比例
	* @param value 
	*/
	public void setAdj_rate(Double value) {
		this.adj_rate = value;
	}
	
	/**
	* 获取 调整比例
	* @return Double
	*/
	public Double getAdj_rate() {
		return this.adj_rate;
	}
	/**
	* 设置 支出预算
	* @param value 
	*/
	public void setCost_budg(Double value) {
		this.cost_budg = value;
	}
	
	/**
	* 获取 支出预算
	* @return Double
	*/
	public Double getCost_budg() {
		return this.cost_budg;
	}
	/**
	* 设置 说明
	* @param value 
	*/
	public void setRemark(String value) {
		this.remark = value;
	}
	
	/**
	* 获取 说明
	* @return String
	*/
	public String getRemark() {
		return this.remark;
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