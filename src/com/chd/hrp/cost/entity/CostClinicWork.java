/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 门诊工作量表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostClinicWork implements Serializable {

	
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
	 * 会计年度
	 */
	private String year_month;
	private String acc_year;
	private String acc_month;
	/**
	 * 科室ID
	 */
	private Long dept_id;
	/**
	 * 科室变更ID
	 */
	private Long dept_no;
	/**
	 * 科室编码
	 */
	private String dept_code;
	/**
	 * 科室名称
	 */
	private String dept_name;
	/**
	 * 患者类别ID
	 */
	private Long patient_id;
	/**
	 * 患者类别
	 */
	private String patient_type_code;
	/**
	 * 患者类别名称
	 */
	private String patient_name;
	/**
	 * 门急诊人次
	 */
	private Long clinic_num;
	/**
	 * 手术人次
	 */
	private Long operation_num;
	/**
	 * 导入验证信息
	 */
	private String error_type;
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	/**
	 * 获取 账套编码
	 */	
	public String getCopy_code() {
		return this.copy_code;
	}
	
	/**
	 * @return the acc_year
	 */
	public String getAcc_year() {
		return acc_year;
	}
	/**
	 * @param acc_year the acc_year to set
	 */
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	/**
	 * @return the acc_month
	 */
	public String getAcc_month() {
		return acc_month;
	}
	/**
	 * @param acc_month the acc_month to set
	 */
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	/**
	 * 设置 科室ID
	 */
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	/**
	 * 获取 科室ID
	 */	
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	 * 设置 科室变更ID
	 */
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	/**
	 * 获取 科室变更ID
	 */	
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	 * 设置 患者类别
	 */
	public void setPatient_type_code(String value) {
		this.patient_type_code = value;
	}
	/**
	 * 获取 患者类别
	 */	
	public String getPatient_type_code() {
		return this.patient_type_code;
	}
	/**
	 * 设置 门急诊人次
	 */
	public void setClinic_num(Long value) {
		this.clinic_num = value;
	}
	/**
	 * 获取 门急诊人次
	 */	
	public Long getClinic_num() {
		return this.clinic_num;
	}
	/**
	 * 设置 手术人次
	 */
	public void setOperation_num(Long value) {
		this.operation_num = value;
	}
	/**
	 * 获取 手术人次
	 */	
	public Long getOperation_num() {
		return this.operation_num;
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
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(Long patient_id) {
		this.patient_id = patient_id;
	}
	/**
	 * @return the year_month
	 */
	public String getYear_month() {
		return year_month;
	}
	/**
	 * @param year_month the year_month to set
	 */
	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}
	
}