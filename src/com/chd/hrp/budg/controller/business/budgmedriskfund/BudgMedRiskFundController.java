/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.controller.business.budgmedriskfund;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgMedRiskFund;
import com.chd.hrp.budg.service.business.budgmedriskfund.BudgMedRiskFundService;

/**
 * 
 * @Description: 提取医疗风险基金预算编制
 * @Table: BUDG_MED_RISK_FUND
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgMedRiskFundController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgMedRiskFundController.class);

	// 引入Service服务
	@Resource(name = "budgMedRiskFundService")
	private final BudgMedRiskFundService budgMedRiskFundService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/budgMedRiskFundMainPage", method = RequestMethod.GET)
	public String budgMedRiskFundMainPage(Model mode) throws Exception {
		return "hrp/budg/business/budgmedriskfund/budgMedRiskFundMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/budgMedRiskFundAddPage", method = RequestMethod.GET)
	public String budgMedRiskFundAddPage(Model mode) throws Exception {
		return "hrp/budg/business/budgmedriskfund/budgMedRiskFundAdd";
	}
	
	/**
	 * @Description 删除数据 提取医疗风险基金预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/saveBudgMedRiskFund", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgMedRiskFund(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("month", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("income_budg", ids[3]);
			mapVo.put("risk_fund_rate", ids[4]);
			mapVo.put("cost_budg", ids[5]);
			mapVo.put("rowNo", ids[6]);
			mapVo.put("flag", ids[7]);

			listVo.add(mapVo);

		}

		String budgMedRiskFundJson = budgMedRiskFundService.save(listVo);

		return JSONObject.parseObject(budgMedRiskFundJson);

	}
	/**
	 * @Description 添加数据 提取医疗风险基金预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/addBudgMedRiskFund", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMedRiskFund(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("budg_year") == null) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}

		String budgMedRiskFundJson = budgMedRiskFundService.add(mapVo);

		return JSONObject.parseObject(budgMedRiskFundJson);

	}

	/**
	 * @Description 更新跳转页面 提取医疗风险基金预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/budgMedRiskFundUpdatePage", method = RequestMethod.GET)
	public String budgMedRiskFundUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("budg_year") == null) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}

		Map<String, Object> budgChargeMat = budgMedRiskFundService.queryByCode(mapVo);
		mode.addAllAttributes(budgChargeMat);
		return "hrp/budg/business/budgmedriskfund/budgMedRiskFundUpdate";
	}

	/**
	 * @Description 更新数据 提取医疗风险基金预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/updateBudgMedRiskFund", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMedRiskFund(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String budgMedRiskFundJson = budgMedRiskFundService.update(mapVo);

		return JSONObject.parseObject(budgMedRiskFundJson);
	}

	/**
	 * @Description 更新数据 提取医疗风险基金预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/addOrUpdateBudgMedRiskFund", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgMedRiskFund(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String budgMedRiskFundJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		budgMedRiskFundJson = budgMedRiskFundService.addOrUpdate(mapVo);

		return JSONObject.parseObject(budgMedRiskFundJson);
	}

	/**
	 * @Description 删除数据 提取医疗风险基金预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/deleteBudgMedRiskFund", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMedRiskFund(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("budg_year", ids[3]);
			mapVo.put("month", ids[4]);
			mapVo.put("dept_id", ids[5]);

			listVo.add(mapVo);

		}

		String budgMedRiskFundJson = budgMedRiskFundService.deleteBatch(listVo);

		return JSONObject.parseObject(budgMedRiskFundJson);

	}

	/**
	 * @Description 查询数据 提取医疗风险基金预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/queryBudgMedRiskFund", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMedRiskFund(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("budg_year") == null) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		String budgMedRiskFund = budgMedRiskFundService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedRiskFund);

	}

	/**
	 * @Description 导入跳转页面 提取医疗风险基金预算编制
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/budgMedRiskFundImportPage", method = RequestMethod.GET)
	public String budgMedRiskFundImportPage(Model mode) throws Exception {

		return "hrp/budg/business/budgmedriskfund/budgMedRiskFundImport";

	}

	/**
	 * @Description 下载导入模版 提取医疗风险基金预算编制
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/budg/business/budgmedriskfund/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "budg\\cost", "提取医疗风险基金预算编制.xls");

		return null;
	}

	/**
	 * @Description 导入数据 提取医疗风险基金预算编制
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/readBudgMedRiskFundFiles", method = RequestMethod.POST)
	public String readBudgMedRiskFundFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"}; 
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
		 
		paraMap.put("hos_id", SessionManager.getHosId());   
		 
		paraMap.put("copy_code", SessionManager.getCopyCode());   
		
		//查询 科室基本信息(根据编码 匹配ID用)
		List<Map<String,Object>> deptList =  budgMedRiskFundService.queryDeptData(paraMap) ;
		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				Map<String,Object> errMap = new HashMap<String,Object>();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					
					if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
						
				}

				if (ExcelReader.validExceLColunm(temp,0)) {

					errMap.put("budg_year",temp[0]);
					mapVo.put("budg_year", temp[0]);
					mapVo.put("year", temp[0]);//查询  科室月份收入预算值 及 医疗风险基金的提取比例 用参数

				} else {

					err_sb.append("预算年度为空  ");

				}

				if (ExcelReader.validExceLColunm(temp,1)) {
					
					if(Arrays.asList(monthData).contains(temp[1])){
						
						mapVo.put("month", temp[1]);
						
					}else{
						
						err_sb.append("月份输入不合法(两位数字01-12);");
						
					}
					errMap.put("month",temp[1]);
					

				} else {

					err_sb.append("月份为空;");

				}

				if (ExcelReader.validExceLColunm(temp,2)) {
					int flag = 0 ;
					
					for(Map<String,Object> item : deptList){
						
						if(temp[2].equals(item.get("dept_code"))){
							
				    		mapVo.put("dept_id", String.valueOf(item.get("dept_id")));
				    		flag = 1;
				    		
				    		break ;
						}
						
					}
					
					if(flag == 0){
						
						err_sb.append("部门编码不存在或已停用,输入错误;");
					}
					errMap.put("dept_code",temp[2]);
					errMap.put("dept_name", temp[3]);

				} else {

					err_sb.append("部门编码为空;");

				}
				//查询 所传 科室 的 科室月份收入预算值  同时查询医疗风险基金的提取比例
				if(mapVo.get("budg_year") != null && mapVo.get("month") != null && mapVo.get("dept_id") != null ){
					
					Map<String,Object> map = JSONObject.parseObject(budgMedRiskFundService.queryWorkload(mapVo)) ;
					
					if(map == null){
						err_sb.append("科室月份医疗收入预算未编制;医疗风险基金的提取比例未设置;");
					}else{
						if(map.get("income_budg") == null){
							
							err_sb.append("科室月份医疗收入预算未编制;医疗风险基金的提取比例未设置;");
						}else{
							mapVo.put("income_budg", map.get("income_budg"));
						}
						if(map.get("risk_fund_rate") == null){
							
							err_sb.append("科室月份医疗收入预算未编制;医疗风险基金的提取比例未设置;");
						}else{
							mapVo.put("risk_fund_rate", map.get("risk_fund_rate"));
						}
					}
				}
				
				if (ExcelReader.validExceLColunm(temp,4)) {

					errMap.put("cost_budg",Double.valueOf(temp[4]));
					mapVo.put("cost_budg", temp[4]);

				} else {
					if(mapVo.get("income_budg") != null && mapVo.get("risk_fund_rate") != null ){
						double cost_budg = Double.parseDouble(String.valueOf(mapVo.get("income_budg"))) * Double.parseDouble(String.valueOf(mapVo.get("risk_fund_rate")))/1000;
						mapVo.put("cost_budg",cost_budg);
					}
					

				}

				int count = budgMedRiskFundService.queryDataExist(mapVo);

				if (count > 0 ) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					errMap.put("error_type",err_sb.toString());

					list_err.add(errMap);

				} else {

					addList.add(mapVo) ;

				}
			}
			
			if(list_err.size() == 0){
				
				if(addList.size() > 0 ){
					
					String dataJson = budgMedRiskFundService.addBatch(addList);
					
				}else{
					
					Map<String, Object> data_exc = new HashMap<String, Object>();
					
					data_exc.put("error_type","没有数据,导入失败!");
					
					list_err.add(data_exc);
				}
				
			}
		} catch (DataAccessException e) {

			Map<String, Object> data_exc = new HashMap<String, Object>();

			data_exc.put("error_type","导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 提取医疗风险基金预算编制
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/addBatchBudgMedRiskFund", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchBudgMedRiskFund(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<BudgMedRiskFund> list_err = new ArrayList<BudgMedRiskFund>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				BudgMedRiskFund budgMedRiskFund = new BudgMedRiskFund();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {

					budgMedRiskFund.setBudg_year((String) jsonObj.get("budg_year"));
					mapVo.put("budg_year", jsonObj.get("budg_year"));
				} else {

					err_sb.append("年度为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("month"))) {

					budgMedRiskFund.setMonth((String) jsonObj.get("month"));
					mapVo.put("month", jsonObj.get("month"));
				} else {

					err_sb.append("月为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {

					budgMedRiskFund.setDept_id(Long.valueOf((String) jsonObj.get("dept_id")));
					mapVo.put("dept_id", jsonObj.get("dept_id"));
				} else {

					err_sb.append("部门ID为空  ");

				}

				BudgMedRiskFund data_exc_extis = budgMedRiskFundService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					budgMedRiskFund.setError_type(err_sb.toString());

					list_err.add(budgMedRiskFund);

				} else {

					budgMedRiskFundService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			BudgMedRiskFund data_exc = new BudgMedRiskFund();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}
	
	/**
	 * 科室名称 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/queryHosDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return budgMedRiskFundService.queryHosDeptDict(mapVo);

	}
	
	/**
	 * 生成
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/generateBudgMedRiskFund", method = RequestMethod.POST)
	@ResponseBody
	public String generateBudgMedRiskFund(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return budgMedRiskFundService.copyBudgMedRiskFund(mapVo);
	}
	
	/**
	 * 查询 所传 科室 的 科室月份收入预算值  同时查询医疗风险基金的提取比例
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgmedriskfund/queryWorkload", method = RequestMethod.POST)
	@ResponseBody
	public String queryWorkload(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("year", mapVo.get("budg_year")) ;
		
		String str  = budgMedRiskFundService.queryWorkload(mapVo) ;

		return str;

	}
}
