package com.chd.hrp.pac.controller.skxy.pactinfo;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.skxy.pactinfo.PactMainSKXYService;

@Controller
@RequestMapping(value = "/hrp/pac/skxy/pactinfo/deadline")
public class PactMainSKXYDeadLineController extends BaseController {

	@Resource(name = "pactMainSKXYService")
	private PactMainSKXYService pactMainSKXYService;

	@RequestMapping(value = "/pactDeadLineSKXYMainPage", method = RequestMethod.GET)
	public String toPactMainSKXYDeadLinePage() {
		return "hrp/pac/skxy/pactinfo/deadline/pactSKXYDeadlineMain";
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactSKXYDeadLine", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "3");
			mapVo.put("state_code", "12");

			String query = pactMainSKXYService.queryPactSKXYForDeadline(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
