﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.tran.dept;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.tran.dept.AssTranDeptDetailInassets;
/**
 * 
 * @Description:
 * 050901 资产转移主表(科室到科室)_无形资产
 * @Table:
 * ASS_TRAN_DEPT_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssTranDeptInassetsService extends SqlService {
	public String updateConfirmTranDeptInassets(List<Map<String, Object>> entityMap,List<Map<String, Object>> cardEntityMap)throws DataAccessException;
	
	public List<AssTranDeptDetailInassets> queryByTranNo(Map<String, Object> entityMap);
	
	/**
	 * 新版打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> assTranDeptInassetsByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询所有未确认数据单号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String>  queryState(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDetails(Map<String, Object> page);
	
	
}