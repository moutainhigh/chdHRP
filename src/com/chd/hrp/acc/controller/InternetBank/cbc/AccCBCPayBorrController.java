/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.acc.controller.InternetBank.cbc;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.InternetBank.cbc.AccCBCPayBorrService;

/**
 * 
 * @Description: state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款
 * @Table: MAT_PAY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@RequestMapping(value="/hrp/acc/internetbank/cbc/borrpay/")
@Controller			    
public class AccCBCPayBorrController extends BaseController {

	private static Logger logger = Logger.getLogger(AccCBCPayBorrController.class);

	// 引入Service服务
	@Resource(name = "accCBCPayBorrService")
	private final AccCBCPayBorrService accCBCPayBorrService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "accCBCBorrMainPage", method = RequestMethod.GET)
	public String accBankNetBorrMainPage(Model mode) throws Exception {

		return "hrp/acc/internetbank/cbc/borrpay/accCBCPayBorrMain";

	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "accCBCBorrOrderPage", method = RequestMethod.GET)
	public String accBankNetBorrOrderPage(Model mode) throws Exception {

		return "hrp/acc/internetbank/cbc/borrpay/accCBCPayBorrOrder";

	}
	
	/**
	 * @Description 明细界面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAccBankNetBorrRdPage", method = RequestMethod.GET)
	public String queryAccBankNetBorrRdPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("apply_code", mapVo.get("apply_code"));
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		
		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		return "hrp/acc/internetbank/cbc/borrpay/accBankNetBorrRdOrder";
		
	}

	/**
	 * @Description 查询数据 state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAccBorrApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBorrApply(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matPayMain = accCBCPayBorrService.queryAccBorrApply(getPage(mapVo));

		return JSONObject.parseObject(matPayMain);

	}

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAccBankNetBorr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankNetBorr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matPayMain = accCBCPayBorrService.queryAccBankNetBorr(getPage(mapVo));

		return JSONObject.parseObject(matPayMain);

	}
	
	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAccBankNetBorrRd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankNetBorrRd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matPayMain = accCBCPayBorrService.queryAccBankNetBorrRd(getPage(mapVo));

		return JSONObject.parseObject(matPayMain);

	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "collectAccBankNetBorrPage", method = RequestMethod.GET)
	public String collectAccBankNetBorrPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("paramVo", mapVo.get("paramVo"));
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		double totalAmt = accCBCPayBorrService.sumTotalAmtByDay(mapVo);
		
		mode.addAttribute("totalAmt", totalAmt);

		return "hrp/acc/internetbank/cbc/borrpay/accBankNetBorrAdd";

	}

	/**
	 * 网上支付
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "collectAccBankNetBorr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccBankNetBorr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matProtocolMainJson = "";
		try {
			matProtocolMainJson =accCBCPayBorrService.addAccBankNetBorr(mapVo);
		} catch (Exception e) {

			matProtocolMainJson = e.getMessage();
		}

		return JSONObject.parseObject(matProtocolMainJson);
	}
	
	/**
	 * 添加付款单明细信息
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateAccBankNetBorr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBankNetBorr(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		// 添加 list
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("acc_year", ids[3]);
			
			mapVo.put("fseqno", ids[4]);

			mapVo.put("vouch_id", ids[5]);
			
			mapVo.put("vouch_state", ids[6]);

			mapVo.put("user_id", SessionManager.getUserId());
			
			mapVo.put("log_table", "ACC_BUSI_LOG_0203");

			listVo.add(mapVo);
		}
		
		String str = null;
		
		try {
			
			str = accCBCPayBorrService.updateAccBankNetBorr(listVo);
			
		} catch (Exception e) {
			
			str = e.getMessage();
			
		}

		return JSONObject.parseObject(str);
	}

}