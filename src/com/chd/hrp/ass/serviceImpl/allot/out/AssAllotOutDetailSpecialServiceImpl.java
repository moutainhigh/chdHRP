﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.allot.out;

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
import com.chd.hrp.ass.dao.allot.out.AssAllotOutDetailSpecialMapper;
import com.chd.hrp.ass.dao.allot.out.AssAllotOutSpecialMapper;
import com.chd.hrp.ass.dao.allot.out.source.AssAllotOutSourceSpecialMapper;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailSpecial;
import com.chd.hrp.ass.service.allot.out.AssAllotOutDetailSpecialService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产无偿调拨出库单明细(专用设备)
 * @Table:
 * ASS_ALLOT_OUT_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assAllotOutDetailSpecialService")
public class AssAllotOutDetailSpecialServiceImpl implements AssAllotOutDetailSpecialService {

	private static Logger logger = Logger.getLogger(AssAllotOutDetailSpecialServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assAllotOutDetailSpecialMapper")
	private final AssAllotOutDetailSpecialMapper assAllotOutDetailSpecialMapper = null;
	
	@Resource(name = "assAllotOutSourceSpecialMapper")
	private final AssAllotOutSourceSpecialMapper assAllotOutSourceSpecialMapper = null;
	
	@Resource(name = "assAllotOutSpecialMapper")
	private final AssAllotOutSpecialMapper assAllotOutSpecialMapper = null;
    
	/**
	 * @Description 
	 * 添加050901 资产无偿调拨出库单明细(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050901 资产无偿调拨出库单明细(专用设备)
		AssAllotOutDetailSpecial assAllotOutDetailSpecial = queryByCode(entityMap);

		if (assAllotOutDetailSpecial != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assAllotOutDetailSpecialMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050901 资产无偿调拨出库单明细(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assAllotOutDetailSpecialMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050901 资产无偿调拨出库单明细(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assAllotOutDetailSpecialMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050901 资产无偿调拨出库单明细(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assAllotOutDetailSpecialMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除050901 资产无偿调拨出库单明细(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assAllotOutDetailSpecialMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050901 资产无偿调拨出库单明细(专用设备)<BR> 
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
			inMapVo.put("allot_out_no", entityList.get(0).get("allot_out_no"));
			
			assAllotOutSourceSpecialMapper.deleteBatch(entityList);
			assAllotOutDetailSpecialMapper.deleteBatch(entityList);
			
			List<AssAllotOutDetailSpecial> list = (List<AssAllotOutDetailSpecial>)assAllotOutDetailSpecialMapper.queryExists(inMapVo);
			
			double price = 0;   
			double add_depre = 0; 
			double cur_money = 0; 
			double fore_money = 0;
			
			if(list != null){
				for(AssAllotOutDetailSpecial temp :  list ){
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
			assAllotOutSpecialMapper.updateAllotOutMoney(inMapVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"price\":\""+price+"\",\"price\":\""+add_depre+"\",\"add_depre\":\""+cur_money+"\",\"fore_money\":\""+fore_money+"\"}";
		

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050901 资产无偿调拨出库单明细(专用设备)<BR> 
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
		//判断是否存在对象050901 资产无偿调拨出库单明细(专用设备)
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssAllotOutDetailSpecial> list = (List<AssAllotOutDetailSpecial>)assAllotOutDetailSpecialMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assAllotOutDetailSpecialMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assAllotOutDetailSpecialMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050901 资产无偿调拨出库单明细(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssAllotOutDetailSpecial> list = (List<AssAllotOutDetailSpecial>)assAllotOutDetailSpecialMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssAllotOutDetailSpecial> list = (List<AssAllotOutDetailSpecial>)assAllotOutDetailSpecialMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050901 资产无偿调拨出库单明细(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assAllotOutDetailSpecialMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050901 资产无偿调拨出库单明细(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssAllotOutDetailSpecial
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assAllotOutDetailSpecialMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050901 资产无偿调拨出库单明细(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssAllotOutDetailSpecial>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assAllotOutDetailSpecialMapper.queryExists(entityMap);
	}
	@Override
	public List<AssAllotOutDetailSpecial> queryByAssAllotOutNo(Map<String, Object> entityMap)
			throws DataAccessException {
		return assAllotOutDetailSpecialMapper.queryByAllotOutNo(entityMap);
	}
	@Override
	public List<AssAllotOutDetailSpecial> queryByImportAllotIn(Map<String, Object> entityMap)
			throws DataAccessException {
		return assAllotOutDetailSpecialMapper.queryByImportAllotIn(entityMap);
	}
	
}