/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.contract;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.contract.AssContractManageMapper;
import com.chd.hrp.ass.entity.contract.AssContractManage;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.contract.AssContractManageService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 付款信息050501 资产合同主表
 * @Table:
 * ASS_CONTRACT_PAY_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

 
@Service("assContractManageService")
public class AssContractManageServiceImpl implements AssContractManageService {

	private static Logger logger = Logger.getLogger(AssContractManageServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assContractManageMapper")
	private final AssContractManageMapper assContractManageMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	/**
	 * @Description 
	 * 添加050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050501 资产合同主表
		AssContractManage assContractManage = queryByCode(entityMap);

		if (assContractManage != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assContractManageMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050501 资产合同主表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assContractManageMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assContractManageMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050501 资产合同主表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assContractManageMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assContractManageMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050501 资产合同主表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assContractManageMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050501 资产合同主表<BR> 
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
		//判断是否存在对象050501 资产合同主表
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	//mapVo.put("acct_year", entityMap.get("acct_year"));
    	mapVo.put("contract_id", entityMap.get("contract_id"));
		List<AssContractManage> list = (List<AssContractManage>) assContractManageMapper.queryExists(mapVo);
		
		
		if (list.size()>0) {

			int state = assContractManageMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"contract_id\":\""+entityMap.get("contract_id")+"\",\"contract_no\":\""+entityMap.get("contract_no")+"\"}";

		}
		
		try {
			entityMap.put("bill_table", "ASS_CONTRACT_Manage");
			String contract_no=assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("contract_no", contract_no);
			int state = assContractManageMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssContractManage> list = (List<AssContractManage>) assContractManageMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssContractManage> list = (List<AssContractManage>) assContractManageMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050501 资产合同主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assContractManageMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050501 资产合同主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssContractManage
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assContractManageMapper.queryByUniqueness(entityMap);
	}
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return assContractManageMapper.queryByCode(entityMap);
	}
	

}
