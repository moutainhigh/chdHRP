package com.chd.hrp.htc.service.task.projectcost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
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

public interface HtcWorkCauseSetService {

	public String initHtcWorkCauseSet(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcWorkCauseSet(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchHtcWorkCauseSet(List<Map<String,Object>> list)throws DataAccessException;
	
	public String updateBatchHtcWorkCauseSet(List<Map<String, Object>> listVo) throws DataAccessException;
}
