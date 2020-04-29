﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.tran.out;

import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.card.AssCardInassetsMapper;
import com.chd.hrp.ass.dao.in.AssInMainInassetsMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInassetsMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRInassetsMapper;
import com.chd.hrp.ass.dao.tran.out.AssOutDetailInassetsMapper;
import com.chd.hrp.ass.dao.tran.out.AssOutInassetsMapper;
import com.chd.hrp.ass.dao.tran.out.map.AssInOutMapInassetsMapper;
import com.chd.hrp.ass.entity.in.AssInMainInassets;
import com.chd.hrp.ass.entity.tran.out.AssOutDetailInassets;
import com.chd.hrp.ass.entity.tran.out.AssOutInassets;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.tran.out.AssOutInassetsService;
import com.chd.hrp.mat.dao.info.basic.MatStoreMapper;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050902 资产领用表_无形资产
 * @Table:
 * ASS_OUT_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assOutInassetsService")
public class AssOutInassetsServiceImpl implements AssOutInassetsService {

	private static Logger logger = Logger.getLogger(AssOutInassetsServiceImpl.class);
	//引入DAO操作
		@Resource(name = "assOutInassetsMapper")
		private final AssOutInassetsMapper assOutInassetsMapper = null;
		
		@Resource(name = "assOutDetailInassetsMapper")
		private final AssOutDetailInassetsMapper assOutDetailInassetsMapper = null;
		
		@Resource(name = "assBaseService")
		private final AssBaseService assBaseService = null;
		
		@Resource(name = "assShareDeptInassetsMapper")
		private final AssShareDeptInassetsMapper assShareDeptInassetsMapper = null;
		
		@Resource(name = "assShareDeptRInassetsMapper")
		private final AssShareDeptRInassetsMapper assShareDeptRInassetsMapper = null;
		
		@Resource(name = "hrpAssSelectMapper")
		private final HrpAssSelectMapper hrpAssSelectMapper = null;
		
		@Resource(name = "assCardInassetsMapper")
		private final AssCardInassetsMapper assCardInassetsMapper = null;
		
		@Resource(name = "matStoreMapper")
		private final MatStoreMapper matStoreMapper = null;

		@Resource(name = "deptDictMapper")
		private final DeptDictMapper deptDictMapper = null;
		
		@Resource(name = "assInOutMapInassetsMapper")
		private final AssInOutMapInassetsMapper assInOutMapInassetsMapper = null;
		
		@Resource(name = "assInMainInassetsMapper")
		private final AssInMainInassetsMapper assInMainInassetsMapper = null;
	    
