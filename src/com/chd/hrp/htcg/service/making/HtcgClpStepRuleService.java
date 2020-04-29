package com.chd.hrp.htcg.service.making;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.making.HtcgClpStepRule;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcgClpStepRuleService {


	public String initHtcgClpStepRule(Map<String,Object> entityMap)throws DataAccessException;

	public String queryHtcgClpStepRule(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchHtcgClpStepRule(List<Map<String,Object>> list)throws DataAccessException;

	public String updateBatchHtcgClpStepRule(List<Map<String,Object>> list)throws DataAccessException;
}