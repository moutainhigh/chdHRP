package com.chd.hrp.pac.controller.fkht.warning;

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
import com.chd.hrp.pac.service.fkht.pactinfo.PactMainFKHTService;

/**
 * 合同执行
 * 
 * @author haotong
 *
 */
@Controller
@RequestMapping(value = "/hrp/pac/fkht/warning")
public class PacFKHTNearRepairWarnController extends BaseController {

	@Resource(name = "pactMainFKHTService")
	private PactMainFKHTService service;

	@RequestMapping(value = "/pactFKHTNearRepairWarnMainPage", method = RequestMethod.GET)
	public String toPactNoRuleMain() {
		return "hrp/pac/fkht/warning/pactFKHTNearRepairWarnMain";
	}

	/**
	 * 查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactMainFKHTForNearRepairWarning", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainFKHTForNearRepairWarning(@RequestParam Map<String, Object> mapVo,
			Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "3");
			mapVo.put("state_code", "12");
			if (mapVo.get("warning_day") == null || mapVo.get("warning_day") == "") {
				mapVo.put("warning_day", 30);
			}
			String query = service.queryPactMainFKHTForNearRepairWarning(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
