﻿/** 
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
import com.chd.hrp.ass.entity.guanLiReports.AssResourceSet; 
/**
 * 
 * @Description:
 * 050802 资产资金来源报表查询
 * @Table:
 * ASS_RESOURCE_GENERAL_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssResourceSetMapper extends SqlMapper{
	 
	/**
	 * @Description 
	 * 查询结果集050802 资产资金来源匹配表 <BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssResourceSet> queryAssResourceSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAssResourceSetPrint(
			Map<String, Object> entityMap);
	 
	
}
