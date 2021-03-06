package com.chd.hrp.htcg.entity.setout;

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

public class HtcgMaterialAdvicePgroup implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String period_type_code;
	private String period_type_name;
	private String period_code;
	private String period_name;
	private String acc_year;
	private String acc_month;
	private String scheme_code;
	private String scheme_name;
	private String drgs_code;
	private String drgs_name;
	private String mate_code;
	private String mate_name;
	private Double amount;
	private Double price;
	private Double income_money;
	private String recipe_type_code;
	private String recipe_type_name;
	private String clp_p_step;
	private String  clp_step_name;
	private Integer item_mr_count;
	private Integer  drgs_mr_count;
	private double prim_percent;
	private double  grand_percent;
	private Integer recipe_p_group;
	private String recipe_group_name;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getPeriod_type_code() {
		return period_type_code;
	}
	public String getPeriod_type_name() {
		return period_type_name;
	}
	public String getPeriod_code() {
		return period_code;
	}
	public String getPeriod_name() {
		return period_name;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public String getAcc_month() {
		return acc_month;
	}
	public String getScheme_code() {
		return scheme_code;
	}
	public String getScheme_name() {
		return scheme_name;
	}
	public String getDrgs_code() {
		return drgs_code;
	}
	public String getDrgs_name() {
		return drgs_name;
	}
	public String getMate_code() {
		return mate_code;
	}
	public String getMate_name() {
		return mate_name;
	}
	public Double getAmount() {
		return amount;
	}
	public Double getPrice() {
		return price;
	}
	public Double getIncome_money() {
		return income_money;
	}
	public String getRecipe_type_code() {
		return recipe_type_code;
	}
	public String getRecipe_type_name() {
		return recipe_type_name;
	}
	public String getClp_p_step() {
		return clp_p_step;
	}
	public String getClp_step_name() {
		return clp_step_name;
	}
	public Integer getItem_mr_count() {
		return item_mr_count;
	}
	public Integer getDrgs_mr_count() {
		return drgs_mr_count;
	}
	public double getPrim_percent() {
		return prim_percent;
	}
	public double getGrand_percent() {
		return grand_percent;
	}
	public Integer getRecipe_p_group() {
		return recipe_p_group;
	}
	public String getRecipe_group_name() {
		return recipe_group_name;
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
	public void setPeriod_type_code(String period_type_code) {
		this.period_type_code = period_type_code;
	}
	public void setPeriod_type_name(String period_type_name) {
		this.period_type_name = period_type_name;
	}
	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}
	public void setPeriod_name(String period_name) {
		this.period_name = period_name;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	public void setScheme_code(String scheme_code) {
		this.scheme_code = scheme_code;
	}
	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}
	public void setDrgs_code(String drgs_code) {
		this.drgs_code = drgs_code;
	}
	public void setDrgs_name(String drgs_name) {
		this.drgs_name = drgs_name;
	}
	public void setMate_code(String mate_code) {
		this.mate_code = mate_code;
	}
	public void setMate_name(String mate_name) {
		this.mate_name = mate_name;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setIncome_money(Double income_money) {
		this.income_money = income_money;
	}
	public void setRecipe_type_code(String recipe_type_code) {
		this.recipe_type_code = recipe_type_code;
	}
	public void setRecipe_type_name(String recipe_type_name) {
		this.recipe_type_name = recipe_type_name;
	}
	public void setClp_p_step(String clp_p_step) {
		this.clp_p_step = clp_p_step;
	}
	public void setClp_step_name(String clp_step_name) {
		this.clp_step_name = clp_step_name;
	}
	public void setItem_mr_count(Integer item_mr_count) {
		this.item_mr_count = item_mr_count;
	}
	public void setDrgs_mr_count(Integer drgs_mr_count) {
		this.drgs_mr_count = drgs_mr_count;
	}
	public void setPrim_percent(double prim_percent) {
		this.prim_percent = prim_percent;
	}
	public void setGrand_percent(double grand_percent) {
		this.grand_percent = grand_percent;
	}
	public void setRecipe_p_group(Integer recipe_p_group) {
		this.recipe_p_group = recipe_p_group;
	}
	public void setRecipe_group_name(String recipe_group_name) {
		this.recipe_group_name = recipe_group_name;
	}

}