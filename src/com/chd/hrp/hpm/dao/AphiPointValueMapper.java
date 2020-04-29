package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiPointValue;

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

public interface AphiPointValueMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addPointValue(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiPointValue> queryPointValue(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiPointValue> queryPointValue(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiPointValue queryPointValueByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deletePointValue(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updatePointValue(Map<String, Object> entityMap) throws DataAccessException;
}
