﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.back.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.back.rest.AssBackRestDetailLandMapper;
import com.chd.hrp.ass.dao.back.rest.AssBackRestHouseMapper;
import com.chd.hrp.ass.dao.back.rest.AssBackRestLandMapper;
import com.chd.hrp.ass.dao.back.rest.source.AssBackRestSourceLandMapper;
import com.chd.hrp.ass.dao.card.AssCardLandMapper;
import com.chd.hrp.ass.dao.resource.AssResourceLandMapper;
import com.chd.hrp.ass.entity.back.rest.AssBackRestLand;
import com.chd.hrp.ass.entity.resource.AssResourceLand;
import com.chd.hrp.ass.service.back.rest.AssBackRestLandService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050701 资产其他退账单主表(土地)
 * @Table: ASS_BACK_REST_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
 
@Service("assBackRestLandService")
public class AssBackRestLandServiceImpl implements AssBackRestLandService {

	private static Logger logger = Logger.getLogger(AssBackRestLandServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assBackRestLandMapper")
	private final AssBackRestLandMapper assBackRestLandMapper = null;

	@Resource(name = "assBackRestDetailLandMapper")
	private final AssBackRestDetailLandMapper assBackRestDetailLandMapper = null;

	@Resource(name = "assBackRestSourceLandMapper")
	private final AssBackRestSourceLandMapper assBackRestSourceLandMapper = null;

	@Resource(name = "assResourceLandMapper")
	private final AssResourceLandMapper assResourceLandMapper = null;

	@Resource(name = "assCardLandMapper")
	private final AssCardLandMapper assCardLandMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;

	/**
	 * @Description 添加050701 资产其他退货单主表(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050701 资产其他退货单主表(土地)
		AssBackRestLand assBackRestLand = queryByCode(entityMap);

		if (assBackRestLand != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assBackRestLandMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050701 资产其他退货单主表(土地)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assBackRestLandMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050701 资产其他退货单主表(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assBackRestLandMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050701 资产其他退货单主表(土地)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assBackRestLandMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050701 资产其他退货单主表(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assBackRestLandMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050701 资产其他退货单主表(土地)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			assBackRestSourceLandMapper.deleteBatch(entityList);
			assBackRestDetailLandMapper.deleteBatch(entityList);
			assBackRestLandMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * @Description 添加050701 资产其他退货单主表(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {

		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listSourceVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("ass_back_no", entityMap.get("ass_back_no"));

		Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
		vCreateDateMap.put("group_id", entityMap.get("group_id"));
		vCreateDateMap.put("hos_id", entityMap.get("hos_id"));
		vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
		vCreateDateMap.put("ass_nature", "06");
		vCreateDateMap.put("use_state", "1,2,3,4,5");
		vCreateDateMap.put("in_date", entityMap.get("create_date"));

		Map<String, Object> vStoreMap = new HashMap<String, Object>();
		vStoreMap.put("group_id", entityMap.get("group_id"));
		vStoreMap.put("hos_id", entityMap.get("hos_id"));
		vStoreMap.put("copy_code", entityMap.get("copy_code"));
		vStoreMap.put("ass_nature", "06");
		vStoreMap.put("use_state", "1,2,3,4,5");
		vStoreMap.put("store_id", entityMap.get("store_id"));
		vStoreMap.put("store_no", entityMap.get("store_no"));

		Map<String, Object> vBusTypeMap = new HashMap<String, Object>();
		vBusTypeMap.put("group_id", entityMap.get("group_id"));
		vBusTypeMap.put("hos_id", entityMap.get("hos_id"));
		vBusTypeMap.put("copy_code", entityMap.get("copy_code"));
		vBusTypeMap.put("ass_nature", "06");
		vBusTypeMap.put("use_state", "1,2,3,4,5");
		vBusTypeMap.put("bus_type_code", entityMap.get("bus_type_code"));

		entityMap.put("state", "0");
		List<AssBackRestLand> list = (List<AssBackRestLand>) assBackRestLandMapper.queryExists(mapVo);
		try {
			boolean flag = true;
			boolean flag1 = true;
			boolean flag2 = true;

			if (list.size() > 0) {
				assBackRestLandMapper.update(entityMap);
			} else {
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_BACK_REST_LAND");
				String ass_back_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("ass_back_no", ass_back_no);
				assBackRestLandMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			Double back_money = 0.0;
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("ass_card_no", detailVo.get("ass_card_no").toString());
				detailVo.put("ass_back_no", entityMap.get("ass_back_no"));
				vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));
				vStoreMap.put("ass_card_no", detailVo.get("ass_card_no"));
				vBusTypeMap.put("ass_card_no", detailVo.get("ass_card_no"));
				Integer createDateExists = hrpAssSelectMapper.queryAssExistsLandCard(vCreateDateMap);
				if (createDateExists == 0) {
					flag = false;
					break;
				}

				Integer storeExists = hrpAssSelectMapper.queryAssExistsLandCard(vStoreMap);
				if (storeExists == 0) {
					flag1 = false;
					break;
				}

				Integer busTypeExists = hrpAssSelectMapper.queryAssExistsLandCard(vBusTypeMap);
				if (busTypeExists == 0) {
					flag2 = false;
					break;
				}

				String create_date = entityMap.get("create_date").toString();

				detailVo.put("ass_year", create_date.substring(0, create_date.indexOf("-")));

				detailVo.put("ass_month",
						create_date.substring(create_date.indexOf("-") + 1, create_date.lastIndexOf("-")));

				detailVo.put("ass_naturs", "06");

				String results = collectAssBackLand(detailVo);

				JSONObject jsonObj = JSONObject.parseObject(results);

				if (jsonObj.containsKey("msg")) {
					transactionManager.rollback(status);
					return jsonObj.toJSONString();
				}

				JSONArray cardArray = JSONArray.parseArray(jsonObj.get("Card").toString());

				JSONObject cardObj = JSONObject.parseObject(cardArray.get(0).toString());

				detailVo.put("ass_back_no", entityMap.get("ass_back_no"));

				detailVo.put("price", detailVo.get("price").toString());

				back_money = back_money + Double.parseDouble(detailVo.get("price").toString());

				detailVo.put("now_depre", cardObj.get("NowDepre").toString());

				detailVo.put("now_manage_depre", cardObj.get("NowManageDepre").toString());

				detailVo.put("add_depre", cardObj.get("AddDepre").toString());

				detailVo.put("add_manage_depre", cardObj.get("AddManageDepre").toString());

				detailVo.put("cur_money", cardObj.get("Cur").toString());

				detailVo.put("fore_money", cardObj.get("Fore").toString());

				detailVo.put("add_depre_month", cardObj.get("AddAccMonth").toString());

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}
				JSONArray sourceArray = JSONArray.parseArray(jsonObj.get("Source").toString());

				List<AssResourceLand> resourceList = assResourceLandMapper.queryByAssCardNo(detailVo);
				for (AssResourceLand resSource : resourceList) {
					Map<String, Object> mapSource = new HashMap<String, Object>();
					mapSource.put("group_id", resSource.getGroup_id());
					mapSource.put("hos_id", resSource.getHos_id());
					mapSource.put("copy_code", resSource.getCopy_code());
					mapSource.put("ass_back_no", entityMap.get("ass_back_no"));
					mapSource.put("ass_card_no", resSource.getAss_card_no());
					mapSource.put("source_id", resSource.getSource_id());
					mapSource.put("price", resSource.getPrice());
					for (int i = 0; i < sourceArray.size(); i++) {
						JSONObject job = sourceArray.getJSONObject(i);
						if (resSource.getSource_id() == Long.parseLong(job.get("ID").toString())) {
							mapSource.put("now_depre", job.get("NowDepre").toString());
							mapSource.put("add_depre", job.get("AddDepre").toString());
							mapSource.put("cur_money", job.get("Cur").toString());
							mapSource.put("fore_money", job.get("Fore").toString());
							mapSource.put("now_manage_depre", job.get("NowManageDepre").toString());
							mapSource.put("add_manage_depre", job.get("AddManageDepre").toString());
							mapSource.put("add_depre_month", job.get("AddAccMonth").toString());
						}
					}

					if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
						mapSource.put("note", detailVo.get("note"));
					} else {
						mapSource.put("note", "");
					}
					listSourceVo.add(mapSource);
				}

				listVo.add(detailVo);
			}
			if (!flag) {
				transactionManager.rollback(status);
				return "{\"warn\":\"存在尚未入账的卡片不能进行退货.\"}";
			}
			if (!flag1) {
				transactionManager.rollback(status);
				return "{\"warn\":\"存在非本仓库的卡片,不能退货.\"}";
			}

			if (!flag2) {
				transactionManager.rollback(status);
				return "{\"warn\":\"存在非其他入库的卡片,不能退货.\"}";
			}

			assBackRestSourceLandMapper.delete(entityMap);
			assBackRestDetailLandMapper.delete(entityMap);
			assBackRestDetailLandMapper.addBatch(listVo);
			if (listSourceVo.size() > 0) {
				assBackRestSourceLandMapper.addBatch(listSourceVo);
			}
			if(back_money != 0 && back_money != null && back_money != 0.0){
				entityMap.put("back_money", back_money+"");
				assBackRestLandMapper.updateBackMoney(entityMap);
			}
			transactionManager.commit(status);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"ass_back_no\":\"" + entityMap.get("ass_back_no").toString()
					+ "\",\"back_money\":\"" + back_money + "\"}";
		} catch (Exception e) {
			transactionManager.rollback(status);
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	public String collectAssBackLand(Map<String, Object> entityMap) {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

		assBackRestLandMapper.collectAssBackLand(entityMap);

		String ass_AppCode = entityMap.get("ass_AppCode").toString();

		String ass_ErrTxt = "";
		if (entityMap.get("ass_ErrTxt") != null && !entityMap.get("ass_ErrTxt").equals("")) {
			ass_ErrTxt = entityMap.get("ass_ErrTxt").toString();
		}

		if ("0".equals(ass_AppCode)) {
			// 计算成功！提交事务
			transactionManager.commit(status);
			return entityMap.get("ass_json_str").toString();

		} else if ("-1".equals(ass_AppCode) || "100".equals(ass_AppCode)) {
			// 计算失败！回滚事务
			transactionManager.rollback(status);

			return "{\"msg\":\"" + ass_ErrTxt + "\",\"state\":\"" + ass_AppCode + "\"}";
		}

		if (!"".equals(entityMap.get("ass_ErrTxt").toString()) && null != entityMap.get("ass_ErrTxt").toString()) {

			ass_ErrTxt = entityMap.get("ass_ErrTxt").toString();

		}
		return "{\"msg\":\"" + ass_ErrTxt + "\",\"state\":\"" + ass_AppCode + "\"}";

	}

	/**
	 * @Description 查询结果集050701 资产其他退货单主表(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssBackRestLand> list = (List<AssBackRestLand>) assBackRestLandMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssBackRestLand> list = (List<AssBackRestLand>) assBackRestLandMapper.query(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050701 资产其他退货单主表(土地)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assBackRestLandMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050701 资产其他退货单主表(土地)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssBackRestLand
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assBackRestLandMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050701 资产其他退货单主表(土地)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssBackRestLand>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assBackRestLandMapper.queryExists(entityMap);
	}

	@Override
	public String updateBackConfirm(List<Map<String, Object>> entityMap, List<Map<String, Object>> cardEntityMap)
			throws DataAccessException {
		try {
			assBackRestLandMapper.updateBatchConfirm(entityMap);
			assCardLandMapper.updateBackConfirm(cardEntityMap);

			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	
	
	@Override
	public Map<String,Object> printAssBackRestLandData(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssBackRestLandMapper assBackRestLandMapper = (AssBackRestLandMapper)context.getBean("assBackRestLandMapper");
			
			//查询凭证主表
			List<Map<String,Object>> mainList=assBackRestLandMapper.queryAssBackRestLandByAssBackNo(map);
					
			//查询凭证明细表
			List<Map<String,Object>> detailList=assBackRestLandMapper.queryAssBackRestLandDetailByAssBackNo(map);
			
			reMap.put("main", mainList);
			reMap.put("detail", detailList);
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}

	@Override
	public List<String> queryAssBackRestLandStates(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return assBackRestLandMapper.assBackRestLandMapper(mapVo);
	}

	@Override
	public String queryDetails(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assBackRestLandMapper.queryDetails(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assBackRestLandMapper.queryDetails(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
}
