﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.tran.store;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050901 资产转移主表(仓库到仓库)_其他固定资产
 * @Table:
 * ASS_TRAN_STORE_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssTranStoreOtherMapper extends SqlMapper{
	public int updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;

	public int updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * 移库主表批量模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssTranStoreOtherPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	 
	/**
	 * 移库主表模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssTranStoreOtherPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 移库明细表模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssTranStoreOtherPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询数据状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryState(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
	
}
