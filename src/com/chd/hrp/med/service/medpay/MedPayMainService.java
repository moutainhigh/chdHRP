﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.medpay;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedPayMain;
/**
 * 
 * @Description:
 * state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR> 
 * @Table:
 * MED_PAY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedPayMainService {

	/**
	 * @Description 
	 * 添加state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR>  
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedPayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR>  
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMedPayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR>  
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedPayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedPayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedPayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedPayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMedPayMain(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedPayMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MedPayMain queryMedPayMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取state  1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedPayMain
	 * @throws DataAccessException
	*/
	public MedPayMain queryMedPayMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 查询 付款单主表当前最大的 ID
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryPayID() throws DataAccessException;
	/**
	 * 修改页面 回值查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedPayMainUnit(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 审核、消审、确认、取消确认
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updatePayState(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据查询条件查询出没有被付款单引用过的发票
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedBillMain_Pay(Map<String, Object> page) throws DataAccessException;
	/**
	 * 根据发票ID 查询发票明细信息
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedBillDetail_Pay(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 生成付款单号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String setPayBillNo(Map<String, Object> mapVo) throws DataAccessException;
	
	//入库模板打印（包含主从表）
	public String queryMedPayMainByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
			
}
