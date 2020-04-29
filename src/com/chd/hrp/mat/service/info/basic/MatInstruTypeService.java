/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.info.basic;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.mat.entity.MatInstruType;
/**
 * 
 * @Description: 医疗器械分类分类字典
 * @Table: MAT_INSTRU_TYPE
 * @Author: weixiaofeng
 * @Version: 1.0
 */

public interface MatInstruTypeService {
	
	/**
	 * @Description 查询MatInstruType菜单
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryMatInstruTypeByTree(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMatInstruType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMatInstruType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMatInstruType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMatInstruType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMatInstruType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMatInstruType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String saveMatInstruType(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInstruType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象医疗器械分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return MatInstruType
	 * @throws DataAccessException
	*/
	public MatInstruType queryMatInstruTypeById(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取医疗器械分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInstruType
	 * @throws DataAccessException
	*/
	public MatInstruType queryMatInstruTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取医疗器械分类字典<BR> 
	 * @param  entityMap<BR>
	 * 参数为要检索的字段
	 * @return MatInstruType
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatInstruTypeByCodeName(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String, Object> importData(Map<String,Object> entityMap)throws DataAccessException;
}
