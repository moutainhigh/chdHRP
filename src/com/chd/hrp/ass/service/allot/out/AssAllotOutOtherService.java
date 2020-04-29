﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.allot.out;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨出库单主表(其他固定资产)
 * @Table:
 * ASS_ALLOT_OUT_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssAllotOutOtherService extends SqlService {
	public String updateAllotOutConfirm(List<Map<String, Object>> entityMap,List<Map<String, Object>> cardEntityMap)throws DataAccessException;

	public String queryByAllotInImport(Map<String, Object> entityMap)throws DataAccessException;
	
	public String queryByAllotInImportView(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 其他固定资产 资产调剂出口库 出库单状态查询（打印校验数据用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssAllotOutOtherState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 其他固定资产 资产调剂 出库  打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssAllotOutOtherByPrintTemlatePrint(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDetails(Map<String, Object> page);
}
