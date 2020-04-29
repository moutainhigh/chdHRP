﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.depre.change;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.depre.change.AssDepreDetailSpecial;
/**
 * 
 * @Description:
 * 050806 资产累计折旧变动明细(专用设备)
 * @Table:
 * ASS_DEPRE_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDepreDetailSpecialMapper extends SqlMapper{
	List<AssDepreDetailSpecial> queryByChangeNo(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String,Object>> queryByChangeNoMap(Map<String,Object> entityMap)throws DataAccessException;
	
	int updateDepreBySource(Map<String,Object> entityMap)throws DataAccessException;
	
	int updateBatchDepreBySource(List<Map<String,Object>> entityMap)throws DataAccessException;
}
