/*
 *
 */package com.chd.hrp.htcg.entity.info;

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

public class HtcgOutcomeDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String outcome_code;
	private String outcome_name;
	private String spell_code;
	private String wbx_code;
	private Integer is_stop;
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getOutcome_code() {
		return outcome_code;
	}
	public String getOutcome_name() {
		return outcome_name;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setOutcome_code(String outcome_code) {
		this.outcome_code = outcome_code;
	}
	public void setOutcome_name(String outcome_name) {
		this.outcome_name = outcome_name;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}

	
}