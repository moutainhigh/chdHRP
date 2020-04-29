package com.chd.hrp.med.dao.order.init;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MedOrderInitMapper extends SqlMapper{

	/**
	 * 订单页面--代销使用查询--查询代销出库数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryAffiOut(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 订单页面--代销使用查询--查询代销出库主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAffiOutMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 订单页面--代销使用查询--查询代销出库明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 中止订单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int abortMedOrderInit(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 还原采购计划单的状态到已审核（STATE=2）
	 * @param entityList
	 * @throws DataAccessException
	 */
	public int updateBatchPurOrder(Map<String, Object> entityList) throws DataAccessException;
	/**
	 * 删除采购计划订单关系表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deletePurOrder(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 还原合并订单中原订单的状态为已审核（STATE=2）
	 * @param entityList
	 * @throws DataAccessException
	 */
	public int updateBatchMergeOrder(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除订单合并表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMergeOrder(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 订单合并--插入合并表
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int addMergeOrder(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 采购计划生成--查询采购计划
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedOrderGenPur(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 采购计划生成 -- 根据主键查询采购主表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedPurById(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 采购计划生成 -- 根据主键查询采购明细信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedPurDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 采购计划生成订单--汇总药品明细信息
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedOrderDetailList(Map<String, Object> entityList) throws DataAccessException;
	/**
	 * 采购计划生成--批量加入到med_pur_order_real关系表中
	 * @param purOrderList
	 * @return
	 * @throws DataAccessException
	 */
	public int addBatchPOReal(List<Map<String, Object>> purOrderList) throws DataAccessException;
	/**
	 * 采购计划生成--更新采购计划单状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updatePurBatch(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据代销使用生成订单明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedAffiOutDetailByGen(Map<String, Object> entityMap) throws DataAccessException;
	
	//入库主表模板打印
	public Map<String, Object> queryMedOrderInitPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
					
	//入库明细表模板打印
	public List<Map<String, Object>> queryMedOrderInitPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;

	//入库主表批量打印
	public List<Map<String, Object>> queryMedOrderInitPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 采购计划导入主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedPurMainForOrder(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 采购计划导入主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedPurMainForOrder(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 采购计划导入明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedPurDetailForOrder(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 关闭采购明细
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMedPurDetailByOrderClose(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 根据采购计划获取订单信息用于导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryOrderByPurImp(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMedPurDetailGenOrder(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 更新采购单状态
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updatePurStates(Map<String, Object> entityMap) throws DataAccessException;

	public String queryPurIds(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryDeleteOldIds(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据order_id 查明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryOrderDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 获得要删除订单中对应关系中采购单
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedPurId(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 汇总明细数据
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedPurDetailCollect(List<Map<String, Object>> detailList) throws DataAccessException;

	public List<Map<String, Object>> queryMedOrderInitPrintTemlatePageMain(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMedOrderInitPrintTemlatePageDetail(
			Map<String, Object> entityMap) throws DataAccessException;
}
