﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.requrie;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatRequireDetail;

/**
 * 
 * @Description:
 * 04202 仓库需求计划明细表
 * @Table:
 * MAT_REQUIRE_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatStoreRequireDetailMapper extends SqlMapper{
	
	/**
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDetailByID(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询明细数据
	 * @param entityMap
	 * @param rowBounds 
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryDetailByCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	// 保存&提交 查找明细数据
	public List<Map<String, Object>> queryDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 获得明细自增ID
	 * @return
	 * @throws DataAccessException
	 */
	public Long getStoreNextDetailReqId() throws DataAccessException;
	
	
	
	
}
