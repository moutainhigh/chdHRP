﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.in;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.accessory.AssAccessoryInassetsMapper;
import com.chd.hrp.ass.dao.bill.AssBillDetailMapper;
import com.chd.hrp.ass.dao.bill.AssBillMainMapper;
import com.chd.hrp.ass.dao.bill.AssBillStageMapper;
import com.chd.hrp.ass.dao.card.AssCardInassetsMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInassetsMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInassetsMapper;
import com.chd.hrp.ass.dao.dict.AssBillNoMapper;
import com.chd.hrp.ass.dao.file.AssFileInassetsMapper;
import com.chd.hrp.ass.dao.in.AssInDetailInassetsMapper;
import com.chd.hrp.ass.dao.in.AssInMainInassetsMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInassetsMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoInassetsMapper;
import com.chd.hrp.ass.dao.prepay.AssPrePayMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInassetsMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInassetsMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRInassetsMapper;
import com.chd.hrp.ass.entity.dict.AssBillNo;
import com.chd.hrp.ass.entity.file.AssFileInassets;
import com.chd.hrp.ass.entity.in.AssInMainInassets;
import com.chd.hrp.ass.entity.photo.AssPhotoInassets;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.in.AssInMainInassetsService;
import com.chd.hrp.mat.dao.info.basic.MatPayTermMapper;
import com.chd.hrp.mat.dao.info.basic.MatStoreMapper;
import com.chd.hrp.mat.entity.MatPayTerm;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 资产入库主表(无形资产)
 * @Table: ASS_IN_MAIN_INASSETS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
 
@Service("assInMainInassetsService")
public class AssInMainInassetsServiceImpl implements AssInMainInassetsService {    

	private static Logger logger = Logger.getLogger(AssInMainInassetsServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assInMainInassetsMapper")
	private final AssInMainInassetsMapper assInMainInassetsMapper = null;

	@Resource(name = "assInDetailInassetsMapper")
	private final AssInDetailInassetsMapper assInDetailInassetsMapper = null;

	@Resource(name = "assCardInassetsMapper")
	private final AssCardInassetsMapper assCardInassetsMapper = null;

	@Resource(name = "assResourceInassetsMapper")
	private final AssResourceInassetsMapper assResourceInassetsMapper = null;

	@Resource(name = "assShareDeptInassetsMapper")
	private final AssShareDeptInassetsMapper assShareDeptInassetsMapper = null;

	@Resource(name = "assShareDeptRInassetsMapper")
	private final AssShareDeptRInassetsMapper assShareDeptRInassetsMapper = null;

	@Resource(name = "assFileInassetsMapper")
	private final AssFileInassetsMapper assFileInassetsMapper = null;

	@Resource(name = "assPhotoInassetsMapper")
	private final AssPhotoInassetsMapper assPhotoInassetsMapper = null;

	@Resource(name = "assAccessoryInassetsMapper")
	private final AssAccessoryInassetsMapper assAccessoryInassetsMapper = null;

	@Resource(name = "assDepreAccInassetsMapper")
	private final AssDepreAccInassetsMapper assDepreAccInassetsMapper = null;

	@Resource(name = "assDepreManageInassetsMapper")
	private final AssDepreManageInassetsMapper assDepreManageInassetsMapper = null;

	@Resource(name = "assPayStageInassetsMapper")
	private final AssPayStageInassetsMapper assPayStageInassetsMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "assPrePayMapper")
	private final AssPrePayMapper assPrePayMapper = null;

	@Resource(name = "matPayTermMapper")
	private final MatPayTermMapper matPayTermMapper = null;
	
	@Resource(name = "matStoreMapper")
	private final MatStoreMapper matStoreMapper = null;

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
	
	@Resource(name = "assBillMainMapper")
	private final AssBillMainMapper assBillMainMapper = null;

	@Resource(name = "assBillStageMapper")
	private final AssBillStageMapper assBillStageMapper = null;

	@Resource(name = "assBillDetailMapper")
	private final AssBillDetailMapper assBillDetailMapper = null;

