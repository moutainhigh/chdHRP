﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.medicalmanagement;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechExec;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_EMP_TECH_REF
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrEmpTechRefService  {
	/**
	 * 添加技术准入相关联手术
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String addTechRef(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 删除技术准入相关联手术
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	String deleteHrTechRef(List<HrEmpTechExec> listVo)throws DataAccessException;

	String updateHrEmpTechRef(Map<String, Object> mapVo)throws DataAccessException;

	String queryHrEmpTechRef(Map<String, Object> page)throws DataAccessException;

}
