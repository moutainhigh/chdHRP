﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.bill;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.chd.hrp.ass.dao.bill.AssBackPreBillDetailMapper;
import com.chd.hrp.ass.dao.bill.AssBackPreBillMainMapper;
import com.chd.hrp.ass.dao.bill.AssPreBillMainMapper;
import com.chd.hrp.ass.entity.bill.AssBackPreBillMain;
import com.chd.hrp.ass.entity.bill.AssPreBillMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.bill.AssBackPreBillMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_BACK_PRE_BILL_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assBackPreBillMainService")
public class AssBackPreBillMainServiceImpl implements AssBackPreBillMainService {

	private static Logger logger = Logger.getLogger(AssBackPreBillMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBackPreBillMainMapper")
	private final AssBackPreBillMainMapper assBackPreBillMainMapper = null;
	
	@Resource(name = "assBackPreBillDetailMapper")
	private final AssBackPreBillDetailMapper assBackPreBillDetailMapper= null ;
    
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	/**
	 * @Description 
	 * 添加tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象tabledesc
		AssBackPreBillMain assBackPreBillMain = queryByCode(entityMap);

		if (assBackPreBillMain != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assBackPreBillMainMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	
	
	/**
	 * @Description 预付款发票打印
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public Map<String,Object> queryAssBackPreBillDY(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssBackPreBillMainMapper assBackPreBillMainMapper = (AssBackPreBillMainMapper)context.getBean("assBackPreBillMainMapper");
			if("1".equals(String.valueOf(map.get("p_num")))){
				// 预退款发票打印模板主表
				List<Map<String,Object>> mainList=assBackPreBillMainMapper.queryAssBackPreBillBatch(map);
						
				//预退款发票打印模板从表
				List<Map<String,Object>> detailList=assBackPreBillMainMapper.queryAssBackPreBill_detail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}else{
				Map<String,Object> mainList=assBackPreBillMainMapper.querAssBackPreBillByPrint(map);
				
				//预退款发票打印模板从表
				List<Map<String,Object>> detailList=assBackPreBillMainMapper.queryAssBackPreBill_detail(map);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}
			
		
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	/**
	 * @Description 
	 * 批量添加tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBackPreBillMainMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assBackPreBillMainMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			throw new SysException(e);

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assBackPreBillMainMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			throw new SysException(e);

		}	
		
	}
	/**
	 * @Description 
	 * 删除tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assBackPreBillMainMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			throw new SysException(e);

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			assBackPreBillDetailMapper.deleteBatch(entityList);
			assBackPreBillMainMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			
			throw new SysException(e);

		}	
	}
	
	/**
	 * @Description 
	 * 添加tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		List<AssPreBillMain> list = (List<AssPreBillMain>)assBackPreBillMainMapper.queryExists(entityMap);
		
		if (list.size()>0) {

			int state = assBackPreBillMainMapper.update(entityMap);
			assBackPreBillDetailMapper.delete(entityMap);
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			for (Map<String,Object> map : detail) {
				if( map.get("ass_id")!= null && !("").equals(map.get("ass_id")) ){
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("bill_no", entityMap.get("bill_no"));
				map.put("create_emp", SessionManager.getUserId());
				map.put("create_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				map.put("ass_no", map.get("ass_id").toString().split("@")[1]);
				map.put("ass_id", map.get("ass_id").toString().split("@")[0]);
				assBackPreBillDetailMapper.add(map);
				}
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"bill_no\":\""+entityMap.get("bill_no")+"\"}";

		}
		if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
			entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
			entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
		}
		entityMap.put("bill_table", "ASS_BACK_PRE_BILL_MAIN");
		try {
			String bill_no = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("bill_no", bill_no);
			int state = assBackPreBillMainMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			for (Map<String,Object>  map : detail) {
				if(map.get("ass_id")!= null && !("").equals(map.get("ass_id"))){
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("bill_no", entityMap.get("bill_no"));
					map.put("create_emp", SessionManager.getUserId());
					map.put("create_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
					map.put("ass_no", map.get("ass_id").toString().split("@")[1]);
					map.put("ass_id", map.get("ass_id").toString().split("@")[0]);
					assBackPreBillDetailMapper.add(map);
				}
				
			}
			
			
			
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"bill_no\":\""+bill_no+"\"}";
			
		}
		
		catch (Exception e) {

			throw new SysException(e);
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssBackPreBillMain> list = (List<AssBackPreBillMain>)assBackPreBillMainMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBackPreBillMain> list = (List<AssBackPreBillMain>)assBackPreBillMainMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assBackPreBillMainMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBackPreBillMain
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assBackPreBillMainMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBackPreBillMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assBackPreBillMainMapper.queryExists(entityMap);
	}
	@Override
	public String queryAssBackPreBilldetail(Map<String, Object> mapVo) {
		List<Map<String,Object>> listVo = assBackPreBillDetailMapper.queryAssBackPreBilldetailByBillno(mapVo);
		return ChdJson.toJson(listVo);
	}
	
	/**
	 *  发票审核
	 */
	@Override
	public String checkAssBackPreBillMain(List<Map<String, Object>> listVo) {
		try {
			assBackPreBillMainMapper.checkAssBackPreBillMain(listVo);
				return "{\"msg\":\"成功.\",\"state\":\"true\"}";
			
		}
		
		catch (Exception e) {

			throw new SysException(e);

		}
	}


	@Override
	public String updateNotToExamineAssPreBillMain(List<Map<String, Object>> listVo) {
		try {
			assBackPreBillMainMapper.updateNotToExamineAssPreBillMain(listVo);
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			throw new SysException(e);
		}
		
	}


	@Override
	public List<String> queryAssBackPreBillState(Map<String, Object> mapVo)
			throws DataAccessException {
		
		return assBackPreBillMainMapper.queryAssBackPreBillState(mapVo);
	}
	
}
