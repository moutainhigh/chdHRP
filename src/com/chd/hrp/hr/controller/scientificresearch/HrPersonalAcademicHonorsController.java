package com.chd.hrp.hr.controller.scientificresearch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.scientificresearch.HrPersonalAcademicHonors;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.scientificresearch.HrPersonalAcademicHonorsService;
import com.chd.hrp.hr.service.sysstruc.HrFiiedTabStrucService;

/**
 * 个人学术荣誉信息
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/scientificresearch")
public class HrPersonalAcademicHonorsController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrPersonalAcademicHonorsController.class);

	// 引入Service服务
	@Resource(name = "hrPersonalAcademicHonorsService")
	private final HrPersonalAcademicHonorsService hrPersonalAcademicHonorsService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	
	@Resource(name = "hrFiiedTabStrucService")
	private final HrFiiedTabStrucService hrFiiedTabStrucService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrPersonalAcademicHonorsMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/scientificresearch/personalacademichonors/personalAcademicHonorsMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPersonalAcademicHonorsPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/scientificresearch/personalacademichonors/personalAcademicHonorsAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPersonalAcademicHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPersonalAcademicHonors(@RequestParam Map<String, Object> mapVo, Model mode)
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
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		try {
			String hosEmpKindJson = hrPersonalAcademicHonorsService.addPersonalAcademicHonors(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePersonalAcademicHonorsPage", method = RequestMethod.GET)
	public String updateHrDeptscientificresearchPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrPersonalAcademicHonors hrdeptscientificresearch = new HrPersonalAcademicHonors();

		hrdeptscientificresearch = hrPersonalAcademicHonorsService.queryByCodePersonalAcademicHonors(mapVo);
		return "hrp/hr/scientificresearch/personalacademichonors/personalAcademicHonorsUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePersonalAcademicHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePersonalAcademicHonors(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		try {
			String hosEmpKindJson = hrPersonalAcademicHonorsService.updatePersonalAcademicHonors(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePersonalAcademicHonors", method = RequestMethod.POST)
	@ResponseBody

	public String deletePersonalAcademicHonors(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
		List<HrPersonalAcademicHonors> listVo = JSONArray.parseArray(paramVo, HrPersonalAcademicHonors.class);
		try {
			for (HrPersonalAcademicHonors hrPersonalAcademicHonors : listVo) {
				/*str = str + hrBaseService.isExistsDataByTable("HR_DUTY", hrDuty.getKind_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_DUTY", hrDuty.getKind_code());*/
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			
			
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的个人学术荣誉信息被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
			 hrPersonalAcademicHonorsService.deletePersonalAcademicHonors(listVo);
			return null;

		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPersonalAcademicHonors", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPersonalAcademicHonors(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		mapVo.put("field_tab_code", "DIC_ACADE_HONOR");
		
		String stationTransef = hrFiiedTabStrucService.queryHrFiiedData(mapVo);
		//String stationTransef = hrPersonalAcademicHonorsService.queryPersonalAcademicHonors(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 查询数据(左侧菜单) 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPersonalAcademicHonorsTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryPersonalAcademicHonorsTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrPersonalAcademicHonorsService.queryPersonalAcademicHonorsTree(mapVo);

	}
}
