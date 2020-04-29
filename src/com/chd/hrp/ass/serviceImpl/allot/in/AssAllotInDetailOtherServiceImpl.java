﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.allot.in;

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
import com.chd.hrp.ass.dao.accessory.AssAccessoryOtherMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInDetailOtherMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInDetailOtherMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInOtherMapper;
import com.chd.hrp.ass.dao.card.AssCardOtherMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccOtherMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageOtherMapper;
import com.chd.hrp.ass.dao.file.AssFileOtherMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageOtherMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoOtherMapper;
import com.chd.hrp.ass.dao.resource.AssResourceOtherMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptROtherMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptOtherMapper;
import com.chd.hrp.ass.entity.allot.in.AssAllotInDetailOther;
import com.chd.hrp.ass.entity.allot.in.AssAllotInDetailOther;
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.entity.file.AssFileOther;
import com.chd.hrp.ass.entity.photo.AssPhotoOther;
import com.chd.hrp.ass.service.allot.in.AssAllotInDetailOtherService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产无偿调拨入库明细(其他固定资产)
 * @Table:
 * ASS_ALLOT_IN_DETAIL_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assAllotInDetailOtherService")
public class AssAllotInDetailOtherServiceImpl implements AssAllotInDetailOtherService {

	private static Logger logger = Logger.getLogger(AssAllotInDetailOtherServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assAllotInDetailOtherMapper")
	private final AssAllotInDetailOtherMapper assAllotInDetailOtherMapper = null;
	
	@Resource(name = "assAllotInOtherMapper")
	private final AssAllotInOtherMapper assAllotInOtherMapper = null;
	
	@Resource(name = "assCardOtherMapper")
	private final AssCardOtherMapper assCardOtherMapper = null;
	
	@Resource(name = "assResourceOtherMapper")
	private final AssResourceOtherMapper assResourceOtherMapper = null;
	
	@Resource(name = "assShareDeptOtherMapper")
	private final AssShareDeptOtherMapper assShareDeptOtherMapper = null;
	
	@Resource(name = "assShareDeptROtherMapper")
	private final AssShareDeptROtherMapper assShareDeptROtherMapper = null;
	
	@Resource(name = "assFileOtherMapper")
	private final AssFileOtherMapper assFileOtherMapper = null;

	@Resource(name = "assPhotoOtherMapper")
	private final AssPhotoOtherMapper assPhotoOtherMapper = null;

	@Resource(name = "assAccessoryOtherMapper")
	private final AssAccessoryOtherMapper assAccessoryOtherMapper = null;

	@Resource(name = "assDepreAccOtherMapper")
	private final AssDepreAccOtherMapper assDepreAccOtherMapper = null;

	@Resource(name = "assDepreManageOtherMapper")
	private final AssDepreManageOtherMapper assDepreManageOtherMapper = null;

	@Resource(name = "assPayStageOtherMapper")
	private final AssPayStageOtherMapper assPayStageOtherMapper = null;
    
	/**
	 * @Description 
	 * 添加050901 资产无偿调拨入库明细(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050901 资产无偿调拨入库明细(专用设备)
		AssAllotInDetailOther assAllotInDetailOther = queryByCode(entityMap);

		if (assAllotInDetailOther != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assAllotInDetailOtherMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050901 资产无偿调拨入库明细(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assAllotInDetailOtherMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050901 资产无偿调拨入库明细(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assAllotInDetailOtherMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050901 资产无偿调拨入库明细(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assAllotInDetailOtherMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除050901 资产无偿调拨入库明细(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assAllotInDetailOtherMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050901 资产无偿调拨入库明细(专用设备)<BR> 
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
			inMapVo.put("allot_in_no", entityList.get(0).get("allot_in_no"));
			inMapVo.put("ass_in_no", entityList.get(0).get("ass_in_no"));
			
			delCard(entityList);
			
			assAllotInDetailOtherMapper.deleteBatch(entityList);
			
			List<AssAllotInDetailOther> list = (List<AssAllotInDetailOther>)assAllotInDetailOtherMapper.queryExists(inMapVo);
			
			double price = 0;   
			double add_depre = 0; 
			double cur_money = 0; 
			double fore_money = 0;
			
			if(list != null){
				for(AssAllotInDetailOther temp :  list ){
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
			assAllotInOtherMapper.updateInMoney(inMapVo);
			
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
			List<Map<String, Object>> cardList = assCardOtherMapper.queryByAssInNo(inMapVo);
			if (cardList.size() > 0) {
				// 删除条码文件
				for (Map<String, Object> map : cardList) {
					AssCardOther assCardOther = assCardOtherMapper.queryByCode(map);
					if (assCardOther.getBar_url() != null && !assCardOther.getBar_url().equals("")) {
						String file_name = assCardOther.getBar_url().substring(
								assCardOther.getBar_url().lastIndexOf("/") + 1, assCardOther.getBar_url().length());
						String path = assCardOther.getBar_url().substring(0,
								assCardOther.getBar_url().lastIndexOf("/"));
						FtpUtil.deleteFile(path, file_name);
					}
				}

				// 删除资产文档
				for (Map<String, Object> fileMap : cardList) {
					List<AssFileOther> assFileOtherList = (List<AssFileOther>) assFileOtherMapper
							.queryExists(fileMap);
					if (assFileOtherList.size() > 0 && assFileOtherList != null) {
						for (AssFileOther assFileOther : assFileOtherList) {
							if (assFileOther.getFile_url() != null && !assFileOther.getFile_url().equals("")) {
								String file_name = assFileOther.getFile_url().substring(
										assFileOther.getFile_url().lastIndexOf("/") + 1,
										assFileOther.getFile_url().length());
								String path = assFileOther.getFile_url().substring(0,
										assFileOther.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0,path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assFileOther.getAss_card_no(), path);
							}
						}
					}
				}
				// 删除资产照片
				for (Map<String, Object> photoMap : cardList) {
					List<AssPhotoOther> assPhotoOtherList = (List<AssPhotoOther>) assPhotoOtherMapper
							.queryExists(photoMap);
					if (assPhotoOtherList.size() > 0 && assPhotoOtherList != null) {
						for (AssPhotoOther assPhotoOther : assPhotoOtherList) {
							if (assPhotoOther.getFile_url() != null && !assPhotoOther.getFile_url().equals("")) {
								String file_name = assPhotoOther.getFile_url().substring(
										assPhotoOther.getFile_url().lastIndexOf("/") + 1,
										assPhotoOther.getFile_url().length());
								String path = assPhotoOther.getFile_url().substring(0,
										assPhotoOther.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0,path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assPhotoOther.getAss_card_no(), path);
							}
						}
					}
				}
				assShareDeptROtherMapper.deleteBatch(cardList);
				assShareDeptOtherMapper.deleteBatch(cardList);
				assResourceOtherMapper.deleteBatch(cardList);
				assFileOtherMapper.deleteBatch(cardList);
				assPhotoOtherMapper.deleteBatch(cardList);
				assAccessoryOtherMapper.deleteBatch(cardList);
				assPayStageOtherMapper.deleteBatch(cardList);
				assDepreAccOtherMapper.deleteBatch(cardList);
				assDepreManageOtherMapper.deleteBatch(cardList);
				assCardOtherMapper.deleteBatchByAssInNo(cardList);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * @Description 
	 * 添加050901 资产无偿调拨入库明细(专用设备)<BR> 
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
		//判断是否存在对象050901 资产无偿调拨入库明细(专用设备)
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssAllotInDetailOther> list = (List<AssAllotInDetailOther>)assAllotInDetailOtherMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assAllotInDetailOtherMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assAllotInDetailOtherMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050901 资产无偿调拨入库明细(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssAllotInDetailOther> list = (List<AssAllotInDetailOther>)assAllotInDetailOtherMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssAllotInDetailOther> list = (List<AssAllotInDetailOther>)assAllotInDetailOtherMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050901 资产无偿调拨入库明细(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assAllotInDetailOtherMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050901 资产无偿调拨入库明细(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssAllotInDetailOther
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assAllotInDetailOtherMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050901 资产无偿调拨入库明细(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssAllotInDetailOther>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assAllotInDetailOtherMapper.queryExists(entityMap);
	}
	@Override
	public List<AssAllotInDetailOther> queryByAssInNo(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInDetailOtherMapper.queryByAssInNo(entityMap);
	}
	
	@Override
	public List<Map<String, Object>> queryByInit(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInDetailOtherMapper.queryByInit(entityMap);
	}
	
}
