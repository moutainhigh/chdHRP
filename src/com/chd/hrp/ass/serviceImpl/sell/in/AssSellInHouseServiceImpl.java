﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.sell.in;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.accessory.AssAccessoryHouseMapper;
import com.chd.hrp.ass.dao.card.AssCardHouseMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccHouseMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageHouseMapper;
import com.chd.hrp.ass.dao.dict.AssBillNoMapper;
import com.chd.hrp.ass.dao.file.AssFileHouseMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageHouseMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoHouseMapper;
import com.chd.hrp.ass.dao.resource.AssResourceHouseMapper;
import com.chd.hrp.ass.dao.sell.in.AssSellInDetailHouseMapper;
import com.chd.hrp.ass.dao.sell.in.AssSellInHouseMapper;
import com.chd.hrp.ass.dao.sell.map.AssSellMapHouseMapper;
import com.chd.hrp.ass.dao.sell.out.AssSellOutDetailHouseMapper;
import com.chd.hrp.ass.dao.sell.out.AssSellOutHouseMapper;
import com.chd.hrp.ass.dao.sell.out.source.AssSellOutSourceHouseMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptHouseMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRHouseMapper;
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.entity.dict.AssBillNo;
import com.chd.hrp.ass.entity.file.AssFileHouse;
import com.chd.hrp.ass.entity.photo.AssPhotoHouse;
import com.chd.hrp.ass.entity.sell.in.AssSellInHouse;
import com.chd.hrp.ass.entity.sell.out.AssSellOutDetailHouse;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.sell.in.AssSellInHouseService;
import com.chd.hrp.mat.dao.info.basic.MatStoreMapper;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产有偿调拨入库单主表（房屋及建筑物）
 * @Table:
 * ASS_SELL_IN_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 
 

@Service("assSellInHouseService")
public class AssSellInHouseServiceImpl implements AssSellInHouseService {    

	private static Logger logger = Logger.getLogger(AssSellInHouseServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assSellInHouseMapper")
	private final AssSellInHouseMapper assSellInHouseMapper = null;

	@Resource(name = "assSellInDetailHouseMapper")
	private final AssSellInDetailHouseMapper assSellInDetailHouseMapper = null;

	@Resource(name = "assSellOutHouseMapper")
	private final AssSellOutHouseMapper assSellOutHouseMapper = null;

	@Resource(name = "assSellOutDetailHouseMapper")
	private final AssSellOutDetailHouseMapper assSellOutDetailHouseMapper = null;

	@Resource(name = "assSellOutSourceHouseMapper")
	private final AssSellOutSourceHouseMapper assSellOutSourceHouseMapper = null;

	@Resource(name = "assCardHouseMapper")
	private final AssCardHouseMapper assCardHouseMapper = null;

	@Resource(name = "assSellMapHouseMapper")
	private final AssSellMapHouseMapper assSellMapHouseMapper = null;

	@Resource(name = "assResourceHouseMapper")
	private final AssResourceHouseMapper assResourceHouseMapper = null;

	@Resource(name = "assShareDeptHouseMapper")
	private final AssShareDeptHouseMapper assShareDeptHouseMapper = null;

	@Resource(name = "assShareDeptRHouseMapper")
	private final AssShareDeptRHouseMapper assShareDeptRHouseMapper = null;

	@Resource(name = "assFileHouseMapper")
	private final AssFileHouseMapper assFileHouseMapper = null;

	@Resource(name = "assPhotoHouseMapper")
	private final AssPhotoHouseMapper assPhotoHouseMapper = null;

	@Resource(name = "assAccessoryHouseMapper")
	private final AssAccessoryHouseMapper assAccessoryHouseMapper = null;

	@Resource(name = "assDepreAccHouseMapper")
	private final AssDepreAccHouseMapper assDepreAccHouseMapper = null;

	@Resource(name = "assDepreManageHouseMapper")
	private final AssDepreManageHouseMapper assDepreManageHouseMapper = null;

	@Resource(name = "assPayStageHouseMapper")
	private final AssPayStageHouseMapper assPayStageHouseMapper = null;

	@Resource(name = "matStoreMapper")
	private final MatStoreMapper matStoreMapper = null;

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 添加050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050901 资产有偿调拨入库单主表（无形资产)
		AssSellInHouse assSellInHouse = queryByCode(entityMap);

