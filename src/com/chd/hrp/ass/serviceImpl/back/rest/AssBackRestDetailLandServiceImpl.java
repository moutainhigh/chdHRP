﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.back.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.back.rest.AssBackRestDetailLandMapper;
import com.chd.hrp.ass.dao.back.rest.AssBackRestLandMapper;
import com.chd.hrp.ass.dao.back.rest.source.AssBackRestSourceLandMapper;
import com.chd.hrp.ass.entity.back.rest.AssBackRestDetailLand;
import com.chd.hrp.ass.service.back.rest.AssBackRestDetailLandService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050701 资产其他退账单明细(土地)
 * @Table: ASS_BACK_REST_DETAIL_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("assBackRestDetailLandService")
public class AssBackRestDetailLandServiceImpl implements AssBackRestDetailLandService {

	private static Logger logger = Logger.getLogger(AssBackRestDetailLandServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assBackRestDetailLandMapper")
	private final AssBackRestDetailLandMapper assBackRestDetailLandMapper = null;

	@Resource(name = "assBackRestSourceLandMapper")
	private final AssBackRestSourceLandMapper assBackRestSourceLandMapper = null;

	@Resource(name = "assBackRestLandMapper")
	private final AssBackRestLandMapper assBackRestLandMapper = null;

	/**
	 * @Description 添加050701 资产其他退货单明细(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050701 资产其他退货单明细(土地)
		AssBackRestDetailLand assBackRestDetailLand = queryByCode(entityMap);

		if (assBackRestDetailLand != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assBackRestDetailLandMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050701 资产其他退货单明细(土地)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assBackRestDetailLandMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050701 资产其他退货单明细(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assBackRestDetailLandMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050701 资产其他退货单明细(土地)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assBackRestDetailLandMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050701 资产其他退货单明细(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assBackRestDetailLandMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050701 资产其他退货单明细(土地)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			Map<String, Object> inMapVo = new HashMap<String, Object>();
			inMapVo.put("group_id", entityList.get(0).get("group_id"));
			inMapVo.put("hos_id", entityList.get(0).get("hos_id"));
			inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
			inMapVo.put("ass_back_no", entityList.get(0).get("ass_back_no"));
			assBackRestSourceLandMapper.deleteBatch(entityList);
			assBackRestDetailLandMapper.deleteBatch(entityList);

			List<AssBackRestDetailLand> list = (List<AssBackRestDetailLand>) assBackRestDetailLandMapper
					.queryExists(inMapVo);

			double back_money = 0;
			if (list != null) {
				for (AssBackRestDetailLand temp : list) {
					back_money += temp.getPrice();
				}
			}
			inMapVo.put("back_money", back_money + "");
			assBackRestLandMapper.updateBackMoney(inMapVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"back_money\":\"" + back_money + "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加050701 资产其他退货单明细(土地)<BR>
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
		// 判断是否存在对象050701 资产其他退货单明细(土地)
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssBackRestDetailLand> list = (List<AssBackRestDetailLand>) assBackRestDetailLandMapper
				.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assBackRestDetailLandMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assBackRestDetailLandMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集050701 资产其他退货单明细(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssBackRestDetailLand> list = (List<AssBackRestDetailLand>) assBackRestDetailLandMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssBackRestDetailLand> list = (List<AssBackRestDetailLand>) assBackRestDetailLandMapper
					.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050701 资产其他退货单明细(土地)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assBackRestDetailLandMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050701 资产其他退货单明细(土地)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssBackRestDetailLand
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assBackRestDetailLandMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050701 资产其他退货单明细(土地)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssBackRestDetailLand>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assBackRestDetailLandMapper.queryExists(entityMap);
	}

	@Override
	public List<AssBackRestDetailLand> queryByAssBackNo(Map<String, Object> entityMap) throws DataAccessException {
		return assBackRestDetailLandMapper.queryByAssBackNo(entityMap);
	}

}
