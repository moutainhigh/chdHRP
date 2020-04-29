/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.contract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.contract.AssContractMain;
import com.chd.hrp.ass.service.contract.AssContractDetailService;
import com.chd.hrp.ass.service.contract.AssContractMainService;

/**
 * 
 * @Description: 050501 资产合同主表
 * @Table: ASS_CONTRACT_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssPerformContractMainController extends BaseController {

	private static Logger logger = Logger.getLogger(AssPerformContractMainController.class);

	// 引入Service服务
	@Resource(name = "assContractMainService")
	private final AssContractMainService assContractMainService = null;

	@Resource(name = "assContractDetailService")
	private final AssContractDetailService assContractDetailService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractmain/assPerformContractMainMainPage", method = RequestMethod.GET)
	public String assPerformContractMainMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assperformcontractmain/assPerformContractMainMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractmain/assPerformContractMainAddPage", method = RequestMethod.GET)
	public String assPerformContractMainAddPage(Model mode) throws Exception {

		return "hrp/ass/assperformcontractmain/assPerformContractMainAdd";

	}

	/**
	 * @Description 添加数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractmain/addAssPerformContractMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPerformContractMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractMainJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String sign_date = String.valueOf(mapVo.get("sign_date"));
		if (StringUtils.isNotEmpty(sign_date)) {
			mapVo.put("sign_date", DateUtil.stringToDate(sign_date, "yyyy-MM-dd"));
		}
		String start_date = String.valueOf(mapVo.get("start_date"));
		if (StringUtils.isNotEmpty(start_date)) {
			mapVo.put("start_date", DateUtil.stringToDate(start_date, "yyyy-MM-dd"));
		}
		String end_date = String.valueOf(mapVo.get("end_date"));
		if (StringUtils.isNotEmpty(end_date)) {
			mapVo.put("end_date", DateUtil.stringToDate(end_date, "yyyy-MM-dd"));
		}

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		mapVo.put("create_emp", SessionManager.getUserId());
		mapVo.put("create_date", DateUtil.stringToDate(dateFormat.format(date), "yyyy/MM/dd"));
		mapVo.put("check_emp", null);
		mapVo.put("check_date", null);
		mapVo.put("contract_money", "0");
		mapVo.put("state", "0");

		String ven_id_no = mapVo.get("ven_id").toString();
		mapVo.put("ven_id", ven_id_no.split("@")[0]);
		mapVo.put("ven_no", ven_id_no.split("@")[1]);

		try {

			assContractMainJson = assContractMainService.addOrUpdateAssContractMain(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractMainJson);

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractmain/updateToExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExamine(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("contract_id", ids[3]);

			mapVo.put("check_emp", SessionManager.getUserId());

			mapVo.put("check_date", new Date());

			AssContractMain assContractMain = assContractMainService.queryAssContractMainByCode(mapVo);

			if (assContractMain.getState() != 1) {

				flag = false;

				break;

			}
			listVo.add(mapVo);

		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"履行失败 状态必须是审核的才能履行! \"}");

		}
		try {

			assInMainJson = assContractMainService.updateToPerform(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractmain/updateNotToExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamine(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("contract_id", ids[3]);

			mapVo.put("check_emp", SessionManager.getUserId());

			mapVo.put("check_date", new Date());

			AssContractMain assContractMain = assContractMainService.queryAssContractMainByCode(mapVo);

			if (assContractMain.getState() != 2) {

				flag = false;

				break;

			}
			listVo.add(mapVo);

		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"取消履行失败 状态必须是履行的才能取消履行! \"}");
		}
		try {

			assInMainJson = assContractMainService.updateNotToPerform(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractmain/assPerformContractMainUpdatePage", method = RequestMethod.GET)
	public String assPerformContractMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		AssContractMain assContractMain = new AssContractMain();

		assContractMain = assContractMainService.queryAssContractMainByCode(mapVo);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assContractMain.getGroup_id());
		mode.addAttribute("hos_id", assContractMain.getHos_id());
		mode.addAttribute("copy_code", assContractMain.getCopy_code());
		mode.addAttribute("contract_id", assContractMain.getContract_id());
		mode.addAttribute("contract_no", assContractMain.getContract_no());
		mode.addAttribute("contract_type", assContractMain.getContract_type());
		mode.addAttribute("contract_ori_no", assContractMain.getContract_ori_no());
		mode.addAttribute("contract_name", assContractMain.getContract_name());
		mode.addAttribute("ass_year", assContractMain.getAss_year());
		mode.addAttribute("ass_month", assContractMain.getAss_month());
		mode.addAttribute("ven_id", assContractMain.getVen_id());
		mode.addAttribute("ven_no", assContractMain.getVen_no());
		if (assContractMain.getSign_date() == null) {
			mode.addAttribute("sign_date", assContractMain.getSign_date());
		} else {
			mode.addAttribute("sign_date", dateFormat.format(assContractMain.getSign_date()));
		}
		mode.addAttribute("buy_type", assContractMain.getBuy_type());
		if (assContractMain.getStart_date() == null) {
			mode.addAttribute("start_date", assContractMain.getStart_date());
		} else {
			mode.addAttribute("start_date", dateFormat.format(assContractMain.getStart_date()));
		}
		if (assContractMain.getEnd_date() == null) {
			mode.addAttribute("end_date", assContractMain.getEnd_date());
		} else {
			mode.addAttribute("end_date", dateFormat.format(assContractMain.getEnd_date()));
		}
		mode.addAttribute("contract_detail", assContractMain.getContract_detail());
		mode.addAttribute("service_detail", assContractMain.getService_detail());
		mode.addAttribute("emp_main", assContractMain.getEmp_main());
		mode.addAttribute("provider", assContractMain.getProvider());
		mode.addAttribute("is_bid", assContractMain.getIs_bid());
		mode.addAttribute("contract_money", assContractMain.getContract_money());
		mode.addAttribute("create_emp", assContractMain.getCreate_emp());
		if (assContractMain.getCreate_date() == null) {
			mode.addAttribute("create_date", assContractMain.getCreate_date());
		} else {
			mode.addAttribute("create_date", dateFormat.format(assContractMain.getCreate_date()));
		}
		mode.addAttribute("check_emp", assContractMain.getCheck_emp());
		if (assContractMain.getCheck_date() == null) {
			mode.addAttribute("check_date", assContractMain.getCheck_date());
		} else {
			mode.addAttribute("check_date", dateFormat.format(assContractMain.getCheck_date()));
		}
		mode.addAttribute("state", assContractMain.getState());
		mode.addAttribute("is_group", assContractMain.getIs_group());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assperformcontractmain/assPerformContractMainUpdate";
	}

	/**
	 * @Description 付款信息跳转页面 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractmain/assPerformContractMainManagePage", method = RequestMethod.GET)
	public String assPerformContractMainManagePage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		AssContractMain assContractMain = new AssContractMain();

		assContractMain = assContractMainService.queryAssContractMainByCode(mapVo);

		mode.addAttribute("group_id", assContractMain.getGroup_id());
		mode.addAttribute("hos_id", assContractMain.getHos_id());
		mode.addAttribute("copy_code", assContractMain.getCopy_code());
		mode.addAttribute("contract_id", assContractMain.getContract_id());
		mode.addAttribute("contract_no", assContractMain.getContract_no());
		mode.addAttribute("contract_name", assContractMain.getContract_name());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assperformcontractmain/assPerformContractMainManage";
	}

	/**
	 * @Description 更新数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractmain/updateAssPerformContractMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssPerformContractMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractMainJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String ven_id_no = mapVo.get("ven_id").toString();
		mapVo.put("ven_id", ven_id_no.split("@")[0]);
		mapVo.put("ven_no", ven_id_no.split("@")[1]);
		try {

			assContractMainJson = assContractMainService.updateAssContractMain(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractMainJson);
	}

	/**
	 * @Description 更新数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractmain/addOrUpdateAssPerformContractMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssPerformContractMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractMainJson = "";

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			if (detailVo.get("group_id") == null) {
				detailVo.put("group_id", SessionManager.getGroupId());
			}

			if (detailVo.get("hos_id") == null) {
				detailVo.put("hos_id", SessionManager.getHosId());
			}

			if (detailVo.get("copy_code") == null) {
				detailVo.put("copy_code", SessionManager.getCopyCode());
			}
			try {
				assContractMainJson = assContractMainService.addOrUpdateAssContractMain(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assContractMainJson);
	}

	/**
	 * @Description 删除数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractmain/deleteAssPerformContractMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPerformContractMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assContractMainJson;
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("contract_id", ids[3]);

			if (Integer.parseInt(ids[4]) > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 已审核的数据不能删除! \"}");
		}
		try {
			assContractDetailService.deleteBatchAssContractDetail(listVo);
			assContractMainJson = assContractMainService.deleteBatchAssContractMain(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assContractMainJson);

	}

	/**
	 * @Description 查询数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractmain/queryAssPerformContractMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPerformContractMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		String assContractMain = assContractMainService.queryAssContractMain(getPage(mapVo));

		return JSONObject.parseObject(assContractMain);

	}

}