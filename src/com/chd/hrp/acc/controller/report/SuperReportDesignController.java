package com.chd.hrp.acc.controller.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.service.report.SuperReportDataSetService;
import com.chd.hrp.acc.service.report.SuperReportDesignService;

@Controller
@RequestMapping(value="/hrp/acc/superReport")
public class SuperReportDesignController extends BaseController{
	
	@Resource(name = "superReportDataSetService")
	private SuperReportDataSetService superReportDataSetService;
	
	private static Logger logger = Logger.getLogger(SuperReportDesignController.class);
	
	@Resource(name = "superReportDesignService")
	private final SuperReportDesignService superReportDesignService = null;
	
	//报表制作主页面
	@RequestMapping(value = "/make/makeMainPage", method = RequestMethod.GET)
	public String mainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/acc/superReport/make/main";
	}
	
	//报表定义页面
	@RequestMapping(value = "/make/reportPage", method = RequestMethod.GET)
	public String reportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("mod_code")==null||mapVo.get("mod_code").toString().equals("undefined")){
			mapVo.put("mod_code", null);
		}
		mode.addAttribute("mod_code", mapVo.get("mod_doe"));
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("report_code", mapVo.get("report_code"));
		String dsJson = superReportDataSetService.querySuperReportDsManager(mapVo);
		mode.addAttribute("ds", dsJson);
		return "hrp/acc/superReport/make/report";
	}

	@RequestMapping(value = "/paraSetPage", method = RequestMethod.GET)
	public String paraSetPage(@RequestParam Map<String,Object> mapVo,Model mode){
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String,Object>> list = superReportDesignService.queryDetailParamForMakeReport(mapVo);
		mode.addAttribute("list", ChdJson.toJson(list));
		mode.addAttribute("rep_code", mapVo.get("report_code"));
		return "hrp/acc/superReport/query/paraSet";
	}
	
	//根据系统编码查询报表
	@RequestMapping(value = "/make/insertRepRepDSPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertRepRepDSPara(@RequestParam String mapVo, Model mode) throws Exception {
		@SuppressWarnings("rawtypes")
		List<Map> listVo = JSONArray.parseArray(mapVo, Map.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("mod_code", SessionManager.getModCode());
		map.put("list", listVo);
		
		String content=superReportDesignService.updateRepRepDSPara(map);
		return JSONObject.parseObject(content);
		
	}
	//根据系统编码查询报表
	@RequestMapping(value = "/querySuperReportByMod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportByMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String content=superReportDesignService.querySuperReportByMod(mapVo);
		return JSONObject.parseObject(content);
		
	}
	
	
	//根据报表编码查询报表
	@RequestMapping(value = "/querySuperReportContentByCode", method = RequestMethod.POST)//,produces = "text/html;charset=UTF-8"
	@ResponseBody
	public Map<String, Object> querySuperReportContentByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String content=superReportDesignService.querySuperReportContentByCode(mapVo);
		
		return JSONObject.parseObject(content);
		
	}
	
	//保存报表
	@RequestMapping(value = "/saveSuperReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSuperReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("user_id", SessionManager.getUserId());
        mapVo.put("create_date",DateUtil.getCurrenDate());
		
		String reJson=null;
		try {
			reJson=superReportDesignService.saveSuperReport(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
       
		return JSONObject.parseObject(reJson);
		
	}
	
	//保存报表内容，大字段
	@RequestMapping(value = "/updateSuperReportContent", method = RequestMethod.POST)//,produces = "text/html;charset=UTF-8"
	@ResponseBody
	public Map<String, Object> updateSuperReportContent(@RequestBody Map<String, Object> mapVo) throws Exception {
		if(mapVo.get("content")==null){
			return JSONObject.parseObject("{\"error\":\"报表内容为空！\"}");
		}
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("user_id", SessionManager.getUserId());
        mapVo.put("create_date",DateUtil.getCurrenDate());
		
		String reJson=null;
		try {
			reJson=superReportDesignService.updateSuperReportContent(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
       
		return JSONObject.parseObject(reJson);
		
	}
	
	//根据报表编码删除报表
	@RequestMapping(value = "/deleteSuperReportByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  deleteSuperReportByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        String reJson=null;
		try{
			reJson=superReportDesignService.deleteSuperReportByCode(mapVo);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
        
		return JSONObject.parseObject(reJson);
		
	}
	
	
	//报表制作主页面-弹出设置单元格页面
	@RequestMapping(value = "/make/cellSetPage", method = RequestMethod.GET)
	public String cellSetPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("mod_code", mapVo.get("mod_code"));
		return "hrp/acc/superReport/make/cellSet";
	}
	
	//报表定义页面  查询数据集数 树形结构
	@ResponseBody
	@RequestMapping(value = "/make/queryMakeSuperReportDsManager", method = RequestMethod.POST)
	public Map<String, Object> queryMakeSuperReportDsManager(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("mod_code", SessionManager.getModCode());
        
        String query = superReportDesignService.queryMakeSuperReportDsManager(mapVo);
        return JSONObject.parseObject(query);
	}
	
	//根据系统编码查询报表元素
	@RequestMapping(value = "/querySuperReportEleByMod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportEleByMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String content=superReportDesignService.querySuperReportEleByMod(mapVo);
		return JSONObject.parseObject(content);
		
	}
	
	//根据报表元素查询报表元素参数
	@RequestMapping(value = "/querySuperReportParaByEle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportParaByEle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String reJson=superReportDesignService.querySuperReportParaByEle(mapVo);
		return JSONObject.parseObject(reJson);
		
	}
	
	
	//报表制作主页面-弹出设置结果集条件页面
	@RequestMapping(value = "/make/dsParaSetPage", method = RequestMethod.GET)
	public String dsParaSetPage(@RequestParam Map<String, Object> mapVo,ModelMap mode) throws Exception {
		mode.put("dsNames", mapVo.get("dsNames"));
		//获取参数值传递到页面
		return "hrp/acc/superReport/make/dsParaSet";
	}
	
	//根据报表元素查询报表元素参数
		@RequestMapping(value = "/make/getReportQueryData", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryParaByDs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			String[] dsCodes=null;
			if(mapVo.get("ds_code")!=null)
				dsCodes=mapVo.get("ds_code").toString().split(",");
			mapVo.put("ds_codes", dsCodes);
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			//根据数据集名称获取参数（去重）
			//sql= select distinct(para_name) from rep_ds_para
			List<Map<String,Object>> list=superReportDesignService.queryDsParaByDistinct(mapVo);
			String reJson=ChdJson.toJsonLower(list);
			return JSONObject.parseObject(reJson);
		} 
	//报表制作主页面-弹出设置查询条件页面
		@RequestMapping(value = "/make/reportQueryPage", method = RequestMethod.GET)
		public String reportQuerySetPage(@RequestParam Map<String, Object> mapVo,ModelMap mode) throws Exception {			
			mode.put("ds_code", mapVo.get("ds_code"));
			mode.put("report_code", mapVo.get("report_code"));
			//获取参数值传递到页面
			return "hrp/acc/superReport/make/querySet";
		}
	
	//根据报表元素查询报表元素参数
	@RequestMapping(value = "/querySuperReportParaByDs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportParaByDs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        String reJson=superReportDesignService.querySuperReportParaByDs(mapVo);
		return JSONObject.parseObject(reJson);
	}
	
	
	@RequestMapping(value = "/saveReportQuery",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveReportQuery(@RequestParam Map<String, Object> mapVo, Model mode){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("rep_code", mapVo.get("report_code"));
		
		String params=mapVo.get("params").toString();
		JSONArray array=JSONArray.parseArray(params);
		JSONObject json=null;
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> tmp=null;
		for(int i=0;i<array.size();i++){
			json=array.getJSONObject(i);
			json=json.getJSONObject("rowData");
			tmp=json.toJavaObject(json, Map.class);
			list.add(tmp);
		}
		mapVo.put("list", list);
		String reJson=null;
		try{
			reJson=superReportDataSetService.saveReportQuery(mapVo);
		}catch(Exception e){
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return  JSONObject.parseObject(reJson);
	}
	
	@RequestMapping(value = "/saveSuperReportDsPara",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSuperReportDsParas(@RequestParam Map<String, Object> mapVo, Model mode){
		String params=mapVo.get("params").toString();
		JSONObject paraJson=JSONObject.parseObject(params);
		Iterator<String> iter=paraJson.keySet().iterator();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("mod_code", SessionManager.getModCode());
		List<Map> listVo=new ArrayList<Map>();
		Map<String,Object> tmp=null;
		String key=null;
		String pvalue=null;
		boolean b=true;
		String reJson=null;
		String[] keys=null;
		try{
		while(iter.hasNext()){
			key=iter.next().toString();
			pvalue=paraJson.getString(key);
			tmp=new HashMap<String,Object>();
			keys=key.split("#");
			if(keys.length==2){
				tmp.put("param_name", keys[0]);
				tmp.put("ds_code", keys[1]);
			}else{
				b=false;
				break;
			}
			tmp.put("param_value",pvalue);
			listVo.add(tmp);
		}
		if(b){
			//删除原参数设置
			
			map.put("list", listVo);
			int m=superReportDataSetService.deleteSuperReportDsParaValues(map);
			reJson=superReportDataSetService.saveSuperReportDSParaValues(map);
		}
		}catch(Exception e){
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return  JSONObject.parseObject(reJson);
	}
	
	//参数下拉框数据初始化，报表定义通用 
	@RequestMapping(value = "/querySuperReportParaSelectData", method = RequestMethod.POST)
	@ResponseBody
	public String querySuperReportParaSelectData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		//存级联条件的值
		if(mapVo.get("paras")!=null && !mapVo.get("paras").toString().equals("")){
			String paras=mapVo.get("paras").toString();
			for(String s:paras.split(",")){
				String[] sv=s.split("@");
				if(mapVo.get(sv[0])==null){
					mapVo.put(sv[0], sv.length==2?sv[1]:"");
				}
			}
			mapVo.remove("paras");
		}
		
		if (mapVo.get("group_id") == null || mapVo.get("group_id").toString().equals("") || mapVo.get("group_id").toString().equals("本集团")) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null || mapVo.get("hos_id").toString().equals("") || mapVo.get("hos_id").toString().equals("本医院")) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null || mapVo.get("copy_code").toString().equals("") || mapVo.get("copy_code").toString().equals("本账套")) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null || mapVo.get("acc_year").toString().equals("") || mapVo.get("acc_year").toString().equals("本年度") || mapVo.get("acc_year").toString().equals("上年") || mapVo.get("acc_year").toString().equals("上两年")) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		if (mapVo.get("budg_year") == null || mapVo.get("budg_year").toString().equals("") || mapVo.get("budg_year").toString().equals("本年度")) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		if (mapVo.get("user_id") == null || mapVo.get("user_id").toString().equals("")) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		if (mapVo.get("user_code") == null || mapVo.get("user_code").toString().equals("")) {
			mapVo.put("user_code", SessionManager.getUserCode());
		}
		
		
		if (mapVo.get("dict_code") == null || mapVo.get("dict_code").equals("")) {
			return "[{\"id\":\"-1\",\"text\":\"字典编码不能为空\"}]";
		}
		String reJson="";
		try {
			reJson=superReportDesignService.querySuperReportParaSelectData(mapVo);
			//查询选中值
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage(),e);
			reJson="[{\"id\":\"-2\",\"text\":\"字典的SQL不能正确执行\"}]";
		}
		return reJson;
	}
}
