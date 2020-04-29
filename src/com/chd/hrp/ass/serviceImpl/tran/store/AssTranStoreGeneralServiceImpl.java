﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.tran.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.card.AssCardGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptGeneralMapper;
import com.chd.hrp.ass.dao.tran.store.AssTranStoreDetailGeneralMapper;
import com.chd.hrp.ass.dao.tran.store.AssTranStoreGeneralMapper;
import com.chd.hrp.ass.dao.tran.store.AssTranStoreSpecialMapper;
import com.chd.hrp.ass.dao.tran.store.AssTranStoreGeneralMapper;
import com.chd.hrp.ass.entity.tran.store.AssTranStoreDetailGeneral;
import com.chd.hrp.ass.entity.tran.store.AssTranStoreGeneral;
import com.chd.hrp.ass.entity.tran.store.AssTranStoreGeneral;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.tran.store.AssTranStoreGeneralService;
import com.chd.hrp.mat.dao.info.basic.MatStoreMapper;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产转移主表(仓库到仓库)_一般设备
 * @Table:
 * ASS_TRAN_STORE_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assTranStoreGeneralService")
public class AssTranStoreGeneralServiceImpl implements AssTranStoreGeneralService {

	private static Logger logger = Logger.getLogger(AssTranStoreGeneralServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assTranStoreGeneralMapper")
	private final AssTranStoreGeneralMapper assTranStoreGeneralMapper = null;
	
	@Resource(name = "assTranStoreDetailGeneralMapper")
	private final AssTranStoreDetailGeneralMapper assTranStoreDetailGeneralMapper = null;
	
	@Resource(name = "assShareDeptGeneralMapper")
	private final AssShareDeptGeneralMapper assShareDeptGeneralMapper = null;
	
	@Resource(name = "assShareDeptRGeneralMapper")
	private final AssShareDeptRGeneralMapper assShareDeptRGeneralMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;
	
	@Resource(name = "matStoreMapper")
	private final MatStoreMapper matStoreMapper = null;

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
	
	@Resource(name = "assCardGeneralMapper")
	private final AssCardGeneralMapper assCardGeneralMapper = null;
    
	/**
	 * @Description 
	 * 添加050901 资产转移主表(仓库到仓库)_一般设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050901 资产转移主表(仓库到仓库)_一般设备
		AssTranStoreGeneral assTranStoreGeneral = queryByCode(entityMap);

		if (assTranStoreGeneral != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assTranStoreGeneralMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050901 资产转移主表(仓库到仓库)_一般设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assTranStoreGeneralMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050901 资产转移主表(仓库到仓库)_一般设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assTranStoreGeneralMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050901 资产转移主表(仓库到仓库)_一般设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assTranStoreGeneralMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除050901 资产转移主表(仓库到仓库)_一般设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assTranStoreGeneralMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050901 资产转移主表(仓库到仓库)_一般设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			assTranStoreDetailGeneralMapper.deleteBatch(entityList);
			assTranStoreGeneralMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加050901 资产转移主表(仓库到仓库)_一般设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("tran_no", entityMap.get("tran_no"));
    	
    	Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
    	vCreateDateMap.put("group_id",entityMap.get("group_id"));
    	vCreateDateMap.put("hos_id",entityMap.get("hos_id"));
    	vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
		vCreateDateMap.put("ass_nature", "03");
		vCreateDateMap.put("use_state", "1,2,3,4,5");
		vCreateDateMap.put("is_dept", "0");
		vCreateDateMap.put("in_date", entityMap.get("create_date"));
		
		Map<String, Object> vStoreMap = new HashMap<String, Object>();
		vStoreMap.put("group_id",entityMap.get("group_id"));
		vStoreMap.put("hos_id",entityMap.get("hos_id"));
		vStoreMap.put("copy_code", entityMap.get("copy_code"));
		vStoreMap.put("ass_nature", "03");
		vStoreMap.put("use_state", "1,2,3,4,5");
		vStoreMap.put("is_dept", "0");
		vStoreMap.put("store_id", entityMap.get("out_store_id"));
    	
    	entityMap.put("state", "0");
		List<AssTranStoreGeneral> list = (List<AssTranStoreGeneral>)assTranStoreGeneralMapper.queryExists(mapVo);
		boolean flag = true;
		boolean flag1 = true;
		try {
			if (list.size()>0) {
				assTranStoreGeneralMapper.update(entityMap);
			}else{
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_TRAN_STORE_GENERAL");
				String tran_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("tran_no", tran_no);
				assTranStoreGeneralMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
				
			}
			
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}
				detailVo.put("group_id",entityMap.get("group_id"));
				detailVo.put("hos_id",entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("tran_no", entityMap.get("tran_no"));
				
				vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));
				vStoreMap.put("ass_card_no", detailVo.get("ass_card_no"));
				
				Integer createDateExists = hrpAssSelectMapper.queryAssExistsGeneralCard(vCreateDateMap);
				if(createDateExists == 0){
					flag = false;
					break;
				}
				
				Integer storeExists = hrpAssSelectMapper.queryAssExistsGeneralCard(vStoreMap);
				if(storeExists == 0){
					flag1 = false;
					break;
				}
				
				listVo.add(detailVo);
				
			}
			if(!flag){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "{\"warn\":\"存在尚未入账的卡片不能进行转移.\"}";
			}
			if(!flag1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "{\"warn\":\"存在非本仓库的卡片,不能转移.\"}";
			}
			
			assTranStoreDetailGeneralMapper.delete(entityMap);
			assTranStoreDetailGeneralMapper.addBatch(listVo);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"tran_no\":\""+entityMap.get("tran_no")+"\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050901 资产转移主表(仓库到仓库)_一般设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssTranStoreGeneral> list = (List<AssTranStoreGeneral>)assTranStoreGeneralMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssTranStoreGeneral> list = (List<AssTranStoreGeneral>)assTranStoreGeneralMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050901 资产转移主表(仓库到仓库)_一般设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assTranStoreGeneralMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050901 资产转移主表(仓库到仓库)_一般设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssTranStoreGeneral
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assTranStoreGeneralMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050901 资产转移主表(仓库到仓库)_一般设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssTranStoreGeneral>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assTranStoreGeneralMapper.queryExists(entityMap);
	}
	@Override
	public String updateConfirmTranStoreGeneral(List<Map<String, Object>> entityMap,
			List<Map<String, Object>> cardEntityMap) throws DataAccessException {
		try {
			List<Map<String, Object>> cardList = new ArrayList<Map<String,Object>>();
			
			for(Map<String, Object> cardEntity : cardEntityMap){
				cardEntity.put("store_id", cardEntity.get("in_store_id"));
				cardEntity.put("store_no", cardEntity.get("in_store_no"));
				Map<String, Object> deptMap = matStoreMapper.queryByCode(cardEntity);
				if (deptMap.get("dept_id") != null && !deptMap.get("dept_id").equals("")) {
					Map<String, Object> deptNoMap = new HashMap<String, Object>();
					deptNoMap.put("group_id", deptMap.get("group_id"));
					deptNoMap.put("hos_id", deptMap.get("hos_id"));
					deptNoMap.put("dept_id", deptMap.get("dept_id"));
					DeptDict deptDict = deptDictMapper.queryDeptDictByDeptCode(deptNoMap);
					cardEntity.put("dept_id", deptDict.getDept_id());
					cardEntity.put("dept_no", deptDict.getDept_no());
					cardEntity.put("area", 1);
					cardEntity.put("ass_year", getAssYear());
					cardEntity.put("ass_month", getAssMonth());
				}else{
					return  "{\"warn\":\"仓库没有维护所在科室,不能确认! \"}";
				}
				cardList.add(cardEntity);
				
			}
			//删除原来的使用科室
			assShareDeptRGeneralMapper.deleteBatchByAssYearMonth(cardEntityMap);
			assShareDeptGeneralMapper.deleteBatchByAssCardNo(cardEntityMap);
			
			//增加新的使用科室
			assShareDeptRGeneralMapper.addBatch(cardList);
			assShareDeptGeneralMapper.addBatch(cardList);
			
			//更新卡片的仓库已经使用科室
			assCardGeneralMapper.updateTranStoreConfirm(cardList);
			
			//更新转移单据表
			assTranStoreGeneralMapper.updateConfirm(entityMap);
			
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	private String getAssYear() {
		String yearMonth = MyConfig.getCurAccYearMonth();
		return yearMonth.substring(0, 4);
	}

	private String getAssMonth() {
		String yearMonth = MyConfig.getCurAccYearMonth();
		return yearMonth.substring(4, 6);
	}
	
	
	@Override
	public List<AssTranStoreDetailGeneral> queryByTranNo(Map<String, Object> entityMap) throws DataAccessException {
		return assTranStoreDetailGeneralMapper.queryByTranNo(entityMap);
	}
	
	@Override
	public List<String> queryState(Map<String, Object> entityMap) throws DataAccessException {
		 return assTranStoreGeneralMapper.queryState(entityMap);
		  
	}
	
	//出库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public Map<String,Object> assTranStoreGeneralByPrintTemlate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		AssTranStoreGeneralMapper assTranStoreGeneralMapper = (AssTranStoreGeneralMapper)context.getBean("assTranStoreGeneralMapper");
		
		
		if("1".equals(String.valueOf(entityMap.get("p_num")))){
			//查询移库主表批量
			List<Map<String,Object>> map = assTranStoreGeneralMapper.queryAssTranStoreGeneralPrintTemlateByMainBatch(entityMap);
			//查询移库明细表
			List<Map<String,Object>> list = assTranStoreGeneralMapper.queryAssTranStoreGeneralPrintTemlateByDetail(entityMap);
			
			reMap.put("main", map);
			reMap.put("detail", list);
			
			return reMap;
		}else{
			//查询移库主表
			Map<String,Object> map = assTranStoreGeneralMapper.queryAssTranStoreGeneralPrintTemlateByMain(entityMap);
			//查询移库明细表
			List<Map<String,Object>> list = assTranStoreGeneralMapper.queryAssTranStoreGeneralPrintTemlateByDetail(entityMap);
			
			reMap.put("main", map);
			reMap.put("detail", list);
			
			return reMap;
		}
		
	}
	@Override
	public String queryDetails(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assTranStoreGeneralMapper.queryDetails(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assTranStoreGeneralMapper.queryDetails(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	
}
