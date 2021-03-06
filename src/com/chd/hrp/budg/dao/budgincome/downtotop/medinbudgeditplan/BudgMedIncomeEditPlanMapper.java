﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.downtotop.medinbudgeditplan;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Table:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedIncomeEditPlanMapper extends SqlMapper{

	public Integer queryBudgMedIncomeEditPlanByCode(Map<String, Object> mapVo);
	
	/**
	 * 生成数据
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void addGenerateBatch(Map<String, Object> entityMap) throws DataAccessException;
	
}
