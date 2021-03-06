package com.chd.hrp.pac.dao.fkht.change;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface PactSourceFKHTCMapper extends SqlMapper{
	
	/**
	 * 根据 合同编号 批量删除
	 * @param listMap
	 * @return
	 */
	public int deleteBatchByPactCode(List<Map<String, Object>> listMap) throws DataAccessException;
	
	/**
	 * 根据 合同编号及明细id 批量删除
	 * @param sourceList
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatchByDet(List<Map<String, Object>> sourceList) throws DataAccessException;


}
