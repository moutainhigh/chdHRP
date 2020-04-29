﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.order.info;
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
import com.chd.hrp.mat.service.order.info.MatOrderInfoService;
/**
 * 
 * @Description:
 * MAT_ORDER_MAIN
 * @Table:
 * MAT_ORDER_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatOrderInfoController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatOrderInfoController.class);
	
	//引入Service服务
	@Resource(name = "matOrderInfoService")
	private final MatOrderInfoService matOrderInfoService = null;
   
	
	/**
	 * @Description 
	 * 订单信息查询--主页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/order/info/matOrderInfoMainPage", method = RequestMethod.GET)
	public String matOrderInfoMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		
		return "hrp/mat/order/info/matOrderInfoMain";

	}
	
	/**
	 * 订单信息查询--主查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/info/queryMatOrderInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOrderInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matOrder = matOrderInfoService.queryMatOrderInfo(getPage(mapVo));
		
		return JSONObject.parseObject(matOrder);
	}
	
}

