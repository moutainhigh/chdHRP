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
 * 取值方法（GET_VALUE_WAY）
01手工录入
02取值函数
03计算公式

 * @Table:
 * BUDG_CHARGE_STAND_GET_WAY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgChargeStandGetWay implements Serializable {

	
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
	 * 费用标准编码
	 */
	private String charge_stand_code;
	
	/**
	 * 取值方法
	 */
	private String get_value_way;
	
	/**
	 * 计算公式ID
	 */
	private String formula_id;
	
	/**
	 * 取值函数ID
	 */
	private String fun_id;
	

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
	* 设置 费用标准编码
	* @param value 
	*/
	public void setCharge_stand_code(String value) {
		this.charge_stand_code = value;
	}
	
	/**
	* 获取 费用标准编码
	* @return String
	*/
	public String getCharge_stand_code() {
		return this.charge_stand_code;
	}
	/**
	* 设置 取值方法
	* @param value 
	*/
	public void setGet_value_way(String value) {
		this.get_value_way = value;
	}
	
	/**
	* 获取 取值方法
	* @return String
	*/
	public String getGet_value_way() {
		return this.get_value_way;
	}
	/**
	* 设置 计算公式ID
	* @param value 
	*/
	public void setFormula_id(String value) {
		this.formula_id = value;
	}
	
	/**
	* 获取 计算公式ID
	* @return String
	*/
	public String getFormula_id() {
		return this.formula_id;
	}
	/**
	* 设置 取值函数ID
	* @param value 
	*/
	public void setFun_id(String value) {
		this.fun_id = value;
	}
	
	/**
	* 获取 取值函数ID
	* @return String
	*/
	public String getFun_id() {
		return this.fun_id;
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