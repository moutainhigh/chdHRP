package com.chd.hrp.htc.entity.task.basic;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class HtcWorkType implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String work_type_code;
	private String work_type_name;
	private String work_type_remark;
	private String spell_code;
	private String wbx_code;
	private Integer is_last;
	private Integer is_stop;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getWork_type_code() {
		return work_type_code;
	}
	public String getWork_type_name() {
		return work_type_name;
	}
	public String getWork_type_remark() {
		return work_type_remark;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public Integer getIs_last() {
		return is_last;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setWork_type_code(String work_type_code) {
		this.work_type_code = work_type_code;
	}
	public void setWork_type_name(String work_type_name) {
		this.work_type_name = work_type_name;
	}
	public void setWork_type_remark(String work_type_remark) {
		this.work_type_remark = work_type_remark;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public void setIs_last(Integer is_last) {
		this.is_last = is_last;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
}