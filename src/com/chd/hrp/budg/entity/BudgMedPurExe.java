/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * @Description: 材料采购预算编制
 * @Table: BUDG_MAT_PUR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgMedPurExe implements Serializable {

	
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
	 * 物资分类ID
	 */
	private Long med_type_id;
	
	/**
	 * 采购预算
	 */
	private Double pur_amount;
	
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
	* 设置 物资分类编码
	* @param value 
	*/
	public void setMed_type_id(Long value) {
		this.med_type_id = value;
	}
	
	/**
	* 获取 物资分类编码
	* @return String
	*/
	public Long getMed_type_id() {
		return this.med_type_id;
	}

	/**
	 * 采购预算
	 * @return
	 */
	public Double getPur_amount() {
		return pur_amount;
	}

	public void setPur_amount(Double pur_amount) {
		this.pur_amount = pur_amount;
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