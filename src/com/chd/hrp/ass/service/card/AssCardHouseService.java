﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.card;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 资产卡片维护_房屋及建筑物
 * @Table:
 * ASS_CARD_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCardHouseService extends SqlService {

	Integer queryCardExistsByAssInNo(Map<String, Object> mapExists);

	List<Map<String, Object>> queryAssCardByAssInNo(Map<String, Object> mapExists);

	Map<String, Object> printAssCardHouseData(Map<String, Object> map) throws DataAccessException;
	public List<Map<String,Object>> queryPrint(Map<String, Object> entityMap) throws DataAccessException ;

}
