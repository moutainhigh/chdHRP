package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptEmpData;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiDeptEmpDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addDeptEmpData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量添加
	 */
	public int addBatchDeptEmpData(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 
	 */
	public int initDeptEmpData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptEmpData> queryDeptEmpData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptEmpData> queryDeptEmpData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryDeptEmpDataPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptEmpData> getEmp(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int getEmpCollect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptEmpData queryDeptEmpDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteDeptEmpData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int collectDeptEmpData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateDeptEmpData(Map<String, Object> entityMap) throws DataAccessException;
}