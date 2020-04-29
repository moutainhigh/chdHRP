﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.general;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 051001资产盘亏记录(一般设备)
 * @Table:
 * ASS_CHK_R_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChkRGeneralMapper extends SqlMapper{
	public Map<String,Object>  collectAssChkRGeneral(Map<String, Object> entityMap)throws DataAccessException;

	public int updateBatchConfirm(List<Map<String, Object>> listVo)throws DataAccessException;

	public void addAssPlanDeptImportBid(Map<String, Object> planApplyMapvo)throws DataAccessException;

	public void deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);
}
