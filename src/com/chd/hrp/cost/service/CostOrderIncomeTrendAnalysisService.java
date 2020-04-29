/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 科室收入数据明细表_开单收入趋势分析<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostOrderIncomeTrendAnalysisService {

	/**
	 * @Description 
	 * 科室收入数据明细表_开单收入趋势分析<BR> 查询CostOrderIncomeTrend分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostOrderIncomeTrend(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本趋势分析
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostTrendAnalysis(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询表头公用方法
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryOrderIncomeTitle(Map<String,Object> entityMap) throws DataAccessException;
	
}