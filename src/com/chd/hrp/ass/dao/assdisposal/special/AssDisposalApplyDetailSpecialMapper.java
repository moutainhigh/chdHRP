﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assdisposal.special;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assdisposal.special.AssDisposalApplyDetailSpecial;
/**
 *  
 * @Description:
 * 050701 资产处置申报单明细(专用设备)
 * @Table:
 * ASS_DISPOSAL_A_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDisposalApplyDetailSpecialMapper extends SqlMapper{

	List<AssDisposalApplyDetailSpecial> queryByDisANo(Map<String, Object> mapVo)throws DataAccessException;
	
}
