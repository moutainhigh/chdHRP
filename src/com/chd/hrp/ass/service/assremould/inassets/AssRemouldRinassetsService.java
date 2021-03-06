﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.assremould.inassets;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
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
 

public interface AssRemouldRinassetsService extends SqlService {

	String queryAssRemouldSourceInassets(Map<String, Object> mapVo);

	String queryAssRemouldRdetailinassets(Map<String, Object> mapVo);

	String saveAssRemouldRSourceinassets(Map<String, Object> mapVo);


	String updateAssRemouldAinassetsConfirmState(List<Map<String, Object>> listVo,
			List<Map<String, Object>> listCardVo);

	String deleteAssSourceInassets(List<Map<String, Object>> listVo);

	String initAssCheckinassets(Map<String, Object> mapVo);

	String deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

}
