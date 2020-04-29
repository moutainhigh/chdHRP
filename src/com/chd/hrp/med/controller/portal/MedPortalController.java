/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.portal;

import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSONArray;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.med.service.warning.MedBatchWarningService;
import com.chd.hrp.med.service.warning.MedInvCertWarningService;
import com.chd.hrp.med.service.warning.MedSafeStoreWarningService;
import com.chd.hrp.med.service.warning.MedSupCertWarningService;
import com.chd.hrp.sys.service.portal.PortalService;
import com.chd.hrp.sys.service.portal.SysPortalTitleSetService;
import com.chd.hrp.sys.service.portal.SysPortalTitleUserService;

/**
 * 
 * @Description:  门户
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedPortalController extends BaseController {

	private static Logger logger = Logger.getLogger(MedPortalController.class);
	

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/portal/medPortalPage", method = RequestMethod.GET)
	public String medPortalPage(Model mode) throws Exception {
		return "hrp/med/portal/medPortal";
	}

	
}