	/**
	 * @Description 添加资产入库主表(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	/**
	 * @Description 批量添加资产入库主表(无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		return null;

	}

	/**
	 * @Description 更新资产入库主表(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		return null;

	}

	/**
	 * @Description 批量更新资产入库主表(无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		return null;

	}

	/**
	 * @Description 删除资产入库主表(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		return null;

	}

	/**
	 * @Description 批量删除资产入库主表(无形资产)<BR>
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
			assInDetailInassetsMapper.deleteBatch(entityList);
			assInMainInassetsMapper.deleteBatch(entityList);

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
			inMapVo.put("ass_spec", entityList.get(0).get("ass_spec"));
			inMapVo.put("ass_mondl", entityList.get(0).get("ass_model"));
			inMapVo.put("ass_brand", entityList.get(0).get("ass_brand"));
			List<Map<String, Object>> cardList = assCardInassetsMapper.queryByAssInNo(inMapVo);
			if (cardList.size() > 0) {
				
				// 删除资产文档
				for (Map<String, Object> fileMap : cardList) {
					List<AssFileInassets> assFileInassetsList = (List<AssFileInassets>) assFileInassetsMapper
							.queryExists(fileMap);
					if (assFileInassetsList.size() > 0 && assFileInassetsList != null) {
						for (AssFileInassets assFileInassets : assFileInassetsList) {
							if (assFileInassets.getFile_url() != null && !assFileInassets.getFile_url().equals("")) {
								String file_name = assFileInassets.getFile_url().substring(
										assFileInassets.getFile_url().lastIndexOf("/") + 1,
										assFileInassets.getFile_url().length());
								String path = assFileInassets.getFile_url().substring(0,
										assFileInassets.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0, path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assFileInassets.getAss_card_no(), path);
							}
						}
					}
				}
				// 删除资产照片
				for (Map<String, Object> photoMap : cardList) {
					List<AssPhotoInassets> assPhotoInassetsList = (List<AssPhotoInassets>) assPhotoInassetsMapper
							.queryExists(photoMap);
					if (assPhotoInassetsList.size() > 0 && assPhotoInassetsList != null) {
						for (AssPhotoInassets assPhotoInassets : assPhotoInassetsList) {
							if (assPhotoInassets.getFile_url() != null && !assPhotoInassets.getFile_url().equals("")) {
								String file_name = assPhotoInassets.getFile_url().substring(
										assPhotoInassets.getFile_url().lastIndexOf("/") + 1,
										assPhotoInassets.getFile_url().length());
								String path = assPhotoInassets.getFile_url().substring(0,
										assPhotoInassets.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0, path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assPhotoInassets.getAss_card_no(), path);
							}
						}
					}
				}
				assShareDeptRInassetsMapper.deleteBatch(cardList);
				assShareDeptInassetsMapper.deleteBatch(cardList);
				assResourceInassetsMapper.deleteBatch(cardList);
				assFileInassetsMapper.deleteBatch(cardList);
				assPhotoInassetsMapper.deleteBatch(cardList);
				assAccessoryInassetsMapper.deleteBatch(cardList);
				assPayStageInassetsMapper.deleteBatch(cardList);
				assDepreAccInassetsMapper.deleteBatch(cardList);
				assDepreManageInassetsMapper.deleteBatch(cardList);
				assCardInassetsMapper.deleteBatchByAssInNo(cardList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	/**
	 * @Description 添加资产入库主表(无形资产)<BR>
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
		mapVo.put("ass_in_no", entityMap.get("ass_in_no"));

		List<AssInMainInassets> list = (List<AssInMainInassets>) assInMainInassetsMapper.queryExists(mapVo);
		try {
			if (list.size() > 0) {
				int state = assInMainInassetsMapper.update(entityMap);
			} else {
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_IN_MAIN_INASSETS");
				String ass_in_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("ass_in_no", ass_in_no);
				int state = assInMainInassetsMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			List<Map<String, Object>> cardList = assCardInassetsMapper.queryByAssInNo(mapVo);

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
			Double in_money = 0.0;
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("ass_in_no", entityMap.get("ass_in_no"));

				String ass_id_no = detailVo.get("ass_id").toString();
				detailVo.put("ass_id", ass_id_no.split("@")[0]);
				detailVo.put("ass_no", ass_id_no.split("@")[1]);

				detailVo.put("in_amount", Integer.parseInt(detailVo.get("in_amount").toString()));
				
				if (detailVo.get("price") != null && !detailVo.get("price").equals("")) {
					detailVo.put("price", Double.parseDouble(detailVo.get("price").toString()));
				} else {
					detailVo.put("price", "0");
				}

				in_money = in_money + Double.parseDouble(detailVo.get("price").toString())
						* Integer.parseInt(detailVo.get("in_amount").toString());

				if (detailVo.get("fac_id") != null && !detailVo.get("fac_id").equals("")
						&& !detailVo.get("fac_id").toString().equals("@")) {
					String fac_id_no = detailVo.get("fac_id").toString();
					detailVo.put("fac_id", fac_id_no.split("@")[0]);
					detailVo.put("fac_no", fac_id_no.split("@")[1]);
				} else {
					detailVo.put("fac_id", "");
					detailVo.put("fac_no", "");
				}

				if (detailVo.get("unit_code") != null && !detailVo.get("unit_code").equals("")) {
					detailVo.put("unit_code", detailVo.get("unit_code"));
				} else {
					detailVo.put("unit_code", "");
				}

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}

				if (detailVo.get("ass_spec") != null && !detailVo.get("ass_spec").equals("")) {
					detailVo.put("ass_spec", detailVo.get("ass_spec"));
				} else {
					detailVo.put("ass_spec", "");
				}

				if (detailVo.get("ass_model") != null && !detailVo.get("ass_model").equals("")) {
					detailVo.put("ass_model", detailVo.get("ass_model"));
				} else {
					detailVo.put("ass_model", "");
				}

				if (detailVo.get("ass_brand") != null && !detailVo.get("ass_brand").equals("")) {
					detailVo.put("ass_brand", detailVo.get("ass_brand"));
				} else {
					detailVo.put("ass_brand", "");
				}
				
				if (detailVo.get("ass_purpose") != null && !detailVo.get("ass_purpose").equals("")) {
					detailVo.put("ass_purpose", detailVo.get("ass_purpose"));
				} else {
					detailVo.put("ass_purpose", "");
				}
				
				if (detailVo.get("reg_no") != null && !detailVo.get("reg_no").equals("")) {
					detailVo.put("reg_no", detailVo.get("reg_no"));
				} else {
					detailVo.put("reg_no", "");
				}

				listVo.add(detailVo);
			}

			assInDetailInassetsMapper.delete(entityMap);
			assInDetailInassetsMapper.addBatch(listVo);
			entityMap.put("in_money", in_money+"");
			assInMainInassetsMapper.updateInMoney(entityMap);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"ass_in_no\":\"" + entityMap.get("ass_in_no").toString()
					+ "\",\"in_money\":\"" + in_money + "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集资产入库主表(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssInMainInassets> list = (List<AssInMainInassets>) assInMainInassetsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssInMainInassets> list = (List<AssInMainInassets>) assInMainInassetsMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象资产入库主表(无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assInMainInassetsMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取资产入库主表(无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssInMainInassets
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assInMainInassetsMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取资产入库主表(无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssInMainInassets>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assInMainInassetsMapper.queryExists(entityMap);
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

	@Override
	public String initAssInCardInassets(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			String basePath = "ass";
			String group_id = mapVo.get("group_id").toString();
			String hos_id = mapVo.get("hos_id").toString();
			String copy_code = mapVo.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/05/";// 资产性质
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/05/";// 资产性质
			List<Map<String, Object>> list = assInDetailInassetsMapper.queryByInit(mapVo);

			delCard(list);

			for (Map<String, Object> temp : list) {
				for (int i = 0; i < Integer.parseInt(temp.get("in_amount").toString()); i++) {
					Map<String, Object> entityMap = new HashMap<String, Object>();
					
					entityMap.put("bill_table", "ASS_CARD_INASSETS");
					entityMap.put("store_code", temp.get("store_code"));
					entityMap.put("year", temp.get("year"));
					entityMap.put("month", temp.get("month"));
					entityMap.put("day", temp.get("day"));
					entityMap.put("group_id", temp.get("group_id"));
					entityMap.put("hos_id", temp.get("hos_id"));
					entityMap.put("copy_code", temp.get("copy_code"));
					String ass_card_no = assBaseService.getBillNOSeqNo(entityMap);
					assBaseService.updateAssBillNoMaxNo(entityMap);
					
					entityMap.put("naturs_code", "05");
					entityMap.put("group_id", mapVo.get("group_id"));
					entityMap.put("hos_id", mapVo.get("hos_id"));
					entityMap.put("copy_code", mapVo.get("copy_code"));
					entityMap.put("ass_card_no", ass_card_no);
					entityMap.put("ass_id", temp.get("ass_id"));
					entityMap.put("ass_no", temp.get("ass_no"));
					entityMap.put("gb_code", temp.get("gb_code"));
					entityMap.put("ass_spec", temp.get("ass_spec"));
					entityMap.put("ass_mondl", temp.get("ass_model"));
					entityMap.put("ass_brand", temp.get("ass_brand"));
					entityMap.put("ass_amount", "1");
					entityMap.put("unit_code", temp.get("unit_code"));
					entityMap.put("fac_no", temp.get("fac_no"));
					entityMap.put("fac_id", temp.get("fac_id"));
					entityMap.put("ven_id", temp.get("ven_id"));
					entityMap.put("ven_no", temp.get("ven_no"));
					entityMap.put("is_dept", "0");
					entityMap.put("dept_id", temp.get("dept_id"));
					entityMap.put("dept_no", temp.get("dept_no"));
					entityMap.put("store_id", temp.get("store_id"));
					entityMap.put("store_no", temp.get("store_no"));
					entityMap.put("proc_store_id", temp.get("store_id"));
					entityMap.put("proc_store_no", temp.get("store_no"));
					
					if(temp.get("dept_id") == null || temp.get("dept_id").equals("")){
						Map<String, Object> deptMap = matStoreMapper.queryByCode(entityMap);
						if(null == deptMap.get("dept_id") || "".equals(deptMap.get("dept_id"))){
							return "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}";
						}
						if (deptMap.get("dept_id") != null && !deptMap.get("dept_id").equals("")) {
							Map<String, Object> deptNoMap = new HashMap<String, Object>();
							deptNoMap.put("group_id", deptMap.get("group_id"));
							deptNoMap.put("hos_id", deptMap.get("hos_id"));
							deptNoMap.put("dept_id", deptMap.get("dept_id"));
							DeptDict deptDict = deptDictMapper.queryDeptDictByDeptCode(deptNoMap);
							entityMap.put("dept_id", deptDict.getDept_id());
							entityMap.put("dept_no", deptDict.getDept_no());
						}
					}
					
					entityMap.put("price", formatNumberZero(temp.get("price")));
					entityMap.put("depre_money", "0");
					entityMap.put("manage_depre_money", "0");
					entityMap.put("cur_money", formatNumberZero(temp.get("price")));
					entityMap.put("fore_money", "0");
					entityMap.put("buy_type", temp.get("bus_type_code"));
					entityMap.put("use_state", "0");
					entityMap.put("is_depr", formatNumberZero(temp.get("is_depre")));
					entityMap.put("depr_method", temp.get("ass_depre_code"));
					entityMap.put("acc_depre_amount", formatNumberZero(temp.get("depre_years")));
					entityMap.put("is_manage_depre", formatNumberZero(temp.get("is_manage_depre")));
					entityMap.put("manage_depr_method", temp.get("manage_depr_method"));
					entityMap.put("manage_depre_amount", formatNumberZero(temp.get("manage_depre_amount")));
					entityMap.put("is_measure", formatNumberZero(temp.get("is_measure")));
					entityMap.put("is_throw", "0");
					entityMap.put("pact_code", null);
					entityMap.put("ins_money", null);
					entityMap.put("ins_date", null);
					entityMap.put("accept_emp", null);
					entityMap.put("accept_date", null);
					entityMap.put("service_date", null);
					entityMap.put("ass_seq_no", null);
					entityMap.put("location", null);
					entityMap.put("note", temp.get("note"));
					entityMap.put("ass_in_no", temp.get("ass_in_no"));
					entityMap.put("in_date", null);
					entityMap.put("run_date", null);
					entityMap.put("is_bar", formatNumberZero(temp.get("is_bar")));
					entityMap.put("bar_type", temp.get("bar_type"));
					entityMap.put("bar_code", ass_card_no);
					entityMap.put("is_init", "0");
					entityMap.put("dispose_type", null);
					entityMap.put("dispose_cost", null);
					entityMap.put("dispose_income", null);
					entityMap.put("dispose_tax", null);
					entityMap.put("ass_purpose", temp.get("ass_purpose"));
					entityMap.put("proj_id", temp.get("proj_id"));
					entityMap.put("proj_no", temp.get("proj_no"));
					entityMap.put("source_id", 1);
					entityMap.put("reg_no", temp.get("reg_no"));
					entityMap.put("common_name", temp.get("common_name"));
					if (temp.get("is_bar") != null && !temp.get("is_bar").equals("")) {
						if (temp.get("is_bar").toString().equals("1")) {
							if (temp.get("bar_type") != null && !temp.get("bar_type").equals("")) {
								if (temp.get("bar_type").toString().equals("1")) {
									FtpUtil.getBarcodeWriteFile(ass_card_no, "", oneFilePath, ass_card_no + ".png");// 一维码
									entityMap.put("bar_url", oneFilePath + ass_card_no + ".png");

								} else if (temp.get("bar_type").toString().equals("2")) {
									FtpUtil.getQRWriteFile(ass_card_no, "", twoFilePath, ass_card_no + ".png");// 二维码
									entityMap.put("bar_url", twoFilePath + ass_card_no + ".png");
								}
							}
						}

					}

					if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
							&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
						entityMap.put("area", 1);
						entityMap.put("ass_year", getAssYear());
						entityMap.put("ass_month", getAssMonth());
					}
					entityMap.put("reg_no", temp.get("reg_no"));
					entityMap.put("six_type_code", temp.get("type_code"));
					listVo.add(entityMap);
				}
			}

			assCardInassetsMapper.addBatch(listVo);

			Double price = Double.parseDouble(listVo.get(0).get("price").toString());

			/**
			 * 通过资产ID和供应商ID查询 查询预付款核定 如果存在，取出资金来源数据插入卡片资金来源 ，插入分期付款 如果资产原值 -
			 * 预付余额 - 回冲金额 = 0 那么分期付款为一条数据 如果资产原值 - 预付余额 - 回冲金额 > 0那么分期付款产生两条数据
			 * 资产原值 - 预付余额 - 回冲金额 = 未付金额
			 */
			listVo.get(0).put("naturs_code", "05");
			MatPayTerm matPayTerm = null;
			
