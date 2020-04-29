﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assremould.inassets;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050805 资产改造记录(无形资产)
 * @Table:
 * ASS_REMOULD_R_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldRinassetsMapper extends SqlMapper{

	void updateConfirmAssRemouldRinassets(List<Map<String, Object>> listVo);

	void collectAssRemoildRinassets(Map<String, Object> entityMap);

	void addAssPlanDeptImportBid(Map<String, Object> planApplyMapvo);

	void deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);
	
}
