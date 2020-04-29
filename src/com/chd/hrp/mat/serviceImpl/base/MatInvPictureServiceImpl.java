package com.chd.hrp.mat.serviceImpl.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.base.MatInvPictureMapper;
import com.chd.hrp.mat.service.base.MatInvPictureService;
import com.github.pagehelper.PageInfo;
@Service("matInvPictureService")
public class MatInvPictureServiceImpl implements MatInvPictureService{

	private static Logger logger = Logger.getLogger(MatInvPictureServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matInvPictureMapper")
	private final MatInvPictureMapper matInvPictureMapper = null;
    
	/**
	 * 添加
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
	
		

		
		if (matInvPictureMapper.queryExists(entityMap).size() >0) {
			
			return "{\"error\":\"数据重复,请重新添加.\"}";
			
		}		
		try {			
			
			int state = matInvPictureMapper.add(entityMap);			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}		
	}
	
	/**
	 * 批量添加
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			matInvPictureMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}	
	}
	
	/**
	 * 更新 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String update(Map<String,Object> entityMap)throws DataAccessException{
       
		
		try {
			
			int state = matInvPictureMapper.update(entityMap);			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}		
	}
	
	/** 
	 * 批量更新MAT_INV_PICTURE<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{		
		try {
			
			matInvPictureMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}		
	}
	
	/**
	 * 删除MAT_INV_PICTURE<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
	    try {	
			int state = matInvPictureMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
	
		}	
	}
    
	/**
	 * @Description 
	 * 批量删除MAT_INV_PICTURE<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matInvPictureMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集MAT_INV_PICTURE<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<?> list = matInvPictureMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<?> list = matInvPictureMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象MAT_INV_PICTURE<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return matInvPictureMapper.queryByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 获取MAT_INV_PICTURE<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatPayType
	 * @throws DataAccessException
	*/
	
	public <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matInvPictureMapper.queryByUniqueness(entityMap);
	}
	
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


}