			if(matPayTermMapper.queryPayTerm(listVo.get(0)) != null && matPayTermMapper.queryPayTerm(listVo.get(0)).size() > 0){
				matPayTerm = (MatPayTerm) matPayTermMapper.queryPayTerm(listVo.get(0)).get(0);
			}else{
				matPayTerm = new MatPayTerm();
				matPayTerm.setPay_term_id((long)1);
			}
			List<Map<String, Object>> prePayStageList = assPrePayMapper.queryByVenAndAss(listVo.get(0));// 汇总的核定数据
			List<Map<String, Object>> prePaySourceList = assPrePayMapper.queryByVenAndAssSource(listVo.get(0));// 带有资金来源的核定数据
			List<Map<String, Object>> payStageListVo = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> reSourceListVo = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> mapPrePay : listVo) {
				Double cur_money = 0.0;// 定义预付款变量
				Double pay_money = 0.0;// 定义预回冲变量
				if (prePayStageList.size() > 0) {
					cur_money = Double.parseDouble(prePayStageList.get(0).get("cur_money").toString());// 预付
					pay_money = Double.parseDouble(prePayStageList.get(0).get("pay_money") == null ? "0.0"
							: prePayStageList.get(0).get("pay_money").toString());// 回冲
				}
				if (prePayStageList.size() > 0) {
					if (cur_money - pay_money == 0) {
						Map<String, Object> payMap = new HashMap<String, Object>();
						Integer stage_no = assPayStageInassetsMapper.queryStageInassetsMaxNo(mapPrePay);
						payMap.put("group_id", mapPrePay.get("group_id"));
						payMap.put("hos_id", mapPrePay.get("hos_id"));
						payMap.put("copy_code", mapPrePay.get("copy_code"));
						payMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
						payMap.put("stage_no", stage_no);
						payMap.put("stage_name", "一期款");
						payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
						payMap.put("pay_plan_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
						payMap.put("pay_plan_percent", "1");
						payMap.put("pay_plan_money", price + "");
						payMap.put("pay_money", "0");
						payMap.put("unpay_money", price + "");
						payMap.put("bill_money", "0");
						payMap.put("unbill_money", price + "");
						payMap.put("ven_id", mapPrePay.get("ven_id"));
						payMap.put("ven_no", mapPrePay.get("ven_no"));
						payMap.put("note", "");
						payMap.put("is_pre", "0");
						payStageListVo.add(payMap);
					} else {
						if ((price - (cur_money - pay_money)) == 0) {
							Map<String, Object> payMap = new HashMap<String, Object>();
							Integer stage_no = assPayStageInassetsMapper.queryStageInassetsMaxNo(mapPrePay);
							payMap.put("group_id", mapPrePay.get("group_id"));
							payMap.put("hos_id", mapPrePay.get("hos_id"));
							payMap.put("copy_code", mapPrePay.get("copy_code"));
							payMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
							payMap.put("stage_no", stage_no);
							payMap.put("stage_name", "一期款");
							payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
							payMap.put("pay_plan_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
							payMap.put("pay_plan_percent", "1");
							payMap.put("pay_plan_money", price + "");
							payMap.put("pay_money", price + "");
							payMap.put("unpay_money", "0");
							payMap.put("bill_money", price + "");
							payMap.put("unbill_money", "0");
							payMap.put("ven_id", mapPrePay.get("ven_id"));
							payMap.put("ven_no", mapPrePay.get("ven_no"));
							payMap.put("note", "预付款");
							payMap.put("is_pre", "1");
							payStageListVo.add(payMap);
						}
						if ((price - (cur_money - pay_money)) > 0) {
							/**
							 * 第一条数据
							 */
							Map<String, Object> payMap = new HashMap<String, Object>();
							Integer stage_no1 = assPayStageInassetsMapper.queryStageInassetsMaxNo(mapPrePay);
							payMap.put("group_id", mapPrePay.get("group_id"));
							payMap.put("hos_id", mapPrePay.get("hos_id"));
							payMap.put("copy_code", mapPrePay.get("copy_code"));
							payMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
							payMap.put("stage_no", stage_no1);
							payMap.put("stage_name", "一期款");
							payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
							payMap.put("pay_plan_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
							BigDecimal bg = new BigDecimal((cur_money - pay_money) / price).setScale(2,
									RoundingMode.UP);
							payMap.put("pay_plan_percent", bg.doubleValue() + "");
							payMap.put("pay_plan_money", cur_money - pay_money + "");
							payMap.put("pay_money", cur_money - pay_money + "");
							payMap.put("unpay_money", "0");
							payMap.put("bill_money", cur_money - pay_money + "");
							payMap.put("unbill_money", "0");
							payMap.put("ven_id", mapPrePay.get("ven_id"));
							payMap.put("ven_no", mapPrePay.get("ven_no"));
							payMap.put("note", "预付款");
							payMap.put("is_pre", "1");
							payStageListVo.add(payMap);

							/**
							 * 第二条数据
							 */
							Double unpay_money = price - (cur_money - pay_money);
							Map<String, Object> payMap2 = new HashMap<String, Object>();
							Integer stage_no2 = assPayStageInassetsMapper.queryStageInassetsMaxNo(mapPrePay);
							payMap2.put("group_id", mapPrePay.get("group_id"));
							payMap2.put("hos_id", mapPrePay.get("hos_id"));
							payMap2.put("copy_code", mapPrePay.get("copy_code"));
							payMap2.put("ass_card_no", mapPrePay.get("ass_card_no"));
							payMap2.put("stage_no", stage_no2 + 1);
							payMap2.put("stage_name", "二期款");
							payMap2.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
							String date2 = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
							String yearMonth = date2.split("-")[0] + date2.split("-")[1];
							yearMonth = DateUtil.getNextYear_Month(yearMonth);
							date2 = yearMonth.substring(0, 4) + "-" + yearMonth.substring(4, 6) + "-"
									+ date2.split("-")[2];
							payMap2.put("pay_plan_date", date2);
							BigDecimal bg2 = new BigDecimal(unpay_money / price).setScale(2, RoundingMode.UP);
							payMap2.put("pay_plan_percent", bg2.doubleValue() + "");
							payMap2.put("pay_plan_money", unpay_money + "");
							payMap2.put("pay_money", "0");
							payMap2.put("unpay_money", unpay_money + "");
							payMap2.put("bill_money", "0");
							payMap2.put("unbill_money", unpay_money + "");
							payMap2.put("ven_id", mapPrePay.get("ven_id"));
							payMap2.put("ven_no", mapPrePay.get("ven_no"));
							payMap2.put("note", "");
							payMap2.put("is_pre", "0");
							payStageListVo.add(payMap2);
						}

						if ((price - (cur_money - pay_money)) < 0) {
							Map<String, Object> payMap = new HashMap<String, Object>();
							Integer stage_no = assPayStageInassetsMapper.queryStageInassetsMaxNo(mapPrePay);
							payMap.put("group_id", mapPrePay.get("group_id"));
							payMap.put("hos_id", mapPrePay.get("hos_id"));
							payMap.put("copy_code", mapPrePay.get("copy_code"));
							payMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
							payMap.put("stage_no", stage_no);
							payMap.put("stage_name", "一期款");
							payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
							payMap.put("pay_plan_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
							payMap.put("pay_plan_percent", "1");
							payMap.put("pay_plan_money", price + "");
							payMap.put("pay_money", price + "");
							payMap.put("unpay_money", "0");
							payMap.put("bill_money", price + "");
							payMap.put("unbill_money", "0");
							payMap.put("ven_id", mapPrePay.get("ven_id"));
							payMap.put("ven_no", mapPrePay.get("ven_no"));
							payMap.put("note", "预付款");
							payMap.put("is_pre", "1");
							payStageListVo.add(payMap);
						}
					}

				} else {
					Map<String, Object> payMap = new HashMap<String, Object>();
					Integer stage_no = assPayStageInassetsMapper.queryStageInassetsMaxNo(mapPrePay);
					payMap.put("group_id", mapPrePay.get("group_id"));
					payMap.put("hos_id", mapPrePay.get("hos_id"));
					payMap.put("copy_code", mapPrePay.get("copy_code"));
					payMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
					payMap.put("stage_no", stage_no);
					payMap.put("stage_name", "一期款");
					payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
					payMap.put("pay_plan_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					payMap.put("pay_plan_percent", "1");
					payMap.put("pay_plan_money", price + "");
					payMap.put("pay_money", "0");
					payMap.put("unpay_money", price + "");
					payMap.put("bill_money", "0");
					payMap.put("unbill_money", price + "");
					payMap.put("ven_id", mapPrePay.get("ven_id"));
					payMap.put("ven_no", mapPrePay.get("ven_no"));
					payMap.put("note", "");
					payMap.put("is_pre", "0");
					payStageListVo.add(payMap);
				}
				
				String is_source = mapVo.get("is_source").toString();
				List<Map> source_data = null;
				if(is_source.equals("1")){
					source_data = ChdJson.fromJsonAsList(Map.class, mapVo.get("source_data").toString());
				}
				
				if (prePaySourceList.size() > 0) {
					if (cur_money - pay_money == 0) {
						if(is_source.equals("1")){
							for(Map source : source_data){
								if(source.get("source_id") == null || source.get("source_id").equals("")){
									continue;
								}
								if(source.get("point") == null || source.get("point").equals("") || source.get("point").equals("0")){
									continue;
								}
						Double point = Double.parseDouble(source.get("point").toString());
						Double source_price = point;		
						Map<String, Object> sourceMap = new HashMap<String, Object>();
						sourceMap.put("group_id", mapPrePay.get("group_id"));
						sourceMap.put("hos_id", mapPrePay.get("hos_id"));
						sourceMap.put("copy_code", mapPrePay.get("copy_code"));
						sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
						sourceMap.put("source_id", source.get("source_id").toString());
						sourceMap.put("price", source_price + "");
						sourceMap.put("depre_money", mapPrePay.get("depre_money") + "");
						sourceMap.put("manage_depre_money", mapPrePay.get("manage_depre_money") + "");
						sourceMap.put("cur_money", source_price + "");
						sourceMap.put("fore_money", mapPrePay.get("fore_money") + "");
						sourceMap.put("pay_money", "0");
						sourceMap.put("unpay_money", source_price + "");
						reSourceListVo.add(sourceMap);
						}
					} else {
						Map<String, Object> sourceMap = new HashMap<String, Object>(); 
						sourceMap.put("group_id", mapPrePay.get("group_id"));
						sourceMap.put("hos_id", mapPrePay.get("hos_id"));
						sourceMap.put("copy_code", mapPrePay.get("copy_code"));
						sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
						sourceMap.put("source_id", "1");
						sourceMap.put("price", mapPrePay.get("price") + "");
						sourceMap.put("depre_money", mapPrePay.get("depre_money") + "");
						sourceMap.put("manage_depre_money", mapPrePay.get("manage_depre_money") + "");
						sourceMap.put("cur_money", mapPrePay.get("cur_money") + "");
						sourceMap.put("fore_money", mapPrePay.get("fore_money") + "");
						sourceMap.put("pay_money", "0");
						sourceMap.put("unpay_money", price + "");
						reSourceListVo.add(sourceMap);
					}
				}else{
						int i = 0;
						Double pay_money_t = 0.0;
						for (Map<String, Object> map : prePaySourceList) {
							Double source_price = Math.rint(price * (Double.parseDouble(map.get("cur_money").toString()) / cur_money));
							Double source_cur_money = Math.rint(Double.parseDouble(mapPrePay.get("cur_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / cur_money));
							Double source_fore_money = Math.rint(Double.parseDouble(mapPrePay.get("fore_money") == null || mapPrePay.get("fore_money").equals("")? "0.0":mapPrePay.get("fore_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / cur_money));
							Double source_depre_money = Math.rint(Double.parseDouble(mapPrePay.get("depre_money") == null  || mapPrePay.get("depre_money").equals("")? "0.0":mapPrePay.get("depre_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / cur_money));
							Double source_manage_depre_money = Math.rint(Double.parseDouble(mapPrePay.get("manage_depre_money") == null || mapPrePay.get("manage_depre_money").equals("")? "0.0":mapPrePay.get("manage_depre_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / cur_money));
							Map<String, Object> sourceMap = new HashMap<String, Object>();
							sourceMap.put("group_id", mapPrePay.get("group_id"));
							sourceMap.put("hos_id", mapPrePay.get("hos_id"));
							sourceMap.put("copy_code", mapPrePay.get("copy_code"));
							sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
							sourceMap.put("source_id", map.get("source_id") + "");
							sourceMap.put("price", source_price + "");
							sourceMap.put("cur_money", source_cur_money + "");
							sourceMap.put("fore_money", source_fore_money + "");
							sourceMap.put("depre_money", source_depre_money + "");
							sourceMap.put("manage_depre_money", source_manage_depre_money + "");
							sourceMap.put("pay_money",  String.valueOf((price - (cur_money - pay_money)) < 0 ? source_price : Double.parseDouble(map.get("cur_money").toString()) - Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString())));
							sourceMap.put("unpay_money", String.valueOf((price - (cur_money - pay_money)) < 0 ? "0":source_price - (Double.parseDouble(map.get("cur_money").toString()) - Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString()))));
							reSourceListVo.add(sourceMap);
							Double pay_money_s = Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString()) + (price - (cur_money - pay_money)) < 0 ? source_price : Double.parseDouble(map.get("cur_money").toString()) - Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString());
							prePaySourceList.get(i).put("pay_money", pay_money_s);
							pay_money_t = pay_money_t + pay_money_s;
							i++;
						}

						prePayStageList.get(0).put("pay_money", pay_money_t);
					}

				} else {
					if(is_source.equals("1")){
						for(Map source : source_data){
							if(source.get("source_id") == null || source.get("source_id").equals("")){
								continue;
							}
							if(source.get("point") == null || source.get("point").equals("") || source.get("point").equals("0")){
								continue;
							}
					Double point = Double.parseDouble(source.get("point").toString());
					Double source_price = point;
					Map<String, Object> sourceMap = new HashMap<String, Object>();
					sourceMap.put("group_id", mapPrePay.get("group_id"));
					sourceMap.put("hos_id", mapPrePay.get("hos_id"));
					sourceMap.put("copy_code", mapPrePay.get("copy_code"));
					sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
					sourceMap.put("source_id", source.get("source_id").toString());
					sourceMap.put("price", source_price + "");
					sourceMap.put("depre_money", mapPrePay.get("depre_money") + "");
					sourceMap.put("manage_depre_money", mapPrePay.get("manage_depre_money") + "");
					sourceMap.put("cur_money", source_price + "");
					sourceMap.put("fore_money", mapPrePay.get("fore_money") + "");
					sourceMap.put("pay_money", "0");
					sourceMap.put("unpay_money", source_price + "");
					reSourceListVo.add(sourceMap);
				}
			}else{
				Map<String, Object> sourceMap = new HashMap<String, Object>(); 
				sourceMap.put("group_id", mapPrePay.get("group_id"));
				sourceMap.put("hos_id", mapPrePay.get("hos_id"));
				sourceMap.put("copy_code", mapPrePay.get("copy_code"));
				sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
				sourceMap.put("source_id", "1");
				sourceMap.put("price", mapPrePay.get("price") + "");
				sourceMap.put("depre_money", mapPrePay.get("depre_money") + "");
				sourceMap.put("manage_depre_money", mapPrePay.get("manage_depre_money") + "");
				sourceMap.put("cur_money", mapPrePay.get("cur_money") + "");
				sourceMap.put("fore_money", mapPrePay.get("fore_money") + "");
				sourceMap.put("pay_money", "0");
				sourceMap.put("unpay_money", price + "");
				reSourceListVo.add(sourceMap);
			}
		}
	}

			if (reSourceListVo.size() > 0) {
				assResourceInassetsMapper.addBatch(reSourceListVo);
			}

			if (payStageListVo.size() > 0) {
				assPayStageInassetsMapper.addBatch(payStageListVo);
			}

			assShareDeptRInassetsMapper.addBatch(listVo);
			assShareDeptInassetsMapper.addBatch(listVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
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

	@Override
	public String updateAudit(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assInMainInassetsMapper.updateAudit(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateReAudit(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assInMainInassetsMapper.updateReAudit(entityMap);

			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateConfirm(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			Double total_cur_money = 0.0;
			Double total_pay_money = 0.0;
			Double total_source_pay_money = 0.0;
			Map<String, Object> cardMap = new HashMap<String, Object>();
			String ass_card_nos = "";
			for (Map<String, Object> map : entityMap) {
				List<Map<String, Object>> cardList = assCardInassetsMapper.queryByAssInNo(map);// 通过单号查询所有卡片

				for (Map<String, Object> card : cardList) {
					ass_card_nos = ass_card_nos + "'" + card.get("ass_card_no").toString() + "',";
					card.put("naturs_code", "05");
					if (total_cur_money <= 0) {
						List<Map<String, Object>> prePaySourceList = assPrePayMapper.queryByVenAndAss(card);// 带有资金来源的核定数据
						for (Map<String, Object> prePay : prePaySourceList) {
							total_cur_money = total_cur_money + Double.parseDouble(prePay.get("cur_money").toString());
							total_source_pay_money = total_source_pay_money + Double.parseDouble(
									prePay.get("pay_money") == null ? "0" : prePay.get("pay_money").toString());
							break;
						}
					}

					List<Map<String, Object>> cardSourceList = assResourceInassetsMapper.queryByAssCardNoMap(card);// 每一张卡片的资金来源
					for (Map<String, Object> cardSource : cardSourceList) {
						total_pay_money = total_pay_money + Double.parseDouble(cardSource.get("pay_money").toString());
					}

				}

			}
			if (total_cur_money != 0) {
				if (total_pay_money > (total_cur_money - total_source_pay_money)) {
					return "{\"warn\":\"存在卡片预付金额超过实际预付金额，不能确认! \"}";
				}
				cardMap.put("group_id", SessionManager.getGroupId());
				cardMap.put("hos_id", SessionManager.getHosId());
				cardMap.put("copy_code", SessionManager.getCopyCode());
				cardMap.put("ass_card_nos", ass_card_nos.substring(0, ass_card_nos.lastIndexOf(",")));

				List<Map<String, Object>> sourceList = assResourceInassetsMapper.queryByAssCardIn(cardMap);

				for (int i = 0; i < sourceList.size(); i++) {
					sourceList.get(i).put("naturs_code", "05");
				}

				assPrePayMapper.updateBatchPayMoney(sourceList);
			}

			assInMainInassetsMapper.updateConfirm(entityMap);
			assCardInassetsMapper.updateConfirm(entityMap);

			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 其他无形资产 采购入库  批量打印  新版打印  调用的方法
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> queryAssInassetsByPrintTemlatePrint(Map<String, Object> entityMap)throws DataAccessException {
		
		try{
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssInMainInassetsMapper assInMainInassetsMapper = (AssInMainInassetsMapper)context.getBean("assInMainInassetsMapper");
			 
			//主页面 批量打印查询
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				
				//查询 其他无形资产 采购入库 入库主表数据
				List<Map<String,Object>> map= assInMainInassetsMapper.queryAssInassetsPrintTemlateByMainBatch(entityMap);
				//查询 其他无形资产 采购入库  入库明细表数据
				List<Map<String,Object>> list= assInMainInassetsMapper.queryAssInassetsPrintTemlateByDetail(entityMap);
				
				reMap.put("main", map);
				
				reMap.put("detail", list); 
				
				return reMap;
				
			}else{ //修改页面 打印查询
				//查询 其他无形资产 采购入库 入库主表数据
				Map<String,Object> map= assInMainInassetsMapper.queryAssInassetsPrintTemlateByMain(entityMap);
				//查询 其他无形资产 采购入库  入库明细表数据
				List<Map<String,Object>> list= assInMainInassetsMapper.queryAssInassetsPrintTemlateByDetail(entityMap);
				
			
				reMap.put("main", map);
				
				reMap.put("detail", list);
				
				return reMap;
				
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
		
	}
	
	/**
	 * 其他无形资产 采购入库  入库单状态查询 （打印时校验数据专用）
	 */
	@Override
	public List<String> queryAssInassetsState(Map<String, Object> mapVo) throws DataAccessException {
		
		return assInMainInassetsMapper.queryAssInassetsState(mapVo);
	}

	@Override
	public String initAssInBatchCardInassets(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			String basePath = "ass";
			String group_id = mapVo.get("group_id").toString();
			String hos_id = mapVo.get("hos_id").toString();
			String copy_code = mapVo.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath +"/" + assTwoPath + "/05/";// 资产性质
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath +"/" + assOnePath + "/05/";// 资产性质
			List<Map<String, Object>> list = assInDetailInassetsMapper.queryByInit(mapVo);
			
			for(int i = 0 ;i < list.size(); i++){
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				mapList.add(list.get(i));
				delCard(mapList);
			}
			

			for (Map<String, Object> temp : list) {
				for (int i = 0; i < Integer.parseInt(temp.get("in_amount").toString()); i++) {
					Map<String, Object> entityMap = new HashMap<String, Object>();
					
					entityMap.put("bill_table", "ASS_CARD_INASSETS");
					entityMap.put("store_code", temp.get("store_code"));
					entityMap.put("year", temp.get("year"));
					entityMap.put("month", temp.get("month"));
					entityMap.put("day", temp.get("day"));
					entityMap.put("group_id", temp.get("group_id"));
					entityMap.put("hos_id", temp.get("hos_id"));
					entityMap.put("copy_code", temp.get("copy_code"));
					String ass_card_no = assBaseService.getBillNOSeqNo(entityMap);
					assBaseService.updateAssBillNoMaxNo(entityMap);
					
					entityMap.put("naturs_code", "05");
					entityMap.put("group_id", mapVo.get("group_id"));
					entityMap.put("hos_id", mapVo.get("hos_id"));
					entityMap.put("copy_code", mapVo.get("copy_code"));
					entityMap.put("ass_card_no", ass_card_no);
					entityMap.put("ass_id", temp.get("ass_id"));
					entityMap.put("ass_no", temp.get("ass_no"));
					entityMap.put("gb_code", temp.get("gb_code"));
					entityMap.put("ass_spec", temp.get("ass_spec"));
					entityMap.put("ass_mondl", temp.get("ass_model"));
					entityMap.put("ass_brand", temp.get("ass_brand"));
					entityMap.put("ass_amount", "1");
					entityMap.put("unit_code", temp.get("unit_code"));
					entityMap.put("fac_no", temp.get("fac_no"));
					entityMap.put("fac_id", temp.get("fac_id"));
					entityMap.put("ven_id", temp.get("ven_id"));
					entityMap.put("ven_no", temp.get("ven_no"));
					entityMap.put("is_dept", "0");
					entityMap.put("dept_id", temp.get("dept_id"));
					entityMap.put("dept_no", temp.get("dept_no"));
					entityMap.put("store_id", temp.get("store_id"));
					entityMap.put("store_no", temp.get("store_no"));
					entityMap.put("proc_store_id", temp.get("store_id"));
					entityMap.put("proc_store_no", temp.get("store_no"));
					
					if(temp.get("dept_id") == null || temp.get("dept_id").equals("")){
						Map<String, Object> deptMap = matStoreMapper.queryByCode(entityMap);
						if(null == deptMap.get("dept_id") || "".equals(deptMap.get("dept_id"))){
							return "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}";
						}
						if (deptMap.get("dept_id") != null && !deptMap.get("dept_id").equals("")) {
							Map<String, Object> deptNoMap = new HashMap<String, Object>();
							deptNoMap.put("group_id", deptMap.get("group_id"));
							deptNoMap.put("hos_id", deptMap.get("hos_id"));
							deptNoMap.put("dept_id", deptMap.get("dept_id"));
							DeptDict deptDict = deptDictMapper.queryDeptDictByDeptCode(deptNoMap);
							entityMap.put("dept_id", deptDict.getDept_id());
							entityMap.put("dept_no", deptDict.getDept_no());
						}
					}
					
					entityMap.put("price", formatNumberZero(temp.get("price")));
					entityMap.put("depre_money", "0");
					entityMap.put("manage_depre_money", "0");
					entityMap.put("cur_money", formatNumberZero(temp.get("price")));
					entityMap.put("fore_money", "0");
					entityMap.put("buy_type", temp.get("bus_type_code"));
					entityMap.put("use_state", "0");
					entityMap.put("is_depr", formatNumberZero(temp.get("is_depre")));
					entityMap.put("depr_method", temp.get("ass_depre_code"));
					entityMap.put("acc_depre_amount", formatNumberZero(temp.get("depre_years")));
					entityMap.put("is_manage_depre", formatNumberZero(temp.get("is_manage_depre")));
					entityMap.put("manage_depr_method", temp.get("manage_depr_method"));
					entityMap.put("manage_depre_amount", formatNumberZero(temp.get("manage_depre_amount")));
					entityMap.put("is_measure", formatNumberZero(temp.get("is_measure")));
					entityMap.put("is_throw", "0");
					entityMap.put("pact_code", null);
					entityMap.put("ins_money", null);
					entityMap.put("ins_date", null);
					entityMap.put("accept_emp", null);
					entityMap.put("accept_date", null);
					entityMap.put("service_date", null);
					entityMap.put("ass_seq_no", null);
					entityMap.put("location", null);
					entityMap.put("note", temp.get("note"));
					entityMap.put("ass_in_no", temp.get("ass_in_no"));
					entityMap.put("in_date", null);
					entityMap.put("run_date", null);
					entityMap.put("is_bar", formatNumberZero(temp.get("is_bar")));
					entityMap.put("bar_type", temp.get("bar_type"));
					entityMap.put("bar_code", ass_card_no);
					entityMap.put("is_init", "0");
					entityMap.put("dispose_type", null);
					entityMap.put("dispose_cost", null);
					entityMap.put("dispose_income", null);
					entityMap.put("dispose_tax", null);
					entityMap.put("ass_purpose", temp.get("ass_purpose"));
					entityMap.put("proj_id", temp.get("proj_id"));
					entityMap.put("proj_no", temp.get("proj_no"));
					entityMap.put("source_id", 1);
					entityMap.put("reg_no", temp.get("reg_no"));
					entityMap.put("common_name", temp.get("common_name"));
					if (temp.get("is_bar") != null && !temp.get("is_bar").equals("")) {
						if (temp.get("is_bar").toString().equals("1")) {
							if (temp.get("bar_type") != null && !temp.get("bar_type").equals("")) {
								if (temp.get("bar_type").toString().equals("1")) {
									FtpUtil.getBarcodeWriteFile(ass_card_no, "", oneFilePath, ass_card_no + ".png");// 一维码
									entityMap.put("bar_url", oneFilePath + ass_card_no + ".png");

								} else if (temp.get("bar_type").toString().equals("2")) {
									FtpUtil.getQRWriteFile(ass_card_no, "", twoFilePath, ass_card_no + ".png");// 二维码
									entityMap.put("bar_url", twoFilePath + ass_card_no + ".png");
								}
							}
						}

					}

					if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
							&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
						entityMap.put("area", 1);
						entityMap.put("ass_year", getAssYear());
						entityMap.put("ass_month", getAssMonth());
					}
					entityMap.put("reg_no", temp.get("reg_no"));
					entityMap.put("six_type_code", temp.get("type_code"));
					listVo.add(entityMap);
				}
			}
			
			
			assCardInassetsMapper.addBatch(listVo);

			
			List<Map<String, Object>> payStageListVo = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> reSourceListVo = new ArrayList<Map<String, Object>>();
			
			String is_source = mapVo.get("is_source").toString();
			Double total_price = 0.0;
			List<Map> source_data = null;
			if(is_source.equals("1")){
				source_data = ChdJson.fromJsonAsList(Map.class, mapVo.get("source_data").toString());
				total_price = Double.parseDouble(mapVo.get("total_price").toString());
			}
			
			for(Map<String, Object> mapPrePay :listVo){
				
				/**
				 * 通过资产ID和供应商ID查询 查询预付款核定 如果存在，取出资金来源数据插入卡片资金来源 ，插入分期付款 如果资产原值 -
				 * 预付余额 - 回冲金额 = 0 那么分期付款为一条数据 如果资产原值 - 预付余额 - 回冲金额 > 0那么分期付款产生两条数据
				 * 资产原值 - 预付余额 - 回冲金额 = 未付金额
				 */
				mapPrePay.put("naturs_code", "05");
				MatPayTerm matPayTerm = null;
				
				if(matPayTermMapper.queryPayTerm(mapPrePay) != null && matPayTermMapper.queryPayTerm(mapPrePay).size() > 0){
					matPayTerm = (MatPayTerm) matPayTermMapper.queryPayTerm(mapPrePay).get(0);
				}else{
					matPayTerm = new MatPayTerm();
					matPayTerm.setPay_term_id((long)1);
				}
				
				List<Map<String, Object>> prePayStageList = assPrePayMapper.queryByVenAndAss(mapPrePay);// 汇总的核定数据
				List<Map<String, Object>> prePaySourceList = assPrePayMapper.queryByVenAndAssSource(mapPrePay);//带有资金来源的核定数据
				
				
				Double cur_money = 0.0;//定义预付款变量
				Double pay_money = 0.0;//定义预回冲变量
				if(prePayStageList.size() > 0){
					cur_money = Double.parseDouble(prePayStageList.get(0).get("cur_money").toString());// 预付
					pay_money = Double.parseDouble(prePayStageList.get(0).get("pay_money") == null ? "0.0" :prePayStageList.get(0).get("pay_money").toString());// 回冲
				}
				if(prePayStageList.size() > 0){
					if(cur_money - pay_money == 0){
						Map<String, Object> payMap = new HashMap<String, Object>();
						Integer stage_no = assPayStageInassetsMapper.queryStageInassetsMaxNo(mapPrePay);
						payMap.put("group_id", mapPrePay.get("group_id"));
						payMap.put("hos_id", mapPrePay.get("hos_id"));
						payMap.put("copy_code", mapPrePay.get("copy_code"));
						payMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
						payMap.put("stage_no", stage_no);
						payMap.put("stage_name", "一期款");
						payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
						payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
						payMap.put("pay_plan_percent", "1");
						payMap.put("pay_plan_money", mapPrePay.get("price") + "");
						payMap.put("pay_money", "0");
						payMap.put("unpay_money", mapPrePay.get("price") + "");
						payMap.put("bill_money", "0");
						payMap.put("unbill_money", mapPrePay.get("price") + "");
						payMap.put("ven_id", mapPrePay.get("ven_id"));
						payMap.put("ven_no", mapPrePay.get("ven_no"));
						payMap.put("note", "");
						payMap.put("is_pre", "0");
						payStageListVo.add(payMap);
					}else{
						if ((Double.parseDouble(mapPrePay.get("price").toString()) - (cur_money - pay_money)) == 0) {
							Map<String, Object> payMap = new HashMap<String, Object>();
							Integer stage_no = assPayStageInassetsMapper.queryStageInassetsMaxNo(mapPrePay);
							payMap.put("group_id", mapPrePay.get("group_id"));
							payMap.put("hos_id", mapPrePay.get("hos_id"));
							payMap.put("copy_code", mapPrePay.get("copy_code"));
							payMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
							payMap.put("stage_no", stage_no);
							payMap.put("stage_name", "一期款");
							payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
							payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
							payMap.put("pay_plan_percent", "1");
							payMap.put("pay_plan_money", mapPrePay.get("price") + "");
							payMap.put("pay_money", mapPrePay.get("price") + "");
							payMap.put("unpay_money", "0");
							payMap.put("bill_money", mapPrePay.get("price") + "");
							payMap.put("unbill_money", "0");
							payMap.put("ven_id", mapPrePay.get("ven_id"));
							payMap.put("ven_no", mapPrePay.get("ven_no"));
							payMap.put("note", "预付款");
							payMap.put("is_pre", "1");
							payStageListVo.add(payMap);
						}
						if ((Double.parseDouble(mapPrePay.get("price").toString()) - (cur_money - pay_money)) > 0) {
							/**
							 * 第一条数据
							 */
							Map<String, Object> payMap = new HashMap<String, Object>();
							Integer stage_no1 = assPayStageInassetsMapper.queryStageInassetsMaxNo(mapPrePay);
							payMap.put("group_id", mapPrePay.get("group_id"));
							payMap.put("hos_id", mapPrePay.get("hos_id"));
							payMap.put("copy_code", mapPrePay.get("copy_code"));
							payMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
							payMap.put("stage_no", stage_no1);
							payMap.put("stage_name", "一期款");
							payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
							payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
							BigDecimal bg = new BigDecimal((cur_money - pay_money) / Double.parseDouble(mapPrePay.get("price").toString())).setScale(2, RoundingMode.UP);
							payMap.put("pay_plan_percent", bg.doubleValue() +"");
							payMap.put("pay_plan_money", cur_money - pay_money +"");
							payMap.put("pay_money", cur_money - pay_money +"");
							payMap.put("unpay_money", "0");
							payMap.put("bill_money", cur_money - pay_money +"");
							payMap.put("unbill_money", "0");
							payMap.put("ven_id", mapPrePay.get("ven_id"));
							payMap.put("ven_no", mapPrePay.get("ven_no"));
							payMap.put("note", "预付款");
							payMap.put("is_pre", "1");
							payStageListVo.add(payMap);

							/**
							 * 第二条数据
							 */
							Double unpay_money = Double.parseDouble(mapPrePay.get("price").toString()) - (cur_money - pay_money);
							Map<String, Object> payMap2 = new HashMap<String, Object>();
							Integer stage_no2 = assPayStageInassetsMapper.queryStageInassetsMaxNo(mapPrePay);
							payMap2.put("group_id", mapPrePay.get("group_id"));
							payMap2.put("hos_id", mapPrePay.get("hos_id"));
							payMap2.put("copy_code", mapPrePay.get("copy_code"));
							payMap2.put("ass_card_no", mapPrePay.get("ass_card_no"));
							payMap2.put("stage_no", stage_no2 + 1);
							payMap2.put("stage_name", "二期款");
							payMap2.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
							String date2 = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
							String yearMonth = date2.split("-")[0] + date2.split("-")[1];
							yearMonth = DateUtil.getNextYear_Month(yearMonth);
							date2 = yearMonth.substring(0, 4) + "-" + yearMonth.substring(4, 6) + "-" + date2.split("-")[2];
							payMap2.put("pay_plan_date", date2);
							BigDecimal bg2 = new BigDecimal(unpay_money / Double.parseDouble(mapPrePay.get("price").toString())).setScale(2, RoundingMode.UP);
							payMap2.put("pay_plan_percent", bg2.doubleValue() +"");
							payMap2.put("pay_plan_money", unpay_money +"");
							payMap2.put("pay_money", "0");
							payMap2.put("unpay_money", unpay_money +"");
							payMap2.put("bill_money", "0");
							payMap2.put("unbill_money", unpay_money +"");
							payMap2.put("ven_id", mapPrePay.get("ven_id"));
							payMap2.put("ven_no", mapPrePay.get("ven_no"));
							payMap2.put("note", "");
							payMap2.put("is_pre", "0");
							payStageListVo.add(payMap2);
						}

						if ((Double.parseDouble(mapPrePay.get("price").toString()) - (cur_money - pay_money)) < 0) {
							Map<String, Object> payMap = new HashMap<String, Object>();
							Integer stage_no = assPayStageInassetsMapper.queryStageInassetsMaxNo(mapPrePay);
							payMap.put("group_id", mapPrePay.get("group_id"));
							payMap.put("hos_id", mapPrePay.get("hos_id"));
							payMap.put("copy_code", mapPrePay.get("copy_code"));
							payMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
							payMap.put("stage_no", stage_no);
							payMap.put("stage_name", "一期款");
							payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
							payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
							payMap.put("pay_plan_percent", "1");
							payMap.put("pay_plan_money", mapPrePay.get("price") + "");
							payMap.put("pay_money", mapPrePay.get("price") + "");
							payMap.put("unpay_money", "0");
							payMap.put("bill_money", mapPrePay.get("price") + "");
							payMap.put("unbill_money", "0");
							payMap.put("ven_id", mapPrePay.get("ven_id"));
							payMap.put("ven_no", mapPrePay.get("ven_no"));
							payMap.put("note", "预付款");
							payMap.put("is_pre", "1");
							payStageListVo.add(payMap);
						}
					}
					
				}else{
					Map<String, Object> payMap = new HashMap<String, Object>();
					Integer stage_no = assPayStageInassetsMapper.queryStageInassetsMaxNo(mapPrePay);
					payMap.put("group_id", mapPrePay.get("group_id"));
					payMap.put("hos_id", mapPrePay.get("hos_id"));
					payMap.put("copy_code", mapPrePay.get("copy_code"));
					payMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
					payMap.put("stage_no", stage_no);
					payMap.put("stage_name", "一期款");
					payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
					payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					payMap.put("pay_plan_percent", "1");
					payMap.put("pay_plan_money", mapPrePay.get("price") + "");
					payMap.put("pay_money", "0");
					payMap.put("unpay_money", mapPrePay.get("price") + "");
					payMap.put("bill_money", "0");
					payMap.put("unbill_money", mapPrePay.get("price") + "");
					payMap.put("ven_id", mapPrePay.get("ven_id"));
					payMap.put("ven_no", mapPrePay.get("ven_no"));
					payMap.put("note", "");
					payMap.put("is_pre", "0");
					payStageListVo.add(payMap);
				}
				Double price = Double.parseDouble(mapPrePay.get("price").toString());
				
				if(prePaySourceList.size() > 0){
					if(cur_money - pay_money == 0){
						if(is_source.equals("1")){
							for(Map source : source_data){
								if(source.get("source_id") == null || source.get("source_id").equals("")){
									continue;
								}
								if(source.get("point") == null || source.get("point").equals("") || source.get("point").equals("0")){
									continue;
								}
								Double point = Double.parseDouble(source.get("point").toString());
								Double source_price = ( price * point ) / total_price;
								Map<String, Object> sourceMap = new HashMap<String, Object>(); 
								sourceMap.put("group_id", mapPrePay.get("group_id"));
								sourceMap.put("hos_id", mapPrePay.get("hos_id"));
								sourceMap.put("copy_code", mapPrePay.get("copy_code"));
								sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
								sourceMap.put("source_id", source.get("source_id").toString());
								sourceMap.put("price", source_price + "");
								sourceMap.put("depre_money", mapPrePay.get("depre_money") + "");
								sourceMap.put("manage_depre_money", mapPrePay.get("manage_depre_money") + "");
								sourceMap.put("cur_money", source_price + "");
								sourceMap.put("fore_money", mapPrePay.get("fore_money") + "");
								sourceMap.put("pay_money", "0");
								sourceMap.put("unpay_money", source_price + "");
								reSourceListVo.add(sourceMap);
							}
						}else{
							Map<String, Object> sourceMap = new HashMap<String, Object>(); 
							sourceMap.put("group_id", mapPrePay.get("group_id"));
							sourceMap.put("hos_id", mapPrePay.get("hos_id"));
							sourceMap.put("copy_code", mapPrePay.get("copy_code"));
							sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
							sourceMap.put("source_id", "1");
							sourceMap.put("price", mapPrePay.get("price") + "");
							sourceMap.put("depre_money", mapPrePay.get("depre_money") + "");
							sourceMap.put("manage_depre_money", mapPrePay.get("manage_depre_money") + "");
							sourceMap.put("cur_money", mapPrePay.get("cur_money") + "");
							sourceMap.put("fore_money", mapPrePay.get("fore_money") + "");
							sourceMap.put("pay_money", "0");
							sourceMap.put("unpay_money", mapPrePay.get("price") + "");
							reSourceListVo.add(sourceMap);
						}
					}else{
						int i = 0;
						Double pay_money_t = 0.0;
						for(Map<String, Object> map :prePaySourceList){
							Double source_price = Math.rint(Double.parseDouble(mapPrePay.get("price").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / cur_money));
							Double source_cur_money = Math.rint(Double.parseDouble(mapPrePay.get("cur_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / cur_money));
							Double source_fore_money = Math.rint(Double.parseDouble(mapPrePay.get("fore_money") == null || mapPrePay.get("fore_money").equals("")? "0.0":mapPrePay.get("fore_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / cur_money));
							Double source_depre_money = Math.rint(Double.parseDouble(mapPrePay.get("depre_money") == null  || mapPrePay.get("depre_money").equals("")? "0.0":mapPrePay.get("depre_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / cur_money));
							Double source_manage_depre_money = Math.rint(Double.parseDouble(mapPrePay.get("manage_depre_money") == null || mapPrePay.get("manage_depre_money").equals("")? "0.0":mapPrePay.get("manage_depre_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / cur_money));
							Map<String, Object> sourceMap = new HashMap<String, Object>(); 
							sourceMap.put("group_id", mapPrePay.get("group_id"));
							sourceMap.put("hos_id", mapPrePay.get("hos_id"));
							sourceMap.put("copy_code", mapPrePay.get("copy_code"));
							sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
							sourceMap.put("source_id", map.get("source_id") + "");
							sourceMap.put("price", source_price + "");
							sourceMap.put("cur_money", source_cur_money + "");
							sourceMap.put("fore_money", source_fore_money + "");
							sourceMap.put("depre_money", source_depre_money + "");
							sourceMap.put("manage_depre_money", source_manage_depre_money + "");
							sourceMap.put("pay_money",  String.valueOf((Double.parseDouble(mapPrePay.get("price").toString()) - (cur_money - pay_money)) < 0 ? source_price : Double.parseDouble(map.get("cur_money").toString()) - Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString())));
							sourceMap.put("unpay_money", String.valueOf((Double.parseDouble(mapPrePay.get("price").toString()) - (cur_money - pay_money)) < 0 ? "0":source_price - (Double.parseDouble(map.get("cur_money").toString()) - Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString()))));
							reSourceListVo.add(sourceMap);
							Double pay_money_s = Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString()) + (Double.parseDouble(mapPrePay.get("price").toString()) - (cur_money - pay_money)) < 0 ? source_price : Double.parseDouble(map.get("cur_money").toString()) - Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString());
							prePaySourceList.get(i).put("pay_money",pay_money_s);
							pay_money_t = pay_money_t + pay_money_s;
							i ++ ;
						}
						
						prePayStageList.get(0).put("pay_money", pay_money_t);
					}
					
				}else{
					if(is_source.equals("1")){
						for(Map source : source_data){
							if(source.get("source_id") == null || source.get("source_id").equals("")){
								continue;
							}
							if(source.get("point") == null || source.get("point").equals("") || source.get("point").equals("0")){
								continue;
							}
							Double point = Double.parseDouble(source.get("point").toString());
							Double source_price = price * (point / total_price);
							Map<String, Object> sourceMap = new HashMap<String, Object>(); 
							sourceMap.put("group_id", mapPrePay.get("group_id"));
							sourceMap.put("hos_id", mapPrePay.get("hos_id"));
							sourceMap.put("copy_code", mapPrePay.get("copy_code"));
							sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
							sourceMap.put("source_id", source.get("source_id").toString());
							sourceMap.put("price", source_price + "");
							sourceMap.put("depre_money", mapPrePay.get("depre_money") + "");
							sourceMap.put("manage_depre_money", mapPrePay.get("manage_depre_money") + "");
							sourceMap.put("cur_money", source_price + "");
							sourceMap.put("fore_money", mapPrePay.get("fore_money") + "");
							sourceMap.put("pay_money", "0");
							sourceMap.put("unpay_money", source_price + "");
							reSourceListVo.add(sourceMap);
						}
					}else{
						Map<String, Object> sourceMap = new HashMap<String, Object>(); 
						sourceMap.put("group_id", mapPrePay.get("group_id"));
						sourceMap.put("hos_id", mapPrePay.get("hos_id"));
						sourceMap.put("copy_code", mapPrePay.get("copy_code"));
						sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
						sourceMap.put("source_id", "1");
						sourceMap.put("price", mapPrePay.get("price") + "");
						sourceMap.put("depre_money", mapPrePay.get("depre_money") + "");
						sourceMap.put("manage_depre_money", mapPrePay.get("manage_depre_money") + "");
						sourceMap.put("cur_money", mapPrePay.get("cur_money") + "");
						sourceMap.put("fore_money", mapPrePay.get("fore_money") + "");
						sourceMap.put("pay_money", "0");
						sourceMap.put("unpay_money", mapPrePay.get("price") + "");
						reSourceListVo.add(sourceMap);
					}
				}
			}
			
			if(reSourceListVo.size() > 0){
				assResourceInassetsMapper.addBatch(reSourceListVo);
			}
			
			if(payStageListVo.size() > 0){
				assPayStageInassetsMapper.addBatch(payStageListVo);
			}
			
			assShareDeptInassetsMapper.addBatch(listVo);
			assShareDeptRInassetsMapper.addBatch(listVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryDetails(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assInMainInassetsMapper.queryDetails(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assInMainInassetsMapper.queryDetails(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String queryInMap(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage"); 
		
		if (sysPage.getTotal()==-1){
			
			List<AssInMainInassets> list = assInMainInassetsMapper.queryAssBackInMainInassetss(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssInMainInassets> list = assInMainInassetsMapper.queryAssBackInMainInassetss(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public String queryAssBackInMainInassets(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage"); 
		
		if (sysPage.getTotal()==-1){
			
			List<AssInMainInassets> list = assInMainInassetsMapper.queryAssBackInMainInassets(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssInMainInassets> list = assInMainInassetsMapper.queryAssBackInMaiInassets(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public Integer querycountStore(Map<String, Object> vStoreMap) {
		return assInMainInassetsMapper.querycountStore(vStoreMap);
	}

	@Override
	public Integer querycountVen(Map<String, Object> vVenMap) {
		return assInMainInassetsMapper.querycountVen(vVenMap);
	}

	@Override
	public String queryByInitOut(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage"); 
		
		if (sysPage.getTotal()==-1){
			
			List<AssInMainInassets> list = assInMainInassetsMapper.queryByInitOut(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssInMainInassets> list = assInMainInassetsMapper.queryByInitOut(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public String queryInMainByOutNo(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage"); 
		
		if (sysPage.getTotal()==-1){
			
			List<AssInMainInassets> list = assInMainInassetsMapper.queryInMainByOutNo(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssInMainInassets> list = assInMainInassetsMapper.queryInMainByOutNo(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public String updateAssInMainBillNo(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			
			String ven_id = entityMap.get("ven_id").toString();
				
			List<AssInMainInassets> invList = assInMainInassetsMapper.queryByInvoiceNo(entityMap);
				
			for(AssInMainInassets assInMainInassets : invList){
				if(assInMainInassets.getVen_id() != Integer.parseInt(ven_id)){
					return "{\"warn\":\"当前发票号已存在供应商，不能维护相同发票号不同的供应商! \"}";
				}
			}
			
			assInMainInassetsMapper.updateAssInMainBillNo(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public String assInGenerateBill(List<Map<String, Object>> listVo) throws DataAccessException {
		List<Map<String, Object>> mainListVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> detailListVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> stageListVo = new ArrayList<Map<String, Object>>();

		try {

			Date date = new Date();

			String user_id = SessionManager.getUserId();

			for (Map<String, Object> inMap : listVo) {
				// 获取主表数据
				Map<String, Object> main = assInMainInassetsMapper.queryAssGenerateBillInassets(inMap).get(0);
				// 获取从表数据
				List<Map<String, Object>> detailList = assInMainInassetsMapper.queryAssGenerateBillCard(inMap);

				Map<String, Object> mps = new HashMap<String, Object>();
				if (main.get("invoice_no") != null && !"".equals(main.get("invoice_no"))) {
					mps.put("invoice_no", main.get("invoice_no"));
				} else {
					return "{\"msg\":\"生成失败,单据中存在没有维护发票的数据.\",\"state\":\"false\"}";
				}
				if (Integer.parseInt(main.get("state").toString()) == 0) {
					return "{\"msg\":\"生成失败,单据中存在没有确认的数据.\",\"state\":\"false\"}";
				}

				String cr_date = DateUtil.dateFormat(date, "yyyy-MM-dd");
				mps.put("year", cr_date.substring(0, 4));
				mps.put("month", cr_date.substring(5, 7));
				mps.put("store_code", main.get("store_code"));
				mps.put("bill_table", "ASS_BILL_MAIN");
				mps.put("group_id", SessionManager.getGroupId());
				mps.put("hos_id", SessionManager.getHosId());
				mps.put("copy_code", SessionManager.getCopyCode());
				String bill_no = assBaseService.getBillNOSeqNo(mps);
				

				mps.put("bill_no", bill_no);
				mps.put("state", "0");
				mps.put("bill_date", main.get("invoice_date") != null && !"".equals(main.get("invoice_date"))?main.get("invoice_date"):date);

				mps.put("ven_id", main.get("ven_id"));
				mps.put("ven_no", main.get("ven_no"));

				mps.put("store_id", main.get("store_id"));
				mps.put("store_no", main.get("store_no"));

				mps.put("create_emp", user_id);
				mps.put("create_date", date);
				mps.put("bill_state", "1");

				mps.put("bill_money", main.get("in_money"));

				mps.put("note", DateUtil.dateFormat(date, "yyyyMMdd") + "入库单生成");

				mainListVo.add(mps);

				for (Map<String, Object> detMap : detailList) {
					Map<String, Object> detMps = new HashMap<String, Object>();
					detMps.put("group_id", SessionManager.getGroupId());
					detMps.put("hos_id", SessionManager.getHosId());
					detMps.put("copy_code", SessionManager.getCopyCode());
					detMps.put("bill_no", bill_no);
					detMps.put("naturs_code", "05");
					detMps.put("bill_state", "1");
					detMps.put("ass_card_no", detMap.get("ass_card_no"));
					
					if(assBillDetailMapper.queryAssCardExists(detMps) > 0){
						return "{\"msg\":\"生成失败,单据中存在卡片做过发票的数据.\",\"state\":\"false\"}";
					}
					
					
					detMps.put("bill_money", detMap.get("price"));
					detMps.put("note", "");

					detailListVo.add(detMps);

					List<Map<String, Object>> stageList = assInMainInassetsMapper.queryAssGenerateBillStage(detMps);
					for (Map<String, Object> stageMap : stageList) {
						Map<String, Object> stageMaps = new HashMap<String, Object>();
						stageMaps.put("group_id", SessionManager.getGroupId());
						stageMaps.put("hos_id", SessionManager.getHosId());
						stageMaps.put("copy_code", SessionManager.getCopyCode());
						stageMaps.put("bill_no", bill_no);
						stageMaps.put("naturs_code", "05");
						stageMaps.put("ass_card_no", stageMap.get("ass_card_no"));
						stageMaps.put("stage_no", stageMap.get("stage_no"));
						stageMaps.put("stage_name", stageMap.get("stage_name"));
						stageMaps.put("pay_plan_money", stageMap.get("pay_plan_money"));
						stageListVo.add(stageMaps);
					}

				}
				assBaseService.updateAssBillNoMaxNo(mps);
			}
			assBillMainMapper.addBatch(mainListVo);
			assBillDetailMapper.addBatch(detailListVo);
			assBillStageMapper.addBatch(stageListVo);
			
			assCardInassetsMapper.updateBillState(detailListVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
}
