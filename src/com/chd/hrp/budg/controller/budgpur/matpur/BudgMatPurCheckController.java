﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgpur.matpur;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgMatPurCheck;
import com.chd.hrp.budg.service.budgpur.matpur.BudgMatPurCheckService;
import com.chd.hrp.budg.serviceImpl.common.BudgNoRulesServiceImpl;

/**
 * 
 * @Description:
 * 材料采购预算调整
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMatPurCheckController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMatPurCheckController.class);
	
	//引入Service服务
	@Resource(name = "budgMatPurCheckService")
	private final BudgMatPurCheckService budgMatPurCheckService = null;
	@Resource(name="budgNoRulesService")
    private final BudgNoRulesServiceImpl  budgNoRulesService=null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/budgMatPurCheckMainPage", method = RequestMethod.GET)
	public String budgMedIncomeCheckMainPage(Model mode) throws Exception {

		return "hrp/budg/budgpur/mat/matpurcheck/budgMatPurCheckMain";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/budgMatPurCheckAddPage", method = RequestMethod.GET)
	public String budgMatPurCheckAddPage(Model mode) throws Exception {

		return "hrp/budg/budgpur/mat/matpurcheck/budgMatPurCheckAdd";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/addBudgMatPurCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMatPurCheck(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		String budgMatPurCheckJson = null ;
		
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			
			//初始审批  校验数据是否存在
			
			if("01".equals(mapVo.get("check_type"))){
				
				int count = budgMatPurCheckService.queryDateExist(mapVo);

				if (count > 0 ) {

					return JSONObject.parseObject("{\"error\":\"该年度初始审批已添加,不能重复添加.\",\"state\":\"false\"}");

				}
				
			}else{
				//构建 查询 添加 审批单年度 初始审批单 是否已下达  参数Map
				
				Map<String,Object> initMap = new HashMap<String,Object>();
				
				initMap.put("group_id", mapVo.get("group_id")) ;
				
				initMap.put("hos_id", mapVo.get("hos_id")) ;
				
				initMap.put("copy_code", mapVo.get("copy_code")) ;
				
				initMap.put("budg_year", mapVo.get("budg_year")) ;
				
				initMap.put("check_type", "01") ;//审批类型  初始审批 
				
				initMap.put("bc_state", "04") ;//状态  04 已下达
				
				// 根据参数 查询 初始审批单是否已下达 
				int count = budgMatPurCheckService.queryInitDateExist(initMap) ;
				
				if(count == 0){
					
					return JSONObject.parseObject("{\"error\":\"该年度初始审批未下达,不允许重复添加调整审批单.\",\"state\":\"false\"}");
				}
				
				//根据参数 查询 是否存在未下达的审批表
				int countIssue = budgMatPurCheckService.queryIssueDateExist(initMap);
				
				if(countIssue > 0){
					
					return JSONObject.parseObject("{\"error\":\"存在未下达的审批表，不允许继续添加.\",\"state\":\"false\"}");
				}
			}
			
			
			//构建 取 审批单号 参数Map
			Map<String,Object> noMap = new HashMap<String,Object>();	
			
			noMap.put("table_code", "BUDG_MAT_PUR_CHECK");
			noMap.put("table_name", "材料采购预算审批");
			noMap.put("budg_year", mapVo.get("budg_year"));
			
			Map<String,Object> map = budgNoRulesService.getBudgNextNo(noMap) ;
			
			if("true".equals(map.get("state"))){
				//自动生成审批单号
				mapVo.put("check_code",map.get("noCode"));
				
			}else{
				
				return map ;
			}
			
			mapVo.put("maker", SessionManager.getUserId())   ;
			
			
			mapVo.put("make_data",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
			mapVo.put("checker", "");
			mapVo.put("check_data", "");
			mapVo.put("issue_data", "");
	       
			budgMatPurCheckJson = budgMatPurCheckService.add(mapVo);
			
		} catch (Exception e) {

			budgMatPurCheckJson = e.getMessage();
		}
			
		return JSONObject.parseObject(budgMatPurCheckJson);
		
	}
	
	/**
	 * @Description 
	 *业务预算审核申请修改
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/budgMatPurCheckUpdatePage", method = RequestMethod.GET)
	public String budgMatPurCheckUpdatePage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		DateFormat df  = new SimpleDateFormat("yyyy-MM-dd");
		
		Map<String,Object> map = budgMatPurCheckService.queryByCode(mapVo);
		mode.addAttribute("group_id",map.get("group_id"));
		mode.addAttribute("hos_id",map.get("hos_id"));
		mode.addAttribute("copy_code",map.get("copy_code"));
		mode.addAttribute("budg_year", map.get("budg_year"));
		mode.addAttribute("adjust_code", map.get("adjust_code"));
		mode.addAttribute("check_code", map.get("check_code"));
		mode.addAttribute("check_type", map.get("check_type"));
		mode.addAttribute("remark", map.get("remark"));
		mode.addAttribute("maker", map.get("maker"));
		mode.addAttribute("maker_name", map.get("maker_name"));
		mode.addAttribute("make_data", df.format(df.parse(String.valueOf(map.get("make_data")))));
		mode.addAttribute("checker", map.get("checker"));
		mode.addAttribute("check_name", map.get("check_name"));
		if(map.get("check_data") != null){
			mode.addAttribute("check_data", df.format(df.parse(String.valueOf(map.get("check_data")))));
		}else{
			mode.addAttribute("check_data","");
		}
		if(map.get("issue_data") != null){
			mode.addAttribute("issue_data", df.format(df.parse(String.valueOf(map.get("issue_data")))));
		}else{
			mode.addAttribute("issue_data","");
		}
		mode.addAttribute("bc_state", map.get("bc_state"));
		return "/hrp/budg/budgpur/mat/matpurcheck/budgMatPurCheckUpdate";	
		
	}
	
	
	/**
	 *  发送 、 召回 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/sendOrRecall", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> sendOrRecall(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("check_code", ids[1]);
				
			mapVo.put("bc_state",ids[2]);
			listVo.add(mapVo);			

		}
		String matOrderMain = budgMatPurCheckService.updateBc_state(listVo);		
		return JSONObject.parseObject(matOrderMain);
	}
	/**
	 * 审核、销审
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/auditOrUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditOrUnAudit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("check_code", ids[1]);
			mapVo.put("bc_state",ids[2]);
			//审核
			if("03".equals(ids[2])){
				
				mapVo.put("checker",SessionManager.getUserId());
				mapVo.put("check_data",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
				
			}else{//消审
				
				mapVo.put("checker","");
				mapVo.put("check_data","");
				
			}
			
			listVo.add(mapVo);			
		}
		String matOrderMain = budgMatPurCheckService.auditOrUnAudit(listVo);		
		return JSONObject.parseObject(matOrderMain);
	}
	/**
	 * 预算下达 、取消预算下达
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/cancelIssueOrIssue", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> cancelIssueOrIssue(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("check_code", ids[1]);
			mapVo.put("bc_state",ids[2]);
		    if(ids[2].toString().equals("04")){
		    	
		    	mapVo.put("issue_data",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
		    	
		    }else{
		    	
		    	mapVo.put("issue_data","");
		    	
		    }
			
			
			listVo.add(mapVo);			
			}

		String matOrderMain = budgMatPurCheckService.cancelIssueOrIssue(listVo);		
		return JSONObject.parseObject(matOrderMain);
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/updateBudgMatPurCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMatPurCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId()); 
		
		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String budgMatPurCheckJson = budgMatPurCheckService.update(mapVo);
		
		return JSONObject.parseObject(budgMatPurCheckJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/addOrUpdateBudgMatPurCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgMatPurCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMatPurCheckJson ="";
		

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
	  
		budgMatPurCheckJson = budgMatPurCheckService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMatPurCheckJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 审批类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/deleteBudgMatPurCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMatPurCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   
		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());             	
	            	
		String budgMatPurCheckJson= budgMatPurCheckService.delete(mapVo);	
		
		return JSONObject.parseObject(budgMatPurCheckJson);

	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/queryBudgMatPurCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMatPurCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgMatPurCheck = budgMatPurCheckService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMatPurCheck);
		
	}
	
  /**
	 * @Description 
	 * 导入
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/budgMatPurCheckImportPage", method = RequestMethod.GET)
	public String budgMatPurCheckImportPage(Model mode) throws Exception {

		return "hrp/budg/budgpur/mat/matpurcheck/budgMatPurCheckImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/budg/budgpur/mat/matpurcheck/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","材料采购预算审批申请.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgpur/mat/matpurcheck/readBudgMatPurCheckFiles",method = RequestMethod.POST)  
    public String readBudgMatPurCheckFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMatPurCheck> list_err = new ArrayList<BudgMatPurCheck>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMatPurCheck budgMatPurCheck = new BudgMatPurCheck();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgMatPurCheck.setBudg_year(temp[3]);
		    		mapVo.put("budg_year", temp[3]);
					
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgMatPurCheck.setCheck_code(temp[4]);
		    		mapVo.put("check_code", temp[4]);
					
					} else {
						
						err_sb.append("审批单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgMatPurCheck.setCheck_type(temp[5]);
		    		mapVo.put("check_type", temp[5]);
					
					} else {
						
						err_sb.append("审批类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgMatPurCheck.setRemark(temp[6]);
		    		mapVo.put("remark", temp[6]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgMatPurCheck.setMaker(Long.valueOf(temp[7]));
		    		mapVo.put("maker", temp[7]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgMatPurCheck.setMake_data(DateUtil.stringToDate(temp[8]));
		    		mapVo.put("make_data", temp[8]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgMatPurCheck.setChecker(Long.valueOf(temp[9]));
		    		mapVo.put("checker", temp[9]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgMatPurCheck.setCheck_data(DateUtil.stringToDate(temp[10]));
		    		mapVo.put("check_data", temp[10]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					budgMatPurCheck.setIssue_data(DateUtil.stringToDate(temp[11]));
		    		mapVo.put("issue_data", temp[11]);
					
					} else {
						
						err_sb.append("预算下达日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					budgMatPurCheck.setBc_state(temp[12]);
		    		mapVo.put("bc_state", temp[12]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					
				BudgMatPurCheck data_exc_extis = budgMatPurCheckService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMatPurCheck.setError_type(err_sb.toString());
					
					list_err.add(budgMatPurCheck);
					
				} else {
			  
					String dataJson = budgMatPurCheckService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMatPurCheck data_exc = new BudgMatPurCheck();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 审批类型
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/addBatchBudgMatPurCheck", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgMatPurCheck(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMatPurCheck> list_err = new ArrayList<BudgMatPurCheck>();
		
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
			
			BudgMatPurCheck budgMatPurCheck = new BudgMatPurCheck();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgMatPurCheck.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_code"))) {
						
					budgMatPurCheck.setCheck_code((String)jsonObj.get("check_code"));
		    		mapVo.put("check_code", jsonObj.get("check_code"));
		    		} else {
						
						err_sb.append("审批单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_type"))) {
						
					budgMatPurCheck.setCheck_type((String)jsonObj.get("check_type"));
		    		mapVo.put("check_type", jsonObj.get("check_type"));
		    		} else {
						
						err_sb.append("审批类型为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgMatPurCheck.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					budgMatPurCheck.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("make_data"))) {
						
					budgMatPurCheck.setMake_data(DateUtil.stringToDate((String)jsonObj.get("make_data")));
		    		mapVo.put("make_data", jsonObj.get("make_data"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					budgMatPurCheck.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_data"))) {
						
					budgMatPurCheck.setCheck_data(DateUtil.stringToDate((String)jsonObj.get("check_data")));
		    		mapVo.put("check_data", jsonObj.get("check_data"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("issue_data"))) {
						
					budgMatPurCheck.setIssue_data(DateUtil.stringToDate((String)jsonObj.get("issue_data")));
		    		mapVo.put("issue_data", jsonObj.get("issue_data"));
		    		} else {
						
						err_sb.append("预算下达日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bc_state"))) {
						
					budgMatPurCheck.setBc_state((String)jsonObj.get("bc_state"));
		    		mapVo.put("bc_state", jsonObj.get("bc_state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					
				BudgMatPurCheck data_exc_extis = budgMatPurCheckService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMatPurCheck.setError_type(err_sb.toString());
					
					list_err.add(budgMatPurCheck);
					
				} else {
			  
					String dataJson = budgMatPurCheckService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMatPurCheck data_exc = new BudgMatPurCheck();
			
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
	 * 其他预算  初始审批 查询（未下达）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/queryBudgMatPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMatPur(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("budg_year") == null){
				
				mapVo.put("budg_year", SessionManager.getAcctYear());
	        
			}
			String budgMatPurCheck = budgMatPurCheckService.queryBudgMatPur(getPage(mapVo));
			return JSONObject.parseObject(budgMatPurCheck);
	}
	/**
	 * 其他预算  初始审批 查询（已下达）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/queryBudgMatPurCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMatPurCopy(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("budg_year") == null){
				
				mapVo.put("budg_year", SessionManager.getAcctYear());
	        
			}
			String budgMatPurCheck = budgMatPurCheckService.queryBudgMatPurCopy(getPage(mapVo));
			return JSONObject.parseObject(budgMatPurCheck);
	}
	
	
	/**
	 * 材料采购预算调整审批 查询（未下达）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/queryBudgMatPurAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMatPurAdjust(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("budg_year") == null){
				
				mapVo.put("budg_year", SessionManager.getAcctYear());
	        
			}
			String budgMatPurCheck = budgMatPurCheckService.queryBudgMatPurAdjust(getPage(mapVo));
			
			return JSONObject.parseObject(budgMatPurCheck);
	}
	
	/**
	 * 材料采购预算调整审批 查询（已下达）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/queryBudgMatPurAdjustCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMatPurAdjustCopy(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("budg_year") == null){
				
				mapVo.put("budg_year", SessionManager.getAcctYear());
	        
			}
			String budgMatPurCheck = budgMatPurCheckService.queryBudgMatPurAdjustCopy(getPage(mapVo));
			
			return JSONObject.parseObject(budgMatPurCheck);
	}
	
	
	/**
	 * 根据 所传参数 查询数据状态
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpurcheck/queryBudgMatPurCheckState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMatPurCheckState(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			List<String> list= budgMatPurCheckService.queryBudgMatPurCheckState(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"state\":\"true\"}");
				
			}else{
				
				String  str = "" ;
				for(String item : list){
					
					str += item +"," ;
				}
				
				return JSONObject.parseObject("{\"check_code\":\""+str+"\",\"state\":\"false\"}");
			}
			
	}

    
}

