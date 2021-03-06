/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
  
package com.chd.hrp.acc.dao.payable.reimbursemt;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.payable.BudgPaymentApplyDet;
/**
 * 
 * @Description:
 * 借款明细
 * @Table:
 * BUDG_BORR_BEGAIN_DET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgPaymentApplyDetMapper extends SqlMapper{
	

	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<BudgPaymentApplyDet> queryForUpdate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<BudgPaymentApplyDet> queryForUpdate(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public List<Map<String,Object>> queryPaymentApplyDetByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	public Map<String,Object> queryPaymentApplyDetByPrintTemlateMain(Map<String,Object> entityMap) throws DataAccessException;
	
	public int updateAmount(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryPaymentApplyDetail(Map<String,Object> entityMap) throws DataAccessException;

	public List<HashMap<String, Object>> queryForUpdateToMap(Map<String, Object> entityMap);

	public List<HashMap<String, Object>> queryForUpdateToMap(Map<String, Object> entityMap, RowBounds rowBounds);
}
