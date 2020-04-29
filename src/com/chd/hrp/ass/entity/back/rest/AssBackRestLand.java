﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.back.rest;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050701 资产其他退账单主表(土地)
 * @Table:
 * ASS_BACK_REST_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssBackRestLand implements Serializable {

	
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
	 * 业务单号
	 */
	private String ass_back_no;
	
	/**
	 * 业务类型
	 */
	private String bus_type_code;
	
	private String bus_type_name;
	
	/**
	 * 退货金额
	 */
	private Double back_money;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 制单人
	 */
	private Long create_emp;
	
	private String create_emp_name;
	
	/**
	 * 制单日期
	 */
	private Date create_date;
	
	/**
	 * 入账日期
	 */
	private Date back_date;
	
	/**
	 * 确认人
	 */
	private Long confirm_emp;
	
	private String confirm_emp_name;
	
	/**
	 * 0:新建 1:审核 2:确认
	 */
	private Integer state;
	
	private String state_name;
	

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
	* 设置 业务单号
	* @param value 
	*/
	public void setAss_back_no(String value) {
		this.ass_back_no = value;
	}
	
	/**
	* 获取 业务单号
	* @return String
	*/
	public String getAss_back_no() {
		return this.ass_back_no;
	}
	/**
	* 设置 业务类型
	* @param value 
	*/
	public void setBus_type_code(String value) {
		this.bus_type_code = value;
	}
	
	/**
	* 获取 业务类型
	* @return String
	*/
	public String getBus_type_code() {
		return this.bus_type_code;
	}
	/**
	* 设置 退货金额
	* @param value 
	*/
	public void setBack_money(Double value) {
		this.back_money = value;
	}
	
	/**
	* 获取 退货金额
	* @return Double
	*/
	public Double getBack_money() {
		return this.back_money;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getNote() {
		return this.note;
	}
	/**
	* 设置 制单人
	* @param value 
	*/
	public void setCreate_emp(Long value) {
		this.create_emp = value;
	}
	
	/**
	* 获取 制单人
	* @return Long
	*/
	public Long getCreate_emp() {
		return this.create_emp;
	}
	/**
	* 设置 制单日期
	* @param value 
	*/
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	
	/**
	* 获取 制单日期
	* @return Date
	*/
	public Date getCreate_date() {
		return this.create_date;
	}
	/**
	* 设置 入账日期
	* @param value 
	*/
	public void setBack_date(Date value) {
		this.back_date = value;
	}
	
	/**
	* 获取 入账日期
	* @return Date
	*/
	public Date getBack_date() {
		return this.back_date;
	}
	/**
	* 设置 确认人
	* @param value 
	*/
	public void setConfirm_emp(Long value) {
		this.confirm_emp = value;
	}
	
	/**
	* 获取 确认人
	* @return Long
	*/
	public Long getConfirm_emp() {
		return this.confirm_emp;
	}
	/**
	* 设置 0:新建 1:审核 2:确认
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 0:新建 1:审核 2:确认
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
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

	public String getBus_type_name() {
		return bus_type_name;
	}

	public void setBus_type_name(String bus_type_name) {
		this.bus_type_name = bus_type_name;
	}

	public String getCreate_emp_name() {
		return create_emp_name;
	}

	public void setCreate_emp_name(String create_emp_name) {
		this.create_emp_name = create_emp_name;
	}

	public String getConfirm_emp_name() {
		return confirm_emp_name;
	}

	public void setConfirm_emp_name(String confirm_emp_name) {
		this.confirm_emp_name = confirm_emp_name;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	
	
}