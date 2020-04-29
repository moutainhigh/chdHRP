﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assdisposal.other;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 051001资产处置记录(其他固定资产)
 * @Table:
 * ASS_DISPOSAL_R_Other
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDisposalRecordOtherMapper extends SqlMapper{     
	public Map<String,Object>  collectAssDisposalRecordOther(Map<String, Object> entityMap)throws DataAccessException;

	public int updateBatchConfirm(List<Map<String, Object>> listVo)throws DataAccessException;
	
	public int updateBatchUnConfirm(List<Map<String, Object>> listVo)throws DataAccessException;
	
	/**
	 * 资产处置记录变动主表模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssDisRecordOtherPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	 
	/**
	 * 资产处置记录变动主表批量模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssDisRecordOtherPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产处置记录变动明细表模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssDisRecordOtherPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询数据状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryState(Map<String,Object> entityMap) throws DataAccessException;

	public void addAssPlanDeptImportBid(Map<String, Object> planApplyMapvo);

	public void deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
	

}
