﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.check.general;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 051101 仓库盘点单明细_医用设备
 * @Table:
 * ASS_CHECK_S_DETAIL_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckSdetailGeneralService extends SqlService {
	/**
	 * @Description 
	 * 复制账面数量<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String copyAmount(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除盘点卡片<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String delCard(Map<String,Object> entityMap)throws DataAccessException;
	public List<String> queryAssInSpecialState(Map<String, Object> mapVo);
	Map<String, Object> queryAssInSpecialByPrintTemlatePrint(Map<String, Object> entityMap) throws DataAccessException;
	public String saveAssCheckSDetailGeneral(Map<String, Object> mapVo)throws DataAccessException;
}
