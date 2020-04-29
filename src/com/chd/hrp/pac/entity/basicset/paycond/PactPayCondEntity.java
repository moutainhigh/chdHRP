package com.chd.hrp.pac.entity.basicset.paycond;

import java.io.Serializable;

public class PactPayCondEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9175236788876565422L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String cond_code;
	private String cond_name;
	private Integer is_stop;
	private String spell_code;
	private String wbx_code;
	private String note;

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public Integer getHos_id() {
		return hos_id;
	}

	public void setHos_id(Integer hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getCond_code() {
		return cond_code;
	}

	public void setCond_code(String cond_code) {
		this.cond_code = cond_code;
	}

	public String getCond_name() {
		return cond_name;
	}

	public void setCond_name(String cond_name) {
		this.cond_name = cond_name;
	}

	public Integer getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}

	public String getSpell_code() {
		return spell_code;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public String getWbx_code() {
		return wbx_code;
	}

	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
