﻿
package com.chd.hrp.htc.serviceImpl.relative.cost.deptincome;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.htc.dao.info.basic.HtcChargeItemDictMapper;
import com.chd.hrp.htc.dao.info.basic.HtcChargeKindDictMapper;
import com.chd.hrp.htc.dao.info.basic.HtcDeptDictMapper;
import com.chd.hrp.htc.dao.relative.cost.deptincome.HtcRelativeDeptIncomeMapper;
import com.chd.hrp.htc.entity.info.basic.HtcChargeItemDict;
import com.chd.hrp.htc.entity.info.basic.HtcChargeKindDict;
import com.chd.hrp.htc.entity.relative.cost.deptincome.HtcRelativeDeptIncome;
import com.chd.hrp.htc.service.relative.cost.deptincome.HtcRelativeDeptIncomeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("htcRelativeDeptIncomeService")
public class HtcRelativeDeptIncomeServiceImpl implements HtcRelativeDeptIncomeService {

	private static Logger logger = Logger.getLogger(HtcRelativeDeptIncomeServiceImpl.class);
	
	@Resource(name = "htcRelativeDeptIncomeMapper")
	private final HtcRelativeDeptIncomeMapper htcRelativeDeptIncomeMapper = null;
	
	@Resource(name = "htcDeptDictMapper")
	private final HtcDeptDictMapper htcDeptDictMapper = null;
	
	@Resource(name = "htcChargeKindDictMapper")
	private final HtcChargeKindDictMapper htcChargeKindDictMapper = null;
	
	@Resource(name = "htcChargeItemDictMapper")
	private final HtcChargeItemDictMapper htcChargeItemDictMapper = null;
    
