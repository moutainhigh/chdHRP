﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.dao.sell.in;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description: 050901 资产有偿调拨入库单主表(土地)
 * @Table: ASS_SELL_IN_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public interface AssSellInLandMapper extends SqlMapper {
	public int updateInMoney(Map<String, Object> entityMap) throws DataAccessException;

	public int updateAudit(List<Map<String, Object>> entityMap) throws DataAccessException;

	public int updateReAudit(List<Map<String, Object>> entityMap) throws DataAccessException;

	public int updateConfirm(List<Map<String, Object>> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAssSellInLandByAssInNo(Map<String, Object> map);

	public List<Map<String, Object>> queryAssSellInLandDetailByAssInNo(Map<String, Object> map);

	public List<Map<String, Object>> queryDept(Map<String, Object> mapVo);

	public List<String> queryAssSellInLandStates(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
}
