﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.back;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.back.AssBackDetailHouse;
/**
 * 
 * @Description:
 * 050701 资产退货单明细(房屋及建筑物)
 * @Table:
 * ASS_BACK_DETAIL_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackDetailHouseService extends SqlService {
	public List<AssBackDetailHouse> queryByAssBackNo(Map<String,Object> entityMap)throws DataAccessException;
}
