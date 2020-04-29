﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.sell.in;

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
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.accessory.AssAccessoryGeneralMapper;
import com.chd.hrp.ass.dao.card.AssCardGeneralMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccGeneralMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageGeneralMapper;
import com.chd.hrp.ass.dao.file.AssFileGeneralMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageGeneralMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoGeneralMapper;
import com.chd.hrp.ass.dao.resource.AssResourceGeneralMapper;
import com.chd.hrp.ass.dao.sell.in.AssSellInDetailGeneralMapper;
import com.chd.hrp.ass.dao.sell.in.AssSellInGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptGeneralMapper;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.file.AssFileGeneral;
import com.chd.hrp.ass.entity.photo.AssPhotoGeneral;
import com.chd.hrp.ass.entity.sell.in.AssSellInDetailGeneral;
import com.chd.hrp.ass.service.sell.in.AssSellInDetailGeneralService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产有偿调拨入库明细(一般设备)
 * @Table:
 * ASS_SELL_IN_DETAIL_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assSellInDetailGeneralService")
public class AssSellInDetailGeneralServiceImpl implements AssSellInDetailGeneralService {

	private static Logger logger = Logger.getLogger(AssSellInDetailGeneralServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assSellInDetailGeneralMapper")
	private final AssSellInDetailGeneralMapper assSellInDetailGeneralMapper = null;
	
	@Resource(name = "assSellInGeneralMapper")
	private final AssSellInGeneralMapper assSellInGeneralMapper = null;
	
	@Resource(name = "assCardGeneralMapper")
	private final AssCardGeneralMapper assCardGeneralMapper = null;
	
	@Resource(name = "assResourceGeneralMapper")
	private final AssResourceGeneralMapper assResourceGeneralMapper = null;
	
	@Resource(name = "assShareDeptGeneralMapper")
	private final AssShareDeptGeneralMapper assShareDeptGeneralMapper = null;
	
	@Resource(name = "assShareDeptRGeneralMapper")
	private final AssShareDeptRGeneralMapper assShareDeptRGeneralMapper = null;
	
	@Resource(name = "assFileGeneralMapper")
	private final AssFileGeneralMapper assFileGeneralMapper = null;

	@Resource(name = "assPhotoGeneralMapper")
	private final AssPhotoGeneralMapper assPhotoGeneralMapper = null;

	@Resource(name = "assAccessoryGeneralMapper")
	private final AssAccessoryGeneralMapper assAccessoryGeneralMapper = null;

	@Resource(name = "assDepreAccGeneralMapper")
	private final AssDepreAccGeneralMapper assDepreAccGeneralMapper = null;

	@Resource(name = "assDepreManageGeneralMapper")
	private final AssDepreManageGeneralMapper assDepreManageGeneralMapper = null;

	@Resource(name = "assPayStageGeneralMapper")
	private final AssPayStageGeneralMapper assPayStageGeneralMapper = null;
    
	/**
	 * @Description 
	 * 添加050901 资产有偿调拨入库明细(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050901 资产有偿调拨入库明细(一般设备)
		AssSellInDetailGeneral assSellInDetailGeneral = queryByCode(entityMap);

		if (assSellInDetailGeneral != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assSellInDetailGeneralMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050901 资产有偿调拨入库明细(一般设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assSellInDetailGeneralMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050901 资产有偿调拨入库明细(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assSellInDetailGeneralMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050901 资产有偿调拨入库明细(一般设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assSellInDetailGeneralMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除050901 资产有偿调拨入库明细(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assSellInDetailGeneralMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050901 资产有偿调拨入库明细(一般设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			Map<String, Object> inMapVo =new HashMap<String, Object>();
			inMapVo.put("group_id",entityList.get(0).get("group_id"));
			inMapVo.put("hos_id",entityList.get(0).get("hos_id"));
			inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
			inMapVo.put("sell_in_no", entityList.get(0).get("sell_in_no"));
			inMapVo.put("ass_in_no", entityList.get(0).get("ass_in_no"));
			
			delCard(entityList);
			
			assSellInDetailGeneralMapper.deleteBatch(entityList);
			
			List<AssSellInDetailGeneral> list = (List<AssSellInDetailGeneral>)assSellInDetailGeneralMapper.queryExists(inMapVo);
			
			double price = 0;   
			double add_depre = 0; 
			double cur_money = 0; 
			double fore_money = 0;
			
			if(list != null){
				for(AssSellInDetailGeneral temp :  list ){
					price += temp.getPrice();
					add_depre += temp.getAdd_depre();
					cur_money += temp.getCur_money();
					fore_money += temp.getFore_money();
				}
			}
			inMapVo.put("price", price+"");
			inMapVo.put("add_depre", add_depre+"");
			inMapVo.put("cur_money", cur_money+"");
			inMapVo.put("fore_money", fore_money+"");
			assSellInGeneralMapper.updateInMoney(inMapVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"price\":\""+price+"\",\"price\":\""+add_depre+"\",\"add_depre\":\""+cur_money+"\",\"fore_money\":\""+fore_money+"\"}";
		
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
			List<Map<String, Object>> cardList = assCardGeneralMapper.queryByAssInNo(inMapVo);
			if (cardList.size() > 0) {
				// 删除条码文件
				for (Map<String, Object> map : cardList) {
					AssCardGeneral assCardGeneral = assCardGeneralMapper.queryByCode(map);
					if (assCardGeneral.getBar_url() != null && !assCardGeneral.getBar_url().equals("")) {
						String file_name = assCardGeneral.getBar_url().substring(
								assCardGeneral.getBar_url().lastIndexOf("/") + 1, assCardGeneral.getBar_url().length());
						String path = assCardGeneral.getBar_url().substring(0,
								assCardGeneral.getBar_url().lastIndexOf("/"));
						FtpUtil.deleteFile(path, file_name);
					}
				}

				// 删除资产文档
				for (Map<String, Object> fileMap : cardList) {
					List<AssFileGeneral> assFileGeneralList = (List<AssFileGeneral>) assFileGeneralMapper
							.queryExists(fileMap);
					if (assFileGeneralList.size() > 0 && assFileGeneralList != null) {
						for (AssFileGeneral assFileGeneral : assFileGeneralList) {
							if (assFileGeneral.getFile_url() != null && !assFileGeneral.getFile_url().equals("")) {
								String file_name = assFileGeneral.getFile_url().substring(
										assFileGeneral.getFile_url().lastIndexOf("/") + 1,
										assFileGeneral.getFile_url().length());
								String path = assFileGeneral.getFile_url().substring(0,
										assFileGeneral.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0,path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assFileGeneral.getAss_card_no(), path);
							}
						}
					}
				}
				// 删除资产照片
				for (Map<String, Object> photoMap : cardList) {
					List<AssPhotoGeneral> assPhotoGeneralList = (List<AssPhotoGeneral>) assPhotoGeneralMapper
							.queryExists(photoMap);
					if (assPhotoGeneralList.size() > 0 && assPhotoGeneralList != null) {
						for (AssPhotoGeneral assPhotoGeneral : assPhotoGeneralList) {
							if (assPhotoGeneral.getFile_url() != null && !assPhotoGeneral.getFile_url().equals("")) {
								String file_name = assPhotoGeneral.getFile_url().substring(
										assPhotoGeneral.getFile_url().lastIndexOf("/") + 1,
										assPhotoGeneral.getFile_url().length());
								String path = assPhotoGeneral.getFile_url().substring(0,
										assPhotoGeneral.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0,path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assPhotoGeneral.getAss_card_no(), path);
							}
						}
					}
				}
				assShareDeptRGeneralMapper.deleteBatch(cardList);
				assShareDeptGeneralMapper.deleteBatch(cardList);
				assResourceGeneralMapper.deleteBatch(cardList);
				assFileGeneralMapper.deleteBatch(cardList);
				assPhotoGeneralMapper.deleteBatch(cardList);
				assAccessoryGeneralMapper.deleteBatch(cardList);
				assPayStageGeneralMapper.deleteBatch(cardList);
				assDepreAccGeneralMapper.deleteBatch(cardList);
				assDepreManageGeneralMapper.deleteBatch(cardList);
				assCardGeneralMapper.deleteBatchByAssInNo(cardList);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * @Description 
	 * 添加050901 资产有偿调拨入库明细(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象050901 资产有偿调拨入库明细(一般设备)
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssSellInDetailGeneral> list = (List<AssSellInDetailGeneral>)assSellInDetailGeneralMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assSellInDetailGeneralMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assSellInDetailGeneralMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050901 资产有偿调拨入库明细(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssSellInDetailGeneral> list = (List<AssSellInDetailGeneral>)assSellInDetailGeneralMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssSellInDetailGeneral> list = (List<AssSellInDetailGeneral>)assSellInDetailGeneralMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050901 资产有偿调拨入库明细(一般设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assSellInDetailGeneralMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050901 资产有偿调拨入库明细(一般设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssSellInDetailGeneral
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assSellInDetailGeneralMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050901 资产有偿调拨入库明细(一般设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssSellInDetailGeneral>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assSellInDetailGeneralMapper.queryExists(entityMap);
	}
	@Override
	public List<AssSellInDetailGeneral> queryByAssInNo(Map<String, Object> entityMap) throws DataAccessException {
		return assSellInDetailGeneralMapper.queryByAssInNo(entityMap);
	}
	
	@Override
	public List<Map<String, Object>> queryByInit(Map<String, Object> entityMap) throws DataAccessException {
		return assSellInDetailGeneralMapper.queryByInit(entityMap);
	}
	
}
