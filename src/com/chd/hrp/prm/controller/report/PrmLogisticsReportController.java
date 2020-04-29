package com.chd.hrp.prm.controller.report;

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
import com.chd.hrp.prm.service.report.PrmLogisticsReportService;

/**
 * 
 * @Description: 行政后勤质量奖
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */


@Controller
public class PrmLogisticsReportController extends BaseController {
	
	
	private static Logger logger = Logger.getLogger(PrmLogisticsReportController.class);
	
	@Resource(name = "prmLogisticsReportService")
	private final PrmLogisticsReportService prmLogisticsReportService = null;
	
	//主页面跳转
	@RequestMapping(value = "/hrp/prm/report/prmLogisticsReportMainPage", method = RequestMethod.GET)
	public String prmLogisticsReportMainPage(Model mode) throws Exception {

		return "hrp/prm/report/prmLogisticsReportMain";

	}
	
	
	/**
	 * @Description 查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/report/queryPrmLogisticsReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmLogisticsReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String reportJon = prmLogisticsReportService.queryPrmLogisticsReport(getPage(mapVo));

		return JSONObject.parseObject(reportJon);

	}
}
