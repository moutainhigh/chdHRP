﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.inassets;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 051101 仓库盘盈登记明细_无形资产
 * @Table:
 * ASS_CHECK_SP_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckSpDetailInassetsMapper extends SqlMapper{

	Map<String, Object> queryAssInSpecialPrintTemlateByMains(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssInSpecialPrintTemlateByDetails(Map<String, Object> entityMap);
	
}
