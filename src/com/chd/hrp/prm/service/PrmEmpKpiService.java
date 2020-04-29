﻿
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmEmpKpi;
/**
 * 
 * @Description:
 * 0401 职工绩效考核指标表
 * @Table:
 * PRM_EMP_KPI
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmEmpKpiService {

	/**
	 * @Description 
	 * 添加0401 职工绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmEmpKpi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0401 职工绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmEmpKpi(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0401 职工绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmEmpKpi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0401 职工绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmEmpKpi(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0401 职工绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmEmpKpi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0401 职工绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmEmpKpi(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0401 职工绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmEmpKpi(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0401 职工绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmEmpKpi queryPrmEmpKpiByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象 职工绩效考核指标表 <BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmEmpKpiTree(Map<String,Object> entityMap)throws DataAccessException;
	
}
