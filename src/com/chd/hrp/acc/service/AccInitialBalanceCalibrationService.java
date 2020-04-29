/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 初始余额查询<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccInitialBalanceCalibrationService {


	public String collectInitialBalanceCalibration(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectBalanceAdjust(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> collectInitialBalanceCalibrationPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> collectBalanceAdjustByPrint(Map<String,Object> entityMap) throws DataAccessException;
}
