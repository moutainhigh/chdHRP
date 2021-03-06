﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.resource;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.resource.AssResourceSpecial;
/**
 * 
 * @Description:
 * 资产资金来源匹配表_专业设备
 * @Table:
 * ASS_RESOURCE_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssResourceSpecialMapper extends SqlMapper{
	
	public List<AssResourceSpecial> queryByAssCardNo(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryByAssCardNoMap(Map<String,Object> entityMap) throws DataAccessException;
	
	public int updateBatchByChange(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public int addBatchByChange(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public int updateBatchByDepre(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public int updateBatchByPay(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	
	public int updateBatchByBack(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryByAssCardIn(Map<String,Object> entityMap) throws DataAccessException;
}
