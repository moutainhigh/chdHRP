﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.allot.in;
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
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.ass.entity.allot.in.AssAllotInHouse;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutHouse;
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.entity.share.AssShareDeptHouse;
import com.chd.hrp.ass.service.allot.in.AssAllotInDetailHouseService;
import com.chd.hrp.ass.service.allot.in.AssAllotInHouseService;
import com.chd.hrp.ass.service.allot.out.AssAllotOutDetailHouseService;
import com.chd.hrp.ass.service.allot.out.AssAllotOutHouseService;
import com.chd.hrp.ass.service.card.AssCardHouseService;
import com.chd.hrp.ass.service.share.AssShareDeptHouseService;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨入库单主表（房屋及建筑物）
 * @Table:
 * ASS_ALLOT_IN_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssAllotInHouseController extends BaseController{

	private static Logger logger = Logger.getLogger(AssAllotInHouseController.class);

	// 引入Service服务
	@Resource(name = "assAllotInHouseService")
	private final AssAllotInHouseService assAllotInHouseService = null;

	@Resource(name = "assAllotInDetailHouseService")
	private final AssAllotInDetailHouseService assAllotInDetailHouseService = null;
	
	@Resource(name = "assShareDeptHouseService")
	private final AssShareDeptHouseService assShareDeptHouseService = null;

	@Resource(name = "assCardHouseService")
	private final AssCardHouseService assCardHouseService = null;

	@Resource(name = "assAllotOutHouseService")
	private final AssAllotOutHouseService assAllotOutHouseService = null;

	@Resource(name = "assAllotOutDetailHouseService")
	private final AssAllotOutDetailHouseService assAllotOutDetailHouseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/assAllotInHouseMainPage", method = RequestMethod.GET)
	public String assAllotInHouseMainPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05058",MyConfig.getSysPara("05058"));
		
		return "hrp/ass/asshouse/assallotin/main";
	}

	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/assImportAllotOutHousePage", method = RequestMethod.GET)
	public String assImportAllotOutHousePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("out_group_id", mapVo.get("out_group_id"));
		mode.addAttribute("out_hos_id", mapVo.get("out_hos_id"));
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("note", mapVo.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assallotin/importoutmain";
	}

	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/assViewAllotOutHousePage", method = RequestMethod.GET)
	public String assViewAllotOutHousePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("out_group_id", mapVo.get("out_group_id"));
		mode.addAttribute("out_hos_id", mapVo.get("out_hos_id"));
		mode.addAttribute("allot_in_no", mapVo.get("allot_in_no"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assallotin/viewoutmain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/assAllotInHouseAddPage", method = RequestMethod.GET)
	public String assAllotInHouseAddPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assallotin/add";
	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨入库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/saveAssAllotInHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssAllotInHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_emp", SessionManager.getUserId());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
		//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get(SessionManager.getModCode());
		
		int result = startyearmonth.compareTo(yearmonth);
		
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
			
		}
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}

		AssAllotInHouse assAllotInHouse = assAllotInHouseService.queryByCode(mapVo);
		if (assAllotInHouse != null) {
			if (assAllotInHouse.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
		}

		String assAllotInHouseJson = assAllotInHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assAllotInHouseJson);

	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨入库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/assAllotInHouseUpdatePage", method = RequestMethod.GET)
	public String assAllotInHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssAllotInHouse assAllotInHouse = new AssAllotInHouse();

		assAllotInHouse = assAllotInHouseService.queryByCode(mapVo);
		mode.addAttribute("is_import", mapVo.get("is_import"));
		mode.addAttribute("group_id", assAllotInHouse.getGroup_id());
		mode.addAttribute("hos_id", assAllotInHouse.getHos_id());
		mode.addAttribute("copy_code", assAllotInHouse.getCopy_code());
		mode.addAttribute("allot_in_no", assAllotInHouse.getAllot_in_no());
		mode.addAttribute("out_group_id", assAllotInHouse.getOut_group_id());
		mode.addAttribute("out_group_name", assAllotInHouse.getOut_group_name());
		mode.addAttribute("out_hos_id", assAllotInHouse.getOut_hos_id());
		mode.addAttribute("out_hos_name", assAllotInHouse.getOut_hos_name());
		mode.addAttribute("price", assAllotInHouse.getPrice());
		mode.addAttribute("add_depre", assAllotInHouse.getAdd_depre());
		mode.addAttribute("cur_money", assAllotInHouse.getCur_money());
		mode.addAttribute("fore_money", assAllotInHouse.getFore_money());
		mode.addAttribute("create_emp", assAllotInHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assAllotInHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assAllotInHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assAllotInHouse.getAudit_emp());
		mode.addAttribute("audit_emp_name", assAllotInHouse.getAudit_emp_name());
		mode.addAttribute("audit_date", DateUtil.dateToString(assAllotInHouse.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", assAllotInHouse.getState());
		mode.addAttribute("state_name", assAllotInHouse.getState_name());
		mode.addAttribute("note", assAllotInHouse.getNote());
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05058",MyConfig.getSysPara("05058"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05058",MyConfig.getSysPara("05058"));
		
		return "hrp/ass/asshouse/assallotin/update";
	}

	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/updateConfirmAllotInMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAllotInMainHouse(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("allot_in_no", ids[3]);
			mapVo.put("ass_in_no", ids[3]);
			//使用科室
			List<Map<String, Object>> listDept = assAllotInHouseService.queryDept(mapVo);
			for(Map<String, Object> temp : listDept){
				Map<String, Object> listDeptt = new HashMap<String, Object>();
				listDeptt.put("group_id", temp.get("group_id"));

				listDeptt.put("hos_id", temp.get("hos_id"));

				listDeptt.put("copy_code", temp.get("copy_code"));

				listDeptt.put("ass_card_no", temp.get("ass_card_no"));
				
				listDeptt.put("dept_id", temp.get("dept_id"));
				
				List<AssShareDeptHouse> dept = (List<AssShareDeptHouse>)assShareDeptHouseService.queryExists(listDeptt);
				
				if(dept.size() == 0){
					return JSONObject.parseObject("{\"warn\":\"使用科室不能为空,不能入库! \"}");
				}
			
			
			}
			AssAllotInHouse assAllotInHouse = assAllotInHouseService.queryByCode(mapVo);
			if (assAllotInHouse.getState() == 2) {
				continue;
			}
			if (DateUtil.compareDate(assAllotInHouse.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			List<Map<String, Object>> list = assAllotInDetailHouseService.queryByInit(mapVo);
			if (list.size() == 0) {
				return JSONObject.parseObject("{\"warn\":\"单据不存在资产,不能入库! \"}");
			}
			for (Map<String, Object> temp : list) {

				Map<String, Object> mapExists = new HashMap<String, Object>();

				mapExists.put("group_id", temp.get("group_id"));

				mapExists.put("hos_id", temp.get("hos_id"));

				mapExists.put("copy_code", temp.get("copy_code"));

				mapExists.put("ass_in_no", temp.get("ass_in_no"));

				mapExists.put("ass_in_no", temp.get("allot_in_no"));

				mapExists.put("ass_id", temp.get("ass_id"));

				mapExists.put("ass_no", temp.get("ass_no"));

				Integer use_state = assCardHouseService.queryCardExistsByAssInNo(mapExists);

				if (use_state == 0) {
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}

				List<Map<String, Object>> cardList = assCardHouseService.queryAssCardByAssInNo(mapExists);
				boolean assFlag = true, oriCardNo = true, specFlag = true, modelFlag = true, brandFlag = true,
						facFlag = true, priceFlag = true, depreFlag = true, depreMonthFlag = true, curMoneyFlag = true,
						foreMoneyFlag = true;

				for (Map<String, Object> card : cardList) {
					if (verification(temp.get("ass_id"), card.get("ass_id"))) {
						assFlag = false;
						break;
					}
					if (verification(temp.get("ass_ori_card_no"), card.get("ass_ori_card_no"))) {
						oriCardNo = false;
						break;
					}
					if (verification(temp.get("ass_spec"), card.get("ass_spec"))) {
						specFlag = false;
						break;
					}
					if (verification(temp.get("ass_model"), card.get("ass_mondl"))) {
						modelFlag = false;
						break;
					}
					if (verification(temp.get("ass_brand"), card.get("ass_brand"))) {
						brandFlag = false;
						break;
					}
					if (verification(temp.get("fac_id"), card.get("fac_id"))) {
						facFlag = false;
						break;
					}
					if (verification(temp.get("price"), card.get("price"))) {
						priceFlag = false;
						break;
					}
					if (verification(temp.get("add_depre"), card.get("depre_money"))) {
						depreFlag = false;
						break;
					}
					if (verification(temp.get("add_depre_month"), card.get("add_depre_month"))) {
						depreMonthFlag = false;
						break;
					}
					if (verification(temp.get("cur_money"), card.get("cur_money"))) {
						curMoneyFlag = false;
						break;
					}
					if (verification(temp.get("fore_money"), card.get("fore_money"))) {
						foreMoneyFlag = false;
						break;
					}
				}
				if (!assFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片或不匹配的资产,不能入库! \"}");
				}
				if (!oriCardNo) {
					return JSONObject.parseObject("{\"warn\":\"存在原始卡片号不匹配的资产,不能入库! \"}");
				}
				if (!specFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在规格不匹配的资产,不能入库! \"}");
				}
				if (!modelFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在型号不匹配的资产,不能入库! \"}");
				}
				if (!brandFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在品牌不匹配的资产,不能入库! \"}");
				}
				if (!facFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在生产厂商不匹配的资产,不能入库! \"}");
				}
				if (!priceFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在资产原值和卡片原值不匹配的资产,不能入库! \"}");
				}
				if (!depreFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在资产累计折旧和卡片累计折旧不匹配的资产,不能入库! \"}");
				}
				if (!depreMonthFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在资产累计折旧月份和卡片累计折旧月份不匹配的资产,不能入库! \"}");
				}
				if (!curMoneyFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在资产净值和卡片资产净值不匹配的资产,不能入库! \"}");
				}
				if (!foreMoneyFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在资产预留残值和预留残值不匹配的资产,不能入库! \"}");
				}
			}

		}

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("allot_in_no", ids[3]);

			mapVo.put("ass_in_no", ids[3]);

			mapVo.put("in_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("confirm_emp", SessionManager.getUserId());

			mapVo.put("audit_emp", SessionManager.getUserId());

			AssAllotInHouse assAllotInHouse = assAllotInHouseService.queryByCode(mapVo);

			if (assAllotInHouse.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}

		try {
			assInMainJson = assAllotInHouseService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	private boolean verification(Object obj1, Object obj2) {
		// true为不相等
		if (NumberUtil.isNumeric(String.valueOf(obj1)) || NumberUtil.isNumeric(String.valueOf(obj2))) {
			Double number1 = obj1 == null || obj1.equals("") ? 0 : Double.parseDouble(String.valueOf(obj1));
			Double number2 = obj2 == null || obj2.equals("") ? 0 : Double.parseDouble(String.valueOf(obj2));
			if (Math.abs(number1 - number2) > 0) {
				return true;
			} else {
				return false;
			}
		}
		if (obj1 == null && obj2 == null) {
			return false;
		}
		if (obj1 == null && obj2 != null) {
			return true;
		}
		if (obj1 != null && obj2 == null) {
			return true;
		}
		if (obj1 != null && obj2 != null) {
			if (!obj1.equals(obj2)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨入库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/deleteAssAllotInHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotInHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("allot_in_no", ids[3]);
			mapVo.put("ass_in_no", ids[3]);

			AssAllotInHouse assInHouse = assAllotInHouseService.queryByCode(mapVo);

			if (assInHouse.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assAllotInHouseJson = assAllotInHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotInHouseJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨入库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/queryAssAllotInHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotInHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assAllotInHouse = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
		
			assAllotInHouse = assAllotInHouseService.query(getPage(mapVo));
		
		}else{
			
			assAllotInHouse = assAllotInHouseService.queryDetails(getPage(mapVo));
			
		}
		

		return JSONObject.parseObject(assAllotInHouse);

	}

	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/initAssAllotInHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssAllotInHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_emp", SessionManager.getUserId());

		String assAllotInHouse = assAllotInHouseService.initAssAllotInHouse(mapVo);

		return JSONObject.parseObject(assAllotInHouse);

	}

	// 查询出库单引入使用
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/queryByAllotInImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByAllotInImport(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assAllotInHouse = assAllotOutHouseService.queryByAllotInImport(getPage(mapVo));

		return JSONObject.parseObject(assAllotInHouse);
	}

	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/queryByAllotInImportView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByAllotInImportView(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assAllotInHouse = assAllotOutHouseService.queryByAllotInImportView(getPage(mapVo));

		return JSONObject.parseObject(assAllotInHouse);
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨入库明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/deleteAssAllotInDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotInDetailHouse(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("allot_in_no", ids[3]);
			mapVo.put("ass_id", ids[4]);
			mapVo.put("ass_no", ids[5]);
			mapVo.put("ass_ori_card_no", ids[6]);
			mapVo.put("ass_in_no", ids[3]);
			AssAllotInHouse assAllotInHouse = assAllotInHouseService.queryByCode(mapVo);

			if (assAllotInHouse.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}

		String assAllotInDetailHouseJson = assAllotInDetailHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotInDetailHouseJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨入库明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/queryAssAllotInDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotInDetailHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAllotInDetailHouse = assAllotInDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assAllotInDetailHouse);

	}

	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/queryAssAllotInCardHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInCardHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		assCard = assCardHouseService.query(getPage(mapVo));
		JSONObject json = JSONObject.parseObject(assCard);
		JSONArray jsonarray = JSONArray.parseArray(json.get("Rows").toString());
		for(int i = 0 ; i < jsonarray.size(); i ++){
			JSONObject job = jsonarray.getJSONObject(i); 
			if(job.get("ass_card_no").equals("合计")){
				jsonarray.remove(i);
			}
		}
		json.put("Rows", jsonarray);
		return JSONObject.parseObject(json.toString());
	}

	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/initAssAllotInCardHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssInCardHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_in_no", mapVo.get("allot_in_no").toString());

		String assCard = "";
		try {
			AssAllotInHouse assAllotInHouse = assAllotInHouseService.queryByCode(mapVo);

			if (assAllotInHouse.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}

			assCard = assAllotInHouseService.initAssAllotInCardHouse(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCard);
	}

	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/initAssAllotInBatchCardHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssAllotInBatchCardHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_in_no",mapVo.get("allot_in_no").toString());
		
		String assCard = "";
		try {
			AssAllotInHouse assAllotInHouse = assAllotInHouseService.queryByCode(mapVo);
			
			if(assAllotInHouse.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assAllotInHouseService.initAssAllotInBatchCardHouse(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}
	
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/deleteAssAllotInCardHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCard(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		boolean flag1 = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_card_no", ids[3]);
			mapVo.put("allot_in_no", ids[4]);
			mapVo.put("ass_in_no", ids[4]);
			AssAllotInHouse assAllotInHouse = assAllotInHouseService.queryByCode(mapVo);

			if (assAllotInHouse.getState() > 0) {
				flag = false;
				break;
			}

			AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
			if (assCardHouse.getUse_state() > 0) {
				flag1 = false;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		if (!flag1) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建的才能进行删除! \"}");
		}

		String assCardGeneralJson = "";
		try {
			assCardGeneralJson = assCardHouseService.deleteBatch(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assCardGeneralJson);

	}

	@RequestMapping(value = "/hrp/ass/assHouse/assallotin/assAllotOutHouseViewPage", method = RequestMethod.GET)
	public String assAllotOutHouseViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssAllotOutHouse assAllotOutHouse = new AssAllotOutHouse();

		assAllotOutHouse = assAllotOutHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assAllotOutHouse.getGroup_id());
		mode.addAttribute("hos_id", assAllotOutHouse.getHos_id());
		mode.addAttribute("copy_code", assAllotOutHouse.getCopy_code());
		mode.addAttribute("allot_out_no", assAllotOutHouse.getAllot_out_no());
		mode.addAttribute("in_group_id", assAllotOutHouse.getIn_group_id());
		mode.addAttribute("in_group_name", assAllotOutHouse.getIn_group_name());
		mode.addAttribute("in_hos_id", assAllotOutHouse.getIn_hos_id());
		mode.addAttribute("in_hos_name", assAllotOutHouse.getIn_hos_name());
		mode.addAttribute("price", assAllotOutHouse.getPrice());
		mode.addAttribute("add_depre", assAllotOutHouse.getAdd_depre());
		mode.addAttribute("cur_money", assAllotOutHouse.getCur_money());
		mode.addAttribute("fore_money", assAllotOutHouse.getFore_money());
		mode.addAttribute("create_emp", assAllotOutHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assAllotOutHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assAllotOutHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assAllotOutHouse.getAudit_emp());
		mode.addAttribute("audit_emp_name", assAllotOutHouse.getAudit_emp_name());
		mode.addAttribute("audit_date", assAllotOutHouse.getAudit_date());
		mode.addAttribute("state", assAllotOutHouse.getState());
		mode.addAttribute("state_name", assAllotOutHouse.getState_name());
		mode.addAttribute("note", assAllotOutHouse.getNote());
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assallotin/viewout";
	}
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/assAllotOutHouseViewPage", method = RequestMethod.GET)
	public String assAllotOuthouseViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssAllotOutHouse assAllotOutHouse = new AssAllotOutHouse();

		assAllotOutHouse = assAllotOutHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assAllotOutHouse.getGroup_id());
		mode.addAttribute("hos_id", assAllotOutHouse.getHos_id());
		mode.addAttribute("copy_code", assAllotOutHouse.getCopy_code());
		mode.addAttribute("allot_out_no", assAllotOutHouse.getAllot_out_no());
		mode.addAttribute("in_group_id", assAllotOutHouse.getIn_group_id());
		mode.addAttribute("in_group_name", assAllotOutHouse.getIn_group_name());
		mode.addAttribute("in_hos_id", assAllotOutHouse.getIn_hos_id());
		mode.addAttribute("in_hos_name", assAllotOutHouse.getIn_hos_name());
		mode.addAttribute("price", assAllotOutHouse.getPrice());
		mode.addAttribute("add_depre", assAllotOutHouse.getAdd_depre());
		mode.addAttribute("cur_money", assAllotOutHouse.getCur_money());
		mode.addAttribute("fore_money", assAllotOutHouse.getFore_money());
		mode.addAttribute("create_emp", assAllotOutHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assAllotOutHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assAllotOutHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assAllotOutHouse.getAudit_emp());
		mode.addAttribute("audit_emp_name", assAllotOutHouse.getAudit_emp_name());
		mode.addAttribute("audit_date", assAllotOutHouse.getAudit_date());
		mode.addAttribute("state", assAllotOutHouse.getState());
		mode.addAttribute("state_name", assAllotOutHouse.getState_name());
		mode.addAttribute("note", assAllotOutHouse.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assallotin/viewout";
	}

	@RequestMapping(value = "/hrp/ass/assHouse/assallotin/queryAllotOutDetByAllotInHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllotOutDetByAllotInHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAllotOutDetailHouse = assAllotOutDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assAllotOutDetailHouse);
	}
	@RequestMapping(value = "/hrp/ass/asshouse/assallotin/queryAllotOutDetByAllotInHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllotOutDetByAllotInhouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAllotOutDetailHouse = assAllotOutDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assAllotOutDetailHouse);
	}
	
	

	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/asshouse/assallotin/queryAssAllotInHouseStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssAllotInHouseStates(@RequestParam Map<String, Object> mapVo, Model mode)
throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assAllotInHouseService.queryAssAllotInHouseStates(mapVo);

if(list.size() == 0){

return JSONObject.parseObject("{\"state\":\"true\"}");

}else{

String  str = "" ;
for(String item : list){
	
	str += item +"," ;
}

return JSONObject.parseObject("{\"error\":\"盘点单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
}


}

}