		if (assSellInHouse != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assSellInHouseMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assSellInHouseMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assSellInHouseMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assSellInHouseMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assSellInHouseMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			for (int i = 0; i < entityList.size(); i++) {
				List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
				listMap.add(entityList.get(i));
				delCard(listMap);
			}
			assSellInDetailHouseMapper.deleteBatch(entityList);
			assSellMapHouseMapper.deleteBatch(entityList);
			assSellInHouseMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	private void delCard(List<Map<String, Object>> entityList) {
		try {
			Map<String, Object> inMapVo = new HashMap<String, Object>();
			inMapVo.put("group_id", entityList.get(0).get("group_id"));
			inMapVo.put("hos_id", entityList.get(0).get("hos_id"));
			inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
			inMapVo.put("ass_in_no", entityList.get(0).get("ass_in_no"));
			inMapVo.put("ass_id", entityList.get(0).get("ass_id"));
			inMapVo.put("ass_no", entityList.get(0).get("ass_no"));

			List<Map<String, Object>> cardList = assCardHouseMapper.queryByAssInNo(inMapVo);
			if (cardList.size() > 0) {

				// 删除资产文档
				for (Map<String, Object> fileMap : cardList) {
					List<AssFileHouse> assFileHouseList = (List<AssFileHouse>) assFileHouseMapper
							.queryExists(fileMap);
					if (assFileHouseList.size() > 0 && assFileHouseList != null) {
						for (AssFileHouse assFileHouse : assFileHouseList) {
							if (assFileHouse.getFile_url() != null && !assFileHouse.getFile_url().equals("")) {
								String file_name = assFileHouse.getFile_url().substring(
										assFileHouse.getFile_url().lastIndexOf("/") + 1,
										assFileHouse.getFile_url().length());
								String path = assFileHouse.getFile_url().substring(0,
										assFileHouse.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0, path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assFileHouse.getAss_card_no(), path);
							}
						}
					}
				}
				// 删除资产照片
				for (Map<String, Object> photoMap : cardList) {
					List<AssPhotoHouse> assPhotoHouseList = (List<AssPhotoHouse>) assPhotoHouseMapper
							.queryExists(photoMap);
					if (assPhotoHouseList.size() > 0 && assPhotoHouseList != null) {
						for (AssPhotoHouse assPhotoHouse : assPhotoHouseList) {
							if (assPhotoHouse.getFile_url() != null && !assPhotoHouse.getFile_url().equals("")) {
								String file_name = assPhotoHouse.getFile_url().substring(
										assPhotoHouse.getFile_url().lastIndexOf("/") + 1,
										assPhotoHouse.getFile_url().length());
								String path = assPhotoHouse.getFile_url().substring(0,
										assPhotoHouse.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0, path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assPhotoHouse.getAss_card_no(), path);
							}
						}
					}
				}
				assShareDeptRHouseMapper.deleteBatch(cardList);
				assShareDeptHouseMapper.deleteBatch(cardList);
				assResourceHouseMapper.deleteBatch(cardList);
				assFileHouseMapper.deleteBatch(cardList);
				assPhotoHouseMapper.deleteBatch(cardList);
				assAccessoryHouseMapper.deleteBatch(cardList);
				assPayStageHouseMapper.deleteBatch(cardList);
				assDepreAccHouseMapper.deleteBatch(cardList);
				assDepreManageHouseMapper.deleteBatch(cardList);
				assCardHouseMapper.deleteBatchByAssInNo(cardList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	/**
	 * @Description 添加050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("sell_in_no", entityMap.get("sell_in_no"));
		mapVo.put("ass_in_no", entityMap.get("sell_in_no"));
		if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
			entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
			entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
		}
		entityMap.put("bill_table", "ASS_SELL_IN_HOUSE");
		List<AssSellInHouse> list = (List<AssSellInHouse>) assSellInHouseMapper.queryExists(mapVo);
		try {
			if (list.size() > 0) {
				int state = assSellInHouseMapper.update(entityMap);
			} else {
				String sell_in_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("sell_in_no", sell_in_no);
				int state = assSellInHouseMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			List<Map<String, Object>> cardList = assCardHouseMapper.queryByAssInNo(mapVo);

			List<Map<String, Object>> delCardList = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> det : cardList) {
				boolean falg = false;
				for (Map<String, Object> temp : detail) {
					if (temp.get("ass_id") == null || "".equals(temp.get("ass_id"))) {
						continue;
					}
					if (det.get("ass_id").toString().equals(temp.get("ass_id").toString().split("@")[0])) {
						falg = true;
						break;
					}
				}
				if (falg == false) {
					delCardList.add(det);
				}
			}
			if (delCardList.size() > 0) {
				delCard(delCardList);
			}

			Double price = 0.0;
			Double add_depre = 0.0;
			Double cur_money = 0.0;
			Double fore_money = 0.0;

			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("sell_in_no", entityMap.get("sell_in_no"));

				String ass_id_no = detailVo.get("ass_id").toString();
				detailVo.put("ass_id", ass_id_no.split("@")[0]);
				detailVo.put("ass_no", ass_id_no.split("@")[1]);
				detailVo.put("ass_ori_card_no", detailVo.get("ass_ori_card_no"));

				if (detailVo.get("price") != null && !detailVo.get("price").equals("")) {
					detailVo.put("price", detailVo.get("price").toString());
					price = price + Double.parseDouble(detailVo.get("price").toString());
				} else {
					detailVo.put("price", "0");
				}

				if (detailVo.get("add_depre") != null && !detailVo.get("add_depre").equals("")) {
					detailVo.put("add_depre", detailVo.get("add_depre").toString());
					add_depre = add_depre + Double.parseDouble(detailVo.get("add_depre").toString());
				} else {
					detailVo.put("add_depre", "0");
				}

				if (detailVo.get("cur_money") != null && !detailVo.get("cur_money").equals("")) {
					detailVo.put("cur_money", detailVo.get("cur_money").toString());
					cur_money = cur_money + Double.parseDouble(detailVo.get("cur_money").toString());
				} else {
					detailVo.put("cur_money", "0");
				}

				if (detailVo.get("fore_money") != null && !detailVo.get("fore_money").equals("")) {
					detailVo.put("fore_money", detailVo.get("fore_money").toString());
					fore_money = fore_money + Double.parseDouble(detailVo.get("fore_money").toString());
				} else {
					detailVo.put("fore_money", "0");
				}

				if (detailVo.get("add_depre_month") != null && !detailVo.get("add_depre_month").equals("")) {
					detailVo.put("add_depre_month", String.valueOf(detailVo.get("add_depre_month")));
				} else {
					detailVo.put("add_depre_month", "0");
				}

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}


				listVo.add(detailVo);
			}

			assSellInDetailHouseMapper.delete(entityMap);
			assSellInDetailHouseMapper.addBatch(listVo);
			entityMap.put("price", price + "");
			entityMap.put("add_depre", add_depre + "");
			entityMap.put("cur_money", cur_money + "");
			entityMap.put("fore_money", fore_money + "");

			assSellInHouseMapper.updateInMoney(entityMap);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"sell_in_no\":\"" + entityMap.get("sell_in_no").toString()
					+ "\",\"price\":\"" + price + "\",\"add_depre\":\"" + add_depre + "\",\"cur_money\":\"" + cur_money
					+ "\",\"fore_money\":\"" + fore_money + "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssSellInHouse> list = (List<AssSellInHouse>) assSellInHouseMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssSellInHouse> list = (List<AssSellInHouse>) assSellInHouseMapper.query(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assSellInHouseMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssSellInHouse
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assSellInHouseMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssSellInHouse>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assSellInHouseMapper.queryExists(entityMap);
	}

	private String getAssYear() {
		String yearMonth = MyConfig.getCurAccYearMonth();
		return yearMonth.substring(0, 4);
	}

	private String getAssMonth() {
		String yearMonth = MyConfig.getCurAccYearMonth();
		return yearMonth.substring(4, 6);
	}

	// 引入DAO操作
	@Resource(name = "assBillNoMapper")
	private final AssBillNoMapper assBillNoMapper = null;

	private int updateAssBillNoMaxNo(String tableName) throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("bill_table", tableName);
		return assBillNoMapper.updateAssBillNoMaxNo(entityMap);
	}

	private String getBillNOSeqNo(String tableName) throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("bill_table", tableName.toUpperCase());
		AssBillNo bill = queryAssBillNoByName(entityMap);
		String pref = bill.getPref();
		int seq_no = bill.getSeq_no();
		int max_no = bill.getMax_no();
		return pref + Strings.alignRight(max_no, seq_no, '0');
	}

	public AssBillNo queryAssBillNoByName(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("bill_table") != null) {
			entityMap.put("bill_table", entityMap.get("bill_table").toString().toUpperCase());
		}
		return assBillNoMapper.queryAssBillNoByCode(entityMap);
	}

	private String formatNumberZero(Object obj) {
		if (obj != null) {
			if (obj.toString().equals("0")) {
				return "0";
			} else {
				return obj.toString();
			}
		} else {
			return "";
		}
	}

	private String initCardBySellInHouse(Map<String, Object> map) {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			List<Map<String, Object>> list = assSellInDetailHouseMapper.queryByInit(map);

			delCard(list);
			for (Map<String, Object> temp : list) {
				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_HOUSE");
				updateAssBillNoMaxNo("ASS_CARD_HOUSE");
				//卡片表
				entityMap.put("group_id", map.get("group_id"));
				entityMap.put("hos_id", map.get("hos_id"));
				entityMap.put("copy_code", map.get("copy_code"));
				entityMap.put("ass_card_no", ass_card_no);
				entityMap.put("ass_ori_card_no", temp.get("ass_ori_card_no"));
				entityMap.put("ass_id", temp.get("ass_id"));
				entityMap.put("ass_no", temp.get("ass_no"));
				entityMap.put("gb_code", temp.get("gb_code"));
				/*entityMap.put("ass_mondl", temp.get("ass_model"));
				entityMap.put("ass_brand", temp.get("ass_brand"));*/
				entityMap.put("ass_amount", "1");
				
				entityMap.put("price", formatNumberZero(temp.get("price")));
				entityMap.put("depre_money", formatNumberZero(temp.get("add_depre")));
				entityMap.put("manage_depre_money", "0");
				entityMap.put("cur_money", formatNumberZero(temp.get("cur_money")));
				entityMap.put("fore_money", formatNumberZero(temp.get("fore_money")));
				entityMap.put("buy_type", temp.get("bus_type_code"));
				entityMap.put("unit_code", temp.get("unit_code"));
				entityMap.put("use_state", "0");
				entityMap.put("dispose_type", null);
				entityMap.put("dispose_date", null);
				entityMap.put("dispose_cost", null);
				entityMap.put("dispose_income", null);
				entityMap.put("dispose_tax", null);
				entityMap.put("is_depr", formatNumberZero(temp.get("is_depre")));
				entityMap.put("depr_method", temp.get("ass_depre_code"));
				entityMap.put("acc_depre_amount", temp.get("depre_years"));
				entityMap.put("is_manage_depre", temp.get("is_manage_depre"));
				entityMap.put("manage_depr_method", temp.get("manage_depr_method"));
				entityMap.put("manage_depre_amount", formatNumberZero(temp.get("manage_depre_amount")));
				entityMap.put("ven_id", null);
				entityMap.put("ven_no", null);
				entityMap.put("house_area", null);
				entityMap.put("struct_code", null);
				entityMap.put("use_area", null);
				entityMap.put("base_area", null);
				entityMap.put("accept_date", null);
				entityMap.put("cert_name", null);    
				entityMap.put("cert_code", null);    
				entityMap.put("cert_date", null); 
				entityMap.put("prop_code", null);    
				entityMap.put("gain_date", null);
				entityMap.put("run_date", null);
				entityMap.put("location", null);
				entityMap.put("dept_id", null);
				entityMap.put("dept_no", null);
				entityMap.put("note", temp.get("note"));
				entityMap.put("ass_in_no", temp.get("sell_in_no"));
				entityMap.put("sell_in_no", temp.get("sell_in_no"));
				entityMap.put("in_date", null);
				entityMap.put("is_init", "0");
				entityMap.put("pact_code", null);
				entityMap.put("apply_date", null);
				entityMap.put("add_depre_month", temp.get("add_depre_month"));
				entityMap.put("add_manage_month", "0");
				
				entityMap.put("proc_store_id", null);
				entityMap.put("proc_store_no", null);
				entityMap.put("simple_name", null);
				entityMap.put("reg_no", null);

				
				//分摊科室设置_房屋及建筑
				//entityMap.put("area", null);//面积
				//资产资金来源匹配表_房屋及建筑 
				entityMap.put("source_id", 1);
				entityMap.put("pay_money", "0");
				entityMap.put("unpay_money", formatNumberZero(temp.get("price")));

				/*if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
						&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
					entityMap.put("area", 1);
					entityMap.put("ass_year", getAssYear());
					entityMap.put("ass_month", getAssMonth());
				}else{
					return "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}";
				}*/


				listVo.add(entityMap);
			}
			assCardHouseMapper.addBatch(listVo);
			assResourceHouseMapper.addBatch(listVo);
			//assShareDeptRHouseMapper.addBatch(listVo);
			//assShareDeptHouseMapper.addBatch(listVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	private String initCardByImportSellInHouse(Map<String, Object> map) {
		String basePath = "ass";
		String group_id = map.get("group_id").toString();
		String hos_id = map.get("hos_id").toString();
		String copy_code = map.get("copy_code").toString();
		String bus_type_code = map.get("bus_type_code").toString();
		map.put("ass_card_no", map.get("ass_ori_card_no"));
		try {
			AssCardHouse assCardHouse = assCardHouseMapper.queryByCode(map);// 查询原始的卡片
			if (assCardHouse == null) {// 通过原始卡片没有找到对应卡片-以普通方式生成卡片
				return initCardBySellInHouse(map);
			} else {
				List<Map<String, Object>> list = assSellInDetailHouseMapper.queryByInit(map);// 查找对应明细数据
				for(int i = 0 ;i < list.size(); i++){
					delCard(list);
				}
				
				Map<String, Object> temp = list.get(0);
				
				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_HOUSE");
				updateAssBillNoMaxNo("ASS_CARD_HOUSE");
				entityMap.put("group_id", map.get("group_id"));
				entityMap.put("hos_id", map.get("hos_id"));
				entityMap.put("copy_code", map.get("copy_code"));
				entityMap.put("ass_card_no", ass_card_no);
				entityMap.put("ass_ori_card_no", map.get("ass_ori_card_no"));
				entityMap.put("ass_id", assCardHouse.getAss_id());
				entityMap.put("ass_no", assCardHouse.getAss_no());
				entityMap.put("gb_code", assCardHouse.getGb_code());
				entityMap.put("ass_amount", "1");
				entityMap.put("unit_code", assCardHouse.getUnit_code());
				entityMap.put("ven_id", assCardHouse.getVen_id());
				entityMap.put("ven_no", assCardHouse.getVen_no());

			
				entityMap.put("price", formatNumberZero(temp.get("price")));
				entityMap.put("depre_money", formatNumberZero(temp.get("add_depre")));
				entityMap.put("add_depre_month", formatNumberZero(temp.get("add_depre_month")));
				entityMap.put("cur_money", formatNumberZero(temp.get("cur_money")));
				entityMap.put("fore_money", formatNumberZero(temp.get("fore_money")));
				entityMap.put("buy_type", list.get(0).get("bus_type_code"));
				entityMap.put("acc_depre_amount", formatNumberZero(assCardHouse.getAcc_depre_amount()));
				entityMap.put("manage_depre_amount", formatNumberZero(assCardHouse.getManage_depre_amount()));
				entityMap.put("manage_depre_money", "0");
				entityMap.put("add_manage_month", "0");
				entityMap.put("is_manage_depre", formatNumberZero(assCardHouse.getIs_manage_depre()));
				entityMap.put("manage_depr_method", assCardHouse.getManage_depr_method());
				entityMap.put("depr_method", assCardHouse.getDepr_method());
				entityMap.put("is_depr", formatNumberZero(assCardHouse.getIs_depr()));
				entityMap.put("use_state", "0");
				entityMap.put("pact_code", assCardHouse.getPact_code());
				entityMap.put("location", assCardHouse.getLocation());
				entityMap.put("note", assCardHouse.getNote());
				entityMap.put("ass_in_no", map.get("sell_in_no"));
				entityMap.put("sell_in_no", map.get("sell_in_no"));
				entityMap.put("in_date", null);
				entityMap.put("run_date", null);
				entityMap.put("house_area", null);
				entityMap.put("struct_code", null);
				entityMap.put("use_area", null);
				entityMap.put("base_area", null);
				entityMap.put("bar_code", ass_card_no);
				entityMap.put("is_init", "0");
				entityMap.put("dispose_type", null);
				entityMap.put("dispose_cost", null);
				entityMap.put("dispose_income", null);
				entityMap.put("dispose_tax", null);
				
				entityMap.put("proc_store_id", assCardHouse.getProc_store_id());
				entityMap.put("proc_store_no", assCardHouse.getProc_store_no());
				entityMap.put("simple_name", assCardHouse.getSimple_name());
				entityMap.put("reg_no", assCardHouse.getReg_no());

				/*
				if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
						&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
					entityMap.put("area", 1);
					entityMap.put("ass_year", getAssYear());
					entityMap.put("ass_month", getAssMonth());
				}else{
					return "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}";
				}*/

				//List<Map<String, Object>> shareDeptList = new ArrayList<Map<String, Object>>();
				//shareDeptList.add(entityMap);

				assCardHouseMapper.add(entityMap);// 插入卡片
				//assShareDeptRHouseMapper.addBatch(shareDeptList);
				//assShareDeptHouseMapper.addBatch(shareDeptList);
				Map<String, Object> sellMap = new HashMap<String, Object>();
				sellMap.put("group_id", map.get("out_group_id"));
				sellMap.put("hos_id", map.get("out_hos_id"));
				sellMap.put("copy_code", map.get("copy_code"));
				sellMap.put("ass_card_no", assCardHouse.getAss_card_no());
				List<Map<String, Object>> newResourceList = new ArrayList<Map<String, Object>>();
				// 平价调拨
				if (bus_type_code.equals("13")) {
					List<Map<String, Object>> resourceList = assSellOutSourceHouseMapper
							.queryBySellOutNoAndAssCardNo(sellMap);// 查询资金来源
					for (Map<String, Object> resource : resourceList) {
						resource.put("group_id", map.get("group_id"));
						resource.put("hos_id", map.get("hos_id"));
						resource.put("copy_code", map.get("copy_code"));
						resource.put("ass_card_no", ass_card_no);
						resource.put("depre_money", resource.get("add_depre") + "");
						resource.put("manage_depre_money", resource.get("add_manage_depre") + "");
						resource.put("pay_money", "0");
						resource.put("unpay_money", formatNumberZero(resource.get("price")));
						newResourceList.add(resource);
					}

				}
				// 异价调拨
				if (bus_type_code.equals("15")) {
					Map<String, Object> resource = new HashMap<String, Object>();
					resource.put("group_id", map.get("group_id"));
					resource.put("hos_id", map.get("hos_id"));
					resource.put("copy_code", map.get("copy_code"));
					resource.put("ass_card_no", ass_card_no);
					resource.put("source_id", 1);
					resource.put("manage_depre_money", "0");
					resource.put("price", list.get(0).get("price"));
					resource.put("depre_money", list.get(0).get("add_depre"));
					resource.put("cur_money", list.get(0).get("cur_money"));
					resource.put("fore_money", list.get(0).get("fore_money"));
					resource.put("pay_money", "0");
					resource.put("unpay_money", list.get(0).get("price"));
					newResourceList.add(resource);
				}

				if (newResourceList.size() > 0) {
					assResourceHouseMapper.addBatch(newResourceList);
				}

				List<Map<String, Object>> fileList = assFileHouseMapper.queryByAssCardNo(map);// 资产文档
				List<Map<String, Object>> newFileList = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> file : fileList) {
					file.put("group_id", map.get("group_id"));
					file.put("hos_id", map.get("hos_id"));
					file.put("copy_code", map.get("copy_code"));
					file.put("ass_card_no", ass_card_no);
					String fileName = file.get("file_url").toString().substring(
							file.get("file_url").toString().lastIndexOf("/") + 1,
							file.get("file_url").toString().length());
					String assFilePath = "assfile";
					String filePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assFilePath
							+ "/01/" + ass_card_no;
					FtpUtil.copyFile(file.get("file_url").toString(), filePath);
					file.put("file_url", filePath + "/" + fileName);
					newFileList.add(file);
				}
				if (newFileList.size() > 0) {
					assFileHouseMapper.addBatch(newFileList);
				}

				List<Map<String, Object>> photoList = assPhotoHouseMapper.queryByAssCardNo(map);// 资产照片
				List<Map<String, Object>> newPhotoList = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> photo : photoList) {
					photo.put("group_id", map.get("group_id"));
					photo.put("hos_id", map.get("hos_id"));
					photo.put("copy_code", map.get("copy_code"));
					photo.put("ass_card_no", ass_card_no);
					String fileName = photo.get("file_url").toString().substring(
							photo.get("file_url").toString().lastIndexOf("/") + 1,
							photo.get("file_url").toString().length());
					String assFilePath = "assphoto";
					String filePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assFilePath
							+ "/01/" + ass_card_no;
					FtpUtil.copyFile(photo.get("file_url").toString(), filePath);
					photo.put("file_url", filePath + "/" + fileName);
					newPhotoList.add(photo);
				}
				if (newPhotoList.size() > 0) {
					assPhotoHouseMapper.addBatch(newPhotoList);
				}

				List<Map<String, Object>> accessoryList = assAccessoryHouseMapper.queryByAssCardNo(map);// 资产附件
				List<Map<String, Object>> newAccessoryList = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> accessory : accessoryList) {
					accessory.put("group_id", map.get("group_id"));
					accessory.put("hos_id", map.get("hos_id"));
					accessory.put("copy_code", map.get("copy_code"));
					accessory.put("ass_card_no", ass_card_no);
					newAccessoryList.add(accessory);
				}
				if (newAccessoryList.size() > 0) {
					assAccessoryHouseMapper.addBatch(newAccessoryList);
				}

			}

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String initAssSellInCardHouse(Map<String, Object> map) throws DataAccessException {

		try {
			if (map.get("is_import").equals("0")) {
				return initCardBySellInHouse(map);
			} else if (map.get("is_import").equals("1")) {
				return initCardByImportSellInHouse(map);
			} else {
				return "{\"warn\":\"生成失败.\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateAudit(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assSellInHouseMapper.updateAudit(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateReAudit(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assSellInHouseMapper.updateReAudit(entityMap);

			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateConfirm(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assSellInHouseMapper.updateConfirm(entityMap);
			assCardHouseMapper.updateConfirm(entityMap);

			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String initAssSellInHouse(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> mapVo = new ArrayList<Map<String, Object>>();
		entityMap.put("bill_table", "ASS_SELL_IN_HOUSE");
		try {
			List<AssSellOutDetailHouse> outDetailList = assSellOutDetailHouseMapper
					.queryByImportSellIn(entityMap);
			String bus_type_code = entityMap.get("bus_type_code").toString();
			String sell_in_no = assBaseService.getBillNOSeqNo(entityMap);
			Double price = 0.0;
			Double add_depre = 0.0;
			Double cur_money = 0.0;
			Double fore_money = 0.0;
			for (AssSellOutDetailHouse assSellOutDetailHouse : outDetailList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", assSellOutDetailHouse.getGroup_id());
				map.put("hos_id", assSellOutDetailHouse.getHos_id());
				map.put("copy_code", assSellOutDetailHouse.getCopy_code());
				map.put("sell_in_no", sell_in_no);
				map.put("ass_id", assSellOutDetailHouse.getAss_id());
				map.put("ass_no", assSellOutDetailHouse.getAss_no());
				map.put("ass_ori_card_no", assSellOutDetailHouse.getAss_card_no());
				map.put("ass_brand", assSellOutDetailHouse.getAss_brand());
				map.put("ass_spec", assSellOutDetailHouse.getAss_spec());
				map.put("ass_model", assSellOutDetailHouse.getAss_model());
				map.put("fac_id", assSellOutDetailHouse.getFac_id());
				map.put("fac_no", assSellOutDetailHouse.getFac_no());
				Double sourcePrice = 0.0;
				Double sourceAddDepre = 0.0;
				Double curPrice = 0.0;
				Double forePrice = 0.0;

				// 平价调拨
				if (bus_type_code.equals("13")) {
					sourcePrice = assSellOutDetailHouse.getPrice();
					sourceAddDepre = 0.0;
					curPrice = assSellOutDetailHouse.getCur_money();
					forePrice = assSellOutDetailHouse.getFore_money();
					map.put("add_depre_month", "0");
				}

				// 异价调拨
				if (bus_type_code.equals("15")) {
					sourcePrice = assSellOutDetailHouse.getSell_price();
					sourceAddDepre = assSellOutDetailHouse.getAdd_depre();
					curPrice = assSellOutDetailHouse.getSell_price();
					forePrice = 0.0;
					map.put("add_depre_month", assSellOutDetailHouse.getAdd_depre_month());
				}

				map.put("price", sourcePrice + "");
				map.put("add_depre", sourceAddDepre + "");
				map.put("cur_money", curPrice + "");
				map.put("fore_money", forePrice);

				map.put("note", assSellOutDetailHouse.getNote());
				price = price + sourcePrice;
				add_depre = add_depre + sourceAddDepre;
				cur_money = cur_money + curPrice;
				fore_money = fore_money + forePrice;
				listVo.add(map);
			}
			// 主表增加
			entityMap.put("sell_in_no", sell_in_no);
			entityMap.put("state", "0");
			entityMap.put("note", "引入调剂出库");
			assSellInHouseMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			// 明细表增加
			assSellInDetailHouseMapper.addBatch(listVo);

			for (int i = 0; i < entityMap.get("sell_out_nos").toString().split(",").length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("sell_in_no", sell_in_no);
				map.put("out_group_id", entityMap.get("out_group_id"));
				map.put("out_hos_id", entityMap.get("out_hos_id"));
				map.put("out_copy_code", entityMap.get("out_copy_codes").toString().split(",")[i].replaceAll("'", ""));
				map.put("sell_out_no", entityMap.get("sell_out_nos").toString().split(",")[i].replaceAll("'", ""));
				mapVo.add(map);
			}
			// 增加关系表
			assSellMapHouseMapper.addBatch(mapVo);

			entityMap.put("price", price + "");
			entityMap.put("add_depre", add_depre + "");
			entityMap.put("cur_money", cur_money + "");
			entityMap.put("fore_money", fore_money + "");
			// 计算主表金额
			assSellInHouseMapper.updateInMoney(entityMap);
			return "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + entityMap.get("group_id") + "|"
					+ entityMap.get("hos_id") + "|" + entityMap.get("copy_code") + "|" + sell_in_no + "|1\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public Map<String,Object> printAssSellInHouseData(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssSellInHouseMapper assSellInHouseMapper = (AssSellInHouseMapper)context.getBean("assSellInHouseMapper");
			
			//查询凭证主表
			List<Map<String,Object>> mainList=assSellInHouseMapper.queryAssSellInHouseByAssInNo(map);
					
			//查询凭证明细表
			List<Map<String,Object>> detailList=assSellInHouseMapper.queryAssSellInHouseDetailByAssInNo(map);
			
			reMap.put("main", mainList);
			reMap.put("detail", detailList);
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}

	@Override
	public String initAssSellInBatchCardHouse(Map<String, Object> map)
			throws DataAccessException {
		try {
			if (map.get("is_import").equals("0")) {
				return initCardBySellBatchInHouse(map);
			} else if (map.get("is_import").equals("1")) {
				return initCardByImportSellBatchInHouse(map);
			} else {
				return "{\"warn\":\"生成失败.\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	private String initCardByImportSellBatchInHouse(Map<String, Object> map) {
		String basePath = "ass";
		String group_id = map.get("group_id").toString();
		String hos_id = map.get("hos_id").toString();
		String copy_code = map.get("copy_code").toString();
		String bus_type_code = map.get("bus_type_code").toString();
	/*	String carno = map.get("ass_ori_card_nos").toString();
		String car[] =carno.split(",");
		  final List<String> ids = new ArrayList<String>();  
		for (int i = 0; i < car.length; i++) {
			ids.add(car[i]);
		}
		map.put("ass_ori_card_no", ids);*/
		//map.put("ass_card_no", map.get("ass_ori_card_nos"));
		try {
			List<Map<String, Object>> list = assSellInDetailHouseMapper.queryByInit(map);// 查找对应明细数据
			for (int i = 0; i < list.size(); i++) {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				mapList.add(list.get(i));
				delCard(mapList);// 先删除卡片
			}
			for (Map<String, Object> map2 : list) {
				Map<String, Object> entiMap = new HashMap<String, Object>();
				entiMap.put("group_id", map2.get("group_id"));
				entiMap.put("hos_id", map2.get("hos_id"));
				entiMap.put("copy_code", map2.get("copy_code"));
				entiMap.put("ass_ori_card_no", map2.get("ass_ori_card_no").toString());
				entiMap.put("ass_card_no", map2.get("ass_ori_card_no").toString());
			
			List<AssCardHouse> cardList = (List<AssCardHouse>)assCardHouseMapper.queryByAssCardOldNo(entiMap);// 查询原始的卡片
			entiMap.put("UNIT_CODE", map2.get("UNIT_CODE"));
			entiMap.put("ass_in_no", map2.get("ass_in_no"));
			entiMap.put("ass_depre_code", map2.get("ass_depre_code"));
			entiMap.put("fore_money", map2.get("fore_money"));
			entiMap.put("ass_id", map2.get("ass_id"));
			entiMap.put("add_depre_month", map2.get("add_depre_month"));
			entiMap.put("depre_years", map2.get("depre_years"));
			entiMap.put("out_group_id", map2.get("out_group_id"));
			entiMap.put("allot_in_no", map2.get("allot_in_no	"));
			entiMap.put("ass_no", map2.get("ass_no"));
			entiMap.put("bus_type_code", map2.get("bus_type_code"));
			entiMap.put("is_measure", map2.get("is_measure"));
			entiMap.put("price", map2.get("price"));
			entiMap.put("cur_money", map2.get("cur_money"));
			entiMap.put("is_depre", map2.get("is_depre	"));
			entiMap.put("add_depre", map2.get("add_depre"));
			entiMap.put("out_hos_id", map2.get("out_hos_id"));
			entiMap.put("is_manage_depre", map2.get("is_manage_depre"));
			
			
			if (cardList == null || cardList.size() == 0) {// 通过原始卡片没有找到对应卡片-以普通方式生成卡片
				 initCardBySellInHouse(entiMap);
			} else {
			
				for(AssCardHouse assCardHouse : cardList){
				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_HOUSE");
				updateAssBillNoMaxNo("ASS_CARD_HOUSE");
				entityMap.put("group_id", map.get("group_id"));
				entityMap.put("hos_id", map.get("hos_id"));
				entityMap.put("copy_code", map.get("copy_code"));
				entityMap.put("ass_card_no", ass_card_no);
				entityMap.put("ass_ori_card_no", assCardHouse.getAss_card_no());
				entityMap.put("ass_id", assCardHouse.getAss_id());
				entityMap.put("ass_no", assCardHouse.getAss_no());
				entityMap.put("gb_code", assCardHouse.getGb_code());
				entityMap.put("ass_amount", "1");
				entityMap.put("unit_code", assCardHouse.getUnit_code());
				entityMap.put("ven_id", assCardHouse.getVen_id());
				entityMap.put("ven_no", assCardHouse.getVen_no());

			
				entityMap.put("price", formatNumberZero(map2.get("price")));
				entityMap.put("depre_money", formatNumberZero(map2.get("add_depre")));
				entityMap.put("add_depre_month", formatNumberZero(map2.get("add_depre_month")));
				entityMap.put("cur_money", formatNumberZero(map2.get("cur_money")));
				entityMap.put("fore_money", formatNumberZero(map2.get("fore_money")));
				entityMap.put("buy_type", list.get(0).get("bus_type_code"));
				entityMap.put("acc_depre_amount", formatNumberZero(assCardHouse.getAcc_depre_amount()));
				entityMap.put("manage_depre_amount", formatNumberZero(assCardHouse.getManage_depre_amount()));
				entityMap.put("manage_depre_money", "0");
				entityMap.put("add_manage_month", "0");
				entityMap.put("is_manage_depre", formatNumberZero(assCardHouse.getIs_manage_depre()));
				entityMap.put("manage_depr_method", assCardHouse.getManage_depr_method());
				entityMap.put("depr_method", assCardHouse.getDepr_method());
				entityMap.put("is_depr", formatNumberZero(assCardHouse.getIs_depr()));
				entityMap.put("use_state", "0");
				entityMap.put("pact_code", assCardHouse.getPact_code());
				entityMap.put("location", assCardHouse.getLocation());
				entityMap.put("note", assCardHouse.getNote());
				entityMap.put("ass_in_no", map.get("sell_in_no"));
				entityMap.put("sell_in_no", map.get("sell_in_no"));
				entityMap.put("in_date", null);
				entityMap.put("run_date", null);
				entityMap.put("house_area", null);
				entityMap.put("struct_code", null);
				entityMap.put("use_area", null);
				entityMap.put("base_area", null);
				entityMap.put("bar_code", ass_card_no);
				entityMap.put("is_init", "0");
				entityMap.put("dispose_type", null);
				entityMap.put("dispose_cost", null);
				entityMap.put("dispose_income", null);
				entityMap.put("dispose_tax", null);
				
				entityMap.put("proc_store_id", assCardHouse.getProc_store_id());
				entityMap.put("proc_store_no", assCardHouse.getProc_store_no());
				entityMap.put("simple_name", assCardHouse.getSimple_name());
				entityMap.put("reg_no", assCardHouse.getReg_no());

				/*
				if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
						&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
					entityMap.put("area", 1);
					entityMap.put("ass_year", getAssYear());
					entityMap.put("ass_month", getAssMonth());
				}else{
					return "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}";
				}*/

				//List<Map<String, Object>> shareDeptList = new ArrayList<Map<String, Object>>();
				//shareDeptList.add(entityMap);

				assCardHouseMapper.add(entityMap);// 插入卡片
				//assShareDeptRHouseMapper.addBatch(shareDeptList);
				//assShareDeptHouseMapper.addBatch(shareDeptList);
				Map<String, Object> sellMap = new HashMap<String, Object>();
				sellMap.put("group_id", map.get("out_group_id"));
				sellMap.put("hos_id", map.get("out_hos_id"));
				sellMap.put("copy_code", map.get("copy_code"));
				sellMap.put("ass_card_no", assCardHouse.getAss_ori_card_no());
				List<Map<String, Object>> newResourceList = new ArrayList<Map<String, Object>>();
				// 平价调拨
				if (bus_type_code.equals("13")) {
					List<Map<String, Object>> resourceList = assSellOutSourceHouseMapper
							.queryBySellOutNoAndAssCardNo(sellMap);// 查询资金来源
					for (Map<String, Object> resource : resourceList) {
						resource.put("group_id", map.get("group_id"));
						resource.put("hos_id", map.get("hos_id"));
						resource.put("copy_code", map.get("copy_code"));
						resource.put("ass_card_no", ass_card_no);
						resource.put("depre_money", resource.get("add_depre") + "");
						resource.put("pay_money", "0");
						resource.put("unpay_money", formatNumberZero(resource.get("price")));
						newResourceList.add(resource);
					}

				}
				// 异价调拨
				if (bus_type_code.equals("15")) {
					Map<String, Object> resource = new HashMap<String, Object>();
					resource.put("group_id", map.get("group_id"));
					resource.put("hos_id", map.get("hos_id"));
					resource.put("copy_code", map.get("copy_code"));
					resource.put("ass_card_no", ass_card_no);
					resource.put("source_id", 1);
					resource.put("manage_depre_money", "0");
					resource.put("price", list.get(0).get("price"));
					resource.put("depre_money", list.get(0).get("add_depre"));
					resource.put("cur_money", list.get(0).get("cur_money"));
					resource.put("fore_money", list.get(0).get("fore_money"));
					resource.put("pay_money", "0");
					resource.put("unpay_money", list.get(0).get("price"));
					newResourceList.add(resource);
				}

				if (newResourceList.size() > 0) {
					assResourceHouseMapper.addBatch(newResourceList);
				}

				List<Map<String, Object>> fileList = assFileHouseMapper.queryByAssCardNo(map2);// 资产文档
				List<Map<String, Object>> newFileList = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> file : fileList) {
					file.put("group_id", map.get("group_id"));
					file.put("hos_id", map.get("hos_id"));
					file.put("copy_code", map.get("copy_code"));
					file.put("ass_card_no", ass_card_no);
					String fileName = file.get("file_url").toString().substring(
							file.get("file_url").toString().lastIndexOf("/") + 1,
							file.get("file_url").toString().length());
					String assFilePath = "assfile";
					String filePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assFilePath
							+ "/01/" + ass_card_no;
					FtpUtil.copyFile(file.get("file_url").toString(), filePath);
					file.put("file_url", filePath + "/" + fileName);
					newFileList.add(file);
				}
				if (newFileList.size() > 0) {
					assFileHouseMapper.addBatch(newFileList);
				}

				List<Map<String, Object>> photoList = assPhotoHouseMapper.queryByAssCardNo(map2);// 资产照片
				List<Map<String, Object>> newPhotoList = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> photo : photoList) {
					photo.put("group_id", map.get("group_id"));
					photo.put("hos_id", map.get("hos_id"));
					photo.put("copy_code", map.get("copy_code"));
					photo.put("ass_card_no", ass_card_no);
					String fileName = photo.get("file_url").toString().substring(
							photo.get("file_url").toString().lastIndexOf("/") + 1,
							photo.get("file_url").toString().length());
					String assFilePath = "assphoto";
					String filePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assFilePath
							+ "/01/" + ass_card_no;
					FtpUtil.copyFile(photo.get("file_url").toString(), filePath);
					photo.put("file_url", filePath + "/" + fileName);
					newPhotoList.add(photo);
				}
				if (newPhotoList.size() > 0) {
					assPhotoHouseMapper.addBatch(newPhotoList);
				}

				List<Map<String, Object>> accessoryList = assAccessoryHouseMapper.queryByAssCardNo(map2);// 资产附件
				List<Map<String, Object>> newAccessoryList = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> accessory : accessoryList) {
					accessory.put("group_id", map.get("group_id"));
					accessory.put("hos_id", map.get("hos_id"));
					accessory.put("copy_code", map.get("copy_code"));
					accessory.put("ass_card_no", ass_card_no);
					newAccessoryList.add(accessory);
				}
				if (newAccessoryList.size() > 0) {
					assAccessoryHouseMapper.addBatch(newAccessoryList);
				}

			}
			}}
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	private String initCardBySellBatchInHouse(Map<String, Object> map) {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			List<Map<String, Object>> list = assSellInDetailHouseMapper.queryByInit(map);

			
			for (int i = 0; i < list.size(); i++) {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				mapList.add(list.get(i));
				delCard(mapList);// 先删除卡片
			}
			for (Map<String, Object> temp : list) {
				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_HOUSE");
				updateAssBillNoMaxNo("ASS_CARD_HOUSE");
				//卡片表
				entityMap.put("group_id", map.get("group_id"));
				entityMap.put("hos_id", map.get("hos_id"));
				entityMap.put("copy_code", map.get("copy_code"));
				entityMap.put("ass_card_no", ass_card_no);
				entityMap.put("ass_ori_card_no", temp.get("ass_ori_card_no"));
				entityMap.put("ass_id", temp.get("ass_id"));
				entityMap.put("ass_no", temp.get("ass_no"));
				entityMap.put("gb_code", temp.get("gb_code"));
				/*entityMap.put("ass_mondl", temp.get("ass_model"));
				entityMap.put("ass_brand", temp.get("ass_brand"));*/
				entityMap.put("ass_amount", "1");
				
				entityMap.put("price", formatNumberZero(temp.get("price")));
				entityMap.put("depre_money", formatNumberZero(temp.get("add_depre")));
				entityMap.put("manage_depre_money", "0");
				entityMap.put("cur_money", formatNumberZero(temp.get("cur_money")));
				entityMap.put("fore_money", formatNumberZero(temp.get("fore_money")));
				entityMap.put("buy_type", temp.get("bus_type_code"));
				entityMap.put("unit_code", temp.get("unit_code"));
				entityMap.put("use_state", "0");
				entityMap.put("dispose_type", null);
				entityMap.put("dispose_date", null);
				entityMap.put("dispose_cost", null);
				entityMap.put("dispose_income", null);
				entityMap.put("dispose_tax", null);
				entityMap.put("is_depr", formatNumberZero(temp.get("is_depre")));
				entityMap.put("depr_method", temp.get("ass_depre_code"));
				entityMap.put("acc_depre_amount", temp.get("depre_years"));
				entityMap.put("is_manage_depre", temp.get("is_manage_depre"));
				entityMap.put("manage_depr_method", temp.get("manage_depr_method"));
				entityMap.put("manage_depre_amount", formatNumberZero(temp.get("manage_depre_amount")));
				entityMap.put("ven_id", null);
				entityMap.put("ven_no", null);
				entityMap.put("house_area", null);
				entityMap.put("struct_code", null);
				entityMap.put("use_area", null);
				entityMap.put("base_area", null);
				entityMap.put("accept_date", null);
				entityMap.put("cert_name", null);    
				entityMap.put("cert_code", null);    
				entityMap.put("cert_date", null); 
				entityMap.put("prop_code", null);    
				entityMap.put("gain_date", null);
				entityMap.put("run_date", null);
				entityMap.put("location", null);
				entityMap.put("dept_id", null);
				entityMap.put("dept_no", null);
				entityMap.put("note", temp.get("note"));
				entityMap.put("ass_in_no", temp.get("sell_in_no"));
				entityMap.put("sell_in_no", temp.get("sell_in_no"));
				entityMap.put("in_date", null);
				entityMap.put("is_init", "0");
				entityMap.put("pact_code", null);
				entityMap.put("apply_date", null);
				entityMap.put("add_depre_month", temp.get("add_depre_month"));
				entityMap.put("add_manage_month", "0");
				
				entityMap.put("proc_store_id", null);
				entityMap.put("proc_store_no", null);
				entityMap.put("simple_name", null);
				entityMap.put("reg_no", null);
				//分摊科室设置_房屋及建筑
				//entityMap.put("area", null);//面积
				//资产资金来源匹配表_房屋及建筑 
				entityMap.put("source_id", 1);
				entityMap.put("pay_money", "0");
				entityMap.put("unpay_money", formatNumberZero(temp.get("price")));

				/*if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
						&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
					entityMap.put("area", 1);
					entityMap.put("ass_year", getAssYear());
					entityMap.put("ass_month", getAssMonth());
				}else{
					return "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}";
				}*/


				listVo.add(entityMap);
			}
			assCardHouseMapper.addBatch(listVo);
			assResourceHouseMapper.addBatch(listVo);
			//assShareDeptRHouseMapper.addBatch(listVo);
			//assShareDeptHouseMapper.addBatch(listVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryDept(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String, Object>> listDept = assSellInHouseMapper.queryDept(mapVo);
		return listDept;
	}
	/**
	 * 入库单状态查询  （打印时校验数据专用）
	 */
	@Override
	public List<String> queryAssSellInHouseStates(Map<String, Object> mapVo) throws DataAccessException {
		
		return assSellInHouseMapper.queryAssSellInHouseStates(mapVo);
	}

	@Override
	public String queryDetails(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assSellInHouseMapper.queryDetails(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assSellInHouseMapper.queryDetails(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}


}
