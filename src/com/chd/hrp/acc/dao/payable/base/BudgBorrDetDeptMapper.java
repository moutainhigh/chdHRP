﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.dao.payable.base;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 借款明细（科室）
 * @Table:
 * BUDG_BORR_DET_DEPT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgBorrDetDeptMapper extends SqlMapper{
	
	public int updateF(Map<String,Object> entityMap)throws DataAccessException;
}
