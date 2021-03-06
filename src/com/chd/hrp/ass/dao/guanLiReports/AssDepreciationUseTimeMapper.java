/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.guanLiReports;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.guanLiReports.AssDepreciationUseTime;
/**
 * 
 * @Description:
 * 051101 资产月报表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssDepreciationUseTimeMapper extends SqlMapper{


	public List<AssDepreciationUseTime> queryUseTime(Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssDepreciationUseTimePrint(
			Map<String, Object> entityMap);
	
	
}
