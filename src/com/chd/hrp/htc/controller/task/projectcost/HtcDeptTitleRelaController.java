package com.chd.hrp.htc.controller.task.projectcost;

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
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptTitleRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptTitleRelaService;


@Controller
public class HtcDeptTitleRelaController extends BaseController{

	private static Logger logger = Logger.getLogger(HtcDeptWorkRelaController.class);
	
	@Resource(name = "htcDeptTitleRelaService")
	private final HtcDeptTitleRelaService htcDeptTitleRelaService = null;
	
	@RequestMapping(value = "/hrp/htc/task/projectcost/depttitlerela/htcDeptTitleRelaMainPage", method = RequestMethod.GET)
	public String htcDeptTitleRelaMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/depttitlerela/htcDeptTitleRelaMain";
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/projectcost/depttitlerela/htcDeptTitleRelaAddPage", method = RequestMethod.GET)
	public String htcDeptTitleRelaAddPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/depttitlerela/htcDeptTitleRelaAdd";
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/depttitlerela/queryHtcDeptTitleRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptTitleRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptTitleRelaJson = htcDeptTitleRelaService.queryHtcDeptTitleRela(getPage(mapVo));

		return JSONObject.parseObject(htcDeptTitleRelaJson);

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/depttitlerela/addHtcDeptTitleRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcDeptTitleRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptTitleRelaJson = null;
		try {
			htcDeptTitleRelaJson = htcDeptTitleRelaService.addHtcDeptTitleRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptTitleRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptTitleRelaJson);

	}
	
	// 删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/depttitlerela/deleteHtcDeptTitleRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcDeptWorkRela(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("plan_code", ids[4]);
			mapVo.put("proj_dept_no", ids[5]);
			mapVo.put("proj_dept_id", ids[6]);
			mapVo.put("title_code", ids[7]);
			listVo.add(mapVo);
		}
		String htcDeptTitleRelaJson = null;
		try {
			htcDeptTitleRelaJson = htcDeptTitleRelaService.deleteBatchHtcDeptTitleRela(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptTitleRelaJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcDeptTitleRelaJson);
	}
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/depttitlerela/htcDeptTitleRelaUpdatePage", method = RequestMethod.GET)
	public String htcDeptTitleRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		HtcDeptTitleRela htcDeptTitleRela = htcDeptTitleRelaService.queryHtcDeptTitleRelaByCode(mapVo);
		mode.addAttribute("group_id", htcDeptTitleRela.getGroup_id());
		mode.addAttribute("hos_id", htcDeptTitleRela.getHos_id());
		mode.addAttribute("copy_code", htcDeptTitleRela.getCopy_code());
		mode.addAttribute("acc_year", htcDeptTitleRela.getAcc_year());
		mode.addAttribute("plan_code", htcDeptTitleRela.getPlan_code());
		mode.addAttribute("plan_name", htcDeptTitleRela.getPlan_name());
		mode.addAttribute("proj_dept_no", htcDeptTitleRela.getProj_dept_no());
		mode.addAttribute("proj_dept_id", htcDeptTitleRela.getProj_dept_id());
		mode.addAttribute("proj_dept_code", htcDeptTitleRela.getProj_dept_code());
		mode.addAttribute("proj_dept_name", htcDeptTitleRela.getProj_dept_name());
		mode.addAttribute("title_code", htcDeptTitleRela.getTitle_code());
		mode.addAttribute("title_name", htcDeptTitleRela.getTitle_name());
		return "hrp/htc/task/projectcost/depttitlerela/htcDeptTitleRelaUpdate";
	}
	
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/depttitlerela/updateHtcDeptTitleRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcDeptTitleRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptTitleRelaJson = null;
		try {
			htcDeptTitleRelaJson = htcDeptTitleRelaService.updateHtcDeptTitleRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptTitleRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptTitleRelaJson);
	}
}
