﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.hosyblimit;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医院医保额度控制
 * @Table:
 * BUDG_HOS_YB_LIMIT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgHosYbLimitService extends SqlService {
	
	/**
	 * 生成时 查询生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>>  queryBudgInsuranceCodeData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 医保类型是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryInsuranceCodeExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键 查询 医院医保额度控制数据是否存在
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> item) throws DataAccessException ;
	
	/**
	 * 医保类型下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgYBType(Map<String, Object> mapVo) throws DataAccessException ;
	
	/**
	 * 保存
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;

}