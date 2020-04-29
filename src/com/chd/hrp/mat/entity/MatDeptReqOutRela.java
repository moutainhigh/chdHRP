/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.entity; 

import java.io.Serializable;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_DEPT_REQ_OUT_RELA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatDeptReqOutRela implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Long hos_id;
	
	/**
	 * 
	 */
	private Long group_id;
	
	/**
	 * 
	 */
	private String copy_code;
	
	/**
	 * 
	 */
	private Long req_id;
	
	/**
	 * 
	 */
	private Long req_detail_id;
	
	/**
	 * 
	 */
	private Long rela_type;
	
	/**
	 * 
	 */
	private Long rela_id;
	
	/**
	 * 
	 */
	private Long rela_detail_id;
	
	/**
	 * 
	 */
	private double rela_amount;

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public Long getReq_id() {
		return req_id;
	}

	public void setReq_id(Long req_id) {
		this.req_id = req_id;
	}

	public Long getReq_detail_id() {
		return req_detail_id;
	}

	public void setReq_detail_id(Long req_detail_id) {
		this.req_detail_id = req_detail_id;
	}

	public Long getRela_type() {
		return rela_type;
	}

	public void setRela_type(Long rela_type) {
		this.rela_type = rela_type;
	}

	public Long getRela_id() {
		return rela_id;
	}

	public void setRela_id(Long rela_id) {
		this.rela_id = rela_id;
	}

	public Long getRela_detail_id() {
		return rela_detail_id;
	}

	public void setRela_detail_id(Long rela_detail_id) {
		this.rela_detail_id = rela_detail_id;
	}

	public double getRela_amount() {
		return rela_amount;
	}

	public void setRela_amount(double rela_amount) {
		this.rela_amount = rela_amount;
	}
}