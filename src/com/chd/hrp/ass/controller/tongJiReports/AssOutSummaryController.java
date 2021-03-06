package com.chd.hrp.ass.controller.tongJiReports;

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
import com.chd.hrp.ass.service.tongJiReports.AssOutSummaryService;

/**
 * 资产出库统计
 * 
 * @author fhqfm
 *
 */

@Controller
public class AssOutSummaryController extends BaseController {      

	private static Logger logger = Logger.getLogger(AssOutSummaryController.class);

	// 引入Service服务
	@Resource(name = "assOutSummaryService")
	private final AssOutSummaryService assOutSummaryService = null;

	/**
	 * @Description 资产出库统计页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/tongJiReports/assOutSummaryPage", method = RequestMethod.GET)
	public String assOutSummaryPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/tongJiReports/assOutSummaryMain";

	}

	/**
	 * @Description 查询数据 资产出库统计
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/tongJiReports/queryAssOutSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreciationAnalyse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String reJson = assOutSummaryService.queryAssOutSummary(getPage(mapVo));
		return JSONObject.parseObject(reJson);

	}
	
	/**
	 * 出库情况查询
	 */
	
	@RequestMapping(value = "/hrp/ass/tongJiReports/assOutMainQueryPage", method = RequestMethod.GET)
	public String assOutMainQueryPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/tongJiReports/assOutMainQuery";

	}
	
	/**
	 * @Description 查询数据  出库情况查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/tongJiReports/queryAssOutMainQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssOutMainQuery(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String reJson = assOutSummaryService.queryOutSituation(getPage(mapVo));
		return JSONObject.parseObject(reJson);

	}
}
