/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.proj;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 财政项目收入预算 /财政项目收入预算执行 
 * @Table:
 * BUDG_PROJ_YEAR 
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgFinancProjMapper extends SqlMapper{
	
	/**
	 * 财政项目收入预算 查询   不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgFinancProjIncome( Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 财政项目收入预算  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgFinancProjIncome( Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 财政项目收入预算执行 查询 不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgFinancProjIncomeExe(Map<String, Object> entityMap);
	/**
	 * 财政项目收入预算执行  查询   分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgFinancProjIncomeExe(Map<String, Object> entityMap, RowBounds rowBounds);

	
	
}
