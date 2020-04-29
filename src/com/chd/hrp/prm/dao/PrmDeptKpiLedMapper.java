package com.chd.hrp.prm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.prm.entity.PrmDeptKpiLed;

public interface PrmDeptKpiLedMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0503 指示灯<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDeptKpiLed(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0503 指示灯<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmDeptKpiLed(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0503 指示灯<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptKpiLed(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0503 指示灯<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiLed
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDeptKpiLed(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0503 指示灯<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmDeptKpiLed(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0503 指示灯<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmDeptKpiLed(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0503 指示灯<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiLed> queryPrmDeptKpiLed(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0503 指示灯<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiLed> queryPrmDeptKpiLed(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0503 指示灯<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiLed
	 * @throws DataAccessException
	*/
	public PrmDeptKpiLed queryPrmDeptKpiLedByCode(Map<String,Object> entityMap)throws DataAccessException;
}
