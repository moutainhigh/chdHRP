/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.guanLiReports;


import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 报废汇总 查询
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */

public interface AssScrapBySummryMapper extends SqlMapper{

	List<Map<String, Object>> queryAssScrapBySummryPrint(
			Map<String, Object> entityMap);
	 
	
	
}
