﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.compilationbasic.dpetexecute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.budg.dao.business.compilationbasic.dpetexecute.BudgWorkDeptExecuteMapper;
import com.chd.hrp.budg.dao.business.compilationbasic.dpetexecute.BudgWorkDeptYearExecuteMapper;
import com.chd.hrp.budg.dao.business.compilationbasic.hosexecute.BudgWorkHosYearExecuteMapper;
import com.chd.hrp.budg.entity.BudgWorkDeptExecute;
import com.chd.hrp.budg.entity.BudgWorkHosExecute;
import com.chd.hrp.budg.service.business.compilationbasic.dpetexecute.BudgWorkDeptExecuteService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室业务执行数据
 * @Table:
 * BUDG_WORK_DEPT_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgWorkDeptExecuteService")
public class BudgWorkDeptExecuteServiceImpl implements BudgWorkDeptExecuteService {

	private static Logger logger = Logger.getLogger(BudgWorkDeptExecuteServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgWorkDeptExecuteMapper")
	private final BudgWorkDeptExecuteMapper budgWorkDeptExecuteMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgWorkHosYearExecuteMapper")
	private final BudgWorkHosYearExecuteMapper budgWorkHosYearExecuteMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgWorkDeptYearExecuteMapper")
	private final BudgWorkDeptYearExecuteMapper budgWorkDeptYearExecuteMapper = null;
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	/**
	 * @Description 
	 * 添加科室业务执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象科室业务执行数据
		BudgWorkDeptExecute budgWorkDeptExecute = queryByCode(entityMap);

		if (budgWorkDeptExecute != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgWorkDeptExecuteMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加科室业务执行数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		String str = "";
		
		try{
			
			for(Map<String,Object> item : entityList){
				
				int  count =budgWorkDeptExecuteMapper.queryDataExists(item) ;
	
				if (count>0) {
	
					str += item.get("year")+"年 "+item.get("dept_id")+"科室 "+item.get("index_code")+ "指标 " + item.get("month")+"月;"	;
				}
			}
			if(str != ""){
				
				return "{\"error\":\"添加失败！以下数据:"+str.substring(0, str.length()-1)+"已存在.\",\"state\":\"false\"}";
			
			}else{
				
				budgWorkDeptExecuteMapper.addBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}
			
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新科室业务执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgWorkDeptExecuteMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新科室业务执行数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgWorkDeptExecuteMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除科室业务执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgWorkDeptExecuteMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除科室业务执行数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgWorkDeptExecuteMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加科室业务执行数据<BR> 
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
		//判断是否存在对象科室业务执行数据
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgWorkDeptExecute> list = (List<BudgWorkDeptExecute>)budgWorkDeptExecuteMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgWorkDeptExecuteMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgWorkDeptExecuteMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集科室业务执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgWorkDeptExecute> list = (List<BudgWorkDeptExecute>)budgWorkDeptExecuteMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgWorkDeptExecute> list = (List<BudgWorkDeptExecute>)budgWorkDeptExecuteMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象科室业务执行数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkDeptExecuteMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室业务执行数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgWorkDeptExecute
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkDeptExecuteMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室业务执行数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgWorkDeptExecute>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkDeptExecuteMapper.queryExists(entityMap);
	}
	/**
	 * 指标名称下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDeptindex_code_name1(Map<String, Object> mapVo) throws DataAccessException {
      RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgWorkDeptExecuteMapper .queryBudgDeptindex_code_name1(mapVo, rowBounds));
	}
	/**
	 * 科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDeptId(Map<String, Object> mapVo) throws DataAccessException {
      RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgWorkDeptExecuteMapper .queryBudgDeptId(mapVo, rowBounds));
	}
//判断指标编码是否存在
	public int queryIndexCode(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return budgWorkDeptExecuteMapper.queryIndexCode(mapVo);
	}
//判断指标名称是否存在
	public int queryIndex_Name(Map<String, Object> mapVo) throws DataAccessException {
	
		return budgWorkDeptExecuteMapper.queryIndex_Name(mapVo);
	}
	@Override
	public String budgWorkDeptMonthExecuteImport(Map<String, Object> map) throws DataAccessException {
		try{
			
			String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"};
			
			String content=map.get("content").toString();
			
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			Map<String, Object> entityMap=new HashMap<String,Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			//查询 指标基本信息
			List<Map<String,Object>> listIndexDict = budgWorkDeptYearExecuteMapper.queryIndexData(entityMap);		
			// 判断指标 是否存在用 map key:name ， value : code、name等信息
			Map<String, Map<String, Object>> dictMap = new HashMap<String, Map<String, Object>>();
		
			for(Map<String, Object> dict : listIndexDict){
				if(dict.get("index_name") != null){
					dictMap.put(dict.get("index_name").toString(), dict);//改成name
					
				}
			}
			
			//查询 末级科室基本信息
			List<Map<String,Object>> listDeptDict = budgWorkDeptYearExecuteMapper.queryDeptData(entityMap);
			
			// 判断科室 是否存在用 mapkey:name ， value : id、code、name等信息
			Map<String, Map<String, Object>> dictDeptMap = new HashMap<String, Map<String, Object>>();
		
			for(Map<String, Object> dict : listDeptDict){
				if(dict.get("dept_name") != null){
					dictDeptMap.put(dict.get("dept_name").toString(), dict);//改成name
					
				}
			}
			List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			
			
			for(Map<String,List<String>> item : liData ){
			     
	
				List<String> budg_year = item.get("年度") ;
				
				List<String> index_name = item.get("指标名称") ;
				
				List<String> execute_value = item.get("执行数据") ;
				
				List<String> remark = item.get("说明") ;
				
				List<String> dept_name = item.get("科室名称") ;
				
				List<String> month = item.get("月份") ;
				
				if(budg_year == null){
					
					return "{\"warn\":\"" + budg_year.get(0) + ",年度为空！\",\"state\":\"false\",\"row_cell\":\"" + budg_year.get(0) + "\"}";
					
				}
				
               if(month == null){
					
					return "{\"warn\":\"" + month.get(0) + ",月份为空！\",\"state\":\"false\",\"row_cell\":\"" + month.get(0) + "\"}";
					
				}else{
					if(!Arrays.asList(monthData).contains(month.get(1))){
						//get(1)获取单元格的值
						return "{\"warn\":\"" + month.get(0) + ",月份输入不合法(两位有效数字01至12)！\",\"state\":\"false\",\"row_cell\":\"" + index_name.get(0) + "\"}";
					}
				}
               
               
               
				if(index_name == null){
					//get(0)获取坐标
					return "{\"warn\":\"" + index_name.get(0) + ",指标名称为空！\",\"state\":\"false\",\"row_cell\":\"" + index_name.get(0) + "\"}";
				
				}else{
					
					if(dictMap.get(index_name.get(1)) == null){
						//get(1)获取单元格的值
						return "{\"warn\":\"" + index_name.get(0) + ",指标不存在或已停用,指标名称输入错误！\",\"state\":\"false\",\"row_cell\":\"" + index_name.get(0) + "\"}";
					}
				}
				
				
				if(dept_name == null){
					//get(0)获取坐标
					return "{\"warn\":\"" + dept_name.get(0) + ",科室名称为空！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
				
				}else{
					
					if(dictDeptMap.get(dept_name.get(1)) == null){
						//get(1)获取单元格的值
						return "{\"warn\":\"" + dept_name.get(0) + ",科室不存在(不是末级科目或已停用),科室名称输入错误！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
					}
				}
				
				if(execute_value == null){
					
					return "{\"warn\":\"" + execute_value.get(0) + ",执行数据为空！\",\"state\":\"false\",\"row_cell\":\"" + execute_value.get(0) + "\"}";
					
				}else{
					
					 try{
						    Double.parseDouble(execute_value.get(1) == null ? "0" : execute_value.get(1));
						   
					 }catch(NumberFormatException e){
						 
						 return "{\"warn\":\"" + execute_value.get(0) + ",执行数据输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + execute_value.get(0) + "\"}";
					  }
					
				}
                
               
				
				//存放 正确信息用Map
				Map<String,Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("group_id", SessionManager.getGroupId());
				
				returnMap.put("hos_id", SessionManager.getHosId());
				
				returnMap.put("copy_code", SessionManager.getCopyCode());
				
				returnMap.put("remark", remark.get(1));
							
				returnMap.put("year", budg_year.get(1));
				
				returnMap.put("month", month.get(1));
				
				returnMap.putAll(dictDeptMap.get(dept_name.get(1)));
				
				returnMap.put("execute_value", execute_value.get(1) == null ? "0" : execute_value.get(1));
				
				returnMap.putAll(dictMap.get(index_name.get(1)));
				
				returnList.add(returnMap);
				
			}
			StringBuffer err_sb = new StringBuffer();
			
			//校验 数据重复
			for( int i = 0; i < returnList.size(); i++ ){
				
				for(int j = i + 1 ; j < returnList.size(); j++){
					
					
					if(returnList.get(i).get("dept_name").equals(returnList.get(j).get("dept_name")) && returnList.get(i).get("year").equals(returnList.get(j).get("year")) &&  returnList.get(i).get("month").equals(returnList.get(j).get("month")) &&returnList.get(i).get("index_name").equals(returnList.get(j).get("index_name"))){
						
						err_sb.append(returnList.get(i).get("dept_name")+"部门名称,"+returnList.get(i).get("year")+"年度,"+returnList.get(i).get("month")+"月份,"+returnList.get(i).get("index_name")+"指标编码");  
					}
				}
				
			}
			
			if( err_sb.length() > 0){
				
				 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
				
			}else{
				
				String str = addBatch(returnList);
				
				if("false".equals(JSONObject.parseObject(str).get("state"))){
					
					return str ;
					
				}else{
					
					return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
				}

			}
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public List<Map<String, Object>> getPrintData(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>) budgWorkDeptExecuteMapper.query(mapVo);
		return list;
	}
	
}
