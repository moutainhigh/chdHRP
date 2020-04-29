﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.bill;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.bill.AssBillDetail;
import com.chd.hrp.ass.entity.prepay.AssPrePayDetail;
/**
 * 
 * @Description:
 * 发票明细表
 * @Table:
 * ASS_BILL_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBillDetailMapper extends SqlMapper{
	List<Map<String, Object>> queryByBillNo(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryByAll(Map<String,Object> entityMap)throws DataAccessException;
	
	int updateBillMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	int queryAssCardExists(Map<String,Object> entityMap)throws DataAccessException;

	//List<AssBillDetail> queryByImportPay(Map<String, Object> entityMap);
}
