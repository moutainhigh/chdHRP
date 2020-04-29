﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.check.inassets;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 051101 科室盘盈登记明细_无形资产
 * @Table:
 * ASS_CHECK_DP_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckDpDetailInassetsService extends SqlService {

	Map<String, Object> queryAssInSpecialByPrintTemlatePrints(Map<String, Object> entityMap) throws DataAccessException;

}