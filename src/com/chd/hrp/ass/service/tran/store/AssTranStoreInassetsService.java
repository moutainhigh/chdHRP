﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.tran.store;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.tran.store.AssTranStoreDetailInassets;
/**
 * 
 * @Description:
 * 050901 资产转移主表(仓库到仓库)_其他无形资产
 * @Table:
 * ASS_TRAN_STORE_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssTranStoreInassetsService extends SqlService {
	public String updateConfirmTranStoreInassets(List<Map<String, Object>> entityMap,List<Map<String, Object>> cardEntityMap)throws DataAccessException;
	
	public List<AssTranStoreDetailInassets> queryByTranNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 新版打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> assTranStoreInassetsByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询所有未确认数据单号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String>  queryState(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDetails(Map<String, Object> page);
	
	
}