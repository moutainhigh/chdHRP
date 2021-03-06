/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostIassetArrt;

/**
* @Title. @Description.
* 成本_无形资产字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIassetArrtService {

	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 添加CostIassetArrt
	 * @param CostIassetArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostIassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 批量添加CostIassetArrt
	 * @param  CostIassetArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostIassetArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 查询CostIassetArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostIassetArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 查询CostIassetArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostIassetArrt queryCostIassetArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 删除CostIassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostIassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 批量删除CostIassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostIassetArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 更新CostIassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostIassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 批量更新CostIassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostIassetArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String syncCostIassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryCostIassetArrtPrint(Map<String,Object> entityMap) throws DataAccessException;
}
