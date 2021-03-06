package com.chd.hrp.htc.dao.relative.cost.deptincome;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.relative.cost.deptincome.HtcRelativeDeptIncome;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com 
 * @Version: 1.0
 */

public interface HtcRelativeDeptIncomeMapper extends SqlMapper{
	
	/**
	 * 
	 */
	public int addHtcRelativeDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int addBatchHtcRelativeDeptIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	

	/**
	 * 
	 */
	public List<HtcRelativeDeptIncome> queryHtcRelativeDeptIncome(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 
	 */
	public List<HtcRelativeDeptIncome> queryHtcRelativeDeptIncome(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 */
	public HtcRelativeDeptIncome queryHtcRelativeDeptIncomeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int deleteHtcRelativeDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
    /**
     * 
     */
    public int deleteBatchHtcRelativeDeptIncome(List<Map<String,Object>> list)throws DataAccessException;
    
	/**
	 * 
	 */
	public int updateHtcRelativeDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
}
