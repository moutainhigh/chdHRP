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
 * 科室月份业务预算备份
 * @Table:
 * BUDG_WORK_DEPT_MONTH_COPY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgWorkDeptMonthCopy implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 调整单号
	 */
	private String adj_code;
	
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
	 * 指标编码
	 */
	private String index_code;
	
	/**
	 * 部门ID
	 */
	private Long dept_id;
	
	/**
	 * 月份
	 */
	private String month;
	
	/**
	 * 计算值
	 */
	private Double count_value;
	
	/**
	 * 预算值
	 */
	private Double budg_value;
	
	/**
	 * 说明
	 */
	private String remark;
	
	/**
	 * 增长比例
	 */
	private Double grow_rate;
	
	/**
	 * 分解比例
	 */
	private Double resolve_rate;
	
	/**
	 * 上年业务量
	 */
	private Double last_year_workload;
	
	/**
	 * 上月结转
	 */
	private Double last_month_carried;
	
	/**
	 * 结转下月
	 */
	private Double carried_next_month;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 调整单号
	* @param value 
	*/
	public void setAdj_code(String value) {
		this.adj_code = value;
	}
	
	/**
	* 获取 调整单号
	* @return String
	*/
	public String getAdj_code() {
		return this.adj_code;
	}
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
	* 设置 指标编码
	* @param value 
	*/
	public void setIndex_code(String value) {
		this.index_code = value;
	}
	
	/**
	* 获取 指标编码
	* @return String
	*/
	public String getIndex_code() {
		return this.index_code;
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
	* 设置 预算值
	* @param value 
	*/
	public void setBudg_value(Double value) {
		this.budg_value = value;
	}
	
	/**
	* 获取 预算值
	* @return Double
	*/
	public Double getBudg_value() {
		return this.budg_value;
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
	* 设置 增长比例
	* @param value 
	*/
	public void setGrow_rate(Double value) {
		this.grow_rate = value;
	}
	
	/**
	* 获取 增长比例
	* @return Double
	*/
	public Double getGrow_rate() {
		return this.grow_rate;
	}
	/**
	* 设置 分解比例
	* @param value 
	*/
	public void setResolve_rate(Double value) {
		this.resolve_rate = value;
	}
	
	/**
	* 获取 分解比例
	* @return Double
	*/
	public Double getResolve_rate() {
		return this.resolve_rate;
	}
	/**
	* 设置 上年业务量
	* @param value 
	*/
	public void setLast_year_workload(Double value) {
		this.last_year_workload = value;
	}
	
	/**
	* 获取 上年业务量
	* @return Double
	*/
	public Double getLast_year_workload() {
		return this.last_year_workload;
	}
	/**
	* 设置 上月结转
	* @param value 
	*/
	public void setLast_month_carried(Double value) {
		this.last_month_carried = value;
	}
	
	/**
	* 获取 上月结转
	* @return Double
	*/
	public Double getLast_month_carried() {
		return this.last_month_carried;
	}
	/**
	* 设置 结转下月
	* @param value 
	*/
	public void setCarried_next_month(Double value) {
		this.carried_next_month = value;
	}
	
	/**
	* 获取 结转下月
	* @return Double
	*/
	public Double getCarried_next_month() {
		return this.carried_next_month;
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