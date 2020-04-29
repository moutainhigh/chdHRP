﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.dict;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.dict.AssLandSourceDict;
/**
 * 
 * @Description:
 * 土地来源
 * @Table:
 * ASS_LAND_SOURCE_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssLandSourceDictService extends SqlService {
	/**
	 * 导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String readAssLandSourceDictFiles(Map<String, Object> entityMap)throws DataAccessException;

	public AssLandSourceDict queryByName(Map<String, Object> entityMap)throws DataAccessException;
}
