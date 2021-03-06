﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.storage.query;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Description:
 * 入库明细查询
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface MatSupInStoreService{


	String queryMatSupInStore(Map<String, Object> mapVo)throws DataAccessException;
	
	List<Map<String, Object>> queryMatSupInStorePrint(Map<String, Object> mapVo)throws DataAccessException;
	
	String queryMatSupInStoreDetail(Map<String, Object> mapVo)throws DataAccessException;

	List<Map<String, Object>> queryMatSupInStoreDetailPrint(
			Map<String, Object> entityMap) throws DataAccessException;
}
