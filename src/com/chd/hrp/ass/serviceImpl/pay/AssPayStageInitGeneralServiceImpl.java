/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.pay;

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
import com.chd.hrp.ass.dao.pay.AssPayStageInitGeneralMapper;
import com.chd.hrp.ass.entity.pay.AssPayStageInitGeneral;
import com.chd.hrp.ass.entity.pay.AssPayStageInitGeneral;
import com.chd.hrp.ass.service.pay.AssPayStageInitGeneralService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 资产分期付款表_专用设备
 * @Table:
 * ASS_PAY_STAGE_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assPayStageInitGeneralService")
public class AssPayStageInitGeneralServiceImpl implements AssPayStageInitGeneralService {

	private static Logger logger = Logger.getLogger(AssPayStageInitGeneralServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assPayStageInitGeneralMapper")
	private final AssPayStageInitGeneralMapper assPayStageInitGeneralMapper = null;
    
	/**
	 * @Description 
	 * 添加资产分期付款表_专用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象资产分期付款表_专用设备
		AssPayStageInitGeneral AssPayStageInitGeneral = queryByCode(entityMap);

		if (AssPayStageInitGeneral != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assPayStageInitGeneralMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加资产分期付款表_专用设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			assPayStageInitGeneralMapper.delete(entityList.get(0));
			assPayStageInitGeneralMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新资产分期付款表_专用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assPayStageInitGeneralMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新资产分期付款表_专用设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assPayStageInitGeneralMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除资产分期付款表_专用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assPayStageInitGeneralMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除资产分期付款表_专用设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assPayStageInitGeneralMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加资产分期付款表_专用设备<BR> 
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
		//判断是否存在对象资产分期付款表_专用设备
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssPayStageInitGeneral> list = (List<AssPayStageInitGeneral>)assPayStageInitGeneralMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assPayStageInitGeneralMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assPayStageInitGeneralMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集资产分期付款表_专用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssPayStageInitGeneral> list = (List<AssPayStageInitGeneral>)assPayStageInitGeneralMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssPayStageInitGeneral> list = (List<AssPayStageInitGeneral>)assPayStageInitGeneralMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象资产分期付款表_专用设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assPayStageInitGeneralMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产分期付款表_专用设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssPayStageInitGeneral
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assPayStageInitGeneralMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产分期付款表_专用设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssPayStageInitGeneral>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assPayStageInitGeneralMapper.queryExists(entityMap);
	}
	
	@Override
	public Integer queryStageMaxNo(Map<String, Object> entityMap) throws DataAccessException {
		return assPayStageInitGeneralMapper.queryStageInitGeneralMaxNo(entityMap);
	}
	
}