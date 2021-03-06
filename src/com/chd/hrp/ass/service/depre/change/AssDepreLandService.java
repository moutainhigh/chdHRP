﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.depre.change;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050806 资产累计折旧变动(土地)
 * @Table:
 * ASS_DEPRE_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDepreLandService extends SqlService {
	String deleteAssDepreSourceLand(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	String queryAssDepreSourceLand(Map<String, Object> entityMap)throws DataAccessException;
	
	String saveAssDepreSourceLand(Map<String, Object> entityMap)throws DataAccessException;
	
	String updateConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
}
