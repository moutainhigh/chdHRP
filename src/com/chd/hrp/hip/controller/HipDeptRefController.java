/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.hip.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.Plupload;
import com.chd.hrp.hip.entity.HipDeptRef;
import com.chd.hrp.hip.service.HipDeptRefService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HipDeptRefController extends BaseController {
	private static Logger logger = Logger.getLogger(HipDeptRefController.class);

	@Resource(name = "hipDeptRefService")
	private final HipDeptRefService hipDeptRefService = null;

	@RequestMapping(value = "/hrp/hip/hipdeptref/hipDeptRefMainPage", method = RequestMethod.GET)
	public String HipDeptRefMainPage(Model mode) throws Exception {
		
		if(SessionManager.getCopyCode() == null || "".equals(SessionManager.getCopyCode())){
			
			mode.addAttribute("copy_code", "copy_code");

		}

		return "hrp/hip/hipdeptref/hipDeptRefMain";
	}

	@RequestMapping(value = "/hrp/hip/hipdeptref/queryHipDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHipDeptRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String str = hipDeptRefService.queryHipDeptRef(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipdeptref/addHipDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHipDeptRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String str = hipDeptRefService.addHipDeptRef(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipdeptref/updateHipDeptRefPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHipDeptRefPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", "0");}
		

		HipDeptRef hdr = hipDeptRefService.queryHipDeptRefByCode(mapVo);

		StringBuffer sb = new StringBuffer();

		sb.append("{");

		sb.append("\"ds_code\":\"" + hdr.getDs_code() + "\",");

		sb.append("\"hip_dept_code\":\"" + hdr.getHip_dept_code() + "\",");

		sb.append("\"hrp_dept_code\":\"" + hdr.getHrp_dept_code() + "\",");
		
		sb.append("\"hip_dept_name\":\"" + hdr.getHip_dept_name() + "\",");

		sb.append("\"hrp_dept_name\":\"" + hdr.getHrp_dept_name() + "\",");

		sb.append("\"ds_name\":\"" + hdr.getDs_name() + "\",");

		sb.append("\"doc_type\":\"" + hdr.getDoc_type() + "\",");

		sb.append("}");

		return JSONObject.parseObject(sb.toString());

	}

	@RequestMapping(value = "/hrp/hip/hipdeptref/deleteHipDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHipDeptRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String apt = hipDeptRefService.delHipDeptRef(mapVo);

		return JSONObject.parseObject(apt);

	}

	@RequestMapping(value = "/hrp/hip/hipdeptref/delCheckHipDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delCheckHipDeptRef(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("ds_code", ids[2]);

			mapVo.put("hip_dept_code", ids[3]);

			mapVo.put("hrp_dept_code", ids[4]);

			mapVo.put("doc_type", ids[5]);

			listVo.add(mapVo);

		}

		String apt = hipDeptRefService.deleteBatchHipDeptRef(listVo);

		return JSONObject.parseObject(apt);

	}

	/**
	 * @Description 下载导入模版 0308 职工表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hip/hipdeptref/downTemplateHipDeptRef", method = RequestMethod.GET)
	public String downTemplateHipDeptRef(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hip\\映射关系", "平台科室映射关系.xls");

		return null;
	}

	/**
	 * @Description 导入跳转页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "hrp/hip/hipdeptref/hipDeptRefImportPage", method = RequestMethod.GET)
	public String hipDeptRefImportPage(Model mode) throws Exception {

		return "hrp/hip/hipdeptref/hipDeptRefImport";

	}
	
	
	/**
	 * @Description 导入跳转页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "hrp/hip/hipdeptref/imphipDeptRefReadFiles", method = RequestMethod.POST)
	@ResponseBody
	public String imphipDeptRefReadFiles(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		try {

			String reJson = hipDeptRefService.imphipDeptRefReadFiles(mapVo);

			return reJson;

		}
		catch (Exception e) {

			return "{\"error\":\"" + e.getMessage() + "\"}";

		}

	}

}
