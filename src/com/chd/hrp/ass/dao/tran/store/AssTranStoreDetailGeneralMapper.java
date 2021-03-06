﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.tran.store;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.tran.store.AssTranStoreDetailGeneral;
/**
 * 
 * @Description:
 * 050901 资产转移明细(仓库到仓库)_一般设备
 * @Table:
 * ASS_TRAN_STORE_DETAIL_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssTranStoreDetailGeneralMapper extends SqlMapper{
	List<AssTranStoreDetailGeneral> queryByTranNo(Map<String,Object> entityMap)throws DataAccessException;
}
