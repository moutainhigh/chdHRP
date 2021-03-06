/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.guanLiReports;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.guanLiReports.AssDepreciationSortMapper;
import com.chd.hrp.ass.entity.guanLiReports.AssDepreciationSort;
import com.chd.hrp.ass.entity.guanLiReports.AssResourceSet;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.guanLiReports.AssDepreciationSortService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051101 资产月报表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assDepreciationSortService")
public class AssDepreciationSortServiceImpl implements AssDepreciationSortService {

	private static Logger logger = Logger.getLogger(AssDepreciationSortServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assDepreciationSortMapper")
	private final AssDepreciationSortMapper assDepreciationSortMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
    


	/**
	 * @Description 
	 * 查询结果集051101 盘点单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			//
			System.out.println("==="+entityMap.get("ass_nature"));
			List<AssDepreciationSort> list = null;
			if ("02".equals(entityMap.get("ass_nature"))){ 
				
				list = assDepreciationSortMapper.querySpecial(entityMap);
			
				}
			if ("03".equals(entityMap.get("ass_nature"))){
				 
				list = assDepreciationSortMapper.queryGeneral(entityMap);
			
				}
			if ("01".equals(entityMap.get("ass_nature"))){
				
				list = assDepreciationSortMapper.queryHouse(entityMap);
			
				}
			if ("04".equals(entityMap.get("ass_nature"))){
				
				list = assDepreciationSortMapper.queryOther(entityMap);
			
				}  
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			//
			List<AssDepreciationSort> list = null;
			if ("02".equals(entityMap.get("ass_nature"))){ 
				
				list = assDepreciationSortMapper.querySpecial(entityMap, rowBounds);
			
				}
			if ("03".equals(entityMap.get("ass_nature"))){
				 
				list = assDepreciationSortMapper.queryGeneral(entityMap, rowBounds);
			
				}
			if ("01".equals(entityMap.get("ass_nature"))){
				
				list = assDepreciationSortMapper.queryHouse(entityMap, rowBounds);
			
				}
			if ("04".equals(entityMap.get("ass_nature"))){
				
				list = assDepreciationSortMapper.queryOther(entityMap, rowBounds);
			
				}  
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051101 盘点单<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assDepreciationSortMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051101 盘点单<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssCheckMain
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assDepreciationSortMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051101 盘点单<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssCheckMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assDepreciationSortMapper.queryExists(entityMap);
	}

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAssDepreciationSort(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssResourceSet> list =  null ;
			
			list = assDepreciationSortMapper.queryAssDepreciationSection(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public List<Map<String, Object>> queryAssDepreciationSortPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assDepreciationSortMapper.queryAssDepreciationSortPrint(entityMap);
	
		return list;
	}

	
	
}
