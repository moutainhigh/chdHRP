﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.general;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050701 资产盘盈入库主表(专用设备)
 * @Table:
 * ASS_CHK_IN_MAIN_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChkInMainGeneralMapper extends SqlMapper{

	public int updateInMoney(Map<String, Object> entityMap)throws DataAccessException;

	public int updateConfirmChkInGeneral(List<Map<String, Object>> listVo)throws DataAccessException;
	
	/**
	 * 盘盈入库主表模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssChkInMainGeneralPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	 
	/**
	 * 盘盈入库主表批量模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssChkInMainGeneralPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 盘盈入库明细表模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssChkInMainGeneralPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询数据状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryState(Map<String,Object> entityMap) throws DataAccessException;
	
	
}
