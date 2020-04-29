package com.chd.hrp.acc.controller.termend.monthend;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.termend.AccTermendTemplateService;
import com.chd.hrp.acc.service.termend.monthend.AccComTransService;

/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccComTransController extends BaseController {
	private static Logger logger = Logger.getLogger(AccComTransController.class);

	@Resource(name = "accComTransService")
	private final AccComTransService accComTransService = null;
	@Resource(name = "accTermendTemplateService")
	private final AccTermendTemplateService accTermendTemplateService = null;
	
	/**
	 * 通用结转<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/comtrans/accComTransPage", method = RequestMethod.GET)
	public String accComTransMainPage(Model mode) throws Exception {
		mode.addAttribute("acc_year_month", MyConfig.getCurAccYearMonth());
		mode.addAttribute("copy_nature", SessionManager.getCopyNature());
		return "hrp/acc/termend/monthend/comtrans/accComTransMain";
	}
	
	/**
	 * 期末处理<BR>
	 * 模板查询
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/comtrans/queryAccComTrans", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccComTrans(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		if (mapVo.get("template_type_code") == null) {
			mapVo.put("template_type_code", "Z007");
		}
		String accTemplateData = accTermendTemplateService.queryAccTermendTemplate(getPage(mapVo));

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 期末处理<BR>
	 * 模板保存
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/comtrans/saveAccComTrans", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccComTrans(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		if (mapVo.get("template_type_code") == null) {
			mapVo.put("template_type_code", "Z007");
		}
		String msg = "";
		if (mapVo.get("template_id") == null || "".equals(mapVo.get("template_id"))) {
			msg = accTermendTemplateService.addAccTermendTemplate(getPage(mapVo));
		}else{
			msg = accTermendTemplateService.updateAccTermendTemplate(getPage(mapVo));
		}
		

		return JSONObject.parseObject(msg);
	}
	
	/**
	 * 期末处理<BR>
	 * 模板删除
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/comtrans/deleteAccComTrans", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccComTrans(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("template_id", id);// 实际实体类变量
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			listVo.add(mapVo);
		}
		String msg = accTermendTemplateService.deleteBatchAccTermendTemplate(listVo);

		return JSONObject.parseObject(msg);
	}

	/**
	 * 通用结转<BR>
	 * 凭证生成
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/comtrans/addAccComTransVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccComTransVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String msgStr = accComTransService.addAccComTransVouch(getPage(mapVo));

		return JSONObject.parseObject(msgStr);
	}
}
