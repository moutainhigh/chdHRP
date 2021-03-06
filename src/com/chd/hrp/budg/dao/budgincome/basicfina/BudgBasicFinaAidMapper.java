﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.basicfina;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 财政基本补助收入预算 
 * @Description:
 * @Table:
 * BUDG_BASIC_FINA_AID
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgBasicFinaAidMapper extends SqlMapper{
	
	/**
	 * 判断收入预算科目是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjCodeExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 收入预算科目下拉框（添加时用）
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgIncomeSubj(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExist(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 审核、消审
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int auditOrUnAudit(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据 所传参数 查询数据状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryBudgBasicFinaAidState(Map<String, Object> entityMap) throws DataAccessException;
	
	
}
