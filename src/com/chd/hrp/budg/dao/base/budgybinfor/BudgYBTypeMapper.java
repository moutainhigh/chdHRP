﻿
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.dao.base.budgybinfor;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 医保类型
 * @Table:
 * HEALTH_INSURANCE_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface BudgYBTypeMapper extends SqlMapper{
	/**
	 * 查询除去99类型以为的其他医保类型
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryBudgYBTypeNew(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询医保类型名称是否被占用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询医保类型性质是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int quryYBTypeNatureExist(Map<String, Object> mapVo) throws DataAccessException;
	
}
