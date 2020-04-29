/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcost.proj;
import java.util.*;

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
import com.chd.hrp.budg.service.budgcost.proj.BudgTeachProjCostService;
/**
 * 
 * @Description:
 * 教学项目支出预算 / 教学项目支出预算执行
 * @Table:
 * BUDG_PROJ_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgTeachProjCostController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgTeachProjCostController.class);
	
	//引入Service服务
	@Resource(name = "budgTeachProjCostService")
	private final BudgTeachProjCostService budgTeachProjCostService = null;
   
	/**
	 * @Description 
	 * 教学项目支出预算 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/proj/budgTeachProjCostMainPage", method = RequestMethod.GET)
	public String budgTeachProjCostExeMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/proj/budgTeachProjCostMain";

	}

	
	/**
	 * @Description 
	 *  教学项目支出预算 查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/proj/queryBudgTeachProjCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgTeachProjCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
    	mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
    
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjYear = budgTeachProjCostService.queryBudgTeachProjCost(getPage(mapVo));

		return JSONObject.parseObject(budgProjYear);
		
	}
	
	/**
	 * @Description 
	 * 教学项目支出预算执行 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/proj/budgTeachProjCostExeMainPage", method = RequestMethod.GET)
	public String budgTeachProjCostMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/proj/budgTeachProjCostExeMain";

	}

	
	/**
	 * @Description 
	 *  教学项目支出预算执行 查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/proj/queryBudgTeachProjCostExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgTeachProjCostExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
    	mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
    
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjYear = budgTeachProjCostService.queryBudgTeachProjCostExe(getPage(mapVo));

		return JSONObject.parseObject(budgProjYear);
		
	}
	
}

