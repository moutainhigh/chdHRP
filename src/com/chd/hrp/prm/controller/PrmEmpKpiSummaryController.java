﻿
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.controller;
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
import com.chd.hrp.prm.entity.PrmEmpKpiSummary;
import com.chd.hrp.prm.service.PrmEmpKpiScoreService;
import com.chd.hrp.prm.service.PrmEmpKpiSummaryService;

/**
 * 
 * @Description:
 * 0410 职工KPI考评总结表
 * @Table:
 * PRM_EMP_KPI_SUMMARY
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class PrmEmpKpiSummaryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmEmpKpiSummaryController.class);
	
	//引入Service服务
	@Resource(name = "prmEmpKpiSummaryService")
	private final PrmEmpKpiSummaryService prmEmpKpiSummaryService = null;
	
	@Resource(name = "prmEmpKpiScoreService")
	private final PrmEmpKpiScoreService prmEmpKpiScoreService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpisummary/prmEmpKpiSummaryMainPage", method = RequestMethod.GET)
	public String prmEmpKpiSummaryMainPage(Model mode) throws Exception {

		return "hrp/prm/prmempkpisummary/prmEmpKpiSummaryMain";

	}


	/**
	 * @Description 
	 * 添加数据 0410 职工KPI考评总结表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpisummary/addPrmEmpKpiSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmEmpKpiSummary(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listAddVo = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> listUpdateVo = new ArrayList<Map<String, Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("goal_code", ids[3])   ;
			mapVo.put("acc_year", ids[4])   ;
			mapVo.put("acc_month", ids[5])   ;
			mapVo.put("emp_no", ids[6]);
			mapVo.put("emp_id", ids[7]);
			mapVo.put("kpi_score", ids[8]);
			mapVo.put("summary", ids[9]); 
			
			 PrmEmpKpiSummary prmEmpKpiSummary =  prmEmpKpiSummaryService.queryPrmEmpKpiSummaryByCode(mapVo);

			if (prmEmpKpiSummary != null) {

				listUpdateVo.add(mapVo);

			} else {

				listAddVo.add(mapVo);

			}

		} 
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("listUpdateVo", listUpdateVo);

		map.put("listAddVo", listAddVo);

       
		String prmEmpKpiSummaryJson = prmEmpKpiSummaryService.addPrmEmpKpiSummary(map);

		return JSONObject.parseObject(prmEmpKpiSummaryJson);
		
	}
	
	

	/**
	 * @Description 
	 * 查询数据 0410 职工KPI考评总结表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpisummary/queryPrmEmpKpiSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiSummary(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null  || "".equals(mapVo.get("hos_id").toString())) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String prmEmpKpiScore = prmEmpKpiScoreService.queryPrmEmpKpiScoreEmp(getPage(mapVo));

		return JSONObject.parseObject(prmEmpKpiScore);
		
	}
	
 
}

