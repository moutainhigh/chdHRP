﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.assremould.house;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REMOULD_A_House
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldAHouseService extends SqlService {

	String queryAssRemouldADict(Map<String, Object> page);


	String updateAssRemouldAhouseConfirmState(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo);


	String queryAssApplyDeptByPlanDept(Map<String, Object> page);

}
