package com.chd.hrp.mat.controller.info.basic;

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
import com.chd.hrp.mat.service.info.basic.MatInvCertQueryService;

/**
 * @Description:
 * 证件材料查询
 * @Table:
 * MAT_INV_CERT_RELA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatInvCertQueryController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatInvCertQueryController.class);
	
	@Resource(name = "matInvCertQueryService")
	private final MatInvCertQueryService matInvCertQueryService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertquery/matInvCertQueryMainPage", method = RequestMethod.GET)
	public String matInvCertQueryMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcertquery/matInvCertQueryMain";

	}
	
	/**
	 * @Description 
	 * 主页面-查询
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertquery/queryMatInvCertQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatInvCertQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
					
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matInvCertQueryJson = matInvCertQueryService.queryMatInvCertQuery(getPage(mapVo));
		
		return JSONObject.parseObject(matInvCertQueryJson);
	}
}
