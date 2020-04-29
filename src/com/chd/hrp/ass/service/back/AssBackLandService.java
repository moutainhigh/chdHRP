﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.back;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050701 资产退货单主表(土地)
 * @Table:
 * ASS_BACK_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackLandService extends SqlService {
	public String updateBackConfirm(List<Map<String, Object>> entityMap,List<Map<String, Object>> cardEntityMap)throws DataAccessException;

	Map<String, Object> printAssBackLanData(Map<String, Object> map) throws DataAccessException;

	public List<String> queryAssBackLanStates(Map<String, Object> mapVo);

	public String queryDetails(Map<String, Object> page) throws DataAccessException;
}
