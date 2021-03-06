﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.allot.in;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨入库单主表（房屋及建筑物）
 * @Table:
 * ASS_ALLOT_IN_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssAllotInHouseMapper extends SqlMapper{
	public int updateInMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryAssAllotInHouseByAssInNo(Map<String, Object> map);

	public List<Map<String, Object>> queryAssAllotInHouseDetailByAssInNo(Map<String, Object> map);

	public List<Map<String, Object>> queryDept(Map<String, Object> mapVo);

	public List<String> queryAssAllotInHouseStates(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);

	
}
