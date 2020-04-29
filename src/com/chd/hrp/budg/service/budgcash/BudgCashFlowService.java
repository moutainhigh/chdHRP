﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgcash;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 现金流量预算
 * @Table:
 * BUDG_CASH_FLOW
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgCashFlowService extends SqlService {

	public String collectBudgCashFlow(Map<String, Object> mapVo) throws DataAccessException;

}
