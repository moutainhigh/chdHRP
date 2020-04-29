﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assremould.house;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050805 资产改造竣工资金来源(专用设备)
 * @Table:
 * ASS_REMOULD_F_SOURCE_House
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldFsourceHouseMapper extends SqlMapper{

	List<Map<String, Object>> queryAssRemouldFSourceHouse(Map<String, Object> entityMap);

	List<Map<String, Object>> queryRemouldSourceByAssCardNo(Map<String, Object> detailVo);

	Map<String, Object> queryRemouldSourceByAssSourceId(Map map);

	int deleteBatchBySourceId(List<Map<String, Object>> listVo);

	
}
