package com.chd.hrp.hpm.entity;

import java.io.Serializable;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class AphiDeptSchemeSeq implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;
	
	private String copy_code;

	private Integer scheme_seq_no;

	private String item_code;

	private Long dept_id;
	
	private Long dept_no;
	
	private String dept_code;
	
	private String dept_name;
	
	private String dept_kind_code;
	
	private String dept_kind_name;

	private String method_code;

	private String formula_code;

	private String fun_code;
	
	private String formula_name;
	
	private String formula_method_chs;
	
	private String formula_method_eng;

	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	public String getCopy_code() {
		return this.copy_code;
	}

	public void setScheme_seq_no(Integer value) {
		this.scheme_seq_no = value;
	}

	public Integer getScheme_seq_no() {
		return this.scheme_seq_no;
	}

	public void setItem_code(String value) {
		this.item_code = value;
	}

	public String getItem_code() {
		return this.item_code;
	}

	public void setDept_code(String value) {
		this.dept_code = value;
	}

	public String getDept_code() {
		return this.dept_code;
	}

	public void setMethod_code(String value) {
		this.method_code = value;
	}

	public String getMethod_code() {
		return this.method_code;
	}

	public void setFormula_code(String value) {
		this.formula_code = value;
	}

	public String getFormula_code() {
		return this.formula_code;
	}

	public void setFun_code(String value) {
		this.fun_code = value;
	}

	public String getFun_code() {
		return this.fun_code;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDept_kind_code() {
		return dept_kind_code;
	}

	public void setDept_kind_code(String dept_kind_code) {
		this.dept_kind_code = dept_kind_code;
	}

	public String getDept_kind_name() {
		return dept_kind_name;
	}

	public void setDept_kind_name(String dept_kind_name) {
		this.dept_kind_name = dept_kind_name;
	}

	public String getFormula_name() {
		return formula_name;
	}

	public void setFormula_name(String formula_name) {
		this.formula_name = formula_name;
	}

	public String getFormula_method_chs() {
		return formula_method_chs;
	}

	public void setFormula_method_chs(String formula_method_chs) {
		this.formula_method_chs = formula_method_chs;
	}

	public String getFormula_method_eng() {
		return formula_method_eng;
	}

	public void setFormula_method_eng(String formula_method_eng) {
		this.formula_method_eng = formula_method_eng;
	}
	
	
	
}