/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.hip.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hip.entity.HipSupRef;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HipSupRefService {

	/**
	 * 查询
	 * 
	 * */
	public String queryHipSupRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询
	 * 
	 * */
	public HipSupRef queryHipSupRefByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 添加
	 * 
	 * */
	public String addHipSupRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 添加
	 * 
	 * */
	public String addBatchHipSupRef(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 删除
	 * 
	 * */
	public String delHipSupRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 批量删除
	 * 
	 * */
	public String deleteBatchHipSupRef(List<Map<String, Object>> entityList) throws DataAccessException;

}
