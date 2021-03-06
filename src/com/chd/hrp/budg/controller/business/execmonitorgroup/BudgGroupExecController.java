﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.execmonitorgroup;
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
import com.chd.hrp.budg.service.business.budgquery.busHosBusinessQuery.BudgWorkHosMService;
import com.chd.hrp.budg.service.business.execmonitorgroup.BudgGroupExecService;
/**
 * 
 * @Description:
 * 科室月份业务预算
 * @Table:
 * BUDG_WORK_DEPT_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgGroupExecController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgGroupExecController.class);
	
	//引入Service服务
	@Resource(name = "budgGroupExecService")
	private final BudgGroupExecService budgGroupExecService = null;
	
	//引入Service服务
		@Resource(name = "budgWorkHosMService")
		private final BudgWorkHosMService budgWorkHosMService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/

	@RequestMapping(value = "/hrp/budg/business/execmonitorgroup/budgGroupExecMainPage", method = RequestMethod.GET)
	public String budgWorkDeptMonthMainPage(Model mode) throws Exception {

		return "hrp/budg/business/execmonitorgroup/budgGroupExecMain";

	}

	
	/**
	 * @Description 
	 * 查询数据 科室月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execmonitorgroup/queryBudgGroupExecData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkDeptMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgWorkDeptMonth = budgGroupExecService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkDeptMonth);
		
	}
	
	
	/**
	 * 预算值连接页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/execmonitorgroup/budgWorkGroupExecuteLinkPage", method = RequestMethod.GET)
	public String budgWorkCheckUpdatePage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id",SessionManager.getHosId());
		mode.addAttribute("copy_code",SessionManager.getCopyCode());
		mode.addAttribute("year", mapVo.get("year"));
		mode.addAttribute("index_code", mapVo.get("index_code"));
		mode.addAttribute("month", mapVo.get("month"));
		return "hrp/budg/business/execmonitorgroup/budgWorkGroupLinkExecuteSum";
		
	}
	
	/**
	 * 预算值连接页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/execmonitorgroup/budgWorkGroupExecuteLinkPageSum", method = RequestMethod.GET)
	public String budgWorkHosLinkPageSum(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id",SessionManager.getHosId());
		mode.addAttribute("copy_code",SessionManager.getCopyCode());
		mode.addAttribute("year", mapVo.get("year"));
		mode.addAttribute("sum_year", mapVo.get("sum_year"));
		mode.addAttribute("index_code", mapVo.get("index_code"));
		return "/hrp/budg/business/execmonitorgroup/budgWorkGroupExecuteLink";	
	}
	/**
	 *  预算值连接页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/hrp/budg/business/execmonitorgroup/queryBudgWorkCheckGroupYear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgWorkCheckDeptMonth(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		String budgWorkHosMonth="";
		//这一部分最好放到service层进行处理,少写两个方法
		if(!((String)mapVo.get("sum_year")==null&&(String)mapVo.get("sum_year")=="")){
			//抓取预算值BUDG_WORK_HOS_YEAR_COPY表中抓取数据	
		     budgWorkHosMonth = budgWorkHosMService.queryGroupYearCopy(getPage(mapVo));
		}
		return JSONObject.parseObject(budgWorkHosMonth);
		
	}
	/**
	 * 预算值连接页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/execmonitorgroup/queryBudgWorkCheckGroupMonthSum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgWorkCheckDeptMonthSum(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		String budgWorkHosMonth = budgWorkHosMService.queryGroupMonthCopy(getPage(mapVo));
		return JSONObject.parseObject(budgWorkHosMonth);
		
	}
			
	
}

