﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.assdisposal.general;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assdisposal.general.AssDisposalRecordDetailGeneral;
/**
 * 
 * @Description:
 * 050701 资产处置记录明细(一般设备)
 * @Table:
 * ASS_DISPOSAL_R_DETAIL_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDisposalRecordDetailGeneralService extends SqlService {

	List<AssDisposalRecordDetailGeneral> queryByDisRNo(Map<String, Object> mapVo)throws DataAccessException;

}
