﻿
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmFunStackMapper;
import com.chd.hrp.prm.entity.PrmFunStack;
import com.chd.hrp.prm.service.PrmFunStackService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 9905 绩效函数参数栈
 * @Table:
 * PRM_FUN_STACK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmFunStackService")
public class PrmFunStackServiceImpl implements PrmFunStackService {

	private static Logger logger = Logger.getLogger(PrmFunStackServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmFunStackMapper")
	private final PrmFunStackMapper prmFunStackMapper = null;
    
	/**
	 * @Description 
	 * 添加9905 绩效函数参数栈<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmFunStack(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9905 绩效函数参数栈
		PrmFunStack prmFunStack = queryPrmFunStackByCode(entityMap);

		if (prmFunStack != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmFunStackMapper.addPrmFunStack(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmFunStack\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9905 绩效函数参数栈<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmFunStack(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmFunStackMapper.addBatchPrmFunStack(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmFunStack\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新9905 绩效函数参数栈<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmFunStack(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmFunStackMapper.updatePrmFunStack(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmFunStack\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新9905 绩效函数参数栈<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmFunStack(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmFunStackMapper.updateBatchPrmFunStack(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmFunStack\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9905 绩效函数参数栈<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmFunStack(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmFunStackMapper.deletePrmFunStack(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmFunStack\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9905 绩效函数参数栈<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmFunStack(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmFunStackMapper.deleteBatchPrmFunStack(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmFunStack\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9905 绩效函数参数栈<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmFunStack(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmFunStack> list = prmFunStackMapper.queryPrmFunStack(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmFunStack> list = prmFunStackMapper.queryPrmFunStack(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象9905 绩效函数参数栈<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmFunStack queryPrmFunStackByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmFunStackMapper.queryPrmFunStackByCode(entityMap);
	}
	
}
