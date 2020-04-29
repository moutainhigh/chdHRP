﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.storage.check;
import java.io.IOException;
import java.util.*;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatCheckMain;
import com.chd.hrp.mat.entity.MatOutMain;
import com.chd.hrp.mat.service.storage.check.MatCheckMainService;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
import com.chd.hrp.mat.service.storage.out.MatOutMainService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_CHECK_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
  

@Controller
public class MatCheckMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatCheckMainController.class);
	
	//引入Service服务
	@Resource(name = "matCheckMainService")
	private final MatCheckMainService matCheckMainService = null;
   
	@Resource(name = "matStorageInService")
	private final MatStorageInService matStorageInService = null;
	
	// 引入Service服务
	@Resource(name = "matOutMainService")
	private final MatOutMainService matOutMainService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/check/matCheckMainMainPage", method = RequestMethod.GET)
	public String matCheckMainMainPage(Model mode) throws Exception {

		mode.addAttribute("p04027", MyConfig.getSysPara("04027"));
		mode.addAttribute("p04046", MyConfig.getSysPara("04046"));

		return "hrp/mat/storage/check/matCheckMainMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/check/matCheckMainAddPage", method = RequestMethod.GET)
	public String matCheckMainAddPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/check/matCheckMainAdd";

	}
	
	/**
	 * @Description 仓库页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/check/matCheckMainByStorePage", method = RequestMethod.GET)
	public String matCheckByStorePage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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

		
		
		if(paras != null && !"null".equals(paras)){
			
			mode.addAttribute("store_id", paras);
		}

		
		
		return "hrp/mat/storage/check/matCheckMainByStore";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/check/addMatCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
	    mapVo.put("maker", SessionManager.getUserId());
		
		mapVo.put("make_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));

		String matJson;
		try {
			matJson = matCheckMainService.add(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
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
	@RequestMapping(value = "/hrp/mat/storage/check/matCheckMainUpdatePage", method = RequestMethod.GET)
	public String matCheckMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

	    if(mapVo.get("group_id") == null){mapVo.put("group_id", SessionManager.getGroupId());}
		
		if(mapVo.get("hos_id") == null){mapVo.put("hos_id", SessionManager.getHosId());}
		
		if(mapVo.get("copy_code") == null){mapVo.put("copy_code", SessionManager.getCopyCode());}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		MatCheckMain matCheckMain = matCheckMainService.queryMatCheckMainByCode(mapVo);
		
		mode.addAttribute("hos_id", matCheckMain.getHos_id());
		mode.addAttribute("group_id", matCheckMain.getGroup_id());
		mode.addAttribute("copy_code", matCheckMain.getCopy_code());
		mode.addAttribute("check_id", matCheckMain.getCheck_id());
		mode.addAttribute("check_code", matCheckMain.getCheck_code());
		mode.addAttribute("store_id", matCheckMain.getStore_id());
		mode.addAttribute("store_no", matCheckMain.getStore_no());
		mode.addAttribute("store_code", matCheckMain.getStore_code());
		mode.addAttribute("store_name", matCheckMain.getStore_name());
		mode.addAttribute("dept_id", matCheckMain.getDept_id());
		mode.addAttribute("dept_no", matCheckMain.getDept_no());
		mode.addAttribute("dept_code", matCheckMain.getDept_code());
		mode.addAttribute("dept_name", matCheckMain.getDept_name());
		mode.addAttribute("create_date", DateUtil.dateToString(matCheckMain.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("check_date", DateUtil.dateToString(matCheckMain.getCheck_date(), "yyyy-MM-dd"));
		mode.addAttribute("emp_id", matCheckMain.getEmp_id());
		mode.addAttribute("emp_no", matCheckMain.getEmp_no());
		mode.addAttribute("emp_code", matCheckMain.getEmp_code());
		mode.addAttribute("emp_name", matCheckMain.getEmp_name());
		mode.addAttribute("maker", matCheckMain.getMaker());
		mode.addAttribute("checker", matCheckMain.getChecker());
		mode.addAttribute("state", matCheckMain.getState());
		mode.addAttribute("brif", matCheckMain.getBrif());

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04046", MyConfig.getSysPara("04046"));
		mode.addAttribute("p04076", MyConfig.getSysPara("04076"));
		
		return "hrp/mat/storage/check/matCheckMainUpdate";
	}
	
	/**
	 * @Description 修改页面查明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/check/queryMatCheckDetailByCheckID", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCheckDetailByCheckID(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String matCheckDetail = matCheckMainService.queryMatCheckDetailByCheckID(mapVo);
		
		return JSONObject.parseObject(matCheckDetail);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/check/updateMatCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}

		String matJson;
		try {
			matJson = matCheckMainService.update(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
	  
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/check/addOrUpdateMatCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matJson ="";

		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());   
		}
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());   
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

			if(detailVo.get("hos_id") == null){
				detailVo.put("hos_id", SessionManager.getHosId());   
			}
			if(detailVo.get("group_id") == null){
				detailVo.put("group_id", SessionManager.getGroupId());   
			}
			if(detailVo.get("copy_code") == null){
				detailVo.put("copy_code", SessionManager.getCopyCode());   
			}
	
			try {
				matJson = matCheckMainService.addOrUpdate(detailVo);
			} catch (Exception e) {
				matJson = e.getMessage();
			}
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
	@RequestMapping(value = "/hrp/mat/storage/check/deleteMatCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_id", ids[3]);

			listVo.add(mapVo);

		}

		String matJson;
		try {
			matJson = matCheckMainService.deleteBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * @Description 消审数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/check/unAuditMatCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_id", ids[3]);
			
			mapVo.put("checker", "");
			mapVo.put("check_date","");

			listVo.add(mapVo);

		}

		String matJson;
		try {
			matJson = matCheckMainService.unAuditMatCheckMain(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * @Description 审核数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/check/auditMatCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_id", ids[3]);
			
			mapVo.put("checker", SessionManager.getUserId());
			mapVo.put("check_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));
			listVo.add(mapVo);

		}

		String matJson;
		try {
			matJson = matCheckMainService.auditMatCheckMain(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/check/queryMatCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		//由于xml中已经to_char （之前我查询的日期是2017-08-11 的日期，查询不到2017-08-11 的数据，必须日期选择为2017-08-12才会查询到）
		//时间格式转换
		/*if(mapVo.get("create_date_start") != null && !"".equals(mapVo.get("create_date_start").toString())){
			mapVo.put("create_date_start", DateUtil.stringToDate(mapVo.get("create_date_start").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("create_date_end") != null && !"".equals(mapVo.get("create_date_end").toString())){
			mapVo.put("create_date_end", DateUtil.stringToDate(mapVo.get("create_date_end").toString(), "yyyy-MM-dd"));
		}*/

		String matCheckMain = matCheckMainService.queryMatCheckMain(getPage(mapVo));

		return JSONObject.parseObject(matCheckMain);
		
	}
	
	/**
	 * @Description 生成出入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/check/createInOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createInOut(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String user_id = SessionManager.getUserId();
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_id", ids[3]);
			mapVo.put("user_id", user_id);
			mapVo.put("state", 3);//已生成
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = matCheckMainService.addInOut(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/check/queryMatCheckMainByMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCheckMainByMatInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		if(mapVo.get("acct_year") == null){
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String matCheckMain = matCheckMainService.queryMatCheckMainByMatInv(getPage(mapVo));

		return JSONObject.parseObject(matCheckMain);
		
	}
	
	/**
	 * 盘盈单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/check/matStorageInPage", method = RequestMethod.GET)
	public String matStorageInPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		Map<String, Object> matInMain = matStorageInService.queryByCode(mapVo);
		if(matInMain.get("in_date") != null && !"".equals(matInMain.get("in_date"))){
			matInMain.put("in_date", DateUtil.dateToString((Date)matInMain.get("in_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("check_date") != null && !"".equals(matInMain.get("check_date"))){
			matInMain.put("check_date", DateUtil.dateToString((Date)matInMain.get("check_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("confirm_date") != null && !"".equals(matInMain.get("confirm_date"))){
			matInMain.put("confirm_date", DateUtil.dateToString((Date)matInMain.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("matInMain", matInMain);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		
		return "hrp/mat/storage/check/matStorageInPage";
	}
	
	/**
	 * @Description 查询数据  常备材料入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/check/queryMatStorageInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageInDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = matStorageInService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * 盘亏单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/check/matStorageOutPage", method = RequestMethod.GET)
	public String matStorageOutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		 Map<String, Object> matOutMain = matOutMainService.queryMatOutMainByCode(mapVo);
		 
		 if(matOutMain.get("out_date") != null && !"".equals(matOutMain.get("out_date").toString())){
			 matOutMain.put("out_date", DateUtil.dateToString((Date)matOutMain.get("out_date"), "yyyy-MM-dd"));
		 }
		 
		 mode.addAttribute("matOutMain", matOutMain);
		
		 String matOutDetail = matOutMainService.queryMatOutDetailByOutId(getPage(mapVo));
		
		 mode.addAttribute("matOutDetail", matOutDetail);

			mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
			mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
			mode.addAttribute("p04015", MyConfig.getSysPara("04015"));
			
		 return "hrp/mat/storage/check/matStorageOutPage";
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/check/matCheckMainImportPage", method = RequestMethod.GET)
	public String matCheckMainImportPage(Model mode) throws Exception {

		return "hrp/mat/storage/check/matCheckMainImport";

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
	 @RequestMapping(value="hrp/mat/storage/check/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate",".xls");
	    
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
	@RequestMapping(value="/hrp/mat/storage/check/readMatCheckMainFiles",method = RequestMethod.POST)  
    public String readMatCheckMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatCheckMain> list_err = new ArrayList<MatCheckMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatCheckMain matCheckMain = new MatCheckMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matCheckMain.setCheck_id(Long.valueOf(temp[3]));
		    		mapVo.put("check_id", temp[3]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
		    		mapVo.put("check_code", temp[4]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					matCheckMain.setStore_id(Long.valueOf(temp[5]));
		    		mapVo.put("store_id", temp[5]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					matCheckMain.setStore_no(Long.valueOf(temp[6]));
		    		mapVo.put("store_no", temp[6]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					matCheckMain.setDept_id(Long.valueOf(temp[7]));
		    		mapVo.put("dept_id", temp[7]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					matCheckMain.setDept_no(Long.valueOf(temp[8]));
		    		mapVo.put("dept_no", temp[8]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					matCheckMain.setCheck_date(DateUtil.stringToDate(temp[9]));
		    		mapVo.put("check_date", temp[9]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					matCheckMain.setEmp_id(Long.valueOf(temp[10]));
		    		mapVo.put("emp_id", temp[10]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					matCheckMain.setMaker(Long.valueOf(temp[11]));
		    		mapVo.put("maker", temp[11]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					matCheckMain.setChecker(Long.valueOf(temp[12]));
		    		mapVo.put("checker", temp[12]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					matCheckMain.setState(Integer.valueOf(temp[13]));
		    		mapVo.put("state", temp[13]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
		    		mapVo.put("brif", temp[14]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					
				MatCheckMain data_exc_extis = matCheckMainService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matCheckMain.setError_type(err_sb.toString());
					
					list_err.add(matCheckMain);
					
				} else {
			  
					String dataJson = matCheckMainService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatCheckMain data_exc = new MatCheckMain();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/storage/check/addBatchMatCheckMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatCheckMain> list_err = new ArrayList<MatCheckMain>();
		
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
			
			MatCheckMain matCheckMain = new MatCheckMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("check_id"))) {
						
					matCheckMain.setCheck_id(Long.valueOf((String)jsonObj.get("check_id")));
		    		mapVo.put("check_id", jsonObj.get("check_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_code"))) {
						
		    		mapVo.put("check_code", jsonObj.get("check_code"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
						
					matCheckMain.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_no"))) {
						
					matCheckMain.setStore_no(Long.valueOf((String)jsonObj.get("store_no")));
		    		mapVo.put("store_no", jsonObj.get("store_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					matCheckMain.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {
						
					matCheckMain.setDept_no(Long.valueOf((String)jsonObj.get("dept_no")));
		    		mapVo.put("dept_no", jsonObj.get("dept_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_date"))) {
						
					matCheckMain.setCheck_date(DateUtil.stringToDate((String)jsonObj.get("check_date")));
		    		mapVo.put("check_date", jsonObj.get("check_date"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("emp_id"))) {
						
					matCheckMain.setEmp_id(Long.valueOf((String)jsonObj.get("emp_id")));
		    		mapVo.put("emp_id", jsonObj.get("emp_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					matCheckMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					matCheckMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					matCheckMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("brif"))) {
						
		    		mapVo.put("brif", jsonObj.get("brif"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					
				MatCheckMain data_exc_extis = matCheckMainService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matCheckMain.setError_type(err_sb.toString());
					
					list_err.add(matCheckMain);
					
				} else {
			  
					String dataJson = matCheckMainService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatCheckMain data_exc = new MatCheckMain();
			
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
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/check/matCheckPrintSetPage", method = RequestMethod.GET)
	public String matCheckPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 盘点单模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/check/queryMatCheckByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCheckByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if (mapVo.get("p_num") .equals("1") ) {
			mapVo.put("p_num", 1);
		}else{
			mapVo.put("p_num", 0);
		}
		//System.out.println("=============="+mapVo.get("p_num"));
		String reJson=null;
		try {
			reJson=matCheckMainService.queryMatCheckByPrintTemlate(mapVo);
		} catch (Exception e) {
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * 引入仓库材料
	 * @param mapVo 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/check/queryMatStoreInvDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreInvDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detailData = matCheckMainService.queryMatStoreInvDetail(mapVo);
		
		return JSONObject.parseObject(detailData);
		
	}
	
	
	/**
	 * @Description 生成出入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/check/checkInOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkInOut(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String user_id = SessionManager.getUserId();
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_id", ids[3]);
			mapVo.put("in_id", ids[4]);
			mapVo.put("out_id", ids[5]);
			mapVo.put("check_code", ids[6]);
			mapVo.put("in_no", ids[7]);
			mapVo.put("out_no", ids[8]);
			mapVo.put("user_id", user_id);
			
			listVo.add(mapVo);
		}

		String matJson;
		
	    matJson = matCheckMainService.updateCheckInOut(listVo);
		
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 仓库页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/check/updateMatCheckDetailByInvBatchPage", method = RequestMethod.GET)
	public String updateMatCheckDetailByInvBatchPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mode.addAttribute("check_id",mapVo.get("check_id").toString());
		mode.addAttribute("inv_msg",mapVo.get("inv_msg").toString());

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/check/updateMatCheckDetailByInvBatch";

	}

	
	
	/**
	 * @Description 材料对应盘点批号明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/check/queryMatCheckDetailByInvBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCheckDetailByInvBatch(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String matCheckDetail = matCheckMainService.queryMatCheckDetailByInvBatch(mapVo);
		
		return JSONObject.parseObject(matCheckDetail);
	}
	
	/**
	 * 修改某种材料相应批号的盘点明细
	 * @return
	 */
	@RequestMapping(value="/hrp/mat/storage/check/updateCheckInvBatchDetail",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCheckInvBatchDetail(@RequestParam Map<String, Object> mapVo,Model model){
		if (mapVo.get("group_id")==null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id")==null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code")==null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String jsonMsg=matCheckMainService.updateCheckInvBatchDetail(mapVo);
		return JSONObject.parseObject(jsonMsg);
	}
}

