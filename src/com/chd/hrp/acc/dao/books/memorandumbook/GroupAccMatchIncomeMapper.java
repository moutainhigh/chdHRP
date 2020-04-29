/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccMatchIncome;

/**
* @Title. @Description.
* 配套资金收入表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface GroupAccMatchIncomeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 添加AccMatchIncome
	 * @param AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addGroupAccMatchIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 批量添加AccMatchIncome
	 * @param  AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchGroupAccMatchIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 查询AccMatchIncome分页
	 * @param  entityMap RowBounds
	 * @return List<AccLederZcheck>
	 * @throws DataAccessException
	*/
	public List<AccMatchIncome> queryGroupAccMatchIncome(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 配套资金收入表<BR> 查询AccMatchIncome所有数据
	 * @param  entityMap
	 * @return List<AccLederZcheck>
	 * @throws DataAccessException
	*/
	public List<AccMatchIncome> queryGroupAccMatchIncome(Map<String,Object> entityMap) throws DataAccessException;
	
	//配套资金收入  打印
	public List<Map<String, Object>>  queryGroupAccMatchIncomePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 查询AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccMatchIncome queryGroupAccMatchIncomeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 删除AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteGroupAccMatchIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 批量删除AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchGroupAccMatchIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 配套资金收入表<BR> 更新AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateGroupAccMatchIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 批量更新AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchGroupAccMatchIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
}
