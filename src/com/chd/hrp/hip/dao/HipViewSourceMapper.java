/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.hip.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hip.entity.HipViewSource;

public interface HipViewSourceMapper extends SqlMapper {

	/**
	 * 添加
	 * 
	 * */
	public int addHipViewSource(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除
	 * 
	 * */
	public int deleteHipViewSource(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 修改
	 * 
	 * */
	public int updateHipViewSource(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据编码查询
	 * 
	 * */
	public HipViewSource queryHipViewSourceByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 插叙患者类别不分页
	 * 
	 * */
	public List<HipViewSource> queryHipViewSource(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询患者类别分页
	 * 
	 * */
	public List<HipViewSource> queryHipViewSource(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}