		/**
		 * @Description 
		 * 添加050902 资产领用表(无形资产)<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String add(Map<String,Object> entityMap)throws DataAccessException{
			
			//获取对象050902 资产领用表(无形资产)
			AssOutInassets assOutInassets = queryByCode(entityMap);

			if (assOutInassets != null) {

				return "{\"error\":\"数据重复,请重新添加.\"}";

			}
			
			try {
				
				int state = assOutInassetsMapper.add(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

			}
			
		}
		/**
		 * @Description 
		 * 批量添加050902 资产领用表(无形资产)<BR> 
		 * @param  entityList
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
			
			try {
				
				assOutInassetsMapper.addBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

			}
			
		}
		
			/**
		 * @Description 
		 * 更新050902 资产领用表(无形资产)<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String update(Map<String,Object> entityMap)throws DataAccessException{
			
			try {
				
			  int state = assOutInassetsMapper.update(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

			}	
			
		}
		/**
		 * @Description 
		 * 批量更新050902 资产领用表(无形资产)<BR> 
		 * @param  entityList
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
			
			try {
				
			  assOutInassetsMapper.updateBatch(entityList);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

			}	
			
		}
		/**
		 * @Description 
		 * 删除050902 资产领用表(无形资产)<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
	    public String delete(Map<String, Object> entityMap) throws DataAccessException {
	    	
	    try {
				
				int state = assOutInassetsMapper.delete(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

			}	
			
	  }
	    
		/**
		 * @Description 
		 * 批量删除050902 资产领用表(无形资产)<BR> 
		 * @param  entityList
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
			
			try {
				assOutDetailInassetsMapper.deleteBatch(entityList);
				assOutInassetsMapper.deleteBatch(entityList);
				assInOutMapInassetsMapper.deleteBatch(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());

			}	
		}
		
		/**
		 * @Description 
		 * 添加050902 资产领用表(无形资产)<BR> 
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
	    	mapVo.put("out_no", entityMap.get("out_no"));
	    	
	    	
	    	String bill_type = entityMap.get("bill_type").toString();
	    	Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
	    	vCreateDateMap.put("group_id",entityMap.get("group_id"));
	    	vCreateDateMap.put("hos_id",entityMap.get("hos_id"));
			vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
			vCreateDateMap.put("ass_nature", "05");
			vCreateDateMap.put("use_state", "1,2,3,4,5");
			vCreateDateMap.put("in_date", entityMap.get("create_date"));
			
			Map<String, Object> vStoreAndDeptMap = new HashMap<String, Object>();
			vStoreAndDeptMap.put("group_id",entityMap.get("group_id"));
			vStoreAndDeptMap.put("hos_id",entityMap.get("hos_id"));
			vStoreAndDeptMap.put("copy_code", entityMap.get("copy_code"));
	    	if(bill_type.equals("01")){//科室领用
	    		vCreateDateMap.put("is_dept", "0");
	    		vStoreAndDeptMap.put("is_dept", "0");
	    		vStoreAndDeptMap.put("ass_nature", "05");
	    		vStoreAndDeptMap.put("use_state", "1,2,3,4,5");
	    		vStoreAndDeptMap.put("store_id", entityMap.get("store_id"));
	    		
	    	}else if(bill_type.equals("04")){
	    		vCreateDateMap.put("is_dept", "1");
	    		vStoreAndDeptMap.put("is_dept", "1");
	    		vStoreAndDeptMap.put("ass_nature", "05");
	    		vStoreAndDeptMap.put("use_state", "1,2,3,4,5");
	    		vStoreAndDeptMap.put("dept_id", entityMap.get("dept_id"));
	    		
	    	}
	    	
	    	
	    	entityMap.put("state", "0");
			List<AssOutInassets> list = (List<AssOutInassets>)assOutInassetsMapper.queryExists(mapVo);
			boolean flag = true;
			boolean flag1 = true;
			try {
				if (list.size()>0) {
					
					assOutInassetsMapper.update(entityMap);
				}else{
					if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
						entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
						entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
					}
					entityMap.put("bill_table", "ASS_OUT_INASSETS");
					String out_no = assBaseService.getBillNOSeqNo(entityMap);
					entityMap.put("out_no", out_no);
					assOutInassetsMapper.add(entityMap);
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
					detailVo.put("out_no", entityMap.get("out_no"));
					
					vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));
					vStoreAndDeptMap.put("ass_card_no", detailVo.get("ass_card_no"));
					
					Integer createDateExists = hrpAssSelectMapper.queryAssExistsInassetsCard(vCreateDateMap);
					if(createDateExists == 0){
						flag = false;
						break;
					}
					
					Integer storeAndDeptExists = hrpAssSelectMapper.queryAssExistsInassetsCard(vStoreAndDeptMap);
					if(storeAndDeptExists == 0){
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
					return "{\"warn\":\"存在非本仓库货非本科室的卡片,不能转移.\"}";
				}
				
				assOutDetailInassetsMapper.delete(entityMap);
				assOutDetailInassetsMapper.addBatch(listVo);
				return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"out_no\":\""+entityMap.get("out_no")+"\"}";
			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());
			}
			
		}
		/**
		 * @Description 
		 * 查询结果集050902 资产领用表(无形资产)<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String query(Map<String,Object> entityMap) throws DataAccessException{
			
			SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<AssOutInassets> list = (List<AssOutInassets>)assOutInassetsMapper.query(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<AssOutInassets> list = (List<AssOutInassets>)assOutInassetsMapper.query(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
			
		}
		
		/**
		 * @Description 
		 * 获取对象050902 资产领用表(无形资产)<BR> 
		 * @param  entityMap<BR>
		 *  参数为主键
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
			return assOutInassetsMapper.queryByCode(entityMap);
		}
		
		/**
		 * @Description 
		 * 获取050902 资产领用表(无形资产)<BR> 
		 * @param  entityMap<BR>
		 *  参数为要检索的字段
		 * @return AssOutInassets
		 * @throws DataAccessException
		*/
		@Override
		public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
			return assOutInassetsMapper.queryByUniqueness(entityMap);
		}
		
		/**
		 * @Description 
		 * 获取050902 资产领用表(无形资产)<BR> 
		 * @param  entityMap<BR>
		 *  参数为要检索的字段
		 * @return List<AssOutInassets>
		 * @throws DataAccessException
		*/
		@Override
		public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
			return assOutInassetsMapper.queryExists(entityMap);
		}
		@Override
		public String updateConfirmOutInassets(List<Map<String, Object>> entityMap, List<Map<String, Object>> cardEntityMap)
				throws DataAccessException {
			try {
				List<Map<String, Object>> getList = new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> backList = new ArrayList<Map<String,Object>>();
				for(Map<String, Object> cardEntity : cardEntityMap){
					String bill_type = cardEntity.get("bill_type").toString();
					if(bill_type.equals("01")){//科室领用
						cardEntity.put("run_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
						cardEntity.put("dept_id", cardEntity.get("dept_id"));
						cardEntity.put("dept_no", cardEntity.get("dept_no"));
						cardEntity.put("area", 1);
						cardEntity.put("ass_year", getAssYear());
						cardEntity.put("ass_month", getAssMonth());
						getList.add(cardEntity);
					}else if(bill_type.equals("04")){
						cardEntity.put("store_id", cardEntity.get("store_id"));
						cardEntity.put("store_no", cardEntity.get("store_no"));
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
						backList.add(cardEntity);
					}
				}
				//删除原来的使用科室
				assShareDeptRInassetsMapper.deleteBatchByAssYearMonth(cardEntityMap);
				assShareDeptInassetsMapper.deleteBatchByAssCardNo(cardEntityMap);
				
				//增加新的使用科室
				if(getList.size() > 0){
					assShareDeptRInassetsMapper.addBatch(getList);
					assShareDeptInassetsMapper.addBatch(getList);
					assCardInassetsMapper.updateDeptGetOutConfirm(getList);
				}
				
				if(backList.size() > 0){
					assShareDeptRInassetsMapper.addBatch(backList);
					assShareDeptInassetsMapper.addBatch(backList);
					assCardInassetsMapper.updateDeptBackOutConfirm(backList);
				}
				
				assOutInassetsMapper.updateConfirm(entityMap);
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
		public List<AssOutDetailInassets> queryByOutNo(Map<String, Object> entityMap) throws DataAccessException {
			return assOutDetailInassetsMapper.queryByOutNo(entityMap);
		}
		
		@Override
		public List<String> queryState(Map<String, Object> entityMap) throws DataAccessException {
			 return assOutInassetsMapper.queryState(entityMap);
			  
		}
		
		//出库模板打印（包含主从表）
		@Resource(name = "superPrintService")
		private final SuperPrintService superPrintService = null;
		@Override
		public Map<String,Object> assOutInassetsByPrintTemlate(Map<String, Object> entityMap)
				throws DataAccessException {
			// TODO Auto-generated method stub
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssOutInassetsMapper assOutInassetsMapper = (AssOutInassetsMapper)context.getBean("assOutInassetsMapper");
			
			
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				//查询移库主表批量
				List<Map<String,Object>> map = assOutInassetsMapper.queryAssOutInassetsPrintTemlateByMainBatch(entityMap);
				//查询移库明细表
				List<Map<String,Object>> list = assOutInassetsMapper.queryAssOutInassetsPrintTemlateByDetail(entityMap);
				
				reMap.put("main", map);
				reMap.put("detail", list);
				
				return reMap;
			}else{
				//查询移库主表
				Map<String,Object> map = assOutInassetsMapper.queryAssOutInassetsPrintTemlateByMain(entityMap);
				//查询移库明细表
				List<Map<String,Object>> list = assOutInassetsMapper.queryAssOutInassetsPrintTemlateByDetail(entityMap);
				
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

				List<Map<String, Object>> list = (List<Map<String, Object>>) assOutInassetsMapper.queryDetails(entityMap);

				return ChdJson.toJson(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list = (List<Map<String, Object>>) assOutInassetsMapper.queryDetails(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJson(list, page.getTotal());

			}
		}
		@Override
		public String importAssInMainByOut(Map<String, Object> map) throws DataAccessException {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> mapListVo = new ArrayList<Map<String, Object>>();
			try {
				List<AssInMainInassets> assPayMain = assInMainInassetsMapper.queryByInitOut(map);
				
				
				// 明细表增加
				
				boolean flag = true;
				boolean flag1= true;

				for (AssInMainInassets assInMainSpecial : assPayMain) {
					Map<String, Object> entityMap = new HashMap<String, Object>();
					entityMap.put("group_id", assInMainSpecial.getGroup_id());
					entityMap.put("hos_id", assInMainSpecial.getHos_id());
					entityMap.put("copy_code", assInMainSpecial.getCopy_code());
					String date = DateUtil.dateFormat(assInMainSpecial.getCreate_date(), "yyyy-MM-dd");
					entityMap.put("year", date.substring(0,4));
					entityMap.put("month", date.substring(5,7));
					entityMap.put("store_code", assInMainSpecial.getStore_code());
					
					entityMap.put("bill_table", "ASS_OUT_INASSETS");
					
					String out_no = assBaseService.getBillNOSeqNo(entityMap);

					// 主表增加
					entityMap.put("out_no", out_no);
					entityMap.put("state", "0");
					entityMap.put("note", "引入采购入库");
					entityMap.put("group_id", assInMainSpecial.getGroup_id());
					entityMap.put("hos_id", assInMainSpecial.getHos_id());
					entityMap.put("copy_code", assInMainSpecial.getCopy_code());
					entityMap.put("dept_id", assInMainSpecial.getDept_id());
					entityMap.put("dept_no", assInMainSpecial.getDept_no());
					entityMap.put("store_id", assInMainSpecial.getStore_id());
					entityMap.put("store_no", assInMainSpecial.getStore_no());
					entityMap.put("bill_type", map.get("bill_type"));
					entityMap.put("note", map.get("note"));
					entityMap.put("purc_emp", map.get("purc_emp"));
					entityMap.put("create_emp", SessionManager.getUserId());
					entityMap.put("create_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

					
					
					Map<String, Object> qcardMap = new HashMap<String, Object>();
					qcardMap.put("group_id", assInMainSpecial.getGroup_id());
					qcardMap.put("hos_id", assInMainSpecial.getHos_id());
					qcardMap.put("copy_code", assInMainSpecial.getCopy_code());
					qcardMap.put("ass_in_no", assInMainSpecial.getAss_in_no());
					qcardMap.put("out_no", out_no);
					qcardMap.put("bill_type", map.get("bill_type"));
					List<Map<String, Object>> cardList = assCardInassetsMapper.queryByAssInNo(qcardMap);
					
	 				for(Map<String, Object> card : cardList){
						if(map.get("bill_type").equals("01")){
							if(card.get("is_dept").toString().equals("1")){
								flag = false;
								break;
							}
						}else if(map.get("bill_type").equals("04")){
							if(card.get("is_dept").toString().equals("0")){
								flag1 = false;
								continue;
							}
						}
						
						
						Map<String, Object> maps = new HashMap<String, Object>();
						maps.put("group_id", card.get("group_id"));
						maps.put("hos_id", card.get("hos_id"));
						maps.put("copy_code", card.get("copy_code"));
						maps.put("out_no", out_no);
						maps.put("ass_card_no", card.get("ass_card_no"));
						maps.put("note", "");
						listVo.add(maps);
					}
					mapListVo.add(qcardMap);
					
					
					assOutInassetsMapper.add(entityMap);
					assBaseService.updateAssBillNoMaxNo(entityMap);
				}
				
				
				
				String msg = "";
				if(map.get("bill_type").equals("04")){
					msg = "科室退库";
				}else if(map.get("bill_type").equals("01")){
					msg = "科室领用";
				}
				
				if(!flag){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "{\"msg\":\"存在已被出库的资产，不能生成.\",\"state\":\"false\"}";
				}
				
				if(!flag1){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "{\"msg\":\"存在已被退库的资产，不能生成.\",\"state\":\"false\"}";
				}
				

				if(listVo.size() == 0){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "{\"msg\":\"引入失败,没有可生成的"+msg+"单据.\",\"state\":\"false\"}";
				}
				
				
				assOutDetailInassetsMapper.addBatch(listVo);
				assInOutMapInassetsMapper.addBatch(mapListVo);

				return "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + map.get("group_id") + "|"
						+ map.get("hos_id") + "|" + map.get("copy_code") + "||1\"}";
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());
			}
		}
	
}
