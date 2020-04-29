﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.dict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.dict.AssRelicGradeDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssRelicGradeDictService;

/**
 * 
 * @Description: 文物等级
 * @Table: ASS_RELIC_GRADE_DICT
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssRelicGradeDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssRelicGradeDictController.class);

	// 引入Service服务
	@Resource(name = "assRelicGradeDictService")
	private final AssRelicGradeDictService assRelicGradeDictService = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrelicgradedict/assRelicGradeDictMainPage", method = RequestMethod.GET)
	public String assRelicGradeDictMainPage(Model mode) throws Exception {

		return "hrp/ass/assrelicgradedict/assRelicGradeDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrelicgradedict/assRelicGradeDictAddPage", method = RequestMethod.GET)
	public String assRelicGradeDictAddPage(Model mode) throws Exception {

		return "hrp/ass/assrelicgradedict/assRelicGradeDictAdd";

	}

	/**
	 * @Description 添加数据 文物等级
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrelicgradedict/addAssRelicGradeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRelicGradeDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("relic_grade_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("relic_grade_name").toString()));

		String retErrot = "";

		// 根据编号获取对象
		AssRelicGradeDict assRelicGradeDict = assRelicGradeDictService.queryByCode(mapVo);
		if (assRelicGradeDict != null) {

			retErrot = "{\"error\":\"编码：【" + assRelicGradeDict.getRelic_grade_code()
					+ "】重复,请修改后添加！\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);

		}
		// 根据名称获取对象
		AssRelicGradeDict assRelicGradeDictName = assRelicGradeDictService.queryByName(mapVo);
		if (assRelicGradeDictName != null) {

			retErrot = "{\"error\":\"名称：【" + assRelicGradeDictName.getRelic_grade_name()
					+ "】重复,请修改后添加！\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);

		}

		String assRelicGradeDictJson = assRelicGradeDictService.add(mapVo);

		return JSONObject.parseObject(assRelicGradeDictJson);

	}

	/**
	 * @Description 更新跳转页面 文物等级
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrelicgradedict/assRelicGradeDictUpdatePage", method = RequestMethod.GET)
	public String assRelicGradeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssRelicGradeDict assRelicGradeDict = new AssRelicGradeDict();

		assRelicGradeDict = assRelicGradeDictService.queryByCode(mapVo);

		mode.addAttribute("group_id", assRelicGradeDict.getGroup_id());
		mode.addAttribute("hos_id", assRelicGradeDict.getHos_id());
		mode.addAttribute("copy_code", assRelicGradeDict.getCopy_code());
		mode.addAttribute("relic_grade_code", assRelicGradeDict.getRelic_grade_code());
		mode.addAttribute("relic_grade_name", assRelicGradeDict.getRelic_grade_name());
		mode.addAttribute("spell_code", assRelicGradeDict.getSpell_code());
		mode.addAttribute("wbx_code", assRelicGradeDict.getWbx_code());
		mode.addAttribute("is_stop", assRelicGradeDict.getIs_stop());

		return "hrp/ass/assrelicgradedict/assRelicGradeDictUpdate";
	}

	/**
	 * @Description 更新数据 文物等级
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrelicgradedict/updateAssRelicGradeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRelicGradeDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("relic_grade_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("relic_grade_name").toString()));

		String assRelicGradeDictJson = assRelicGradeDictService.update(mapVo);

		return JSONObject.parseObject(assRelicGradeDictJson);
	}

	/**
	 * @Description 删除数据 文物等级
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrelicgradedict/deleteAssRelicGradeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRelicGradeDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		String str = "";
		boolean falg = true;
		String retErrot = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			str = str + assBaseService.isExistsDataByTable("ASS_RELIC_GRADE_DICT", ids[3]) == null ? ""
					: assBaseService.isExistsDataByTable("ASS_RELIC_GRADE_DICT", ids[3]);

			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("relic_grade_code", ids[3]);
			listVo.add(mapVo);
		}

		if (!falg) {
			retErrot = "{\"error\":\"删除失败，选择的文物等级被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}
		String assRelicGradeDictJson = assRelicGradeDictService.deleteBatch(listVo);

		return JSONObject.parseObject(assRelicGradeDictJson);

	}

	/**
	 * @Description 查询数据 文物等级
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrelicgradedict/queryAssRelicGradeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRelicGradeDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assRelicGradeDict = assRelicGradeDictService.query(getPage(mapVo));

		return JSONObject.parseObject(assRelicGradeDict);

	}

	/**
	 * @Description 导入跳转页面 文物等级
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrelicgradedict/assRelicGradeDictImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String assRelicGradeDictImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		try {

			String reJson = assRelicGradeDictService.readAssRelicGradeDictFiles(mapVo);

			return reJson;

		} catch (Exception e) {

			return "{\"error\":\"" + e.getMessage() + "\"}";

		}

	}

	/**
	 * @Description 下载导入模版 文物等级
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assrelicgradedict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "文物等级.xls");

		return null;
	}

	/**
	 * @Description 导入数据 文物等级
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assrelicgradedict/readAssRelicGradeDictFiles", method = RequestMethod.POST)
	public String readAssRelicGradeDictFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssRelicGradeDict> list_err = new ArrayList<AssRelicGradeDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssRelicGradeDict assRelicGradeDict = new AssRelicGradeDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assRelicGradeDict.setRelic_grade_code(temp[3]);
					mapVo.put("relic_grade_code", temp[3]);

				} else {

					err_sb.append("文物等级代码为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assRelicGradeDict.setRelic_grade_name(temp[4]);
					mapVo.put("relic_grade_name", temp[4]);

				} else {

					err_sb.append("文物等级名称为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assRelicGradeDict.setSpell_code(temp[5]);
					mapVo.put("spell_code", temp[5]);

				} else {

					err_sb.append("拼音码为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assRelicGradeDict.setWbx_code(temp[6]);
					mapVo.put("wbx_code", temp[6]);

				} else {

					err_sb.append("五笔码为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assRelicGradeDict.setIs_stop(Integer.valueOf(temp[7]));
					mapVo.put("is_stop", temp[7]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssRelicGradeDict data_exc_extis = assRelicGradeDictService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assRelicGradeDict.setError_type(err_sb.toString());

					list_err.add(assRelicGradeDict);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

					String dataJson = assRelicGradeDictService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AssRelicGradeDict data_exc = new AssRelicGradeDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 文物等级
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assrelicgradedict/addBatchAssRelicGradeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssRelicGradeDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssRelicGradeDict> list_err = new ArrayList<AssRelicGradeDict>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AssRelicGradeDict assRelicGradeDict = new AssRelicGradeDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("relic_grade_code"))) {

					assRelicGradeDict.setRelic_grade_code((String) jsonObj.get("relic_grade_code"));
					mapVo.put("relic_grade_code", jsonObj.get("relic_grade_code"));
				} else {

					err_sb.append("文物等级代码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("relic_grade_name"))) {

					assRelicGradeDict.setRelic_grade_name((String) jsonObj.get("relic_grade_name"));
					mapVo.put("relic_grade_name", jsonObj.get("relic_grade_name"));
				} else {

					err_sb.append("文物等级名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {

					assRelicGradeDict.setSpell_code((String) jsonObj.get("spell_code"));
					mapVo.put("spell_code", jsonObj.get("spell_code"));
				} else {

					err_sb.append("拼音码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {

					assRelicGradeDict.setWbx_code((String) jsonObj.get("wbx_code"));
					mapVo.put("wbx_code", jsonObj.get("wbx_code"));
				} else {

					err_sb.append("五笔码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assRelicGradeDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));
					mapVo.put("is_stop", jsonObj.get("is_stop"));
				} else {

					err_sb.append("是否停用为空  ");

				}

				AssRelicGradeDict data_exc_extis = assRelicGradeDictService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assRelicGradeDict.setError_type(err_sb.toString());

					list_err.add(assRelicGradeDict);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

					String dataJson = assRelicGradeDictService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AssRelicGradeDict data_exc = new AssRelicGradeDict();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

}
