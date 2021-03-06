package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiHospTargetData;
import com.chd.hrp.hpm.service.AphiHospTargetDataService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiHospTargetDataController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiHospTargetDataController.class);

	@Resource(name = "aphiHospTargetDataService")
	private final AphiHospTargetDataService aphiHospTargetDataService = null;

//	@Resource(name = "aphiTargetService")
//	private final AphiTargetService aphiTargetService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/hpmHospTargetDataMainPage", method = RequestMethod.GET)
	public String hpmHospTargetDataMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmhosptargetdata/hpmHospTargetDataMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/hpmHospTargetDataAddPage", method = RequestMethod.GET)
	public String hospTargetDataAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmhosptargetdata/hpmHospTargetDataAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/addHpmHospTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmHospTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		if(mapVo.get("user_code") == null){
			
			mapVo.put("user_code", SessionManager.getUserCode());
		}

		mapVo.put("is_audit", 0);
		
		mapVo.put("audit_time", "");

		String hospTargetDataJson = aphiHospTargetDataService.addHospTargetData(mapVo);

		return JSONObject.parseObject(hospTargetDataJson);

	}

	/*
	 * // 生成
	 * 
	 * @RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/initHospTargetData", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public Map<String, Object> initHospTargetData(@RequestParam
	 * Map<String, Object> mapVo, Model mode) throws Exception {
	 * 
	 * mapVo.put("comp_code", COMP_CODE); mapVo.put("copy_code", COPY_CODE);
	 * String hospTargetDataJson = ""; List<HospTargetData> list =
	 * aphiHospTargetDataService.getTargetCode(mapVo); for (HospTargetData temp
	 * : list) { mapVo.put("comp_code", COMP_CODE); mapVo.put("copy_code",
	 * COPY_CODE); mapVo.put("is_audit", 0); mapVo.put("target_value", 0); if
	 * (mapVo.containsKey("acct_yearm")) { if (mapVo.get("acct_yearm") != null
	 * && !"".equals(mapVo.get("acct_yearm"))) { mapVo.put("acct_year",
	 * mapVo.get("acct_yearm").toString().substring(0, 4));
	 * mapVo.put("acct_month", mapVo.get("acct_yearm").toString().substring(4,
	 * mapVo.get("acct_yearm").toString().length())); } }
	 * mapVo.put("target_code", temp.getTarget_code()); hospTargetDataJson =
	 * aphiHospTargetDataService.initHospTargetData(mapVo); } return
	 * JSONObject.parseObject(hospTargetDataJson);
	 * 
	 * }
	 */

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/queryHpmHospTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmHospTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}

		String hospTargetData = aphiHospTargetDataService.queryHospTargetData(getPage(mapVo));

		return JSONObject.parseObject(hospTargetData);

	}

	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/queryHospTargetDataByDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHospTargetDataByDay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}

		String hospTargetData = aphiHospTargetDataService.queryHospTargetDataByDay(getPage(mapVo));

		return JSONObject.parseObject(hospTargetData);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/deleteHpmHospTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmHospTargetData(@RequestParam String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String hospTargetDataJson = aphiHospTargetDataService.deleteHospTargetData(mapVo, checkIds);

		return JSONObject.parseObject(hospTargetDataJson);

	}

	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/deleteHospTargetDataByDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHospTargetDataByDay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}

		String hospTargetDataJson = aphiHospTargetDataService.deleteBatchHospTargetData(mapVo);

		return JSONObject.parseObject(hospTargetDataJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/hpmHospTargetDataUpdatePage", method = RequestMethod.GET)
	public String hospTargetDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		AphiHospTargetData hospTargetData = new AphiHospTargetData();

		hospTargetData = aphiHospTargetDataService.queryHospTargetDataByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", hospTargetData.getCopy_code());

		mode.addAttribute("acct_year", hospTargetData.getAcct_year());

		mode.addAttribute("acct_month", hospTargetData.getAcct_month());

		mode.addAttribute("target_code", hospTargetData.getTarget_code());

		mode.addAttribute("target_name", hospTargetData.getTarget_name());

		mode.addAttribute("target_value", hospTargetData.getTarget_value());

		mode.addAttribute("is_audit", hospTargetData.getIs_audit());

		mode.addAttribute("user_code", hospTargetData.getUser_code());

		mode.addAttribute("audit_time", hospTargetData.getAudit_time());

		return "hrp/hpm/hpmhosptargetdata/hpmHospTargetDataUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/updateHpmHospTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHospTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String hospTargetDataJson = aphiHospTargetDataService.updateHospTargetData(mapVo);

		return JSONObject.parseObject(hospTargetDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/shenhe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> shenhe(@RequestParam String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		String USER_CODE = SessionManager.getUserCode();

		String hospTargetDataJson = "";

		for (String ids : checkIds.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String target_code = ids.split(";")[0];

			String acct_yearm = ids.split(";")[1];

			String is_audit = ids.split(";")[2];

			mapVo.put("target_code", target_code);

			mapVo.put("acct_year", acct_yearm.substring(0, 4));

			mapVo.put("acct_month", acct_yearm.substring(4));

			if (mapVo.get("group_id") == null) {

				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {

				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("copy_code", COPY_CODE);

			mapVo.put("is_audit", is_audit);

			if ("1".equals(is_audit)) {

				mapVo.put("user_code", USER_CODE);

				mapVo.put("audit_time", DateUtil.getCurrenDate("yyyy/MM/dd"));

			} else {

				mapVo.put("user_code", "");

				mapVo.put("audit_time", "");
			}

			hospTargetDataJson = aphiHospTargetDataService.shenhe(mapVo);

		}

		return JSONObject.parseObject(hospTargetDataJson);

	}

	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/hpmHospTargetDataChoosePage", method = RequestMethod.GET)
	public String hospTargetDataChoosePage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		String[] paraArray = paras.split("@");

		mode.addAttribute("acct_yearm", paraArray[0]);

		if (!"null".equals(paraArray[1])) {

			mode.addAttribute("target_code", paraArray[1]);

		}

		return "hrp/hpm/hpmhosptargetdata/hpmHospTargetDataChoose";

	}

	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/initHpmHospTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHpmHospTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String targetValueJson = aphiHospTargetDataService.initHospTargetData(mapVo);

		return JSONObject.parseObject(targetValueJson);
	}

	/**
	 * 导入
	 */

	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/hpmHospTargetDataImportPage", method = RequestMethod.GET)
	public String hospTargetDataImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmhosptargetdata/hpmHospTargetDataImport";

	}

	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/readHospTargetDataFiles", method = RequestMethod.POST)
	public String readHospTargetDataFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		String COPY_CODE = SessionManager.getCopyCode();

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		List<AphiHospTargetData> list2 = new ArrayList<AphiHospTargetData>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		// List<Item> errorList = new ArrayList<Item>();

		try {

			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AphiHospTargetData hospTargetData = new AphiHospTargetData();

				String temp[] = list.get(i);// 行

				if (temp[2].length() == 1) {

					temp[2] = "0" + temp[2];

				}

				if (StringUtils.isNotEmpty(temp[0])) {

					hospTargetData.setAcct_year(temp[0]);

					mapVo.put("acct_year", temp[0]);

				} else {

					err_sb.append("年份为空  ");
				}

				if (StringUtils.isNotEmpty(temp[1])) {

					hospTargetData.setAcct_month(temp[1]);

					mapVo.put("acct_month", temp[1]);

				} else {

					err_sb.append("月份为空  ");

				}

				if (StringUtils.isNotEmpty(temp[2])) {

					hospTargetData.setTarget_code(temp[2]);

					mapVo.put("target_code", temp[2]);

				} else {

					err_sb.append("指标编码为空  ");

				}

				if (StringUtils.isNotEmpty(temp[3])) {

					hospTargetData.setTarget_name(temp[3]);

					mapVo.put("target_name", temp[3]);

				} else {

					err_sb.append("指标名称为空  ");

				}

				if (StringUtils.isNotEmpty(temp[4])) {

					// BigDecimal b = new BigDecimal(temp[4]);

					hospTargetData.setTarget_value(Double.parseDouble(temp[4]));

					mapVo.put("target_value", temp[4]);

				} else {

					err_sb.append("指标值为空  ");

				}

				AphiHospTargetData htd = aphiHospTargetDataService.queryHospTargetDataByCode(mapVo);

				if (htd != null) {

					err_sb.append("数据编码已经存在！ ");

				}

//				AphiTarget target = aphiTargetService.queryTargetByCode(mapVo);
//
//				if (target == null) {
//
//					err_sb.append("指标名称不存在");
//
//				}

				if (err_sb.toString().length() > 0) {

					hospTargetData.setError_type(err_sb.toString());

					list2.add(hospTargetData);

				} else {

					aphiHospTargetDataService.addHospTargetData(mapVo);

				}

			}

		} catch (DataAccessException e) {
			AphiHospTargetData cd = new AphiHospTargetData();

			cd.setError_type("导入系统出错");

			list2.add(cd);

			response.getWriter().print(JsonListBeanUtil.listToJson(list2, list2.size()));

			return null;
		}

		mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(list2, list2.size()));
		return "/hrp/hpm/hpmhosptargetdata/hpmHospTargetDataImportMessage";
	}

	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/addBatchHospTargetDataDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchHospTargetDataDict(@RequestParam(value = "ParamVo") String paramVo, Model mode, HttpServletResponse response)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		List<AphiHospTargetData> list_err = new ArrayList<AphiHospTargetData>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		String s = null;

		Iterator it = json.iterator();
		try {
			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AphiHospTargetData hospTargetData = new AphiHospTargetData();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				// Set<String> key = jsonObj.keySet();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}

				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}

				mapVo.put("copy_code", SessionManager.getCopyCode());

				mapVo.put("acct_year", jsonObj.get("acct_year"));

				mapVo.put("acct_month", jsonObj.get("acct_month"));

				mapVo.put("target_code", jsonObj.get("target_code"));

				mapVo.put("target_name", jsonObj.get("target_name"));

				mapVo.put("target_value", jsonObj.get("target_value"));

				AphiHospTargetData htd = aphiHospTargetDataService.queryHospTargetDataByCode(mapVo);

				if (htd != null) {

					err_sb.append("数据编码已经存在！ ");
				}

//				AphiTarget target = aphiTargetService.queryTargetByCode(mapVo);
//
//				if (target == null) {
//
//					err_sb.append("指标名称不存在");
//
//				}

				if (err_sb.toString().length() > 0) {

					hospTargetData.setAcct_year((String) jsonObj.get("acct_year"));

					hospTargetData.setAcct_month((String) jsonObj.get("acct_month"));

					hospTargetData.setTarget_code((String) jsonObj.get("target_code"));

					hospTargetData.setTarget_name((String) jsonObj.get("target_name"));

					hospTargetData.setTarget_value(Double.parseDouble(jsonObj.get("target_value").toString()));

					hospTargetData.setError_type(err_sb.toString());

					list_err.add(hospTargetData);

				} else {

					s = aphiHospTargetDataService.addHospTargetData(mapVo);

				}
			}

		} catch (DataAccessException e) {

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(JsonListBeanUtil.listToJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmhosptargetdata/downTemplateHospTargetData", method = RequestMethod.GET)
	public String downTemplateHospTargetData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\数据准备\\奖金指标数据采集", "院级指标数据采集.xls");
		return null;
	}

}
