package com.chd.hrp.acc.controller.books.memorandumbook;

import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.books.memorandumbook.AccMatchIncomeService;

@Controller
public class AccMatchInomeQueryController extends BaseController{
	private static Logger logger = Logger.getLogger(AccMatchInomeQueryController.class);
	
	@Resource(name = "accMatchIncomeService")
	private final AccMatchIncomeService accMatchIncomeService = null;
	
	/**
	*配套资金初始账表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accmatchincome/queryAccMatchIncomeQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccMatchIncomeQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String accMatchIncomeJson = accMatchIncomeService.queryAccMatchIncome(getPage(mapVo));

		return JSONObject.parseObject(accMatchIncomeJson);
		
	}
	
	/**
	*配套资金初始账表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accmatchincome/accMatchIncomeQueryMainPage", method = RequestMethod.GET)
	public String accMatchIncomeMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		//返回当前月
		int month = new Date().getMonth() + 1;
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", month < 10 ? "0"+month:month);
		mode.addAttribute("attr_code",mapVo.get("attr_code"));

		return "hrp/acc/accmatchincome/accMatchIncomeMain";
	}
	
}
