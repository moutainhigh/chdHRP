﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.special;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.check.special.AssCheckSspecial;
/**
 * 
 * @Description:
 * 051101 仓库盘点单(专用设备)
 * @Table:
 * ASS_CHECK_S_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckSspecialMapper extends SqlMapper{

	AssCheckSspecial queryState(Map<String, Object> mapVo);
	
	
	List<AssCheckSspecial> queryAll(Map<String,Object> entityMap) throws DataAccessException;
	
	List<AssCheckSspecial> queryAll(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
