﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 08117 包装单位表
 * @Table:
 * HOS_PACKAGE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class HosPackage implements Serializable {

	
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
	 * 包装单位编码
	 */
	private String pack_code;
	
	/**
	 * 包装单位名称
	 */
	private String pack_name;
	
	
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 备注
	 */
	private String note;
	

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
	* 设置 包装单位编码
	* @param value 
	*/
	public void setPack_code(String value) {
		this.pack_code = value;
	}
	
	/**
	* 获取 包装单位编码
	* @return String
	*/
	public String getPack_code() {
		return this.pack_code;
	}
	
	
	
	
	/**
	* 设置 包装单位名称
	* @param value 
	*/
	public void setPack_name(String value) {
		this.pack_name = value;
	}
	
	/**
	* 获取 包装单位名称
	* @return String
	*/
	public String getPack_name() {
		return this.pack_name;
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
	* 设置 五笔码
	* @param value 
	*/
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	
	/**
	* 获取 五笔码
	* @return String
	*/
	public String getWbx_code() {
		return this.wbx_code;
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