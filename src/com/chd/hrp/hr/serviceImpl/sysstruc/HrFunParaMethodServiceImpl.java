﻿
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hr.serviceImpl.sysstruc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.sysstruc.HrFunParaMethodMapper;
import com.chd.hrp.hr.entity.sysstruc.HrFunParaMethod;
import com.chd.hrp.hr.service.sysstruc.HrFunParaMethodService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 9912 绩效函数参数取值表
 * @Table:
 * PRM_FUN_PARA_METHOD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("hrFunParaMethodService")
public class HrFunParaMethodServiceImpl implements HrFunParaMethodService {

	private static Logger logger = Logger.getLogger(HrFunParaMethodServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrFunParaMethodMapper")
	private final HrFunParaMethodMapper hrFunParaMethodMapper = null;
    
	/**
	 * @Description 
	 * 添加9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmFunParaMethod(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9912 绩效函数参数取值表
		HrFunParaMethod prmFunParaMethod = queryPrmFunParaMethodByCode(entityMap);

		if (prmFunParaMethod != null) {
                 if(prmFunParaMethod.getPara_code().equals(entityMap.get("para_code"))){
                	 return "{\"error\":\"参数代码重复,请重新添加.\"}";
                 }else{
                	 return "{\"error\":\"参数名称重复,请重新添加.\"}";
                 }
		}
		
		try {
			
			int state = hrFunParaMethodMapper.addPrmFunParaMethod(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmFunParaMethod\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9912 绩效函数参数取值表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmFunParaMethod(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			hrFunParaMethodMapper.addBatchPrmFunParaMethod(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmFunParaMethod\"}";

		}
		
	}
	
	/**
	 * @Description 更新9912 绩效函数参数取值表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmFunParaMethod(Map<String, Object> entityMap) throws DataAccessException {
		try {
			int count = hrFunParaMethodMapper.queryExistsByName(entityMap);
			if (count > 0) {
				return "{\"error\":\"参数名称被占用.\",\"state\":\"false\"}";
			}
			
			hrFunParaMethodMapper.updatePrmFunParaMethod(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updatePrmFunParaMethod\"}";
		}
	}
	/**
	 * @Description 
	 * 批量更新9912 绩效函数参数取值表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmFunParaMethod(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  hrFunParaMethodMapper.updateBatchPrmFunParaMethod(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmFunParaMethod\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmFunParaMethod(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = hrFunParaMethodMapper.deletePrmFunParaMethod(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmFunParaMethod\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9912 绩效函数参数取值表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmFunParaMethod(List<HrFunParaMethod> entityList)throws DataAccessException{
		
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(HrFunParaMethod hr : entityList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", hr.getGroup_id());
				map.put("hos_id", hr.getHos_id());
				map.put("copy_code", hr.getCopy_code());
				map.put("para_code", hr.getPara_code());
				list.add(map);
			}
			
 			hrFunParaMethodMapper.deleteBatchPrmFunParaMethod(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmFunParaMethod\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmFunParaMethod(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HrFunParaMethod> list = hrFunParaMethodMapper.queryPrmFunParaMethod(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HrFunParaMethod> list = hrFunParaMethodMapper.queryPrmFunParaMethod(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public HrFunParaMethod queryPrmFunParaMethodByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return hrFunParaMethodMapper.queryByCode(entityMap);
		
	}
	/**
	 * 应用模式
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmFunParaByDict(Map<String, Object> map) throws DataAccessException {
		
		HrFunParaMethod pfpm= hrFunParaMethodMapper.queryPrmFunParaMethodByCode(map);
		
		if (pfpm!=null){
			
			map.put("sql", pfpm.getPara_sql().replace("{", "#{"));
			 
			
			return JSON.toJSONString(hrFunParaMethodMapper.queryPrmFunParaByDict(map));
		}
		else {
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
			
		}
		
		
	}
	 
}
