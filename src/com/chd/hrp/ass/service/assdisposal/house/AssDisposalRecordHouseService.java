/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.assdisposal.house;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 051001资产处置记录(土地)
 * @Table:
 * ASS_DISPOSAL_R_House
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDisposalRecordHouseService extends SqlService {

	String updateConfirmDisposalRecordHouse(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo)throws DataAccessException;

	Map<String, Object> printAssDisposalRecordHouseData(Map<String, Object> map) throws DataAccessException;

	String initAssCheckSpecial(Map<String, Object> mapVo);

	String deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

	List<String> queryAssDisposalRecordHouseStates(Map<String, Object> mapVo);

	String queryDetails(Map<String, Object> page);

}