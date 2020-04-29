/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.guanLiReports;


import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 051101 资产月报表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssDepreciationSortService extends SqlService{

	String queryAssDepreciationSort(Map<String, Object> page);
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssDepreciationSortPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	
}
