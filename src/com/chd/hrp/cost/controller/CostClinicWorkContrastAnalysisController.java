package com.chd.hrp.cost.controller;

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
import com.chd.base.SessionManager;
import com.chd.hrp.cost.service.CostClinicWorkContrastAnalysisService;
@Controller
public class CostClinicWorkContrastAnalysisController extends com.chd.base.BaseController {
	
	private static Logger logger = Logger.getLogger(CostClinicWorkContrastAnalysisController.class);

	@Resource(name = "costClinicWorkContrastAnalysisService")
	private final CostClinicWorkContrastAnalysisService costClinicWorkContrastAnalysisService = null;
	
	/**
	*成本构成变化趋势分析<BR>
	*分析显示页面
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costclinicworkcontrastanalysis/costClinicWorkContrastAnalysisMainPage", method = RequestMethod.GET)
	public String costClinicWorkContrastAnalysisMainPage(Model mode) throws Exception {

		return "hrp/cost/costanalysis/costclinicworkcontrastanalysis/costClinicWorkContrastAnalysisMain";

	}
	
	@RequestMapping(value = "/hrp/cost/costanalysis/costclinicworkcontrastanalysis/queryCostClinicWorkContrastAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  queryCostClinicWorkContrastAnalysis(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
	
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		 if(!"0".equals(mapVo.get("acct_year_type"))){
			 
			 mapVo.put("acc_year", SessionManager.getAcctYear());
			 
		 }
		
		String costClinicWorkContrast = costClinicWorkContrastAnalysisService.queryCostClinicWorkContrast(getPage(mapVo));

		return JSONObject.parseObject(costClinicWorkContrast);
	}

}
