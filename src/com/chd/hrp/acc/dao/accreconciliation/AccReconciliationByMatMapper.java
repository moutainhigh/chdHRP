/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.accreconciliation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 科目性质<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

public interface AccReconciliationByMatMapper extends SqlMapper{
	
	public List<Map<String,Object>> queryAccReconciliationByMat(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}