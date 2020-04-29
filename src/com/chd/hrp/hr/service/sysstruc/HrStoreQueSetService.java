﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.sysstruc;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_STORE_QUE_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrStoreQueSetService extends SqlService {
	String saveHrStoreQueSet(Map<String, Object> entityMap) throws DataAccessException;

	//批量同步
	String addHrStoreQueSetBatch(Map<String, Object> mapVo);
}
