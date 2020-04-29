/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 管理分摊设置采集数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostParaManSetData implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private String dept_name;
	private String server_dept_name;
	private String cost_item_name;
	private String cost_item_code;
	private String para_name;
	private String dept_code;
	private String server_dept_code;
	private String acc_year;
	private String acc_month;
	private String type_code;
	private String s_type_code;
	
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
	 * 统计年月
	 */
	private String year_month;
	/**
	 * 服务科室ID
	 */
	private Long dept_id;
	/**
	 * 服务科室变更ID
	 */
	private Long dept_no;
	/**
	 * 受益科室ID
	 */
	private Long server_dept_id;
	/**
	 * 受益科室变更ID
	 */
	private Long server_dept_no;
	/**
	 * 成本项目ID
	 */
	private Long cost_item_id;
	/**
	 * 成本项目变更ID
	 */
	private Long cost_item_no;
	/**
	 * 分摊参数
	 */
	private String para_code;
	/**
	 * 受益科室参数值
	 */
	private double para_value;
	/**
	 * 总参数值
	 */
	private double total_value;
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
	 * 设置 统计年月
	 */
	public void setYear_month(String value) {
		this.year_month = value;
	}
	/**
	 * 获取 统计年月
	 */	
	public String getYear_month() {
		return this.year_month;
	}
	
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	/**
	 * 获取 统计年月
	 */	
	
	public String getAcc_year() {
		return this.acc_year;
	}
	
	public String getAcc_month() {
		return this.acc_month;
	}
	
	public void setAcc_month(String value) {
		this.acc_month = value;
	}
	/**
	 * 设置 服务科室ID
	 */
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	/**
	 * 获取 服务科室ID
	 */	
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	 * 设置 服务科室变更ID
	 */
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	/**
	 * 获取 服务科室变更ID
	 */	
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	 * 设置 受益科室ID
	 */
	public void setServer_dept_id(Long value) {
		this.server_dept_id = value;
	}
	/**
	 * 获取 受益科室ID
	 */	
	public Long getServer_dept_id() {
		return this.server_dept_id;
	}
	/**
	 * 设置 受益科室变更ID
	 */
	public void setServer_dept_no(Long value) {
		this.server_dept_no = value;
	}
	/**
	 * 获取 受益科室变更ID
	 */	
	public Long getServer_dept_no() {
		return this.server_dept_no;
	}
	/**
	 * 设置 成本项目ID
	 */
	public void setCost_item_id(Long value) {
		this.cost_item_id = value;
	}
	/**
	 * 获取 成本项目ID
	 */	
	public Long getCost_item_id() {
		return this.cost_item_id;
	}
	/**
	 * 设置 成本项目变更ID
	 */
	public void setCost_item_no(Long value) {
		this.cost_item_no = value;
	}
	/**
	 * 获取 成本项目变更ID
	 */	
	public Long getCost_item_no() {
		return this.cost_item_no;
	}
	/**
	 * 设置 分摊参数
	 */
	public void setPara_code(String value) {
		this.para_code = value;
	}
	/**
	 * 获取 分摊参数
	 */	
	public String getPara_code() {
		return this.para_code;
	}
	/**
	 * 设置 受益科室参数值
	 */
	public void setPara_value(double value) {
		this.para_value = value;
	}
	/**
	 * 获取 受益科室参数值
	 */	
	public double getPara_value() {
		return this.para_value;
	}
	/**
	 * 设置 总参数值
	 */
	public void setTotal_value(double value) {
		this.total_value = value;
	}
	/**
	 * 获取 总参数值
	 */	
	public double getTotal_value() {
		return this.total_value;
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
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getServer_dept_name() {
		return server_dept_name;
	}
	public void setServer_dept_name(String server_dept_name) {
		this.server_dept_name = server_dept_name;
	}
	public String getCost_item_name() {
		return cost_item_name;
	}
	public void setCost_item_name(String cost_item_name) {
		this.cost_item_name = cost_item_name;
	}
	public String getPara_name() {
		return para_name;
	}
	public void setPara_name(String para_name) {
		this.para_name = para_name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCost_item_code() {
		return cost_item_code;
	}
	public void setCost_item_code(String cost_item_code) {
		this.cost_item_code = cost_item_code;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getServer_dept_code() {
		return server_dept_code;
	}
	public void setServer_dept_code(String server_dept_code) {
		this.server_dept_code = server_dept_code;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getS_type_code() {
		return s_type_code;
	}
	public void setS_type_code(String s_type_code) {
		this.s_type_code = s_type_code;
	}
	
	
}