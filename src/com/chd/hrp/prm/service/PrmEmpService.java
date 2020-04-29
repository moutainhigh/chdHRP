package com.chd.hrp.prm.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmEmp;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface PrmEmpService {
	
	/**
	 * 
	 */
	public String addPrmEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	public String initBatchPrmEmp(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchPrmEmp( List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * 
	 */
	public String queryPrmEmp(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryPrmEmpDict(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 
	 */
	public PrmEmp queryPrmEmpByCode(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 *  
	 */
	public PrmEmp queryPrmEmpByCodeEmp(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String updatePrmEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public String deleteBatchPrmEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public String updateBatchPrmEmp( List<Map<String, Object>> entityList)throws DataAccessException;
	
	public String importPrmEmp(Map<String,Object> entityMap)throws DataAccessException;
}
