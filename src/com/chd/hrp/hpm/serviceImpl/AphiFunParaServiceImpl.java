﻿
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson; 
import com.chd.hrp.hpm.dao.AphiFunParaMapper;
import com.chd.hrp.hpm.dao.AphiFunParaMethodMapper;
import com.chd.hrp.hpm.entity.AphiFunPara;
import com.chd.hrp.hpm.service.AphiFunParaService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 9911 绩效函数参数表
 * @Table:
 * PRM_FUN_PARA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("aphiFunParaService")
public class AphiFunParaServiceImpl implements AphiFunParaService {

	private static Logger logger = Logger.getLogger(AphiFunParaServiceImpl.class);
	//引入DAO操作
	@Resource(name = "aphiFunParaMapper")
	private final AphiFunParaMapper aphiFunParaMapper = null;
	
	//引入DAO操作
		@Resource(name = "aphiFunParaMethodMapper")
		private final AphiFunParaMethodMapper aphiFunParaMethodMapper = null;
	
	/**
	 * @Description 
	 * 添加9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmFunPara(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9911 绩效函数参数表
		AphiFunPara prmFunPara = queryPrmFunParaByCode(entityMap);

		if (prmFunPara != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = aphiFunParaMapper.addPrmFunPara(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmFunPara\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9911 绩效函数参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmFunPara(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			aphiFunParaMapper.addBatchPrmFunPara(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmFunPara\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmFunPara(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = aphiFunParaMapper.updatePrmFunPara(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmFunPara\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新9911 绩效函数参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmFunPara(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  aphiFunParaMapper.updateBatchPrmFunPara(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmFunPara\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmFunPara(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = aphiFunParaMapper.deletePrmFunPara(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmFunPara\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9911 绩效函数参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmFunPara(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			aphiFunParaMapper.deleteBatchPrmFunPara(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmFunPara\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmFunPara(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AphiFunPara> list = aphiFunParaMapper.queryPrmFunPara(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AphiFunPara> list = aphiFunParaMapper.queryPrmFunPara(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AphiFunPara queryPrmFunParaByCode(Map<String,Object> entityMap)throws DataAccessException{
		return aphiFunParaMapper.queryPrmFunParaByCode(entityMap);
	}
	
	@Override
    public String queryComTypePara(Map<String, Object> entityMap) throws DataAccessException {
	    
		List<AphiFunPara> list = aphiFunParaMapper.queryComTypePara(entityMap); 
		 
		if (list.size() >0){
	 
			return ChdJson.toJson(list);

		}
		else { 
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
		}
		

    }
	@Override
	public String queryComTypeParaByDept(Map<String, Object> entityMap) throws DataAccessException {
		List<AphiFunPara> list = aphiFunParaMapper.queryComTypeParaByDept(entityMap); 
		 
		if (list.size() >0){
	 
			return ChdJson.toJson(list);

		}
		else { 
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
		}
	}
	@Override
	public String queryComTypeParaByEmp(Map<String, Object> entityMap) throws DataAccessException {
		List<AphiFunPara> list = aphiFunParaMapper.queryComTypeParaByEmp(entityMap); 
		 
		if (list.size() >0){
	 
			return ChdJson.toJson(list);

		}
		else { 
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
		}
	}
	@Override
	public String queryComTypeParaByHos(Map<String, Object> entityMap) throws DataAccessException {
		List<AphiFunPara> list = aphiFunParaMapper.queryComTypeParaByHos(entityMap); 
		 
		if (list.size() >0){
	 
			return ChdJson.toJson(list);

		}
		else { 
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
		}
	}
	@Override
	public String queryPrmFunParaByFunCode(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AphiFunPara> list = aphiFunParaMapper.queryPrmFunParaByFunCode(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AphiFunPara> list = aphiFunParaMapper.queryPrmFunParaByFunCode(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
    
	
}
