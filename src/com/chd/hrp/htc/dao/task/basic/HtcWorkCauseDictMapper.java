package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.basic.HtcWorkCauseDict;
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

public interface HtcWorkCauseDictMapper extends SqlMapper{
	
	/**
	 * 
	 */
	public int addHtcWorkCauseDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int addBatchHtcWorkCauseDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public List<HtcWorkCauseDict> queryHtcWorkCauseDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcWorkCauseDict> queryHtcWorkCauseDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 */
	public HtcWorkCauseDict queryHtcWorkCauseDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int deleteHtcWorkCauseDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcWorkCauseDict(List<Map<String, Object>> entityList)throws DataAccessException;

	public int updateHtcWorkCauseDict(Map<String,Object> entityMap)throws DataAccessException;
	
}
