package com.chd.hrp.ass.dao.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2016年6月29日 下午8:38:29
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: BELL
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public interface AssBaseMapper extends SqlMapper {

	/**
	 * @Description 获取编码规则<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public Map<String, Object> getHosRules(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取 资产结账相关信息<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public List<?> queryAssYearMonth(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 更新资产结账状态<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public int updateAssYearMonth(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String, Object> queryAssYearMonthByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询科室集合<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public List<?> queryStores(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 查询科室集合<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public List<?> queryDepts(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 查询科室集合<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public List<?> queryCheckPlanFin(Map<String, Object> entityMap) throws DataAccessException;

}
