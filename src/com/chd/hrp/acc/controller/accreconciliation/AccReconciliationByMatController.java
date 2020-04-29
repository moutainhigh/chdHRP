/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.accreconciliation;
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
import com.chd.hrp.acc.serviceImpl.accreconciliation.AccReconciliationByMatServiceImpl;

/**
* @Title. @Description.
* 物资对账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccReconciliationByMatController extends BaseController{
	private static Logger logger = Logger.getLogger(AccReconciliationByMatController.class);
	
	
	@Resource(name = "accReconciliationByMatService")
	private final AccReconciliationByMatServiceImpl accReconciliationByMatService = null;
   
    
	/**
	*物资对账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accreconciliation/accReconciliationByMatMainPage", method = RequestMethod.GET)
	public String accReconciliationByMatMainPage(Model mode) throws Exception {

		return "hrp/acc/accreconciliation/accReconciliationByMatMain";

	}
	
	/**
	*物资对账<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accreconciliation/queryAccReconciliationByMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccReconciliationByMat(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
				
		}
				
		if (mapVo.get("hos_id") == null) {
					
			mapVo.put("hos_id", SessionManager.getHosId());
				
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
				
		}
		
		String accSubjType = accReconciliationByMatService.queryAccReconciliationByMat(getPage(mapVo));

		return JSONObject.parseObject(accSubjType);
		
	}
	
}

