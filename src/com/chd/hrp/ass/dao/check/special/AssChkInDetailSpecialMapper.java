﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.special;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.check.special.AssChkInDetailSpecial;
/**
 * 
 * @Description:
 * 050701 资产盘盈入库明细(专用设备)
 * @Table:
 * ASS_CHK_IN_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChkInDetailSpecialMapper extends SqlMapper{

	List<Map<String, Object>> queryByInit(Map<String, Object> map)throws DataAccessException;

	List<Map<String, Object>> queryStore(List<Map<String, Object>> listVo5)throws DataAccessException;

	List<Map<String, Object>> queryState(Map<String, Object> mapVo)throws DataAccessException;

	List<Map<String, Object>> queryDept(List<Map<String, Object>> listVo)throws DataAccessException;

	

	
}