﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.in.rest;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050701 资产其他入库主表(其他无形资产)
 * @Table:
 * ASS_IN_REST_MAIN_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssInRestMainInassetsMapper extends SqlMapper{
	public int updateInMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;

	/**
	 * 打印查询 其他无形资产 其它入库   入库主表数据(主页面) 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssInRestInassetsPrintTemlateByMainBatch(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 打印查询 其他无形资产 其它入库   入库主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssInRestInassetsPrintTemlateByDetail(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 打印查询 其他无形资产 其它入库   入库主表数据(修改页面) 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssInRestInassetsPrintTemlateByMain(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 其他无形资产 其它入库  入库单状态查询 （打印时校验数据专用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssInRestInassetsState(Map<String, Object> mapVo)throws DataAccessException;

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
}
