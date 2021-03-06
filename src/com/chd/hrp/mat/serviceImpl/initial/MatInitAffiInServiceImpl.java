/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.initial;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.affi.in.MatAffiInCommonMapper;
import com.chd.hrp.mat.dao.base.MatAffiInMapper;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.base.MatStoreModMapper;
import com.chd.hrp.mat.dao.initial.MatInitAffiInMapper;
import com.chd.hrp.mat.dao.initial.MatInitChargeMapper;
import com.chd.hrp.mat.dao.initial.MatInitInMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.initial.MatInitAffiInService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 代销材料期初入库
 * @Table:
 * MAT_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matInitAffiInService")
public class MatInitAffiInServiceImpl implements MatInitAffiInService {

	private static Logger logger = Logger.getLogger(MatInitAffiInServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matInitAffiInMapper")
	private final MatInitAffiInMapper matInitAffiInMapper = null;
	
	@Resource(name = "matAffiInMapper")
	private final MatAffiInMapper matAffiInMapper = null;
	
	@Resource(name = "matInitInMapper")
	private final MatInitInMapper matInitInMapper = null;
	
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	@Resource(name = "matInitChargeMapper")
	private final MatInitChargeMapper matInitChargeMapper = null;
    
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	
	@Resource(name = "matAffiInCommonMapper")
	private final MatAffiInCommonMapper matAffiInCommonMapper = null;
	
	@Resource(name = "matStoreModMapper")
	private final MatStoreModMapper matStoreModMapper = null;
	/**
	 * @Description 
	 * 添加代销材料期初入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//入库日期需在系统启用日期之前
			/*int flag = matInitInMapper.existsInDateCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，入库日期需在系统启用日期之前！\",\"state\":\"false\"}";
			}*/
			
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMM" );
			//判断期初入库单的日期不能大于仓库的启用日期
			  Map<String, Object> info = matStoreModMapper.existsStoreMod(entityMap) ; 
			 int year_month =  Integer.parseInt(info.get("YEAR_MONTH").toString()) ;
			 int in_date1  = Integer.parseInt(sdf.format(entityMap.get("in_date")).trim().toString()) ;
			 if(in_date1 >= year_month){
		 		return "{\"error\":\"添加失败，入库日期必须小于仓库启用年月！\",\"state\":\"false\"}";
			 }
			 
			 
			//如果该仓库已期初记账，则不能添加期初入库单
			int flag = matInitChargeMapper.existsStoreIsAccount(entityMap);
			if(flag != 0){
				return "{\"error\":\"添加失败，仓库已期初记账！\",\"state\":\"false\"}";
			}
			//判断存不存在此会计期间，如果不存在，提示请配置。
			/*flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}*/
			
			//自动生成代销入库单据号
			if("自动生成".equals(entityMap.get("in_no"))){
				entityMap.put("prefixe", "");
				entityMap.put("table_code", "MAT_AFFI_IN");
				entityMap.put("in_no", matCommonService.getMatNextNo(entityMap));
				//entityMap.put("in_no", getNextIn_no(entityMap));
			}
			//取出主表ID（自增序列）
			entityMap.put("in_id", matAffiInMapper.queryAffiInMainSeq());

			//修改期初单据期间为0000年00月
			entityMap.put("year", "0000");
			entityMap.put("month", "00");
			
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
				
				///*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "mat_affi_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同材料批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				///*用于查询批次----end-----*/
				
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						Map<String,Object> detailMap = new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表单号
						detailMap.put("detail_id",matAffiInMapper.queryAffiInDetailSeq());
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料ID
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no", "-");//默认批号
						}else{
							detailMap.put("batch_no",  jsonObj.get("batch_no").toString());//批号
						}
						/**********************自动生成批次-------begin--------*/
						invBatchKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString();
						//判断是否已经取出该批号的最大批次
						if(invBatchKeySnMap.get(invBatchKey) == null){
							//查询该批号最大批次
							batchSnMap.put("c_value", detailMap.get("inv_id"));//材料ID
							batchSnMap.put("c_value1", detailMap.get("batch_no"));//材料批号
							String batchSn = matCommonMapper.getMatMaxNo(batchSnMap);//最大批次
							if(batchSn == null || "".equals(batchSn) || "0".equals(batchSn)){
								detailMap.put("batch_sn",  1);//批次
							}else{
								detailMap.put("batch_sn",  Integer.parseInt(batchSn) + 1);//批次
							}
							invBatchKeySnMap.put(invBatchKey, Integer.parseInt(String.valueOf(detailMap.get("batch_sn"))));
						}else{
							detailMap.put("batch_sn",  invBatchKeySnMap.get(invBatchKey) + 1);//批次
							invBatchKeySnMap.put(invBatchKey, invBatchKeySnMap.get(invBatchKey) + 1);
						}
						/**********************自动生成批次-------end---------*/
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))){
							detailMap.put("inva_date", DateUtil.stringToDate( jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sn")))){
							detailMap.put("sn",  jsonObj.get("sn"));//条形码
						}else{
							detailMap.put("sn",  "-");//条形码
						}
						detailMap.put("bar_code", detailMap.get("sn"));//个体码--期初个体码默认为条形码
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id",  "0");//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}

						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_price")))){
							detailMap.put("sale_price",  jsonObj.get("sale_price"));//批发价
						}else{
							detailMap.put("sale_price",  "0");//批发价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_money")))){
							detailMap.put("sale_money",  jsonObj.get("sale_money"));//批发金额
						}else{
							detailMap.put("sale_money",  "0");//批发金额
						}

						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))){
							detailMap.put("sell_price",  jsonObj.get("sell_price"));//零售价
						}else{
							detailMap.put("sell_price",  "0");//零售价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_money")))){
							detailMap.put("sell_money",  jsonObj.get("sell_money"));//零售金额
						}else{
							detailMap.put("sell_money",  "0");//零售金额
						}
						
						detailMap.put("allot_price",  "0");//调拨价
						detailMap.put("allot_money",  "0");//调拨金额
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id", jsonObj.get("cert_id"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("fac_date")))){
							detailMap.put("fac_date", DateUtil.stringToDate(jsonObj.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("serial_no")))){
							detailMap.put("serial_no", jsonObj.get("serial_no"));//序列号
						}
						
						detailList.add(detailMap);
					}
				}
				if(detailList.size()>0){
					//新增入库主表数据
					matAffiInMapper.addMatAffiInMain(entityMap);
					//新增入库明细数据
					matAffiInMapper.addMatAffiInDetail(detailList);
				}else{
					return "{\"error\":\"请选择材料\",\"state\":\"false\"}";
				}
				
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
			return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+","+
				entityMap.get("hos_id").toString()+","+
				entityMap.get("copy_code").toString()+","+
				entityMap.get("in_id").toString()+","
				+"\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatAffiInit\"}";
		}
	}
	/**
	 * @Description 
	 * 批量添加代销材料期初入库<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatInitAffiIn\"}";
		}
	}
	
		/**
	 * @Description 
	 * 更新代销材料期初入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMM" );
			//判断期初入库单的日期不能大于仓库的启用日期
			  Map<String, Object> info = matStoreModMapper.existsStoreMod(entityMap) ; 
			 int year_month =  Integer.parseInt(info.get("YEAR_MONTH").toString()) ;
			 int in_date1  = Integer.parseInt(entityMap.get("in_date").toString().trim().substring(0,4)+""+entityMap.get("in_date").toString().trim().substring(5,7)) ;
			 if(in_date1 >= year_month){
		 		return "{\"error\":\"添加失败，入库日期必须小于仓库启用年月！\",\"state\":\"false\"}";
			 }
			
			 
			if(entityMap.get("state") != null && !"".equals(entityMap.get("state")) && !"1".equals(entityMap.get("state"))){
				return "{\"error\":\"更新失败,单据状态不为未审核状态！.\",\"state\":\"false\"}";
			}
			
			//判断存不存在此会计期间，如果不存在，提示请配置。
			/*int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}*/
			  
			/*组装校验明细*/
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
			
				//用于查询批次----begin
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "mat_affi_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同材料批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				//用于查询批次----end
				//修改明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				List<Map<String,Object>> detailAddList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> detailMap=new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表单号
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料变更号
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no", "-");//默认批号
						}else{
							detailMap.put("batch_no",  jsonObj.get("batch_no").toString());//批号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_sn")))){
							detailMap.put("batch_sn", jsonObj.get("batch_sn"));
						}else{
							/**********************自动生成批次-------begin--------*/
							invBatchKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString();
							//判断是否已经取出该批号的最大批次
							if(invBatchKeySnMap.get(invBatchKey) == null){
								//查询该批号最大批次
								batchSnMap.put("c_value", detailMap.get("inv_id"));//材料ID
								batchSnMap.put("c_value1", detailMap.get("batch_no"));//材料批号
								String batchSn = matCommonMapper.getMatMaxNo(batchSnMap);//最大批次
								if(batchSn == null || "".equals(batchSn) || "0".equals(batchSn)){
									detailMap.put("batch_sn",  1);//批次
								}else{
									detailMap.put("batch_sn",  Integer.parseInt(batchSn) + 1);//批次
								}
								invBatchKeySnMap.put(invBatchKey, Integer.parseInt(String.valueOf(detailMap.get("batch_sn"))));
							}else{
								detailMap.put("batch_sn",  invBatchKeySnMap.get(invBatchKey) + 1);//批次
								invBatchKeySnMap.put(invBatchKey, invBatchKeySnMap.get(invBatchKey) + 1);
							}
							/**********************自动生成批次-------end---------*/
						}
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))){
							detailMap.put("inva_date", DateUtil.stringToDate( jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sn")))){
							detailMap.put("sn",  jsonObj.get("sn"));//条形码
						}else{
							detailMap.put("sn",  "-");//条形码
						}
						detailMap.put("bar_code", detailMap.get("sn"));//个体码--期初个体码默认为条形码
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id",  "0");//货位
						}
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}

						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_price")))){
							detailMap.put("sale_price",  jsonObj.get("sale_price"));//批发价
						}else{
							detailMap.put("sale_price",  "0");//批发价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_money")))){
							detailMap.put("sale_money",  jsonObj.get("sale_money"));//批发金额
						}else{
							detailMap.put("sale_money",  "0");//批发金额
						}

						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))){
							detailMap.put("sell_price",  jsonObj.get("sell_price"));//零售价
						}else{
							detailMap.put("sell_price",  "0");//零售价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_money")))){
							detailMap.put("sell_money",  jsonObj.get("sell_money"));//零售金额
						}else{
							detailMap.put("sell_money",  "0");//零售金额
						}
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id", jsonObj.get("cert_id"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("fac_date")))){
							detailMap.put("fac_date", DateUtil.stringToDate(jsonObj.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("serial_no")))){
							detailMap.put("serial_no", jsonObj.get("serial_no"));//序列号
						}
						
						//明细表ID
						if(jsonObj.get("detail_id") == null || "".equals(jsonObj.get("detail_id"))){
							detailMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());
						}else{
							detailMap.put("detail_id", jsonObj.get("detail_id"));
						}
						detailAddList.add(detailMap);
					}
				}
				
				if(detailAddList.size()>0){
					//修改入库主表数据
					matAffiInMapper.updateMatAffiInMain(entityMap);
					matAffiInMapper.deleteBatchDetail(entityMap);
					//System.out.println(ChdJson.toJson(detailAddList));
					matAffiInMapper.addMatAffiInDetail(detailAddList);
				}else{
					return "{\"error\":\"请选择材料\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatInitAffi\"}";
		}			
	}
	/**
	 * @Description 
	 * 批量更新代销材料期初入库，无此业务<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂无该业务
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMatInitAffiIn\"}";
		}	
	}
	/**
	 * @Description 
	 * 删除代销材料期初入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
		try {
			//状态不为新建不能删除，从页面判断
			//期初记账状态不为0的不能删除，从页面判断
			//先删除明细表
			
			int state = matAffiInMapper.deleteMatAffiInDetail(entityMap);
			
			if(state > 0){
				//在删除主表
			    matAffiInMapper.deleteMatAffiInMain(entityMap);
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatInitAffiIn\"}";
		}	
  }
    
	/**
	 * @Description 
	 * 批量删除代销材料期初入库<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//状态不为新建不能删除，从页面判断
			//期初记账状态不为0的不能删除，从页面判断
			//先删除明细表  在删除主表
			int state = matAffiInMapper.deleteBatchMatAffiInDetail(entityList);
			
			if(state > 0){
				//批量删除明细表
				matAffiInMapper.deleteBatchMatAffiInMain(entityList);
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatInitAffiIn\"}";
		}	
	}
	
	
	/**
	 * @Description 
	 * 查询结果集代销材料期初入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matInitAffiInMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matInitAffiInMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 获取对象代销材料期初入库<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matAffiInMapper.queryByCode(entityMap);
	}
	
	/**
	 * 获得明细表数据
	 */
	@Override
	public String queryDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		List<?> list = matInitAffiInMapper.queryDetailByCode(entityMap);
		
		return ChdJson.toJson(list);
	}
	 
	/**
	 * @Description 
	 * 入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String confirmMatAffiInitIn(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			//批量入库确认
			entityMap.put("user_id", SessionManager.getUserId());
			//因存储过程为批量确认故需要该参数
			entityMap.put("in_ids", entityMap.get("in_id"));
			matAffiInCommonMapper.confirmAffiIn(entityMap);	
			return entityMap.get("msg") == null ? "" : entityMap.get("msg").toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 confirmMatInitIn\"}";
		}	
	}
	
	/**
	 * 入库确认
	 */
	@Override
	public String confirmMatInitAffiInBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {	
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、入库单Id、材料Id
			String copy_code = "", in_ids = "",confirm_date = "";
			Map<String, Object> inMap = new HashMap<String, Object>();
			//取得所有in_id组装map
			for(Map<String, Object> m : entityMap){
				if(group_id == null){
					group_id = Integer.parseInt(m.get("group_id").toString());
				}
				if(hos_id == null){
					hos_id = Integer.parseInt(m.get("hos_id").toString());
				}
				if("".equals(copy_code)){
					copy_code = m.get("copy_code").toString();
				}
				if("".equals(confirm_date)){
					confirm_date = m.get("confirm_date").toString();
				}
				in_ids = in_ids + m.get("in_id").toString() + ",";
			}
			//组装数据
			inMap.put("group_id", group_id);
			inMap.put("hos_id", hos_id);
			inMap.put("copy_code", copy_code);
			inMap.put("confirm_date", confirm_date);
			inMap.put("user_id", SessionManager.getUserId());
			inMap.put("in_ids", in_ids.substring(0, in_ids.length()-1));
			
			//批量入库确认
			matAffiInCommonMapper.confirmAffiIn(inMap);	
			
			return inMap.get("msg") == null ? "" : inMap.get("msg").toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 confirmMatCommonInBatch\"}";
		}	
	}
	
	
	
	/**
	 * @Description 
	 * 获取代销材料期初入库<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInitIn
	 * @throws DataAccessException
	*/
	@Override
	public  <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matInitAffiInMapper.queryByUniqueness(entityMap);
	}
	
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取需要入库单号
	 * @param entityMap
	 * map参数必须包含(group_id, hos_id, copy_code, store_id, year, month, bus_type_code)这六个键值
	 * @return
	 */
	public String getNextIn_no(Map<String, Object> entityMap){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("store_id", entityMap.get("store_id"));
		//获取仓库别名store_alias
		String store_alias =  matCommonMapper.queryStoreAliasById(map);
		map.put("table_code", "MAT_AFFI_IN");
		map.put("year", entityMap.get("year"));
		map.put("month", entityMap.get("month"));
		map.put("prefixe", "");
		map.put("store_alias", store_alias);
		map.put("bus_type", entityMap.get("bus_type_code"));
		//判断是否存在该业务流水码
		int flag = matNoManageMapper.queryIsExists(map);
		String max_no = "";
		if(flag == 0){
			//如不存在则流水码为1，并插入流水码表中
			max_no = "1";
			map.put("max_no", 1);
			matNoManageMapper.add(map);
		}else{
			//更新该业务流水码+1
			matNoManageMapper.updateMaxNo(map);
			//取出该业务更新后的流水码
			max_no = matNoManageMapper.queryMaxCode(map);
		}
		
		//补流水码前缀0
		for (int i = max_no.length() ; i < 5; i++) {
			max_no = "0" + max_no;
		}
		//组装流水码
		String in_no = "";
		if( entityMap.get("bus_type_code").toString().length() > 1 ){
			in_no = store_alias + "-" + entityMap.get("bus_type_code").toString() + entityMap.get("year") + entityMap.get("month") + max_no;
		}else{
			in_no = store_alias + "-0" + entityMap.get("bus_type_code").toString() + entityMap.get("year") + entityMap.get("month") + max_no;
		}
		
		return in_no;
	}

	
	/**
	 * @Description 
	 * 查询材料信息用于导入<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public List<Map<String, Object>> queryInvListForImport(Map<String, Object> entityMap)throws DataAccessException{
		
		return JsonListMapUtil.toListMapLower((List<Map<String, Object>>) matInitAffiInMapper.queryInvListForImport(entityMap));
	}
	
	/**
	 * @Description 
	 * 查询货位信息用于导入<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public List<Map<String, Object>> queryLocationListForImport(Map<String, Object> entityMap)throws DataAccessException{
		
		return JsonListMapUtil.toListMapLower((List<Map<String, Object>>) matInitAffiInMapper.queryLocationListForImport(entityMap));	
	}

		public static List<Map<String, Object>> nullToEmpty(List<Map<String, Object>> list) {
			for(Map<String, Object> map : list){
				Set<String> set = map.keySet();
				if(set != null && !set.isEmpty()) {
					for(String key : set) {
						if(map.get(key) == null) { map.put(key, ""); }
					}
				}
			}
			return list;
		}

	//入库模板打印（包含主从表）
		@Resource(name = "superPrintService")
		private final SuperPrintService superPrintService = null;
		public String queryMatInByPrintTemlate(Map<String, Object> entityMap)
				throws DataAccessException {
			try {
				if("1".equals(String.valueOf(entityMap.get("p_num")))){
					List<Map<String,Object>> map=nullToEmpty(matInitAffiInMapper.queryMatInPrintTemlateByMainBatch(entityMap));
					List<Map<String,Object>> list=nullToEmpty(matInitAffiInMapper.queryMatInPrintTemlateByDetail(entityMap));
					return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
				}else{
					//查询入库主表
					Map<String,Object> map=matInitAffiInMapper.queryMatInPrintTemlateByMain(entityMap);
									
					//查询入库明细表
					List<Map<String,Object>> list=matInitAffiInMapper.queryMatInPrintTemlateByDetail(entityMap);
					return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}

			
		}
		//新版打印
		public Map<String, Object> queryMatInByPrintTemlateNewPrint(Map<String, Object> entityMap)
				throws DataAccessException {
			try {
				
				Map<String,Object> reMap=new HashMap<String,Object>();
				WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				MatInitAffiInMapper matInitAffiInMapper = (MatInitAffiInMapper)context.getBean("matInitAffiInMapper");
				
				if("1".equals(String.valueOf(entityMap.get("p_num")))){
					
					List<Map<String,Object>>  map= matInitAffiInMapper.queryMatInPrintTemlateByMainBatch(entityMap); 
					//查询入库明细表
					List<Map<String,Object>> list=matInitAffiInMapper.queryMatInPrintTemlateByDetail(entityMap);
					reMap.put("main", map);
					
					reMap.put("detail", list); 
					
					return reMap;
				}else{

					//查询入库主表
					Map<String,Object> map=matInitAffiInMapper.queryMatInPrintTemlateByMain(entityMap); 
					//查询入库明细表
					List<Map<String,Object>> list=matInitAffiInMapper.queryMatInPrintTemlateByDetail(entityMap);
					
					reMap.put("main", map);
					
					reMap.put("detail", list); 
					
					return reMap;
							
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
		}
	
}
