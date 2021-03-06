﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.in.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.card.AssCardLandMapper;
import com.chd.hrp.ass.dao.file.AssFileLandMapper;
import com.chd.hrp.ass.dao.in.rest.AssInRestMainLandMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoLandMapper;
import com.chd.hrp.ass.dao.prepay.AssPrePayMapper;
import com.chd.hrp.ass.dao.resource.AssResourceLandMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptLandMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRLandMapper;
import com.chd.hrp.ass.entity.in.rest.AssInRestMainLand;
import com.chd.hrp.ass.service.in.rest.AssInRestMainLandService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050701 资产其他入账主表(土地)
 * @Table: ASS_IN_REST_MAIN_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("assInRestMainLandService")
public class AssInRestMainLandServiceImpl implements AssInRestMainLandService {

	private static Logger logger = Logger.getLogger(AssInRestMainLandServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assInRestMainLandMapper")
	private final AssInRestMainLandMapper assInRestMainLandMapper = null;
	@Resource(name = "assCardLandMapper")
	private final AssCardLandMapper assCardLandMapper = null;

	@Resource(name = "assResourceLandMapper")
	private final AssResourceLandMapper assResourceLandMapper = null;

	@Resource(name = "assShareDeptLandMapper")
	private final AssShareDeptLandMapper assShareDeptLandMapper = null;

	@Resource(name = "assShareDeptRLandMapper")
	private final AssShareDeptRLandMapper assShareDeptRLandMapper = null;

	@Resource(name = "assFileLandMapper")
	private final AssFileLandMapper assFileLandMapper = null;

	@Resource(name = "assPhotoLandMapper")
	private final AssPhotoLandMapper assPhotoLandMapper = null;
	
	@Resource(name = "assPrePayMapper")
	private final AssPrePayMapper assPrePayMapper = null;

	/**
	 * @Description 添加050701 资产其他入账主表(房屋及建筑物)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050701 资产其他入账主表(房屋及建筑物)
		AssInRestMainLand assInRestMainLand = queryByCode(entityMap);

		if (assInRestMainLand != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assInRestMainLandMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050701 资产其他入账主表(房屋及建筑物)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assInRestMainLandMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050701 资产其他入账主表(房屋及建筑物)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assInRestMainLandMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050701 资产其他入账主表(房屋及建筑物)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assInRestMainLandMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050701 资产其他入账主表(房屋及建筑物)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assInRestMainLandMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050701 资产其他入账主表(房屋及建筑物)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assInRestMainLandMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * @Description 添加050701 资产其他入账主表(房屋及建筑物)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象050701 资产其他入账主表(房屋及建筑物)
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssInRestMainLand> list = (List<AssInRestMainLand>) assInRestMainLandMapper.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assInRestMainLandMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assInRestMainLandMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集050701 资产其他入账主表(房屋及建筑物)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssInRestMainLand> list = (List<AssInRestMainLand>) assInRestMainLandMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssInRestMainLand> list = (List<AssInRestMainLand>) assInRestMainLandMapper.query(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050701 资产其他入账主表(房屋及建筑物)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assInRestMainLandMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050701 资产其他入账主表(房屋及建筑物)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssInRestMainLand
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assInRestMainLandMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050701 资产其他入账主表(房屋及建筑物)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssInRestMainLand>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assInRestMainLandMapper.queryExists(entityMap);
	}

	@Override
	public String updateConfirm(List<Map<String, Object>> entityMap) {
		try {

			Double total_cur_money = 0.0;
			Double total_pay_money = 0.0;
			Double total_source_pay_money = 0.0;
			Map<String, Object> cardMap = new HashMap<String, Object>();
			String ass_card_nos = "";
			for (Map<String, Object> map : entityMap) {
				List<Map<String, Object>> cardList = assCardLandMapper.queryByAssInNo(map);// 通过单号查询所有卡片

				for (Map<String, Object> card : cardList) {
					ass_card_nos = ass_card_nos + "'" + card.get("ass_card_no").toString() + "',";
					card.put("naturs_code", "03");
					if (total_cur_money <= 0) {
						List<Map<String, Object>> prePaySourceList = assPrePayMapper.queryByVenAndAss(card);// 带有资金来源的核定数据
						for (Map<String, Object> prePay : prePaySourceList) {
							total_cur_money = total_cur_money + Double.parseDouble(prePay.get("cur_money").toString());
							total_source_pay_money = total_source_pay_money + Double.parseDouble(
									prePay.get("pay_money") == null ? "0" : prePay.get("pay_money").toString());
							break;
						}
					}

					List<Map<String, Object>> cardSourceList = assResourceLandMapper.queryByAssCardNoMap(card);// 每一张卡片的资金来源
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

				List<Map<String, Object>> sourceList = assResourceLandMapper.queryByAssCardIn(cardMap);

				for (int i = 0; i < sourceList.size(); i++) {
					sourceList.get(i).put("naturs_code", "03");
				}

				assPrePayMapper.updateBatchPayMoney(sourceList);
			}

			assInRestMainLandMapper.updateConfirm(entityMap);
			assCardLandMapper.updateConfirm(entityMap);

			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String initAssInCardLand(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateAudit(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateReAudit(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String,Object> printAssInRestMainData(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssInRestMainLandMapper assInRestMainLandMapper = (AssInRestMainLandMapper)context.getBean("assInRestMainLandMapper");
			
			//查询凭证主表
			List<Map<String,Object>> mainList=assInRestMainLandMapper.queryAssInRestMainByAssInNo(map);
					
			//查询凭证明细表
			List<Map<String,Object>> detailList=assInRestMainLandMapper.queryAssInRestMainDetailByAssInNo(map);
			
			reMap.put("main", mainList);
			reMap.put("detail", detailList);
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	@Override
	public Integer queryBydept(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return assInRestMainLandMapper.queryBydept(entityMap);
	}

	@Override
	public Integer queryByRdept(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return assInRestMainLandMapper.queryByRdept(entityMap);
	}
	
	/**
	 * 入库单状态查询  （打印时校验数据专用）
	 */
	@Override
	public List<String> queryAssInRestMainLandStates(Map<String, Object> mapVo) throws DataAccessException {
		
		return assInRestMainLandMapper.queryAssInRestMainLandStates(mapVo);
	}

	@Override
	public String queryDetails(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assInRestMainLandMapper.queryDetails(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assInRestMainLandMapper.queryDetails(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
}
