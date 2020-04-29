/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.accreconciliation;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 部门字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccReconciliationByMatService {
	
	public String queryAccReconciliationByMat(Map<String,Object> entityMap) throws DataAccessException;
	
}