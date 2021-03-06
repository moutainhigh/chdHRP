﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.tongJiReports;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.guanLiReports.AssResourceSet; 
/**
 * 
 * @Description:
 * 050801 入库金额 根据库房  供应商  汇总
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface AssInMainSummaryMapper extends SqlMapper{     
	 
	/**
	 * @Description 
	 * 查询结果集050802 资产入库汇总 <BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAssInMainBySummary(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public List<Map<String,Object>> queryAssInMainBySummaryByIn(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String,Object>> queryAssInMainBySummaryByBack(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 资产入库分类汇总
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssInMainSummaryByType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAssInMainSummaryPrint(
			Map<String, Object> entityMap);
	
	public List<Map<String, Object>> queryAssInMainSummaryPrintByIn(
			Map<String, Object> entityMap);
	
	public List<Map<String, Object>> queryAssInMainSummaryPrintByBack(
			Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAssInMainSummaryByTypePrint(
			Map<String, Object> entityMap);
	 
	
	public List<Map<String, Object>> queryInMainSituation(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryInMainSituation(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryInMainSituationPrint(
			Map<String, Object> entityMap);
	
}
