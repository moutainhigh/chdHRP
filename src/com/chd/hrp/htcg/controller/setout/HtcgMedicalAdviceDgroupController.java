﻿package com.chd.hrp.htcg.controller.setout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.htcg.service.setout.HtcgMedicalAdviceDgroupService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgMedicalAdviceDgroupController extends BaseController {
	private static Logger logger = Logger
			.getLogger(HtcgMedicalAdviceDgroupController.class);

	@Resource(name = "htcgMedicalAdviceDgroupService")
	private final HtcgMedicalAdviceDgroupService htcgMedicalAdviceDgroupService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicedgroup/htcgMedicalAdviceDgroupMainPage", method = RequestMethod.GET)
	public String htcgMedicalAdviceDgroupMainPage(Model mode) throws Exception {
		return "hrp/htcg/setout/medicaladvicedgroup/htcgMedicalAdviceDgroupMain";

	}

	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicedgroup/initHtcgMedicalAdviceDgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgMedicalAdviceDgroup(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
			String medicalAdviceDgroupJson;	
			 try {
				 mapVo.put("group_id", SessionManager.getGroupId());
				 mapVo.put("hos_id", SessionManager.getHosId());
				 mapVo.put("copy_code", SessionManager.getCopyCode());
				 medicalAdviceDgroupJson = htcgMedicalAdviceDgroupService.initHtcgMedicalAdviceDgroup(mapVo);
			} catch (Exception e) {
				// TODO: handle exception
				medicalAdviceDgroupJson = e.getMessage();
			}
			 return JSONObject.parseObject(medicalAdviceDgroupJson);
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicedgroup/queryHtcgMedicalAdviceDgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgMedicalAdviceDgroup(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		 mapVo.put("group_id", SessionManager.getGroupId());
		 mapVo.put("hos_id", SessionManager.getHosId());
		 mapVo.put("copy_code", SessionManager.getCopyCode());
		String medicalAdviceDgroup = htcgMedicalAdviceDgroupService.queryHtcgMedicalAdviceDgroup(getPage(mapVo));

		return JSONObject.parseObject(medicalAdviceDgroup);

	}
		
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicedgroup/calculateHtcgMedicalAdviceDgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> calculateHtcgMedicalAdviceDgroup(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
			String medicalAdviceDgroupJson;	
			 try {
				 mapVo.put("group_id", SessionManager.getGroupId());
				 mapVo.put("hos_id", SessionManager.getHosId());
				 mapVo.put("copy_code", SessionManager.getCopyCode());
				 medicalAdviceDgroupJson = htcgMedicalAdviceDgroupService.calculateHtcgMedicalAdviceDgroup(mapVo);
			} catch (Exception e) {
				// TODO: handle exception
				medicalAdviceDgroupJson = e.getMessage();
			}
			 return JSONObject.parseObject(medicalAdviceDgroupJson);

	}
	
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicedgroup/admittanceHtcgMedicalAdviceDgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> admittanceHtcgMedicalAdviceDgroup(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
			String medicalAdviceDgroupJson;	
			 try {
				 mapVo.put("group_id", SessionManager.getGroupId());
				 mapVo.put("hos_id", SessionManager.getHosId());
				 mapVo.put("copy_code", SessionManager.getCopyCode());
				 medicalAdviceDgroupJson = htcgMedicalAdviceDgroupService.admittanceHtcgMedicalAdviceDgroup(mapVo);
			} catch (Exception e) {
				// TODO: handle exception
				medicalAdviceDgroupJson = e.getMessage();
			}
			 return JSONObject.parseObject(medicalAdviceDgroupJson);

	}

	// 删除
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicedgroup/deleteHtcgMedicalAdviceDgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgMedicalAdviceDgroup(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("period_type_code", ids[3]);
			mapVo.put("acc_year", ids[4]);
			mapVo.put("acc_month", ids[5]);
			mapVo.put("scheme_code", ids[6]);
			mapVo.put("drgs_code", ids[7]);
			mapVo.put("charge_item_code", ids[8]);
			mapVo.put("price", ids[9]);
			mapVo.put("recipe_type_code", ids[10]);
			mapVo.put("clp_d_step", ids[11]);
			listVo.add(mapVo);
		}
		String medicalAdviceDgroupJson = null;
		try {
			medicalAdviceDgroupJson = htcgMedicalAdviceDgroupService.deleteBatchHtcgMedicalAdviceDgroup(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			medicalAdviceDgroupJson = e.getMessage();
		}
		return JSONObject.parseObject(medicalAdviceDgroupJson);

	}

}
