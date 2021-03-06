﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.project.query;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 年度项目预算
 * @Table:
 * BUDG_PROJ_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgProjYearQueryMapper extends SqlMapper{
	
	/**
	 * 项目预算查询  报表(一)   专用  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<Map<String, Object>> queryBudgProjYearQuery(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 项目预算查询  报表(一)  专用 不分页
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryBudgProjYearQuery(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 项目预算查询  报表(二) 专用   不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryBudgProjYearQueryT(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 项目预算查询  报表(二) 专用   分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryBudgProjYearQueryT(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 项目预算查询  报表(一)(集团)  专用  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryBudgProjYearGroupQuery(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 项目预算查询  报表(一)(集团)  专用  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryBudgProjYearGroupQuery(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 项目预算查询  报表(二)(集团)  专用  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryBudgProjYearGroupQueryT(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 项目预算查询  报表(二)(集团)  专用  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryBudgProjYearGroupQueryT(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
