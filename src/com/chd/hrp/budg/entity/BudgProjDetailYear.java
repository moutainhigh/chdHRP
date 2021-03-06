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
 * 年度项目预算明细
 * @Table:
 * BUDG_PROJ_DETAIL_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgProjDetailYear implements Serializable {

	
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
	 * 预算年度
	 */
	private String budg_year;
	
	/**
	 * 项目ID
	 */
	private Long proj_id;
	
	/**
	 * 用自增ID
	 */
	private Long source_id;
	
	/**
	 * 支出项目ID
	 */
	private Long payment_item_id;
	
	/**
	 * 期初预算金额
	 */
	private Double b_budg_amount;
	
	/**
	 * 期初支出金额
	 */
	private Double b_cost_amount;
	
	/**
	 * 期初预算余额
	 */
	private Double b_remain_amoun;
	
	/**
	 * 本期预算金额
	 */
	private Double budg_amount;
	
	/**
	 * 本期支出金额
	 */
	private Double cost_amount;
	
	/**
	 * 预算金额累计
	 */
	private Double y_budg_amount;
	
	/**
	 * 支出金额累计
	 */
	private Double y_cost_amount;
	
	/**
	 * 预算余额
	 */
	private Double remain_amoun;
	
	/**
	 * 执行进度
	 */
	private Double rate;
	
	/**
	 * 累计执行进度
	 */
	private Double t_rate;
	
	/**
	* 设置 累计执行进度
	* @param value 
	*/
  public Double getT_rate() {
		return t_rate;
	}

  /**
	* 获取 累计执行进度
	* @param value 
	*/
	public void setT_rate(Double t_rate) {
		this.t_rate = t_rate;
	}
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
	* 设置 预算年度
	* @param value 
	*/
	public void setBudg_year(String value) {
		this.budg_year = value;
	}
	
	/**
	* 获取 预算年度
	* @return String
	*/
	public String getBudg_year() {
		return this.budg_year;
	}
	/**
	* 设置 项目ID
	* @param value 
	*/
	public void setProj_id(Long value) {
		this.proj_id = value;
	}
	
	/**
	* 获取 项目ID
	* @return Long
	*/
	public Long getProj_id() {
		return this.proj_id;
	}
	/**
	* 设置 用自增ID
	* @param value 
	*/
	public void setSource_id(Long value) {
		this.source_id = value;
	}
	
	/**
	* 获取 用自增ID
	* @return Long
	*/
	public Long getSource_id() {
		return this.source_id;
	}
	/**
	* 设置 支出项目ID
	* @param value 
	*/
	public void setPayment_item_id(Long value) {
		this.payment_item_id = value;
	}
	
	/**
	* 获取 支出项目ID
	* @return Long
	*/
	public Long getPayment_item_id() {
		return this.payment_item_id;
	}
	/**
	* 设置 期初预算金额
	* @param value 
	*/
	public void setB_budg_amount(Double value) {
		this.b_budg_amount = value;
	}
	
	/**
	* 获取 期初预算金额
	* @return Double
	*/
	public Double getB_budg_amount() {
		return this.b_budg_amount;
	}
	/**
	* 设置 期初支出金额
	* @param value 
	*/
	public void setB_cost_amount(Double value) {
		this.b_cost_amount = value;
	}
	
	/**
	* 获取 期初支出金额
	* @return Double
	*/
	public Double getB_cost_amount() {
		return this.b_cost_amount;
	}
	/**
	* 设置 期初预算余额
	* @param value 
	*/
	public void setB_remain_amoun(Double value) {
		this.b_remain_amoun = value;
	}
	
	/**
	* 获取 期初预算余额
	* @return Double
	*/
	public Double getB_remain_amoun() {
		return this.b_remain_amoun;
	}
	/**
	* 设置 本期预算金额
	* @param value 
	*/
	public void setBudg_amount(Double value) {
		this.budg_amount = value;
	}
	
	/**
	* 获取 本期预算金额
	* @return Double
	*/
	public Double getBudg_amount() {
		return this.budg_amount;
	}
	
	/**
	* 设置 本期支出金额
	* @param value 
	*/
	public void setCost_amount(Double value) {
		this.cost_amount = value;
	}
	
	/**
	* 获取 本期支出金额
	* @return Double
	*/
	public Double getCost_amount() {
		return this.cost_amount;
	}
	/**
	* 设置 预算金额累计
	* @param value 
	*/
	public void setY_budg_amount(Double value) {
		this.y_budg_amount = value;
	}
	
	/**
	* 获取 预算金额累计
	* @return Double
	*/
	public Double getY_budg_amount() {
		return this.y_budg_amount;
	}
	/**
	* 设置 支出金额累计
	* @param value 
	*/
	public void setY_cost_amount(Double value) {
		this.y_cost_amount = value;
	}
	
	/**
	* 获取 支出金额累计
	* @return Double
	*/
	public Double getY_cost_amount() {
		return this.y_cost_amount;
	}
	/**
	* 设置 预算余额
	* @param value 
	*/
	public void setRemain_amoun(Double value) {
		this.remain_amoun = value;
	}
	
	/**
	* 获取 预算余额
	* @return Double
	*/
	public Double getRemain_amoun() {
		return this.remain_amoun;
	}
	/**
	* 设置 执行进度
	* @param value 
	*/
	public void setRate(Double value) {
		this.rate = value;
	}
	
	/**
	* 获取 执行进度
	* @return Double
	*/
	public Double getRate() {
		return this.rate;
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