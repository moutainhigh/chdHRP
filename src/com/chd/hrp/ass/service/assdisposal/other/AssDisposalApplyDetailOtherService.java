package com.chd.hrp.ass.service.assdisposal.other;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assdisposal.other.AssDisposalApplyDetailOther;

/**
 * 
 * @Description: 050701 资产处置申报单明细(一般设备) 
 * @Table: ASS_DISPOSAL_A_DETAIL_Other
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssDisposalApplyDetailOtherService extends SqlService{

	List<AssDisposalApplyDetailOther> queryByDisANo(Map<String, Object> mapVo)throws DataAccessException;

}
