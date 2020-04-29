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
import com.chd.hrp.ass.dao.allot.out.AssAllotOutDetailGeneralMapper;
import com.chd.hrp.ass.dao.allot.out.AssAllotOutDetailGeneralMapper;
import com.chd.hrp.ass.dao.allot.out.AssAllotOutGeneralMapper;
import com.chd.hrp.ass.dao.allot.out.source.AssAllotOutSourceGeneralMapper;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailGeneral;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailGeneral;
import com.chd.hrp.ass.service.allot.out.AssAllotOutDetailGeneralService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产无偿调拨出库单明细(一般设备)
 * @Table:
 * ASS_ALLOT_OUT_DETAIL_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assAllotOutDetailGeneralService")
public class AssAllotOutDetailGeneralServiceImpl implements AssAllotOutDetailGeneralService {

	private static Logger logger = Logger.getLogger(AssAllotOutDetailGeneralServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assAllotOutDetailGeneralMapper")
	private final AssAllotOutDetailGeneralMapper assAllotOutDetailGeneralMapper = null;
	
	@Resource(name = "assAllotOutSourceGeneralMapper")
	private final AssAllotOutSourceGeneralMapper assAllotOutSourceGeneralMapper = null;
	
	@Resource(name = "assAllotOutGeneralMapper")
	private final AssAllotOutGeneralMapper assAllotOutGeneralMapper = null;
    
	/**
	 * @Description 
	 * 添加050901 资产无偿调拨出库单明细(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050901 资产无偿调拨出库单明细(一般设备)
		AssAllotOutDetailGeneral assAllotOutDetailGeneral = queryByCode(entityMap);

		if (assAllotOutDetailGeneral != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assAllotOutDetailGeneralMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050901 资产无偿调拨出库单明细(一般设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assAllotOutDetailGeneralMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050901 资产无偿调拨出库单明细(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assAllotOutDetailGeneralMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050901 资产无偿调拨出库单明细(一般设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assAllotOutDetailGeneralMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除050901 资产无偿调拨出库单明细(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assAllotOutDetailGeneralMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050901 资产无偿调拨出库单明细(一般设备)<BR> 
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
			
			assAllotOutSourceGeneralMapper.deleteBatch(entityList);
			assAllotOutDetailGeneralMapper.deleteBatch(entityList);
			
			List<AssAllotOutDetailGeneral> list = (List<AssAllotOutDetailGeneral>)assAllotOutDetailGeneralMapper.queryExists(inMapVo);
			
			double price = 0;   
			double add_depre = 0; 
			double cur_money = 0; 
			double fore_money = 0;
			
			if(list != null){
				for(AssAllotOutDetailGeneral temp :  list ){
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
			assAllotOutGeneralMapper.updateAllotOutMoney(inMapVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"price\":\""+price+"\",\"price\":\""+add_depre+"\",\"add_depre\":\""+cur_money+"\",\"fore_money\":\""+fore_money+"\"}";
		

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050901 资产无偿调拨出库单明细(一般设备)<BR> 
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
		//判断是否存在对象050901 资产无偿调拨出库单明细(一般设备)
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssAllotOutDetailGeneral> list = (List<AssAllotOutDetailGeneral>)assAllotOutDetailGeneralMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assAllotOutDetailGeneralMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assAllotOutDetailGeneralMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050901 资产无偿调拨出库单明细(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssAllotOutDetailGeneral> list = (List<AssAllotOutDetailGeneral>)assAllotOutDetailGeneralMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssAllotOutDetailGeneral> list = (List<AssAllotOutDetailGeneral>)assAllotOutDetailGeneralMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050901 资产无偿调拨出库单明细(一般设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assAllotOutDetailGeneralMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050901 资产无偿调拨出库单明细(一般设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssAllotOutDetailGeneral
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assAllotOutDetailGeneralMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050901 资产无偿调拨出库单明细(一般设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssAllotOutDetailGeneral>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assAllotOutDetailGeneralMapper.queryExists(entityMap);
	}
	@Override
	public List<AssAllotOutDetailGeneral> queryByAssAllotOutNo(Map<String, Object> entityMap)
			throws DataAccessException {
		return assAllotOutDetailGeneralMapper.queryByAllotOutNo(entityMap);
	}
	
}
