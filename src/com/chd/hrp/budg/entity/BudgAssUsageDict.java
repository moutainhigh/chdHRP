﻿
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050104 资产用途
 * @Table:
 * ASS_USAGE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class BudgAssUsageDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
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
	 * 用途编码
	 */
	private String equi_usage_code;
	
	/**
	 * 用途名称
	 */
	private String equi_usage_name;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
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
	* 设置 用途编码
	* @param value 
	*/
	public void setEqui_usage_code(String value) {
		this.equi_usage_code = value;
	}
	
	/**
	* 获取 用途编码
	* @return String
	*/
	public String getEqui_usage_code() {
		return this.equi_usage_code;
	}
	/**
	* 设置 用途名称
	* @param value 
	*/
	public void setEqui_usage_name(String value) {
		this.equi_usage_name = value;
	}
	
	/**
	* 获取 用途名称
	* @return String
	*/
	public String getEqui_usage_name() {
		return this.equi_usage_name;
	}
	/**
	* 设置 拼音码
	* @param value 
	*/
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	
	/**
	* 获取 拼音码
	* @return String
	*/
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	* 获取 五笔码
	* @return String
	*/
    public String getWbx_code() {
    	return wbx_code;
    }

    /**
	* 设置 五笔码
	* @param value 
	*/
    public void setWbx_code(String wbx_code) {
    	this.wbx_code = wbx_code;
    }
	/**
	* 设置 是否停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 是否停用
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
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