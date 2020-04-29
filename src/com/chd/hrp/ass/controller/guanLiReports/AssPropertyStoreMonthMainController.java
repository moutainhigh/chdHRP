package com.chd.hrp.ass.controller.guanLiReports;

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
import com.chd.hrp.ass.service.guanLiReports.AssPropertyStoreMonthMainService;
/**
 * 
 * @author 资产月报表(财务折旧)
 *
 */
@Controller
public class AssPropertyStoreMonthMainController extends BaseController {
	

	
	private static Logger logger = Logger.getLogger(AssPropertyStoreMonthMainController.class);
	
	@Resource(name = "assPropertyStoreMonthMainService")
	private final AssPropertyStoreMonthMainService assPropertyStoreMonthMainService = null;
	 
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/assPropertyStoreMonthMainPage", method = RequestMethod.GET)
	public String assPlanDeptMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/guanLiReports/assPropertyStoreMonthMain";

	}


	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssPropertyStoreMonthMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPropertyStoreMonthMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		if(mapVo.get("year_month") != null){
			String year = mapVo.get("year_month").toString().substring(0, 4);
			String month = mapVo.get("year_month").toString().substring(4, 6);
			mapVo.put("acc_year",year);
			mapVo.put("acc_month",month);
		}
		
		String assPlanDept = assPropertyStoreMonthMainService.queryAssPropertyStoreMonthMain(getPage(mapVo));

		return JSONObject.parseObject(assPlanDept);
		
	}
 
	
}