	/**
	 * 
	 */
	@Override
	public String addHtcRelativeDeptIncome(Map<String,Object> entityMap)throws DataAccessException{
		
		
		try {
			
			htcRelativeDeptIncomeMapper.addHtcRelativeDeptIncome(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	/**
	 * 
	 */
	@Override
	public String queryHtcRelativeDeptIncome(Map<String,Object> entityMap) throws DataAccessException{
		
	    SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcRelativeDeptIncome> list = htcRelativeDeptIncomeMapper.queryHtcRelativeDeptIncome(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcRelativeDeptIncome> list = htcRelativeDeptIncomeMapper.queryHtcRelativeDeptIncome(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}
	
	/**
	 * 
	 */
	@Override
	public HtcRelativeDeptIncome queryHtcRelativeDeptIncomeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcRelativeDeptIncomeMapper.queryHtcRelativeDeptIncomeByCode(entityMap);
	}
	
	/**
	 * 
	 */
	@Override
	public String updateHtcRelativeDeptIncome(Map<String,Object> entityMap)throws DataAccessException{
		
	try {
		   htcRelativeDeptIncomeMapper.updateHtcRelativeDeptIncome(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}



	@Override
	public String deleteHtcRelativeDeptIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			   htcRelativeDeptIncomeMapper.deleteHtcRelativeDeptIncome(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String deleteBatchHtcRelativeDeptIncome(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
			try {
				   htcRelativeDeptIncomeMapper.deleteBatchHtcRelativeDeptIncome(list);
					
					return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
					
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(e.getMessage(), e);
	
					throw new SysException("{\"error\":\"删除失败\"}");
				}
	}
	
	public Map<String, Object> deptDictCheck(){
		/*
		 *科室字典
		 **/
		Map<String, Object> backMap = new HashMap<String, Object>();
		Map<String, Object> deptMap = new HashMap<String, Object>();
		deptMap.put("group_id", SessionManager.getGroupId());
		deptMap.put("hos_id", SessionManager.getHosId());
		deptMap.put("is_last", 1);
		List<Map<String,Object>> deptMapResult = htcDeptDictMapper.queryHtcDeptDict(deptMap);
		
		for (Map<String,Object> dept :deptMapResult) {
			backMap.put(dept.get("dept_code").toString(), dept.get("dept_id") + "."+ dept.get("dept_no"));
		}
	    return backMap;
	}
	
	public Map<String, Object> chargeKindCheck(){
		/*
		 *收费类别
		 **/
		Map<String, Object> backMap = new HashMap<String, Object>();
		Map<String, Object> chargeKindMap = new HashMap<String, Object>();
		chargeKindMap.put("group_id", SessionManager.getGroupId());
		chargeKindMap.put("hos_id", SessionManager.getHosId());
		chargeKindMap.put("copy_code", SessionManager.getCopyCode());
		List<HtcChargeKindDict> htcChargeKindDict = htcChargeKindDictMapper.queryHtcChargeKindDict(chargeKindMap);
		

		for (HtcChargeKindDict chargeKindDict :htcChargeKindDict) {
			
			backMap.put(String.valueOf(chargeKindDict.getCharge_kind_code()),chargeKindDict.getCharge_kind_id());
		}
	    return backMap;
	}
	
	public Map<String, Object> chargeItemCheck(){
		/*
		 *收费项目
		 **/
		Map<String, Object> backMap = new HashMap<String, Object>();
		Map<String, Object> chargeItemMap = new HashMap<String, Object>();
		chargeItemMap.put("group_id", SessionManager.getGroupId());
		chargeItemMap.put("hos_id", SessionManager.getHosId());
		chargeItemMap.put("copy_code", SessionManager.getCopyCode());
		List<HtcChargeItemDict> htcChargeItemDict = htcChargeItemDictMapper.queryHtcChargeItemDict(chargeItemMap);
		
		for (HtcChargeItemDict chargeItemDict :htcChargeItemDict) {
			
			 backMap.put(String.valueOf(chargeItemDict.getCharge_item_code()),chargeItemDict.getCharge_item_id());
		}
	    return backMap;
	}
	
	public Map<String, Object> busiDataSourceCheck(){
		/*
		 *数据来源
		 **/
		Map<String, Object> backMap = new HashMap<String, Object>();
		backMap.put("01", "01");
		backMap.put("02", "02");
		backMap.put("03", "03");
	    return backMap;
	}
	@Override
	public String impHtcRelativeDeptIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		     Map<String, Object> dept = deptDictCheck();
		     Map<String, Object> chargeKind = chargeKindCheck();
		     Map<String, Object> chargeItem = chargeItemCheck();
		     Map<String, Object> busiData = busiDataSourceCheck();
		try {
		    
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			
			List<Map<String,Object>> resultSet = new ArrayList<Map<String,Object>>();
			
			for (Map<String, List<String>> map : list) {
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("acc_year", map.get("year_month").get(1).substring(0, 4));
				
				mapVo.put("acc_month", map.get("year_month").get(1).substring(4, 6));
				
				
                 if(null == dept.get(map.get("appl_dept_code").get(1))){
					
					return "{\"error\":\""+ map.get("appl_dept_code").get(0)+"\n开单科室编码:"+map.get("appl_dept_code").get(1)+"不存在.\",\"state\":\"false\"}";
				
				}else {
			
					String appl_dept_code= dept.get(map.get("appl_dept_code").get(1)).toString();
					int index = appl_dept_code.indexOf(".");
					int lenth = appl_dept_code.length();
					mapVo.put("appl_dept_id", appl_dept_code.substring(0, index));
					mapVo.put("appl_dept_no", appl_dept_code.substring(index+1, lenth));
					
				}
                 
                 if(null == dept.get(map.get("exec_dept_code").get(1))){
 					
 					return "{\"error\":\""+ map.get("exec_dept_code").get(0)+"\n执行科室编码:"+map.get("exec_dept_code").get(1)+"不存在.\",\"state\":\"false\"}";
 				
 				}else {
 			
 					String exec_dept_code= dept.get(map.get("exec_dept_code").get(1)).toString();
 					int index = exec_dept_code.indexOf(".");
 					int lenth = exec_dept_code.length();
 					mapVo.put("exec_dept_id", exec_dept_code.substring(0, index));
 					mapVo.put("exec_dept_no", exec_dept_code.substring(index+1, lenth));
 					
 				}
               
                 if(null == chargeKind.get(map.get("charge_kind_code").get(1))){
  					
  					return "{\"error\":\""+ map.get("charge_kind_code").get(0)+"\n收费类别编码:"+map.get("charge_kind_code").get(1)+"不存在.\",\"state\":\"false\"}";
  				
  				}else {
  			

  					mapVo.put("charge_kind_id", chargeKind.get(map.get("charge_kind_code").get(1)));
  					
  				}
                 
 
                if(null == chargeItem.get(map.get("charge_item_code").get(1))){
   					
   					return "{\"error\":\""+ map.get("charge_item_code").get(0)+"\n收费项目编码:"+map.get("charge_item_code").get(1)+"不存在.\",\"state\":\"false\"}";
   				
   				}else {
   			

   					mapVo.put("charge_item_id", chargeItem.get(map.get("charge_item_code").get(1)));
   					
   				}

                if(null == busiData.get(map.get("busi_data_source_code").get(1))){
   					
   					return "{\"error\":\""+ map.get("busi_data_source_code").get(0)+"\n收入数据来源:"+map.get("busi_data_source_code").get(1)+"不存在.\",\"state\":\"false\"}";
   				
   				}else {
   			

   					mapVo.put("busi_data_source_code", busiData.get(map.get("busi_data_source_code").get(1)));
   					
   				}
                
                 mapVo.put("price", Double.parseDouble(map.get("money").get(1).toString()) / Double.parseDouble(map.get("num").get(1).toString()));
                
                 mapVo.put("num", map.get("num").get(1));
                 
                 mapVo.put("money", map.get("money").get(1));
                
                 mapVo.put("create_user", SessionManager.getUserId());
    			
    			 mapVo.put("create_date",new Date());
    			
                 resultSet.add(mapVo);
			 }
			     
			   if(resultSet.size() > 0 ){
				   System.out.println("resultSet = " + resultSet);
				   htcRelativeDeptIncomeMapper.addBatchHtcRelativeDeptIncome(resultSet);
			   }
			  return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
}