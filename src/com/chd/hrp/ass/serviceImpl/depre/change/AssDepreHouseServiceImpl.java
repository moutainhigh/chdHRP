﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.depre.change;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.card.AssCardHouseMapper;
import com.chd.hrp.ass.dao.depre.change.AssDepreDetailHouseMapper;
import com.chd.hrp.ass.dao.depre.change.AssDepreHouseMapper;
import com.chd.hrp.ass.dao.depre.change.AssDepreHouseMapper;
import com.chd.hrp.ass.dao.depre.change.source.AssDepreSourceHouseMapper;
import com.chd.hrp.ass.dao.resource.AssResourceHouseMapper;
import com.chd.hrp.ass.entity.depre.change.AssDepreHouse;
import com.chd.hrp.ass.entity.depre.change.AssDepreHouse;
import com.chd.hrp.ass.entity.depre.change.source.AssDepreSourceHouse;
import com.chd.hrp.ass.entity.resource.AssResourceHouse;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.depre.change.AssDepreHouseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050806 资产累计折旧变动(房屋及建筑物)
 * @Table:
 * ASS_DEPRE_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assDepreHouseService")
public class AssDepreHouseServiceImpl implements AssDepreHouseService {

	private static Logger logger = Logger.getLogger(AssDepreHouseServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assDepreHouseMapper")
	private final AssDepreHouseMapper assDepreHouseMapper = null;

	@Resource(name = "assDepreDetailHouseMapper")
	private final AssDepreDetailHouseMapper assDepreDetailHouseMapper = null;

	@Resource(name = "assDepreSourceHouseMapper")
	private final AssDepreSourceHouseMapper assDepreSourceHouseMapper = null;

	@Resource(name = "assResourceHouseMapper")
	private final AssResourceHouseMapper assResourceHouseMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;

	@Resource(name = "assCardHouseMapper")
	private final AssCardHouseMapper assCardHouseMapper = null;

	/**
	 * @Description 添加050806 资产累计折旧变动(房屋及建筑)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050806 资产累计折旧变动(房屋及建筑)
		AssDepreHouse assDepreHouse = queryByCode(entityMap);

		if (assDepreHouse != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assDepreHouseMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050806 资产累计折旧变动(房屋及建筑)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assDepreHouseMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050806 资产累计折旧变动(房屋及建筑)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assDepreHouseMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050806 资产累计折旧变动(房屋及建筑)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assDepreHouseMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050806 资产累计折旧变动(房屋及建筑)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assDepreHouseMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050806 资产累计折旧变动(房屋及建筑)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			assDepreSourceHouseMapper.deleteBatch(entityList);
			assDepreDetailHouseMapper.deleteBatch(entityList);
			assDepreHouseMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加050806 资产累计折旧变动(房屋及建筑)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> detSourceList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("change_no", entityMap.get("change_no"));

		Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
		vCreateDateMap.put("group_id", entityMap.get("group_id"));
		vCreateDateMap.put("hos_id", entityMap.get("hos_id"));
		vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
		vCreateDateMap.put("ass_nature", "01");
		vCreateDateMap.put("use_state", "1,2,3,4,5");
		vCreateDateMap.put("in_date", entityMap.get("create_date"));
		vCreateDateMap.put("depre_change_where", "true");

		entityMap.put("state", "0");
		List<AssDepreHouse> list = (List<AssDepreHouse>) assDepreHouseMapper.queryExists(mapVo);
		boolean flag = true;
		try {
			if (list.size() > 0) {
				assDepreHouseMapper.update(entityMap);
			} else {
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_DEPRE_HOUSE");
				String change_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("change_no", change_no);
				assDepreHouseMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			List<Map<String, Object>> detailList = assDepreDetailHouseMapper.queryByChangeNoMap(mapVo);

			for (Map<String, Object> det : detailList) {
				boolean falg = false;
				for (Map<String, Object> temp : detail) {
					if (temp.get("ass_card_no") == null || "".equals(temp.get("ass_card_no"))) {
						continue;
					}
					if (det.get("ass_card_no").toString().equals(temp.get("ass_card_no").toString())) {
						falg = true;
						break;
					}
				}
				if (falg == false) {
					detSourceList.add(det);
				}
			}
			if (detSourceList.size() > 0) {
				assDepreSourceHouseMapper.deleteBatch(detSourceList);
			}

			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}

				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("change_no", entityMap.get("change_no"));

				vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));

				Integer createDateExists = hrpAssSelectMapper.queryAssExistsHouseCard(vCreateDateMap);
				if (createDateExists == 0) {
					flag = false;
					break;
				}

				if (detailVo.get("old_depre") != null && !detailVo.get("old_depre").equals("")) {
					detailVo.put("old_depre", detailVo.get("old_depre") + "");
				} else {
					detailVo.put("old_depre", "0");
				}

				if (detailVo.get("change_depre") != null && !detailVo.get("change_depre").equals("")) {
					detailVo.put("change_depre", detailVo.get("change_depre") + "");
				} else {
					detailVo.put("change_depre", "0");
				}

				if (detailVo.get("new_depre") != null && !detailVo.get("new_depre").equals("")) {
					detailVo.put("new_depre", detailVo.get("new_depre") + "");
				} else {
					detailVo.put("new_depre", "0");
				}

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}
				Double new_depre = 0.0;
				Double change_depre = 0.0;
				List<Map<String, Object>> existsSource = assDepreSourceHouseMapper
						.queryDepreSourceByAssCardNo(detailVo);
				if (existsSource.size() == 0 || existsSource == null) {
					List<Map<String, Object>> reSourceList = assResourceHouseMapper.queryByAssCardNoMap(detailVo);
					for (Map<String, Object> source : reSourceList) {
						source.put("change_no", entityMap.get("change_no"));
						source.put("ass_card_no", detailVo.get("ass_card_no"));
						source.put("source_id", source.get("source_id"));
						source.put("old_depre", source.get("depre_money"));
						source.put("change_depre", "0");
						source.put("new_depre", source.get("depre_money"));
						source.put("note", "");
						new_depre = new_depre + Double.parseDouble(source.get("new_depre").toString());
						sourceList.add(source);
					}
				} else {
					for (Map<String, Object> source : existsSource) {
						new_depre = new_depre + Double.parseDouble(source.get("new_depre").toString());
						change_depre = change_depre + Double.parseDouble(source.get("change_depre").toString());
					}
				}
				detailVo.put("new_depre", new_depre);
				detailVo.put("change_depre", change_depre);
				listVo.add(detailVo);
			}

			if (!flag) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "{\"warn\":\"存在尚未入账的卡片不能进行变动.\"}";
			}
			assDepreSourceHouseMapper.delete(entityMap);
			assDepreDetailHouseMapper.delete(entityMap);
			assDepreDetailHouseMapper.addBatch(listVo);

			if (sourceList.size() > 0) {
				assDepreSourceHouseMapper.deleteBatch(sourceList);
				assDepreSourceHouseMapper.addBatch(sourceList);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"change_no\":\"" + entityMap.get("change_no") + "\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * @Description 查询结果集050806 资产累计折旧变动(房屋及建筑)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssDepreHouse> list = (List<AssDepreHouse>) assDepreHouseMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssDepreHouse> list = (List<AssDepreHouse>) assDepreHouseMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050806 资产累计折旧变动(房屋及建筑)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assDepreHouseMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050806 资产累计折旧变动(房屋及建筑)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssDepreHouse
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assDepreHouseMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050806 资产累计折旧变动(房屋及建筑)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssDepreHouse>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assDepreHouseMapper.queryExists(entityMap);
	}

	@Override
	public String deleteAssDepreSourceHouse(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assDepreSourceHouseMapper.deleteBatch(entityMap);

			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get(0).get("group_id"));
			mapVo.put("hos_id", entityMap.get(0).get("hos_id"));
			mapVo.put("copy_code", entityMap.get(0).get("copy_code"));
			mapVo.put("change_no", entityMap.get(0).get("change_no"));
			mapVo.put("ass_card_no", entityMap.get(0).get("ass_card_no"));
			Double change_depre = 0.0;
			Double new_depre = 0.0;
			List<Map<String, Object>> listMap = assDepreSourceHouseMapper.queryDepreSourceByAssCardNo(mapVo);
			for (Map<String, Object> map : listMap) {
				change_depre = change_depre + Double.parseDouble(map.get("change_depre").toString());
				new_depre = new_depre + Double.parseDouble(map.get("price").toString());
			}
			mapVo.put("change_depre", change_depre + "");
			mapVo.put("new_depre", new_depre + "");

			assDepreDetailHouseMapper.updateDepreBySource(mapVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"change_depre\":\"" + change_depre + "\",\"new_depre\":\""
					+ new_depre + "\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryAssDepreSourceHouse(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssDepreSourceHouse> list = (List<AssDepreSourceHouse>) assDepreSourceHouseMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssDepreSourceHouse> list = (List<AssDepreSourceHouse>) assDepreSourceHouseMapper
					.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String saveAssDepreSourceHouse(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
		Double change_depre = 0.0;
		Double new_depre = 0.0;
		try {
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("source_id") == null || "".equals(detailVo.get("source_id"))) {
					continue;
				}
				if (Double.parseDouble(detailVo.get("new_depre").toString())>Double.parseDouble(detailVo.get("price").toString())) {
					return "{\"warn\":\"变动后折旧大于原值,请修改后保存!\"}";
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("change_no", entityMap.get("change_no"));
				detailVo.put("ass_card_no", entityMap.get("ass_card_no"));
				detailVo.put("source_id", String.valueOf(detailVo.get("source_id")));

				if (detailVo.get("old_depre") != null && !detailVo.get("old_depre").equals("")) {
					detailVo.put("old_depre", detailVo.get("old_depre") + "");
				} else {
					detailVo.put("old_depre", "0");
				}

				if (detailVo.get("change_depre") != null && !detailVo.get("change_depre").equals("")) {
					change_depre = change_depre + Double.parseDouble(detailVo.get("change_depre").toString());
					detailVo.put("change_depre", detailVo.get("change_depre") + "");
				} else {
					detailVo.put("change_price", "0");
				}

				if (detailVo.get("new_depre") != null && !detailVo.get("new_depre").equals("")) {
					new_depre = new_depre + Double.parseDouble(detailVo.get("new_depre").toString());
					detailVo.put("price", detailVo.get("new_depre") + "");
				} else {
					detailVo.put("new_depre", "0");
				}

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}

				listVo.add(detailVo);
			}

			assDepreSourceHouseMapper.delete(entityMap);
			assDepreSourceHouseMapper.addBatch(listVo);

			entityMap.put("change_depre", change_depre + "");
			entityMap.put("new_depre", new_depre + "");

			assDepreDetailHouseMapper.updateDepreBySource(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"change_depre\":\"" + change_depre + "\",\"new_depre\":\""
					+ new_depre + "\"}";
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateConfirm(List<Map<String, Object>> entityMap) throws DataAccessException {
		List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> reSourceUpdateList = new ArrayList<Map<String, Object>>();
		try {
			for (Map<String, Object> map : entityMap) {
				List<Map<String, Object>> detailMap = assDepreDetailHouseMapper.queryByChangeNoMap(map);
				if (detailMap == null || detailMap.size() == 0) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "{\"warn\":\"存在没有明细数据的单据,不能确认.\"}";
				}
				for (Map<String, Object> detail : detailMap) {
					List<Map<String, Object>> sourceMap = assDepreSourceHouseMapper
							.queryDepreSourceByAssCardNo(detail);
					if (sourceMap == null || sourceMap.size() == 0) {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return "{\"warn\":\"存在没有资金来源的单据,不能确认.\"}";
					}
					cardList.add(detail);
					for (Map<String, Object> source : sourceMap) {
						AssResourceHouse assResourceHouse = assResourceHouseMapper.queryByCode(source);
						if (assResourceHouse != null) {
							reSourceUpdateList.add(source);
						}
					}
				}
			}

			assDepreHouseMapper.updateConfirm(entityMap);// 更新主表状态
			assCardHouseMapper.updateDepreMoneyConfirm(cardList);// 更新卡片原值
			if (reSourceUpdateList.size() > 0) {
				assResourceHouseMapper.updateBatchByDepre(reSourceUpdateList);
			}
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}
