/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.invdisburse;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室材料支出预算
 * @Table:
 * BUDG_MAT
 * @Author: slient
 * @email:  slient@e-tonggroup.com
 * @Version: 1.0
 */
public interface BudgMatPurMapper extends SqlMapper{

	/**
	 * 查询物资分类列表
	 * 根据年度从BUDG_MAT_TYPE_SUBJ中取
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatTypeSubj(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 获取生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> getCollectData(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 添加生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public void addGenerateBatch(List<Map<String, Object>> datalist) throws DataAccessException;
	/**
	 * 查询数据是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExists(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 批量查询 数据是否存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExistList(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 根据 预算年度 、 月份 、物资分类  查询其支出预算
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryCostBudg(Map<String, Object> mapVo) throws DataAccessException;
	
	
}
