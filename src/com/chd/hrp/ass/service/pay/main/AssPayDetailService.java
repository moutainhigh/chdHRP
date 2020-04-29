﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.pay.main;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.pay.main.AssPayDetail;
/**
 * 
 * @Description:
 * 付款明细表
 * @Table:
 * ASS_PAY_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssPayDetailService extends SqlService {
	public List<Map<String, Object>> queryByAll(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryByPayNo(Map<String,Object> entityMap)throws DataAccessException;

	public String[] queryBillNo(Map<String, Object> mapVo);


}
