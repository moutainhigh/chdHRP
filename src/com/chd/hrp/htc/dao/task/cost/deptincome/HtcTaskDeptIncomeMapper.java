package com.chd.hrp.htc.dao.task.cost.deptincome;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.cost.deptincome.HtcTaskDeptIncome;
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

public interface HtcTaskDeptIncomeMapper extends SqlMapper{
	
	/**
	 * 
	 */
	public int addHtcTaskDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int addBatchHtcTaskDeptIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	

	/**
	 * 
	 */
	public List<HtcTaskDeptIncome> queryHtcTaskDeptIncome(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 
	 */
	public List<HtcTaskDeptIncome> queryHtcTaskDeptIncome(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 */
	public HtcTaskDeptIncome queryHtcTaskDeptIncomeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int deleteHtcTaskDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
    /**
     * 
     */
    public int deleteBatchHtcTaskDeptIncome(List<Map<String,Object>> list)throws DataAccessException;
    
	/**
	 * 
	 */
	public int updateHtcTaskDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
}
