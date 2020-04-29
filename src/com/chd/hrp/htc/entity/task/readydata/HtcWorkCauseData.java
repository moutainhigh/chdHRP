package com.chd.hrp.htc.entity.task.readydata;

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

public class HtcWorkCauseData implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String acc_year;
	private String plan_code;
	private String plan_name;
	private long proj_dept_no;
	private long proj_dept_id;
	private String proj_dept_code;
	private String proj_dept_name;
	private long charge_item_id;
	private String charge_item_code;
	private String charge_item_name;
	private String work_code;
	private String work_name;
	private String work_cause_code;
	private String work_cause_name;
	private String work_remark;
	private double work_cause_data;
	private String copn_cia;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public String getPlan_code() {
		return plan_code;
	}
	public String getPlan_name() {
		return plan_name;
	}
	public long getProj_dept_no() {
		return proj_dept_no;
	}
	public long getProj_dept_id() {
		return proj_dept_id;
	}
	public String getProj_dept_code() {
		return proj_dept_code;
	}
	public String getProj_dept_name() {
		return proj_dept_name;
	}
	public long getCharge_item_id() {
		return charge_item_id;
	}
	public String getCharge_item_code() {
		return charge_item_code;
	}
	public String getCharge_item_name() {
		return charge_item_name;
	}
	public String getWork_code() {
		return work_code;
	}
	public String getWork_name() {
		return work_name;
	}
	public String getWork_cause_code() {
		return work_cause_code;
	}
	public String getWork_cause_name() {
		return work_cause_name;
	}
	public String getWork_remark() {
		return work_remark;
	}
	public double getWork_cause_data() {
		return work_cause_data;
	}
	public String getCopn_cia() {
		return copn_cia;
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
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public void setProj_dept_no(long proj_dept_no) {
		this.proj_dept_no = proj_dept_no;
	}
	public void setProj_dept_id(long proj_dept_id) {
		this.proj_dept_id = proj_dept_id;
	}
	public void setProj_dept_code(String proj_dept_code) {
		this.proj_dept_code = proj_dept_code;
	}
	public void setProj_dept_name(String proj_dept_name) {
		this.proj_dept_name = proj_dept_name;
	}
	public void setCharge_item_id(long charge_item_id) {
		this.charge_item_id = charge_item_id;
	}
	public void setCharge_item_code(String charge_item_code) {
		this.charge_item_code = charge_item_code;
	}
	public void setCharge_item_name(String charge_item_name) {
		this.charge_item_name = charge_item_name;
	}
	public void setWork_code(String work_code) {
		this.work_code = work_code;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
	public void setWork_cause_code(String work_cause_code) {
		this.work_cause_code = work_cause_code;
	}
	public void setWork_cause_name(String work_cause_name) {
		this.work_cause_name = work_cause_name;
	}
	public void setWork_remark(String work_remark) {
		this.work_remark = work_remark;
	}
	public void setWork_cause_data(double work_cause_data) {
		this.work_cause_data = work_cause_data;
	}
	public void setCopn_cia(String copn_cia) {
		this.copn_cia = copn_cia;
	}
}