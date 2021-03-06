/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 分摊参数<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostDeptParaDict implements Serializable {

	
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
	 * 分摊参数编码
	 */
	private String para_code;
	/**
	 * 分摊参数名称
	 */
	private String para_name;
	/**
	 * 说明
	 */
	private String remark;
	/**
	 * 拼音
	 */
	private String spell_code;
	/**
	 * 五笔码
	 */
	private String wbx_code;
	/**
	 * 导入验证信息
	 */
	private String error_type;
	
	private Integer is_sys;
	
	public Integer getIs_sys() {
		return is_sys;
	}
	public void setIs_sys(Integer is_sys) {
		this.is_sys = is_sys;
	}
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
	 * 设置 分摊参数编码
	 */
	public void setPara_code(String value) {
		this.para_code = value;
	}
	/**
	 * 获取 分摊参数编码
	 */	
	public String getPara_code() {
		return this.para_code;
	}
	/**
	 * 设置 分摊参数名称
	 */
	public void setPara_name(String value) {
		this.para_name = value;
	}
	/**
	 * 获取 分摊参数名称
	 */	
	public String getPara_name() {
		return this.para_name;
	}
	/**
	 * 设置 说明
	 */
	public void setRemark(String value) {
		this.remark = value;
	}
	/**
	 * 获取 说明
	 */	
	public String getRemark() {
		return this.remark;
	}
	/**
	 * 设置 拼音
	 */
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	/**
	 * 获取 拼音
	 */	
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	 * 设置 五笔码
	 */
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	/**
	 * 获取 五笔码
	 */	
	public String getWbx_code() {
		return this.wbx_code;
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