﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.file;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 资产文档_一般设备
 * @Table:
 * ASS_FILE_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssFileGeneral implements Serializable {

	
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
	 * 卡片编号
	 */
	private String ass_card_no;
	
	/**
	 * 文档编号
	 */
	private String file_code;
	
	/**
	 * 文档名称
	 */
	private String file_name;
	
	/**
	 * 文档类别
	 */
	private String equi_usage_code;
	
	/**
	 * 文档路径
	 */
	private String file_url;
	
	/**
	 * 存档位置
	 */
	private String location;
	
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
	
	private String equi_usage_name;
	
	
	
	public String getEqui_usage_name() {
		return equi_usage_name;
	}

	public void setEqui_usage_name(String equi_usage_name) {
		this.equi_usage_name = equi_usage_name;
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
	* 设置 卡片编号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 卡片编号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	/**
	* 设置 文档编号
	* @param value 
	*/
	public void setFile_code(String value) {
		this.file_code = value;
	}
	
	/**
	* 获取 文档编号
	* @return String
	*/
	public String getFile_code() {
		return this.file_code;
	}
	/**
	* 设置 文档名称
	* @param value 
	*/
	public void setFile_name(String value) {
		this.file_name = value;
	}
	
	/**
	* 获取 文档名称
	* @return String
	*/
	public String getFile_name() {
		return this.file_name;
	}
	/**
	* 设置 文档类别
	* @param value 
	*/
	public void setEqui_usage_code(String value) {
		this.equi_usage_code = value;
	}
	
	/**
	* 获取 文档类别
	* @return String
	*/
	public String getEqui_usage_code() {
		return this.equi_usage_code;
	}
	/**
	* 设置 文档路径
	* @param value 
	*/
	public void setFile_url(String value) {
		this.file_url = value;
	}
	
	/**
	* 获取 文档路径
	* @return String
	*/
	public String getFile_url() {
		return this.file_url;
	}
	/**
	* 设置 存档位置
	* @param value 
	*/
	public void setLocation(String value) {
		this.location = value;
	}
	
	/**
	* 获取 存档位置
	* @return String
	*/
	public String getLocation() {
		return this.location;
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