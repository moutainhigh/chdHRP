﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgcost.medcostcheck;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 
 * @Table:
 * BUDG_MED_COST_CHECK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedCostCheckMapper extends SqlMapper{
	

	/**
	 * 发送、召回 修改状态
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBc_state(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 审核、消审
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int auditOrUnAudit(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 *预算下达 、取消预算下达
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int cancelIssueOrIssue(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
	/**
	 * 调整审批 医院医疗收入预算  查询(修改页面 未下达) 不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedCostHosMonth(Map<String, Object> entityMap);
	
	/**
	 * 初始审批 医院医疗收入预算  查询(修改页面 未下达)  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedCostHosMonth(Map<String, Object> entityMap, RowBounds rowBounds);
	
	/**
	 * 初始审批 医院医疗收入预算  查询 (修改页面 未下达)  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedCostHosMonthAdjust(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 调整审批 医院医疗收入预算  查询(修改页面 未下达)   分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedCostHosMonthAdjust(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 初始审批 科室医疗收入预算  查询(修改页面 未下达) 不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedCostDeptMonth(Map<String, Object> entityMap);
	
	/**
	 * 初始审批 科室医疗收入预算  查询(修改页面 未下达)  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedCostDeptMonth(Map<String, Object> entityMap, RowBounds rowBounds);
	
	/**
	 * 调整审批 科室医疗收入预算  查询 (修改页面 未下达)  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedCostCheckDeptMonthAdjust(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 调整审批 科室医疗收入预算  查询 (修改页面 未下达)  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedCostCheckDeptMonthAdjust(Map<String, Object> entityMap ,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询医院明细 初始审核 (修改页面 已下达) 不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedCostHosMonthCopy(Map<String, Object> entityMap);
	/**
	 * 查询医院明细 初始审核 (修改页面 已下达) 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedCostHosMonthCopy(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 查询医院明细 调整审核 (修改页面 已下达) 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedCostHosMonthAdjustCopy(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询医院明细 调整审核 (修改页面 已下达) 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedCostHosMonthAdjustCopy(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询科室明细 初始审核 (修改页面 已下达) 不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedCostDeptMonthCopy(Map<String, Object> entityMap);
	/**
	 * 查询科室明细 初始审核 (修改页面 已下达) 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedCostDeptMonthCopy(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 查询科室明细 调整审核 (修改页面 已下达) 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedCostCheckDeptMonthAdjustCopy(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询科室明细 调整审核 (修改页面 已下达) 不分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedCostCheckDeptMonthAdjustCopy(Map<String, Object> entityMap ,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 初始审批  校验数据是否存在
	 * @param entityMap
	 * @return
	 */
	public int queryDateExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据 所传参数 查询数据状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryBudgMedCostCheckState(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据参数 查询 初始审批单是否已下达 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryInitDateExist(Map<String, Object> mapVo) throws DataAccessException ;
	
	/**
	 * 根据参数 查询 是否存在未下达的审批表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIssueDateExist(Map<String, Object> mapVo) throws DataAccessException ;
	
	/**
	 * 备份 其他收入预算数据
	 * @param listVo
	 * @throws DataAccessException
	 */
	public int copyData(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 删除 其他收入预算备份数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteCopyData(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 查询 审核申请单 是否已做调整（取消下达时使用）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryCheckIsAdjust(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 校验 审批申请单 是否已调整
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsAdjust(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询最大审批单号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMaxCheckCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改 预算单据号管理表中 该年度 其他收入预算的最大单号
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public int updateMaxNo(Map<String, Object> entityMap) throws DataAccessException ;
}
