package com.chd.hrp.pac.entity.basicset.doc;

import java.io.Serializable;

public class PactFileEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5781713492961746221L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pact_code;
	private String file_type;
	private Integer sort_id;
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

	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public Integer getSort_id() {
		return sort_id;
	}

	public void setSort_id(Integer sort_id) {
		this.sort_id = sort_id;
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
