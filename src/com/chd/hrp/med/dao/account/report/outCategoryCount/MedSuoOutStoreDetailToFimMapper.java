package com.chd.hrp.med.dao.account.report.outCategoryCount;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 出库分类统计:科室统计-查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedSuoOutStoreDetailToFimMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 科室统计出库金额 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedSuoOutStoreDetailToFim(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室统计出库金额 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedSuoOutStoreDetailToFim(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询会计期间
	 * 当前所查询的年月是否已结帐
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryClosingYearMonth(Map<String, Object> entityMap);
	
	 
}
