﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.tran.out;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050901 资产领用明细_其他固定资产
 * @Table:
 * ASS_OUT_DETAIL_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssOutDetailOther implements Serializable {

	
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
	 * 资产转移单号
	 */
	private String out_no;
	
	/**
	 * 卡片编号
	 */
	private String ass_card_no;
	
	/**
	 * 备注
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String ass_id;
	
	private String ass_no;
	
	private String ass_code;
	
	private String ass_name;
	
	private String bill_type;
	private String store_id;   
	private String store_no;   
	private String dept_id;    
	private String dept_no;
	
	private String ass_spec;   
	private String ass_mondl;  
	private String ass_model;
	public String getAss_model() {
		return ass_model;
	}

	public void setAss_model(String ass_model) {
		this.ass_model = ass_model;
	}
	private String ass_brand;    
	private String ass_seq_no;
	private Double price;
	
	private String ass_unit_name;
	
	
	
	
	
	public String getAss_unit_name() {
		return ass_unit_name;
	}

	public void setAss_unit_name(String ass_unit_name) {
		this.ass_unit_name = ass_unit_name;
	}
	
	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public String getAss_mondl() {
		return ass_mondl;
	}

	public void setAss_mondl(String ass_mondl) {
		this.ass_mondl = ass_mondl;
	}

	public String getAss_brand() {
		return ass_brand;
	}

	public void setAss_brand(String ass_brand) {
		this.ass_brand = ass_brand;
	}

	public String getAss_seq_no() {
		return ass_seq_no;
	}

	public void setAss_seq_no(String ass_seq_no) {
		this.ass_seq_no = ass_seq_no;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBill_type() {
		return bill_type;
	}

	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStore_no() {
		return store_no;
	}

	public void setStore_no(String store_no) {
		this.store_no = store_no;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getAss_id() {
		return ass_id;
	}

	public void setAss_id(String ass_id) {
		this.ass_id = ass_id;
	}

	public String getAss_no() {
		return ass_no;
	}

	public void setAss_no(String ass_no) {
		this.ass_no = ass_no;
	}

	public String getAss_code() {
		return ass_code;
	}

	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
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
	* 设置 资产转移单号
	* @param value 
	*/
	public void setOut_no(String value) {
		this.out_no = value;
	}
	
	/**
	* 获取 资产转移单号
	* @return String
	*/
	public String getOut_no() {
		return this.out_no;
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