﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.sc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.sc.HrTableTypeMapper;
import com.chd.hrp.hr.entity.sc.HrTableType;
import com.chd.hrp.hr.service.sc.HrTableTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;

/**
 * 
 * @Description: 数据表分类 如：人事档案、组织机构
 * @Table: HR_TAB_TYPE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrTableTypeService")
public class HrTableTypeServiceImpl implements HrTableTypeService {

	private static Logger logger = Logger.getLogger(HrTableTypeServiceImpl.class);
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	// 引入DAO操作
	@Resource(name = "hrTableTypeMapper")
	private final HrTableTypeMapper hrTableTypeMapper = null;

	@Override
	public String addHrTableType(Map<String, Object> entityMap) throws DataAccessException {

		//判断编码或者名称是否存在
		int count = hrTableTypeMapper.queryTableTypeExits(entityMap);

		if (count > 0) {
			throw new SysException("分类代码或名称已存在");
		}

		try {
			hrTableTypeMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	
	/**
	 * 更新
	 */
	@Override
	public String updateHrTableType(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());

			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("type_tab_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_tab_name").toString()));
			
			if("1".equals(entityMap.get("is_change").toString())){
				Map<String,Object> type = hrTableTypeMapper.queryTypeTabByName(entityMap);
				if(type != null){
					if(type.get("type_tab_name").toString().equals(entityMap.get("type_tab_name").toString())){
						return "{\"error\":\"名称：" + entityMap.get("type_tab_name").toString() + "已存在.\"}";
					}
				}
			}
			
			hrTableTypeMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteHrTableType(Map<String, Object> entityMap) throws DataAccessException {

		try {
			hrTableTypeMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteBatchHrTableType(List<HrTableType> entityList) throws DataAccessException {

		try {
			Map<String,Object> map = new HashMap<String,Object>();
			StringBuffer dict_id_str = new StringBuffer();
			for(HrTableType htt : entityList){
				dict_id_str.append(htt.getType_tab_code()).append("','");
			}
			
			
			String reStr="";
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("acc_year", "");
			map.put("p_flag", "");
			map.put("dict_code", "HR_TAB_TYPE".toUpperCase());
			map.put("dict_id_str",dict_id_str.substring(0, dict_id_str.length()-3));
			sysFunUtilMapper.querySysDictDelCheck(map);
			
			if(map.get("reNote")!=null) {
				reStr+=map.get("reNote");
			}
			
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败,选择的数据表分类被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			hrTableTypeMapper.deleteBatchHrTableType(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryHrTableType(Map<String, Object> entityMap) throws DataAccessException {
		List<HrTableType> list = (List<HrTableType>) hrTableTypeMapper.query(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public HrTableType queryHrTableTypeByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrTableTypeMapper.queryByCode(entityMap);
	}

	@Override
	public List<List<String>> queryHrTableTypeList(Map<String, Object> entityMap) throws DataAccessException {
		List<List<String>> reList = new ArrayList<List<String>>();
		List<HrTableType> list = (List<HrTableType>) hrTableTypeMapper.query(entityMap);
		
		for (HrTableType type : list) {
			List<String> l = new ArrayList<String>();
			l.add(type.getType_tab_code());
			l.add(type.getType_tab_name());
			l.add(type.getTable_note() == null ? "" : type.getTable_note());
			reList.add(l);
		}
		
		return reList;
	}

	@Override
	public String addBatchHrTableType(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			hrTableTypeMapper.addBatch(entityList);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String importExcel(Map<String, Object> entityMap) throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("type_tab_code", map.get("type_tab_code").get(1));
					saveMap.put("type_tab_name", map.get("type_tab_name").get(1));
					saveMap.put("note", map.get("note").get(1));
					
					HrTableType type = hrTableTypeMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append("<br/>类别代码 "+map.get("type_tab_code")+" 已存在; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
			}
			if(saveList.size() > 0){
				hrTableTypeMapper.addBatch(saveList);
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

	
}
