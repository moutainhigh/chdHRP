﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.special;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 051101 盘点任务单(专用设备)
 * @Table:
 * ASS_CHECK_PLAN_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckPlanSpecialMapper extends SqlMapper{
	/**
	 * @Description 
	 * 生成数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addGenerateStore(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addGenerateDept(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addGenerateStoreDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addGenerateDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 盘点单树形展示
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryByTree(Map<String, Object> entityMap) throws DataAccessException;
	public Integer queryCheckS(Map<String, Object> map) throws DataAccessException;
	public Integer queryCheckD(Map<String, Object> map)throws DataAccessException;
	public List<Map<String, Object>> queryStores(Map<String, Object> entityMap)throws DataAccessException;
	public List<Map<String, Object>> queryDepts(Map<String, Object> entityMap)throws DataAccessException;
}
