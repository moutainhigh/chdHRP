﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.check.general;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 051101 科室盘盈登记明细_医用设备
 * @Table:
 * ASS_CHECK_DP_DETAIL_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckDpDetailGeneralService extends SqlService {

	Map<String, Object> queryAssInSpecialByPrintTemlatePrints(Map<String, Object> entityMap) throws DataAccessException;

}
