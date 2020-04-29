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
import com.chd.hrp.prm.entity.PrmGoalDetailKpi;
/**
 * 
 * @Description:
 * 0103 目标管理明细指标表
 * @Table:
 * PRM_GOAL_DETAIL_KPI
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmGoalDetailKpiService {

	/**
	 * @Description 
	 * 添加0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmGoalDetailKpi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmGoalDetailKpi(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmGoalDetailKpi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmGoalDetailKpi(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmGoalDetailKpi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmGoalDetailKpi(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmGoalDetailKpi(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmGoalDetailKpi queryPrmGoalDetailKpiByCode(Map<String,Object> entityMap)throws DataAccessException;
	
}
