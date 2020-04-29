package com.chd.hrp.htc.service.task.readydata;
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

public interface HtcWorkCauseDataService {

    public String queryHtcWorkCauseDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryHtcWorkCauseData(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectHtcWorkCauseData(List<Map<String,Object>> list)throws DataAccessException;
}
