﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.dict;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 付款支付方式与资金来源对应关系
 * @Table:
 * ASS_PAY_SOURCE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssPaySourceMapper extends SqlMapper{
	List<Map<String, Object>> queryByPayCode(Map<String,Object> entityMap)throws DataAccessException;
}
