/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.controller.budgsysset;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.BaseController;
/**
 * @Description:  预算门户
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class BudgPortalController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgPortalController.class);
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/portal/budgPortalPage", method = RequestMethod.GET)
	public String matPortalPage(Model mode) throws Exception {
		return "hrp/budg/portal/budgPortal";
	}
}
