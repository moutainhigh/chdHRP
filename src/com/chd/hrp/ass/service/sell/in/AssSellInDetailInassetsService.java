﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.sell.in;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.sell.in.AssSellInDetailInassets;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨入库明细(无形资产)
 * @Table:
 * ASS_SELL_IN_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssSellInDetailInassetsService extends SqlService {
	public List<AssSellInDetailInassets> queryByAssInNo(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryByInit(Map<String, Object> entityMap) throws DataAccessException;
}
