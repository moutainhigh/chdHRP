﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.toptodown.hosmonthinbudg;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.entity.BudgMedIncomeHosMonth;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.toptodown.hosmonthinbudg.MedInHMBudgService;
import com.chd.hrp.hr.util.excel.ExcelUtils;
/**
 * 
 * @Description:
 * 医院月份医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_HOS_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class MedInHMBudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedInHMBudgController.class);
	
	//引入Service服务
	@Resource(name = "medInHMBudgService")
	private final MedInHMBudgService medInHMBudgService = null;
	
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjService budgIncomeSubjService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/medInHMBudgMainPage", method = RequestMethod.GET)
	public String medInHMBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosmonthinbudg/medInHMBudgMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/medInHMBudgAddPage", method = RequestMethod.GET)
	public String medInHMBudgAddPage(Model mode) throws Exception {
		return "hrp/budg/budgincome/toptodown/hosmonthinbudg/medInHMBudgAdd";
	}
	
	/**
	 * @Description 
	 * 预算分解维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/medInHMBudgResolveMainPage", method = RequestMethod.GET)
	public String medInDYBudgResolveMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("year", mapVo.get("year"));
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		return "hrp/budg/budgincome/toptodown/hosmonthinbudg/medInHMBudgResolveMain";

	}
	
	/**
	 * 保存数据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/saveMedInHMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMedInHMBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1])   ;
			mapVo.put("subj_code", ids[2])   ;
			mapVo.put("subj_name", ids[3])   ;
			
			if("-1".equals(ids[4])){
				mapVo.put("budg_value", "");
			}else{
				mapVo.put("budg_value", ids[4]);
			}
			
			mapVo.put("rowNo", ids[5]);
			mapVo.put("flag", ids[6]);
			
			//构建 查询上年业务量  参数 Map
			Map<String,Object> paraMap =  new HashMap<String,Object>();
			
			paraMap.put("group_id", SessionManager.getGroupId())   ;
			paraMap.put("hos_id", SessionManager.getHosId())   ;
			paraMap.put("copy_code", SessionManager.getCopyCode())   ;
			paraMap.put("subj_code", ids[2]);
			paraMap.put("year", Integer.parseInt(String.valueOf(mapVo.get("year")))-1);
			mapVo.put("month", ids[1])   ;

			
			String last_year_income = medInHMBudgService.queryLastYearIncome(mapVo);
			
			
			if(last_year_income == null || "".equals(last_year_income) ){
				
				mapVo.put("last_year_income", "");
				
			}else{
				
				mapVo.put("last_year_income", last_year_income);
			}
			
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			mapVo.put("count_value", "");
			mapVo.put("remark", "");
			mapVo.put("last_month_carried", "");
			mapVo.put("carried_next_month", "");
			
			listVo.add(mapVo);
		}
											
		String budgMedIncomeDeptMonthJson = medInHMBudgService.save(listVo);
		
		return JSONObject.parseObject(budgMedIncomeDeptMonthJson);
	}
	
	
	
	/**
	 * @Description 
	 * 添加数据 医院月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/addMedInHMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInHMBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1])   ;
			mapVo.put("subj_code", ids[2])   ;
			mapVo.put("subj_name", ids[3])   ;
			if("-1".equals(ids[4])){
				mapVo.put("budg_value", "");
			}else{
				mapVo.put("budg_value", ids[4]);
			}
			
			mapVo.put("last_year_income", "");
			mapVo.put("remark", "");
			mapVo.put("last_month_carried", "");
			mapVo.put("carried_next_month", "");
			
			mapVo.put("count_value", "");
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			
			listVo.add(mapVo);
		}
		
		String budgMedIncomeHosMonthJson = medInHMBudgService.addBatch(listVo);

		return JSONObject.parseObject(budgMedIncomeHosMonthJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 医院月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/medInHMBudgUpdatePage", method = RequestMethod.GET)
	public String budgMedIncomeHosMonthUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
        mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		BudgMedIncomeHosMonth budgMedIncomeHosMonth = new BudgMedIncomeHosMonth();
    
		budgMedIncomeHosMonth = medInHMBudgService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgMedIncomeHosMonth.getGroup_id());
		mode.addAttribute("hos_id", budgMedIncomeHosMonth.getHos_id());
		mode.addAttribute("copy_code", budgMedIncomeHosMonth.getCopy_code());
		mode.addAttribute("year", budgMedIncomeHosMonth.getYear());
		mode.addAttribute("subj_code", budgMedIncomeHosMonth.getSubj_code());
		mode.addAttribute("month", budgMedIncomeHosMonth.getMonth());
		mode.addAttribute("count_value", budgMedIncomeHosMonth.getCount_value());
		mode.addAttribute("budg_value", budgMedIncomeHosMonth.getBudg_value());
		mode.addAttribute("remark", budgMedIncomeHosMonth.getRemark());
		mode.addAttribute("grow_rate", budgMedIncomeHosMonth.getGrow_rate());
		mode.addAttribute("resolve_rate", budgMedIncomeHosMonth.getResolve_rate());
		mode.addAttribute("last_year_income", budgMedIncomeHosMonth.getLast_year_income());
		mode.addAttribute("last_month_carried", budgMedIncomeHosMonth.getLast_month_carried());
		mode.addAttribute("carried_next_month", budgMedIncomeHosMonth.getCarried_next_month());
		
		return "hrp/budg/budgincome/toptodown/hosmonthinbudg/medInHMBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/updateMedInHMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInHMBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1])   ;
			mapVo.put("subj_code", ids[2])   ;
			
			if("-1".equals(ids[3])){
				mapVo.put("budg_value", "");
			}else{
				mapVo.put("budg_value", ids[3]);
			}
			
			
			listVo.add(mapVo);
		}
		
		//批量修改  预算值
		String budgWorkHosMonthJson = medInHMBudgService.updateBatchBudgValue(listVo);
		
		return JSONObject.parseObject(budgWorkHosMonthJson);
	}
	/**
	 * @Description 
	 * 更新数据 医院月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/updateMedInHMBudgUpResolve", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInHMBudgUpResolve(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1])   ;
			mapVo.put("subj_code", ids[2])   ;
			
			if("-1".equals(ids[3])){
				mapVo.put("budg_value", "");
			}else{
				mapVo.put("budg_value", ids[3]);
			}
			if("-1".equals(ids[4])){
				mapVo.put("grow_rate", "");
			}else{
				mapVo.put("grow_rate", ids[4]);
			}
			if("-1".equals(ids[5])){
				mapVo.put("resolve_rate", "");
			}else{
				mapVo.put("resolve_rate", ids[5]);
			}
			if("-1".equals(ids[6])){
				mapVo.put("count_value", "");
			}else{
				mapVo.put("count_value", ids[6]);
			}
			if("-1".equals(ids[7])){
				mapVo.put("remark", "");
			}else{
				mapVo.put("remark", ids[7]);
			}
			if("-1".equals(ids[8])){
				mapVo.put("last_year_income", "");
			}else{
				mapVo.put("last_year_income", ids[8]);
			}
			
			listVo.add(mapVo);
		}
		
		//批量修改  预算值
		String budgWorkHosMonthJson = medInHMBudgService.updateBatchBudgValue(listVo);
		
		return JSONObject.parseObject(budgWorkHosMonthJson);
	}
	/**
	 * @Description 
	 * 更新数据 医院月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/addOrUpdateMedInHMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedInHMBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMedIncomeHosMonthJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		budgMedIncomeHosMonthJson = medInHMBudgService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMedIncomeHosMonthJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/deleteMedInHMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInHMBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("subj_code", ids[4])   ;
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgMedIncomeHosMonthJson = medInHMBudgService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgMedIncomeHosMonthJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/queryMedInHMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInHMBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgMedIncomeHosMonth = medInHMBudgService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeHosMonth);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院月份医疗收入预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/medInHMBudgImportPage", method = RequestMethod.GET)
	public String budgMedIncomeHosMonthImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosmonthinbudg/medInHMBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院月份医疗收入预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/toptodown/hosmonthinbudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\toptodown","医院月份医疗收入预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院月份医疗收入预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/toptodown/hosmonthinbudg/readMedInHMBudgFiles",method = RequestMethod.POST)  
    public String readMedInHMBudgFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> errMap = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
		    		
					if (StringTool.isNotBlank(temp[0])) {
						
						errMap.put("year", temp[0]);
						
						mapVo.put("year", temp[0]);
						
						mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
						errMap.put("subj_code", temp[1]);
						
			    		mapVo.put("subj_code", temp[1]);
			    		
			    		BudgIncomeSubj income = budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
			    		
						if(income == null ){
							err_sb.append("该年度收入预算科目编码不存在;");
						}
					
					} else {
						
						err_sb.append("科目编码为空 ;");
						
					}
					 
					mapVo.put("last_year_income", "");
					mapVo.put("remark", "");
					mapVo.put("last_month_carried", "");
					mapVo.put("carried_next_month", "");
					
					mapVo.put("count_value", "");
					mapVo.put("grow_rate", "");
					mapVo.put("resolve_rate", "");
					
					
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "01");
						
						map.put("budg_value", temp[2]);
						
						errMap.put("m01", temp[2]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "01");
						
						map.put("budg_value", "");
						
						errMap.put("m01", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "02");
						
						map.put("budg_value", temp[3]);
						
						errMap.put("m02", temp[3]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "02");
						
						map.put("budg_value", "");
						
						errMap.put("m02", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "03");
						
						map.put("budg_value", temp[4]);
						
						errMap.put("m03", temp[4]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "03");
						
						map.put("budg_value", "");
						
						errMap.put("m03", "");
						
						addList.add(map);
						
					}

					if (ExcelReader.validExceLColunm(temp,5)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "04");
						
						map.put("budg_value", temp[5]);
						
						errMap.put("m04", temp[5]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "04");
						
						map.put("budg_value", "");
						
						errMap.put("m04", "");
						
						addList.add(map);
						
					}
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "05");
						
						map.put("budg_value", temp[6]);
						
						errMap.put("m05", temp[6]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "05");
						
						map.put("budg_value", "");
						
						errMap.put("m05", "");
						
						addList.add(map);
						
					}
					if (ExcelReader.validExceLColunm(temp,7)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "06");
						
						map.put("budg_value", temp[7]);
						
						errMap.put("m06", temp[7]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "06");
						
						map.put("budg_value", "");
						
						errMap.put("m06", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,8)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "07");
						
						map.put("budg_value", temp[8]);
						
						errMap.put("m07", temp[8]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "07");
						
						map.put("budg_value", "");
						
						errMap.put("m07", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,9)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "08");
						
						map.put("budg_value", temp[9]);
						
						errMap.put("m08", temp[9]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "08");
						
						map.put("budg_value", "");
						
						errMap.put("m08", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,10)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "09");
						
						map.put("budg_value", temp[10]);
						
						errMap.put("m09", temp[10]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "09");
						
						map.put("budg_value", "");
						
						errMap.put("m09", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,11)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "10");
						
						map.put("budg_value", temp[11]);
						
						errMap.put("m10", temp[11]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "10");
						
						map.put("budg_value", "");
						
						errMap.put("m10", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,12)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "11");
						
						map.put("budg_value", temp[12]);
						
						errMap.put("m11", temp[12]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "11");
						
						map.put("budg_value", "");
						
						errMap.put("m11", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,13)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "12");
						
						map.put("budg_value", temp[13]);
						
						errMap.put("m12", temp[13]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "12");
						
						map.put("budg_value", "");
						
						errMap.put("m12", "");
						
						addList.add(map);
						
					}

					
					int count  = medInHMBudgService.queryDataExist(mapVo);
					
					if (count > 0) {
						
						err_sb.append("该年度预算科目已有数据已经存在！ ");
						
					}
				
				if (err_sb.toString().length() > 0) {
					
					errMap.put("error_type",err_sb.toString());
					
					list_err.add(errMap);
					
				}
				
			}
			
			if(list_err.size()==0){
				
				String dataJson = medInHMBudgService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			Map<String,Object> data_exc = new HashMap<String,Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医院月份医疗收入预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/addBatchMedInHMBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedInHMBudg(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedIncomeHosMonth> list_err = new ArrayList<BudgMedIncomeHosMonth>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
			}
			
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}
		
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			BudgMedIncomeHosMonth budgMedIncomeHosMonth = new BudgMedIncomeHosMonth();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgMedIncomeHosMonth.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgMedIncomeHosMonth.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgMedIncomeHosMonth.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
					budgMedIncomeHosMonth.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgMedIncomeHosMonth.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgMedIncomeHosMonth.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
					budgMedIncomeHosMonth.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
		    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_rate"))) {
						
					budgMedIncomeHosMonth.setResolve_rate(Double.valueOf((String)jsonObj.get("resolve_rate")));
		    		mapVo.put("resolve_rate", jsonObj.get("resolve_rate"));
		    		} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_income"))) {
						
					budgMedIncomeHosMonth.setLast_year_income(Double.valueOf((String)jsonObj.get("last_year_income")));
		    		mapVo.put("last_year_income", jsonObj.get("last_year_income"));
		    		} else {
						
						err_sb.append("上年收入为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_month_carried"))) {
						
					budgMedIncomeHosMonth.setLast_month_carried(Double.valueOf((String)jsonObj.get("last_month_carried")));
		    		mapVo.put("last_month_carried", jsonObj.get("last_month_carried"));
		    		} else {
						
						err_sb.append("上月结转为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("carried_next_month"))) {
						
					budgMedIncomeHosMonth.setCarried_next_month(Double.valueOf((String)jsonObj.get("carried_next_month")));
		    		mapVo.put("carried_next_month", jsonObj.get("carried_next_month"));
		    		} else {
						
						err_sb.append("结转下月为空  ");
						
					}
					
					
				BudgMedIncomeHosMonth data_exc_extis = medInHMBudgService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedIncomeHosMonth.setError_type(err_sb.toString());
					
					list_err.add(budgMedIncomeHosMonth);
					
				} else {
			  
					String dataJson = medInHMBudgService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeHosMonth data_exc = new BudgMedIncomeHosMonth();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
	/**
	 * 汇总
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/collectMedInHMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectMedInHMBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
	    	mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String budgMedIncomeHosMonth = medInHMBudgService.collectMedInHMBudgUp(mapVo);

		return JSONObject.parseObject(budgMedIncomeHosMonth);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 医院月份医疗收入预算  预算分解维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/queryMedInHMBudgResolveUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInHMBudgResolveUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgMedIncomeHosMonth = medInHMBudgService.queryResolve(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeHosMonth);
		
	}
	
	/**
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/countMedInHMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> countMedInHMBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
    	mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgMedIncomeHosMonth = medInHMBudgService.countMedInHMBudg(mapVo);

		return JSONObject.parseObject(budgMedIncomeHosMonth);
		
	}
	
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosmonthinbudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			

			// 查询要生成的数据
			List<Map<String,Object>> list = medInHMBudgService.queryData(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【医院月份业务预算编制方案】数据.\"}");
			}
			
			String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"};
			
			//String str = "" ;
			
			for(Map<String, Object> item : list ){
				
				for(String month : monthData){

					Map<String,Object> map = new HashMap<String,Object>();
					
					map.putAll(item);
					
					map.put("month", month);
					
					//根据主键  查询数据是否存在
					int count  = medInHMBudgService.queryDataExist(map);
					
					//不存在  组装 生成数据
					if(count == 0){
						
						//构建 查询上年业务量  参数 Map
						Map<String,Object> paraMap =  new HashMap<String,Object>();
						
						paraMap.put("group_id", SessionManager.getGroupId())   ;
						paraMap.put("hos_id", SessionManager.getHosId())   ;
						paraMap.put("copy_code", SessionManager.getCopyCode())   ;
						paraMap.put("budg_year", map.get("year"));
						paraMap.put("subj_code", map.get("subj_code"));
						paraMap.put("year", map.get("year"));//年度不作处理  在service中做处理
						paraMap.put("month", map.get("month"));
						
						
						//查询上年收入
						String  last_year_income = medInHMBudgService.queryLastYearIncome(paraMap);
						
						if(last_year_income == null || "".equals(last_year_income)){
							
							
							map.put("last_year_income", "");
							
						}else{
							
							map.put("last_year_income", last_year_income);
						}	
						map.put("count_value", "");
						map.put("budg_value", "");
						map.put("remark", "");
						map.put("grow_rate", "");
						map.put("resolve_rate", "");
						map.put("last_month_carried", "");
						map.put("carried_next_month", "");
				        listVo.add(map); 
						
					}
				}
					
			}
			
			String budgWorkHosYearJson = null ;
			
			/*if( str != ""){
				
				budgWorkHosYearJson = "{\"error\":\"操作失败！"+mapVo.get("year")+"年度以下数据:【"+str.substring(0,str.length()-1)+"】上年业务量不存在,无法生成.\",\"state\":\"false\"}";
				
			}else{*/
				
				if(listVo.size() > 0 ){
					
					  budgWorkHosYearJson =medInHMBudgService.addBatch(listVo);
			         
				}else{
					budgWorkHosYearJson = "{\"error\":\"数据已全部生成,无需再次生成.\"}";
				}
				
			//}
			
			return JSONObject.parseObject(budgWorkHosYearJson);	
	} 	
	
	//导出
	@RequestMapping(value="/hrp/budg/budgincome/toptodown/hosmonthinbudg/exportExcel")  
	public String exportExcel(@RequestParam Map<String, Object> mapVo, HttpServletResponse response,Model mode) throws IOException { 
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			List<String> header = new ArrayList<String>();
			header.add("预算年度");
			header.add("科目编码");
			header.add("科目名称");
			header.add("01月");
			header.add("02月");
			header.add("03月");
			header.add("04月");
			header.add("05月");
			header.add("06月");
			header.add("07月");
			header.add("08月");
			header.add("09月");
			header.add("10月");
			header.add("11月");
			header.add("12月");
			
			List<List<String>> list = medInHMBudgService.queryExportData(mapVo);
			String filename = "医院月份医疗收入预算.xlsx";
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			ExcelUtils.getInstance().exportObjects2Excel(list, header,response.getOutputStream());
			return null;
		} catch (Exception e) {
			return "{\"error\":\"导出失败\"}";
		}
	}
}

