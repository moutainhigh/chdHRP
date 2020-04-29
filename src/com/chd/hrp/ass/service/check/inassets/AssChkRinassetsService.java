﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.check.inassets;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 051001资产盘亏记录(无形资产)
 * @Table:
 * ASS_CHK_R_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChkRinassetsService extends SqlService {
	String updateConfirmChkRinassets(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo)throws DataAccessException;

	String initAssCheckInassets(Map<String, Object> mapVo)throws DataAccessException;

	String deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

}
