/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.dura.tran;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.dura.tran.MatDuraTranDeptDeptService;

/**
 * 
 * @Description: 耐用品科室到科室转移 
 * @Table: MAT_DURA_DEPT_(DEPT/DEPT_D)
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatDuraTranDeptDeptController extends BaseController {

	private static Logger logger = Logger.getLogger(MatDuraTranDeptDeptController.class);

	// 引入Service服务
	@Resource(name = "matDuraTranDeptDeptService")
	private final MatDuraTranDeptDeptService matDuraTranDeptDeptService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/mainPage", method = RequestMethod.GET)
	public String matDuraTranDeptDeptMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));
		
		return "hrp/mat/dura/tran/deptDept/main";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/addPage", method = RequestMethod.GET)
	public String matDuraTranDeptDeptAddPage(Model mode) throws Exception {
       
		
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("user_code", SessionManager.getUserCode());
		mode.addAttribute("user_name", SessionManager.getUserName());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("user_id", SessionManager.getUserId());
		mode.addAttribute("user_msg", matCommonService.getLoginUserMsg(map));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/dura/tran/deptDept/add";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/queryMatDuraTranDeptDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraTranDeptDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		//转换日期格式
		if(mapVo.get("begin_make_date") != null && !"".equals(mapVo.get("begin_make_date"))){
			mapVo.put("begin_make_date", DateUtil.stringToDate(mapVo.get("begin_make_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_make_date") != null && !"".equals(mapVo.get("end_make_date"))){
			mapVo.put("end_make_date", DateUtil.stringToDate(mapVo.get("end_make_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("begin_confirm_date") != null && !"".equals(mapVo.get("begin_confirm_date"))){
			mapVo.put("begin_confirm_date", DateUtil.stringToDate(mapVo.get("begin_confirm_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_confirm_date") != null && !"".equals(mapVo.get("end_confirm_date"))){
			mapVo.put("end_confirm_date", DateUtil.stringToDate(mapVo.get("end_confirm_date").toString(), "yyyy-MM-dd"));
		}
		
		String matIn = matDuraTranDeptDeptService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/queryMatDuraTranDeptDeptDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraTranDeptDeptDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String detail = matDuraTranDeptDeptService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/queryMatDuraTranDeptDeptById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraTranDeptDeptById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		return matDuraTranDeptDeptService.queryMainByCode(mapVo);
	}

	/**
	 * @Description 添加数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/addMatDuraTranDeptDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatDuraTranDeptDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson;
		try {
			
			matJson = matDuraTranDeptDeptService.add(mapVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/updatePage", method = RequestMethod.GET)
	public String matDuraTranDeptDeptUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		Map<String, Object> matDuraDeptDept = matDuraTranDeptDeptService.queryMainByCode(mapVo);
		
		//编制日期
		if(matDuraDeptDept.get("make_date") != null && !"".equals(matDuraDeptDept.get("make_date"))){
			matDuraDeptDept.put("make_date", DateUtil.dateToString((Date)matDuraDeptDept.get("make_date"), "yyyy-MM-dd"));
		}
		//审核日期
		if(matDuraDeptDept.get("check_date") != null && !"".equals(matDuraDeptDept.get("check_date"))){
			matDuraDeptDept.put("check_date", DateUtil.dateToString((Date)matDuraDeptDept.get("check_date"), "yyyy-MM-dd"));
		}
		//确认日期
		if(matDuraDeptDept.get("confirm_date") != null && !"".equals(matDuraDeptDept.get("confirm_date"))){
			matDuraDeptDept.put("confirm_date", DateUtil.dateToString((Date)matDuraDeptDept.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("matDuraDeptDept", matDuraDeptDept);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));
		
		return "hrp/mat/dura/tran/deptDept/update";
	}

	/**
	 * @Description 更新数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/updateMatDuraTranDeptDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatDuraTranDeptDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		//编制日期
		if(mapVo.get("make_date") != null && !"".equals(mapVo.get("make_date"))){
			mapVo.put("make_date", DateUtil.stringToDate(mapVo.get("make_date").toString(), "yyyy-MM-dd"));
		}
		
		String matJson;
		try {
			
			matJson = matDuraTranDeptDeptService.update(mapVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 删除数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/deleteMatDuraTranDeptDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatDuraTranDeptDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String matJson;
		try {
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("dura_id", ids[3]);
				mapVo.put("check_state", 1);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraTranDeptDeptService.deleteBatch(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/auditMatDuraTranDeptDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatDuraTranDeptDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String user_id = SessionManager.getUserId();
		String matJson;
		try {
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("dura_id", ids[3]);
				mapVo.put("state", 2);
				mapVo.put("checker", user_id);
				mapVo.put("check_date", date);
				mapVo.put("check_state", 1);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraTranDeptDeptService.auditOrUnAuditBatch(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/unAuditMatDuraTranDeptDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatDuraTranDeptDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String matJson;
		try {
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("dura_id", ids[3]);
				mapVo.put("state", 1);
				mapVo.put("checker", "");
				mapVo.put("check_date", "");
				mapVo.put("check_state", 2);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraTranDeptDeptService.auditOrUnAuditBatch(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/confirmMatDuraTranDeptDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatDuraTranDeptDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String dates = DateUtil.dateToString(date, "yyyy-MM-dd");
		String year = dates.substring(0, 4);
		String month = dates.substring(5, 7);
		String user_id = SessionManager.getUserId();
		String matJson;
		try {
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("dura_id", ids[3]);
				mapVo.put("state", 3);
				mapVo.put("confirmer", user_id);
				mapVo.put("confirm_date", date);
				mapVo.put("check_state", 2);  //用于判断状态
				mapVo.put("year", year);  //用于修改单据期间
				mapVo.put("month", month);  //用于修改单据期间
				listVo.add(mapVo);
			}
		
			matJson = matDuraTranDeptDeptService.confirmBatch(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 选择材料页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/choiceInvPage", method = RequestMethod.GET)
	public String matDuraTranDeptDeptChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/dura/tran/deptDept/choiceInv";
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/printSetPage", method = RequestMethod.GET)
	public String printSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*//*
	@RequestMapping(value = "/hrp/mat/dura/tran/deptDept/queryDataByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDataByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if (mapVo.get("p_num") .equals("1") ) {
			mapVo.put("p_num", 1);
		}else{
			mapVo.put("p_num", 0);
		}
		String reJson=null;
		try {
			
			reJson=matDuraTranDeptDeptService.queryDataByPrintTemlate(mapVo);
		} catch (Exception e) {
			
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}*/
}