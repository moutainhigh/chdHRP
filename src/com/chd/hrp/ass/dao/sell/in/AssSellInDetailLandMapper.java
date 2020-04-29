﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.dao.sell.in;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.sell.in.AssSellInDetailLand;

/**
 * 
 * @Description: 050901 资产有偿调拨入库明细(土地)
 * @Table: ASS_SELL_IN_DETAIL_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public interface AssSellInDetailLandMapper extends SqlMapper {
	public List<Map<String, Object>> queryByInit(Map<String, Object> entityMap) throws DataAccessException;

	public List<AssSellInDetailLand> queryByAssInNo(Map<String, Object> entityMap) throws DataAccessException;
}
