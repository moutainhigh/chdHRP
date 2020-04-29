﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.check.house;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050701 资产盘盈入库明细(一般设备)
 * @Table:
 * ASS_CHK_IN_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChkInDetailHouseService extends SqlService {

	List<Map<String, Object>> queryByInit(Map<String, Object> mapVo)throws DataAccessException;

	String saveHouseInventory(List<Map<String, Object>> listVo)throws DataAccessException;

}
