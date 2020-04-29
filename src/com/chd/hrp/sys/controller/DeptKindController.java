/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.chd.hrp.sys.entity.DeptKind;
import com.chd.hrp.sys.entity.FacType;
import com.chd.hrp.sys.serviceImpl.DeptKindServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class DeptKindController extends BaseController{
	private static Logger logger = Logger.getLogger(DeptKindController.class);
	
	
	@Resource(name = "deptKindService")
	private final DeptKindServiceImpl deptKindService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/deptkind/deptKindMainPage", method = RequestMethod.GET)
	public String deptKindMainPage(Model mode) throws Exception {

		return "hrp/sys/deptkind/deptKindMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/deptkind/deptKindAddPage", method = RequestMethod.GET)
	public String deptKindAddPage(Model mode) throws Exception {

		return "hrp/sys/deptkind/deptKindAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/deptkind/addDeptKind", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String deptKindJson = deptKindService.addDeptKind(mapVo);

		return JSONObject.parseObject(deptKindJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/deptkind/queryDeptKind", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String deptKind = deptKindService.queryDeptKind(getPage(mapVo));

		return JSONObject.parseObject(deptKind);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/deptkind/deleteDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDeptKind(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("kind_code", id.split("@")[2]);
            listVo.add(mapVo);
        }
		String deptKindJson = deptKindService.deleteBatchDeptKind(listVo);
	   return JSONObject.parseObject(deptKindJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/deptkind/deptKindUpdatePage", method = RequestMethod.GET)
	
	public String deptKindUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        DeptKind deptKind = new DeptKind();
		deptKind = deptKindService.queryDeptKindByCode(mapVo);
		mode.addAttribute("group_id", deptKind.getGroup_id());
		mode.addAttribute("hos_id", deptKind.getHos_id());
		mode.addAttribute("kind_code", deptKind.getKind_code());
		mode.addAttribute("kind_name", deptKind.getKind_name());
		mode.addAttribute("is_stop", deptKind.getIs_stop());
		mode.addAttribute("spell_code", deptKind.getSpell_code());
		mode.addAttribute("wbx_code", deptKind.getWbx_code());
		mode.addAttribute("note", deptKind.getNote());
		
		return "hrp/sys/deptkind/deptKindUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/deptkind/updateDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String deptKindJson = deptKindService.updateDeptKind(mapVo);
		
		return JSONObject.parseObject(deptKindJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/deptkind/importDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String deptKindJson = deptKindService.importDeptKind(mapVo);
		
		return JSONObject.parseObject(deptKindJson);
	}

	@RequestMapping(value = "/hrp/sys/deptkind/deptKindImportPage", method = RequestMethod.GET)
	public String deptKindImportPage(Model mode) throws Exception {

		return "hrp/sys/deptkind/deptKindImport";

	}

	@RequestMapping(value = "hrp/sys/deptkind/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sys\\医院信息", "部门分类.xls");

		return null;
	}

	/**
	 * 部门分类<BR>
	 * 导入
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@RequestMapping(value = "/hrp/sys/deptkind/readDeptKindFiles", method = RequestMethod.POST)
	public String readDeptKindFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws Exception {

		List<DeptKind> list_err = new ArrayList<DeptKind>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				DeptKind deptKind = new DeptKind();

				String temp[] = list.get(i);// 行

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (StringTool.isNotBlank(temp[0])) {

					mapVo.put("kind_code", temp[0]);

					deptKind.setKind_code(temp[0]);

				} else {

					err_sb.append("部门分类编码为空");
				}

				if (StringTool.isNotBlank(temp[1])) {

					mapVo.put("kind_name", temp[1]);

					deptKind.setKind_name(temp[1]);

				} else {

					err_sb.append("部门分类名称为空");
				}

				if (StringTool.isNotBlank(temp[2])) {

					mapVo.put("is_stop", temp[2]);

					deptKind.setIs_stop(Integer.parseInt(temp[2].toString()));

				} else {

					err_sb.append("是否停用为空");
				}

				if (temp.length -1 >= 3) {

					if (StringTool.isNotBlank(temp[3])) {

						mapVo.put("note", temp[3]);

						deptKind.setNote(temp[3]);

					}

				} else {

					mapVo.put("note", "");

					deptKind.setNote("");
				}

				DeptKind eDeptKind = deptKindService.queryDeptKindByCode(mapVo);

				if (eDeptKind != null) {

					err_sb.append("部门分类编码已经存在");
				}

				if (err_sb.toString().length() > 0) {

					deptKind.setError_type(err_sb.toString());

					list_err.add(deptKind);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kind_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kind_name").toString()));

					deptKindService.addDeptKind(mapVo);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			DeptKind data_exc = new DeptKind();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	@RequestMapping(value = "/hrp/sys/deptkind/addImportDeptKindDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addImportDeptKindDict(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<DeptKind> list_err = new ArrayList<DeptKind>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				JSONObject jsonObj = JSONObject.parseObject(it.next()
						.toString());

				StringBuffer err_sb = new StringBuffer();

				DeptKind deptKind = new DeptKind();

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (StringTool.isNotBlank(jsonObj.get("kind_code"))) {

					mapVo.put("kind_code", jsonObj.get("kind_code").toString());

					deptKind.setKind_code(jsonObj.get("kind_code").toString());

				} else {

					err_sb.append("部门分类编码为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("kind_name"))) {

					mapVo.put("kind_name", jsonObj.get("kind_name").toString());

					deptKind.setKind_name(jsonObj.get("kind_name").toString());

				} else {

					err_sb.append("部门分类名称为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					mapVo.put("is_stop",Integer.parseInt(jsonObj.get("is_stop").toString()));

					deptKind.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));

				} else {

					err_sb.append("是否停用为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					mapVo.put("note", jsonObj.get("note").toString());

					deptKind.setNote(jsonObj.get("note").toString());

				} else {

					mapVo.put("note", "");

					deptKind.setNote("");
				}

				DeptKind eDeptKind = deptKindService.queryDeptKindByCode(mapVo);

				if (eDeptKind != null) {

					err_sb.append("部门分类编码已经存在");
				}

				if (err_sb.toString().length() > 0) {

					deptKind.setError_type(err_sb.toString());

					list_err.add(deptKind);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kind_code").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kind_code").toString()));

					deptKindService.addDeptKind(mapVo);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			return JSONObject
					.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err,
					list_err.size()));

		} else {
			return JSONObject
					.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}
	}
}

