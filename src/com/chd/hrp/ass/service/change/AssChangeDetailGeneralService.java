﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.change;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050805 资产原值变动明细(一般设备)
 * @Table:
 * ASS_CHARGE_DETAIL_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChangeDetailGeneralService extends SqlService {
	String queryByAssCardNo(Map<String,Object> entityMap)throws DataAccessException;
}
