﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.contract;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050502 资产合同违约
 * @Table:
 * ASS_CONTRACT_BREACH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssContractBreach implements Serializable {

	
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
	 * 年度
	 */
	private String acct_year;
	
	/**
	 * 违约单号
	 */
	private String breach_no;
	
	/**
	 * 合同ID
	 */
	private Long contract_id;
	
	/**
	 * 供应商ID
	 */
	private Integer ven_id;
	
	/**
	 * 供应商变更ID
	 */
	private String ven_no;
	
	/**
	 * 登记日期
	 */
	private String create_date;
	
	/**
	 * 违约金额
	 */
	private Double breach_money;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 违约方
	 */
	private String breach_flag;
	
	/**
	 * 甲方负责人
	 */
	private String first_emp;
	
	/**
	 * 乙方负责人
	 */
	private String second_emp;
	
	/**
	 * 开始日期
	 */
	private String begin_date;
	
	/**
	 * 结束日期
	 */
	private String end_date;
	
	/**
	 * 违约原因
	 */
	private String breach_reason;
	
	/**
	 * 处理结果
	 */
	private String result;
	

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
	* 设置 年度
	* @param value 
	*/
	public void setAcct_year(String value) {
		this.acct_year = value;
	}
	
	/**
	* 获取 年度
	* @return String
	*/
	public String getAcct_year() {
		return this.acct_year;
	}
	/**
	* 设置 违约单号
	* @param value 
	*/
	public void setBreach_no(String value) {
		this.breach_no = value;
	}
	
	/**
	* 获取 违约单号
	* @return String
	*/
	public String getBreach_no() {
		return this.breach_no;
	}
	/**
	* 设置 合同ID
	* @param value 
	*/
	public void setContract_id(Long value) {
		this.contract_id = value;
	}
	
	/**
	* 获取 合同ID
	* @return Long
	*/
	public Long getContract_id() {
		return this.contract_id;
	}
	/**
	* 设置 供应商ID
	* @param value 
	*/
	public void setVen_id(Integer value) {
		this.ven_id = value;
	}
	
	/**
	* 获取 供应商ID
	* @return Integer
	*/
	public Integer getVen_id() {
		return this.ven_id;
	}
	/**
	* 设置 供应商变更ID
	* @param value 
	*/
	public void setVen_no(String value) {
		this.ven_no = value;
	}
	
	/**
	* 获取 供应商变更ID
	* @return String
	*/
	public String getVen_no() {
		return this.ven_no;
	}
	/**
	* 设置 登记日期
	* @param value 
	*/
	public void setCreate_date(String value) {
		this.create_date = value;
	}
	
	/**
	* 获取 登记日期
	* @return Date
	*/
	public String getCreate_date() {
		return this.create_date;
	}
	/**
	* 设置 违约金额
	* @param value 
	*/
	public void setBreach_money(Double value) {
		this.breach_money = value;
	}
	
	/**
	* 获取 违约金额
	* @return Double
	*/
	public Double getBreach_money() {
		return this.breach_money;
	}
	/**
	* 设置 状态
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 状态
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
	}
	/**
	* 设置 违约方
	* @param value 
	*/
	public void setBreach_flag(String value) {
		this.breach_flag = value;
	}
	
	/**
	* 获取 违约方
	* @return String
	*/
	public String getBreach_flag() {
		return this.breach_flag;
	}
	/**
	* 设置 甲方负责人
	* @param value 
	*/
	public void setFirst_emp(String value) {
		this.first_emp = value;
	}
	
	/**
	* 获取 甲方负责人
	* @return String
	*/
	public String getFirst_emp() {
		return this.first_emp;
	}
	/**
	* 设置 乙方负责人
	* @param value 
	*/
	public void setSecond_emp(String value) {
		this.second_emp = value;
	}
	
	/**
	* 获取 乙方负责人
	* @return String
	*/
	public String getSecond_emp() {
		return this.second_emp;
	}
	/**
	* 设置 开始日期
	* @param value 
	*/
	public void setBegin_date(String value) {
		this.begin_date = value;
	}
	
	/**
	* 获取 开始日期
	* @return Date
	*/
	public String getBegin_date() {
		return this.begin_date;
	}
	/**
	* 设置 结束日期
	* @param value 
	*/
	public void setEnd_date(String value) {
		this.end_date = value;
	}
	
	/**
	* 获取 结束日期
	* @return Date
	*/
	public String getEnd_date() {
		return this.end_date;
	}
	/**
	* 设置 违约原因
	* @param value 
	*/
	public void setBreach_reason(String value) {
		this.breach_reason = value;
	}
	
	/**
	* 获取 违约原因
	* @return String
	*/
	public String getBreach_reason() {
		return this.breach_reason;
	}
	/**
	* 设置 处理结果
	* @param value 
	*/
	public void setResult(String value) {
		this.result = value;
	}
	
	/**
	* 获取 处理结果
	* @return String
	*/
	public String getResult() {
		return this.result;
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