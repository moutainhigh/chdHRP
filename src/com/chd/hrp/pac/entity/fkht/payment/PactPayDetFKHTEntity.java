package com.chd.hrp.pac.entity.fkht.payment;

import java.io.Serializable;

public class PactPayDetFKHTEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String pay_code;
	private String pact_code;
	private Integer detail_id;
	private Integer source_id;
	private String pay_way;
	private Double pay_money;
	private String cheq_no;
	private String note;

	private String source_name;
	private String pay_name;

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

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
	}

	public Integer getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Integer detail_id) {
		this.detail_id = detail_id;
	}

	public Integer getSource_id() {
		return source_id;
	}

	public void setSource_id(Integer source_id) {
		this.source_id = source_id;
	}

	public String getPay_way() {
		return pay_way;
	}

	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}

	public Double getPay_money() {
		return pay_money;
	}

	public void setPay_money(Double pay_money) {
		this.pay_money = pay_money;
	}

	public String getCheq_no() {
		return cheq_no;
	}

	public void setCheq_no(String cheq_no) {
		this.cheq_no = cheq_no;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public String getPay_name() {
		return pay_name;
	}

	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}

}