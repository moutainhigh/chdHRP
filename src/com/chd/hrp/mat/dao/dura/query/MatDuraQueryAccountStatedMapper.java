package com.chd.hrp.mat.dao.dura.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description: 耐用品明细账
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatDuraQueryAccountStatedMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatDuraQueryAccountStated(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分页查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatDuraQueryAccountStated(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
}
