﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assremould.house;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assremould.house.AssRemouldAHouse;
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
 

public interface AssRemouldAHouseMapper extends SqlMapper{

	Map<String,Object> collectAssBackHouse(Map<String, Object> entityMap);

	void updateAssRemouldAHouseConfirmState(List<Map<String, Object>> listVo);

	List<AssRemouldAHouse> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap);

	List<AssRemouldAHouse> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap, RowBounds rowBounds);
	
}
