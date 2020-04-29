/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.affi.back;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.mat.dao.affi.back.MatAffiBackMapper;
import com.chd.hrp.mat.dao.affi.in.MatAffiInCommonMapper;
import com.chd.hrp.mat.dao.base.MatAffiInMapper;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatInCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.initial.MatInitChargeMapper;
import com.chd.hrp.mat.entity.MatAffiIn;
import com.chd.hrp.mat.service.affi.back.MatAffiBackService;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 常备材料退货 
 * @Table:
 * MAT_IN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matAffiBackService")
public class MatAffiBackServiceImpl implements MatAffiBackService {

	private static Logger logger = Logger.getLogger(MatAffiBackServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matInCommonMapper")
	private final MatInCommonMapper matInCommonMapper = null;
	
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	
	@Resource(name = "matInitChargeMapper")
	private final MatInitChargeMapper matInitChargeMapper = null;
	
	@Resource(name = "matAffiBackMapper")
	private final MatAffiBackMapper matAffiBackMapper = null;
	
	@Resource(name = "matAffiInMapper")
	private final MatAffiInMapper matAffiInMapper = null;
	
	@Resource(name = "matAffiInCommonMapper")
	private final MatAffiInCommonMapper matAffiInCommonMapper = null;
	
	
	/**
	 * @Description 
	 * 添加常备材料退货<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		try {
			
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));

			if(entityMap.get("in_date") == null || "".equals(entityMap.get("in_date"))){
				return "{\"error\":\"入库日期不能为空\",\"state\":\"false\"}";
			}
			//截取期间
			String[] date = entityMap.get("in_date").toString().split("-");
			//entityMap.put("year", date[0]);
			//entityMap.put("month", date[1]);
			entityMap.put("day", date[2]);  //用于生成单号
			
			//主表的年月取会计期间表
			entityMap.put("dateString", entityMap.get("in_date").toString());
			Map<String,Object> monthMap = JsonListMapUtil.toMapLower(matCommonMapper.queryAccYearAndMonth(entityMap));
			entityMap.put("year", monthMap.get("year"));
			entityMap.put("month", monthMap.get("month"));
			
			//转换日期格式
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("in_date").toString(), "yyyy-MM-dd"));
			}
			
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			//判断退货日期所在期间是否已结账
			
			//自动生成常备材料退货单据号
			if("自动生成".equals(entityMap.get("in_no"))){
				entityMap.put("table_code", "MAT_AFFI_IN");
				entityMap.put("in_no", matCommonService.getMatNextNo(entityMap));
			}
			//取出主表ID（自增序列）
			entityMap.put("in_id", matAffiInMapper.queryAffiInMainSeq());
			StringBuffer invEnoughMsg = new StringBuffer(); 
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(entityMap.get("detailData"))){
				float money_sum = 0;//记录明细总金额
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					//不存在材料ID视为空行
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_id")))) {
						Map<String,Object> detailMap = new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表ID
						detailMap.put("store_id", entityMap.get("store_id"));//库房ID用于判断库存
						//detailMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());//明细ID
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料ID
						if("0".equals(jsonObj.get("price"))){
							detailMap.put("price",  "0.00");//单价
						}else{
							detailMap.put("price",  jsonObj.get("price").toString());//单价
						}
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						detailMap.put("batch_no",  jsonObj.get("batch_no"));//批号
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))){
							detailMap.put("sell_price",  jsonObj.get("sell_price"));//零售价
						}else{
							detailMap.put("sell_price", "0.00");//零售价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_money")))){
							detailMap.put("sell_money",  jsonObj.get("sell_money"));//零售金额
						}else{
							detailMap.put("sell_money", "0.00");//零售金额
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))){
							detailMap.put("inva_date", DateUtil.stringToDate( jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate( jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
							detailMap.put("bar_code",  jsonObj.get("bar_code"));//个体码
							detailMap.put("sn",  jsonObj.get("bar_code"));//条形码
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id", "0");//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}

						/*材料批次信息---------------begin---------------------*/
						//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
							
							for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
								//没材料ID视为空行
								if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
									Map<String, Object> invMap = new HashMap<String, Object>();
									
									invMap.putAll(detailMap);
									invMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());//明细ID
									if (ChdJson.validateJSON(String.valueOf(m.get("batch_sn")))) {invMap.put("batch_sn", m.get("batch_sn"));}
									if (ChdJson.validateJSON(String.valueOf(m.get("amount")))) {invMap.put("amount", m.get("amount"));}
									if (ChdJson.validateJSON(String.valueOf(m.get("amount_money")))) {
										invMap.put("amount_money", m.get("amount_money"));
									}
									if (ChdJson.validateJSON(String.valueOf(m.get("sale_money")))) {invMap.put("sale_money", m.get("sale_money"));}
									if (ChdJson.validateJSON(String.valueOf(m.get("sell_money")))) {invMap.put("sell_money", m.get("sell_money"));}
									//该条明细数据添加到List中
									detailList.add(invMap);
								}
							}
						}else{
							//按先进先出匹配批次信息
							List<Map<String, Object>> fifoList = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatAffiOutDetailInvList(detailMap));
							Double amount = (-1)*Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
							Double cur_amount = null;
							//按先进先出生成出库单
							for(Map<String, Object> map : fifoList){
								Map<String, Object> invMap = new HashMap<String, Object>();
								invMap.putAll(detailMap);
								
								invMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());//明细ID
								invMap.put("batch_sn", map.get("batch_sn"));
								//当前批次当前库存
								cur_amount = Double.valueOf(String.valueOf((map.get("cur_amount") == null ? 0 : map.get("imme_amount"))));
								//判断当前批号批次是否充足
								if(amount <= cur_amount){
									invMap.put("amount", -1*amount);
									//计算金额
									invMap.put("amount_money", NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para));
									invMap.put("sale_money", NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para));
									invMap.put("sell_money", NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para));
									invMap.put("allot_money", NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para));
									
									//该条明细数据添加到List中
									detailList.add(invMap);
									amount = 0.0;
									break;
								}else{
									//取当前批号批次数量并且申请单数量响应减少
									invMap.put("amount", -1*cur_amount);		
									//计算金额
									invMap.put("amount_money", NumberUtil.numberToRound(-1*cur_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para));
									invMap.put("sale_money", NumberUtil.numberToRound(-1*cur_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para));
									invMap.put("sell_money", NumberUtil.numberToRound(-1*cur_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para));
									invMap.put("allot_money", NumberUtil.numberToRound(-1*cur_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para));
									money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
									//该条明细数据添加到List中
									detailList.add(invMap);			
									amount = NumberUtil.sub(amount, cur_amount);
								}
								//当数量为0，证明已经完成先进先出操作
								if(amount == 0){
									break;
								}
							}
							if(amount > 0){
								invEnoughMsg.append(String.valueOf(jsonObj.get("inv_code"))).append(" ").append(String.valueOf(jsonObj.get("inv_name"))).append(",");
							}
						}
						/*材料批次信息---------------end-----------------------*/
					}
				}
				
				if(detailList.size() <= 0){
					return "{\"error\":\"出库单明细数据为空!\"}";
				}
				if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
					return "{\"error\":\""+invEnoughMsg.toString()+"库存物资不足!\"}";
				}
				//判断即时库存是否充足(防止多人同时操作同一材料，额外加的判断)
				//String invEnough = matAffiBackMapper.existsMatAffiBackInvIsEnough(detailList);
				//if(invEnough != null && !"".equals(invEnough)){
				//	return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
				//}
				
				//新增退货主表数据
				matAffiInMapper.addMatAffiInMain(entityMap);
				//新增退货明细数据
				matAffiInMapper.addMatAffiInDetail(detailList);
				
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatStorageBack\"}";
		}
		return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("in_id").toString()+","
			+"\"}";
	}
	/**
	 * @Description 
	 * 批量添加常备材料退货<BR> 
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
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatStorageBack\"}";
		}
	}
	
		/**
	 * @Description 
	 * 更新常备材料退货<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			if(entityMap.get("state") != null && !"".equals(entityMap.get("state")) && !"1".equals(entityMap.get("state"))){
				return "{\"error\":\"更新失败,单据状态不为未审核状态！.\",\"state\":\"false\"}";
			}
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));

			StringBuffer invEnoughMsg = new StringBuffer(); 
			
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(entityMap.get("detailData"))){
				float money_sum = 0;//记录明细总金额
				//修改明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				StringBuffer detail_ids = new StringBuffer();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						Map<String,Object> detailMap = new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id").toString());
						detailMap.put("hos_id", entityMap.get("hos_id").toString());
						detailMap.put("copy_code", entityMap.get("copy_code").toString());
						detailMap.put("in_id", entityMap.get("in_id").toString());//主表ID
						detailMap.put("in_no", entityMap.get("in_no").toString());//主表ID
						detailMap.put("store_id", entityMap.get("store_id").toString());//库房ID用于判断库存
						/*if(ChdJson.validateJSON(String.valueOf(jsonObj.get("detail_id")))){
							detailMap.put("detail_id", jsonObj.get("detail_id"));//明细ID
						}else{
							detailMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());//明细ID
						}*/
						detailMap.put("inv_id", jsonObj.get("inv_id").toString());//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no").toString());//材料NO
						//detailMap.put("price",  jsonObj.get("price").toString());//单价
						if("0".equals(jsonObj.get("price").toString())){
							detailMap.put("price",  "0.00");//单价
						}else{
							detailMap.put("price",  jsonObj.get("price").toString());//单价
						}
						detailMap.put("amount",  jsonObj.get("amount").toString());//数量
						detailMap.put("amount_money",  jsonObj.get("amount_money").toString());//金额
						detailMap.put("batch_no",  jsonObj.get("batch_no").toString());//批号
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))){
							detailMap.put("sell_price",  jsonObj.get("sell_price").toString());//零售价
						}else{
							detailMap.put("sell_price",  "0.00");//零售价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_money")))){
							detailMap.put("sell_money",  jsonObj.get("sell_money").toString());//零售金额
						}else{
							detailMap.put("sell_money",  "0.00");//零售金额
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))){
							detailMap.put("inva_date", DateUtil.stringToDate( jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate( jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
							detailMap.put("bar_code",  jsonObj.get("bar_code").toString());//个体码
							detailMap.put("sn",  jsonObj.get("bar_code").toString());//条形码
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id", "0");//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}

						/*材料批次信息---------------begin---------------------*/
						//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
							
							for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
								//没材料ID视为空行
								if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
									Map<String, Object> invMap = new HashMap<String, Object>();
									invMap.putAll(detailMap);
									
									invMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());//明细ID
									
									if (ChdJson.validateJSON(String.valueOf(m.get("batch_sn")))) {
										invMap.put("batch_sn", m.get("batch_sn").toString());
									}
									if (ChdJson.validateJSON(String.valueOf(m.get("amount")))) {
										invMap.put("amount", m.get("amount").toString());
									}
									if (ChdJson.validateJSON(String.valueOf(m.get("amount_money")))) {
										invMap.put("amount_money", m.get("amount_money").toString());
									}
									if (ChdJson.validateJSON(String.valueOf(m.get("sale_money")))) {
										invMap.put("sale_money", m.get("sale_money").toString());
									}
									if (ChdJson.validateJSON(String.valueOf(m.get("sell_money")))) {
										invMap.put("sell_money", m.get("sell_money").toString());
									}
									//该条明细数据添加到List中
									detailList.add(invMap);
								}
							}
						}else{
							//按先进先出匹配批次信息
							List<Map<String, Object>> fifoList = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatAffiOutDetailInvList(detailMap));
							Double amount = -1*Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
							Double imme_amount = null;
							//按先进先出生成出库单
							for(Map<String, Object> map : fifoList){
								Map<String, Object> invMap = new HashMap<String, Object>();
								invMap.putAll(detailMap);
								invMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());//明细ID
								invMap.put("batch_sn", map.get("batch_sn").toString());
								//当前批次即时库存
								imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
								//判断当前批号批次是否充足
								if(amount <= imme_amount){
									invMap.put("amount", -1*amount);
									invMap.put("amount", invMap.get("amount").toString());
									//计算金额
									invMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
									invMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
									invMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
									invMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
									
									//该条明细数据添加到List中
									detailList.add(invMap);
									amount = 0.0;
									break;
								}else{
									//取当前批号批次数量并且申请单数量响应减少
									invMap.put("amount", -1*imme_amount);	
									invMap.put("amount", invMap.get("amount").toString());
									//计算金额
									invMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(-1*imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
									invMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(-1*imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
									invMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(-1*imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
									invMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(-1*imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
									
									//该条明细数据添加到List中
									detailList.add(invMap);			
									amount = NumberUtil.sub(amount, imme_amount);
								}
								//当数量为0，证明已经完成先进先出操作
								if(amount == 0){
									break;
								}
							}
							if(amount > 0){
								invEnoughMsg.append(String.valueOf(jsonObj.get("inv_code"))).append(" ").append(String.valueOf(jsonObj.get("inv_name"))).append(",");
							}
						}
						/*材料批次信息---------------end-----------------------*/
						
					}
				}
				
				if(detailList.size() <= 0){
					return "{\"error\":\"退货单明细数据为空!\"}";
				}
				if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
					return "{\"error\":\""+invEnoughMsg.toString()+"库存物资不足!\"}";
				}
				//判断即时库存是否充足(防止多人同时操作同一材料，额外加的判断)
				String invEnough = matAffiBackMapper.existsMatAffiBackInvIsEnough(detailList);
				if(invEnough != null && !"".equals(invEnough)){
					return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
				}
				
				//修改退货主表数据
				matAffiInMapper.updateMatAffiInMain(entityMap);
				//明细先删除再添加
				matAffiInMapper.deleteMatAffiInDetail(entityMap);
				matAffiInMapper.addMatAffiInDetail(detailList);
				
			}else{
				return "{\"error\":\"更新失败，明细数据为空\",\"state\":\"false\"}";
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatStorageBack\"}";
		}		
	}
	/**
	 * @Description 
	 * 批量更新常备材料退货，无此业务<BR> 
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
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMatStorageBack\"}";
		}	
	}
	/**
	 * @Description 
	 * 删除常备材料退货<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
		try {
			//状态不为新建不能删除，从页面判断
			//期初记账状态不为0的不能删除，从页面判断
			//删除明细
			matAffiInMapper.deleteMatAffiInDetail(entityMap);
			//删除主表
			matAffiInMapper.deleteMatAffiInMain(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatStorageBack\"}";
		}	
  }
    
	/**
	 * @Description 
	 * 批量删除常备材料退货<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//状态不为新建不能删除，从页面判断
			//期初记账状态不为0的不能删除，从页面判断
			//批量删除明细表
			matAffiInMapper.deleteBatchMatAffiInDetail(entityList);
			//批量删除主表
			matAffiInMapper.deleteBatchMatAffiInMain(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatStorageBack\"}";
		}	
	}

	/**
	 * @Description 
	 * 批量复制<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String copyMatAffiBackBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、入库单Id、材料Id
			String copy_code = "", in_ids = "";
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
				in_ids = in_ids + m.get("in_id").toString() + ",";
			}
			//组装数据
			inMap.put("group_id", group_id);
			inMap.put("hos_id", hos_id);
			inMap.put("copy_code", copy_code);
			inMap.put("in_ids", in_ids.substring(0, in_ids.length()-1));
			
			//获取选中的主表、明细表数据
			List<Map<String, Object>> mainList = (List<Map<String, Object>>)matAffiInMapper.queryMatInMainByIds(inMap);
			//List<Map<String, Object>> detailList = (List<Map<String, Object>>)matAffiInMapper.queryMatInDetailByIds(inMap);
			
			//存放组装数据的List
			List<Map<String, Object>> insertMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> insertDetailList = new ArrayList<Map<String,Object>>();
			Map<String, List<Map<String, Object>>> fifoMap = new HashMap<String, List<Map<String,Object>>>(); //存放材料批次信息
			
			/*复制操作中的固定值-----begin--------*/
			Date date = new Date();
			String[] dates = DateUtil.dateToString(date, "yyyy-MM-dd").split("-");
			String user_id = SessionManager.getUserId();
			String old_id;
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));  //金额位数
			String fifoKey = "";
			StringBuffer invEnoughMsg = new StringBuffer(); 
			//用于查询明细记录
			Map<String, Object> entityDetailMap = new HashMap<String, Object>();
			entityDetailMap.put("group_id", group_id);
			entityDetailMap.put("hos_id", hos_id);
			entityDetailMap.put("copy_code", copy_code);
			/*复制操作中的固定值-----end--------*/
			
			//循环组装数据
			for(Map<String, Object> mainMap : mainList){ 
				old_id = mainMap.get("in_id").toString();//记录旧的in_id用于筛选明细
				mainMap.put("in_id", matAffiInMapper.queryAffiInMainSeq());//替换旧的主表ID（自增序列）
				mainMap.put("in_date", date);//编制日期
				mainMap.put("year", dates[0]);//年份
				mainMap.put("month", dates[1]);//月份
				mainMap.put("day", dates[2]);  //用于生成单号
				mainMap.put("maker", user_id);//制单人
				mainMap.put("state", 1);//状态（新建状态）
				mainMap.put("table_code", "MAT_AFFI_IN");
				mainMap.put("in_no", matCommonService.getMatNextNo(mainMap));
				
				//添加到主表新增的list中
				insertMainList.add(mainMap);
				
				entityDetailMap.put("in_id", old_id);
				List<Map<String, Object>> detailList = JsonListMapUtil.toListMapLower((List<Map<String, Object>>)matAffiBackMapper.queryMatAffiInDetailForCopy(entityDetailMap));
				
				/*处理明细-------begin-------*/
				for(Map<String, Object> detailMap : detailList){
					detailMap.put("in_id", mainMap.get("in_id"));//替换旧的主表ID
					detailMap.put("in_no", mainMap.get("in_no"));//替换旧的主表单号
					detailMap.put("store_id", mainMap.get("store_id"));//用于判断库存是否充足
					
					/**********************按先进先出匹配批次信息************************/
					fifoKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString() + detailMap.get("bar_code").toString() + Double.parseDouble(detailMap.get("price").toString());
					List<Map<String, Object>> fifoList;
					if(fifoMap.get(fifoKey) != null){
						fifoList = fifoMap.get(fifoKey);
					}else{
						fifoList = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatStockInvDetailList(detailMap));
					}
					//由于退货数量为负数所以要转成整数判断
					Double amount = -1 * Double.valueOf(String.valueOf((detailMap.get("amount") == null ? 0 : detailMap.get("amount"))));
					Double imme_amount = null;
					List<Map<String, Object>> keyList = new ArrayList<Map<String,Object>>();
					//按先进先出生成出库单
					for(Map<String, Object> map : fifoList){
						//当数量为0，证明已经完成先进先出操作
						if(amount == 0){
							keyList.add(map);
							continue;
						}
						Map<String, Object> invMap = new HashMap<String, Object>();
						invMap.putAll(detailMap);
						invMap.put("batch_sn", map.get("batch_sn").toString());
						//当前批次即时库存
						imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
						//判断当前批号批次是否充足
						if(amount <= imme_amount){
							invMap.put("amount", NumberUtil.numberToString(-1 * amount));
							//计算金额
							invMap.put("amount_money", NumberUtil.numberToString(amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para));
							invMap.put("sale_money", NumberUtil.numberToString(amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para));
							invMap.put("sell_money", NumberUtil.numberToString(amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para));
							invMap.put("allot_money", NumberUtil.numberToString(amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para));

							invMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());
							insertDetailList.add(invMap);
							map.put("imme_amount", NumberUtil.sub(imme_amount, amount));
							amount = 0.0;
							keyList.add(map);
							break;
						}else{
							//取当前批号批次数量并且申请单数量响应减少
							invMap.put("amount", NumberUtil.numberToString(-1 * imme_amount));		
							//计算金额
							invMap.put("amount_money", NumberUtil.numberToString(imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para));
							invMap.put("sale_money", NumberUtil.numberToString(imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para));
							invMap.put("sell_money", NumberUtil.numberToString(imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para));
							invMap.put("allot_money", NumberUtil.numberToString(imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para));
			
							invMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());
							insertDetailList.add(invMap);			
							amount = NumberUtil.sub(amount, imme_amount);
						}
						//当数量为0，证明已经完成先进先出操作
						if(amount == 0){
							break;
						}
					}
					fifoMap.put(fifoKey, keyList);
					if(amount > 0){
						invEnoughMsg.append(String.valueOf(detailMap.get("inv_code"))).append(" ").append(String.valueOf(detailMap.get("inv_name"))).append(",");
					}
				}
			}
			if(insertDetailList.size() == 0){
				return "{\"error\":\"所选单据中的材料库存物资不足!\"}";
			}
			if(invEnoughMsg.length() > 0){
				return "{\"error\":\""+invEnoughMsg.toString()+"库存物资不足!\"}";
			}
			//判断即时库存是否充足
			String invEnough = matAffiBackMapper.existsMatAffiBackInvIsEnough(insertDetailList);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
			}
			
			//批量新增主表数据
			matAffiInMapper.addMatAffiInMainBatch(insertMainList);
			//批量新增明细表数据
			matAffiInMapper.addMatAffiInDetail(insertDetailList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 copyMatStorageBackBatch\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String auditMatAffiBack(Map<String, Object> entityMap) throws DataAccessException {
		
		try {	
			//批量审核
			matAffiBackMapper.auditOrUnAudit(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 auditMatStorageBack\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String unAuditMatAffiBack(Map<String, Object> entityMap) throws DataAccessException {
		
		try {	
			//批量消审
			matAffiBackMapper.auditOrUnAudit(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 unAuditMatStorageBack\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 退货确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String confirmMatAffiBack(Map<String, Object> entityMap) throws DataAccessException {
		
		try {	
			//判断当前期间是否已结账，如结账，则不能添加入库单
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("year", entityMap.get("confirm_date").toString().substring(0, 4));
			map.put("month", entityMap.get("confirm_date").toString().substring(5, 7));
			map.put("day", entityMap.get("confirm_date").toString().substring(8, 10));
			int flag = matCommonMapper.existsMatYearMonthIsAccount(map);
			if(flag != 0){
				return "{\"error\":\"当月已结账不能确认！\",\"state\":\"false\"}";
			}
			//操作用户
			entityMap.put("user_id", SessionManager.getUserId());
			//因存储过程为批量确认故需要该参数
			entityMap.put("in_ids", entityMap.get("in_id"));
			
			//批量退货确认
			matAffiInCommonMapper.confirmAffiIn(entityMap);

			return entityMap.get("msg") == null ? "" : entityMap.get("msg").toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			//throw new SysException("{\"error\":\"操作失败\"}");
			return "{\"error\":\"退货确认失败! 方法 confirmMatAffiBack\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 批量审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String auditMatAffiBackBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//批量审核
			matAffiBackMapper.auditOrUnAuditBatch(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			//throw new SysException("{\"error\":\"操作失败\"}");
			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 auditMatStorageBackBatch\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 批量消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String unAuditMatAffiBackBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//批量消审
			matAffiBackMapper.auditOrUnAuditBatch(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 unAuditMatStorageBackBatch\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 批量退货确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String confirmMatAffiBackBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {	
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、退货单Id、材料Id
			String copy_code = "", in_ids = "" , confirm_date = "";
			Map<String, Object> inMap = new HashMap<String, Object>();
			//取得所有in_id组装map
			for(Map<String, Object> m : entityList){
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
			inMap.put("user_id", SessionManager.getUserId());
			inMap.put("in_ids", in_ids.substring(0, in_ids.length()-1));
			inMap.put("confirm_date", confirm_date);
			
			//判断当前期间是否已结账，如结账，则不能添加入库单
			inMap.put("year", confirm_date.substring(0, 4));
			inMap.put("month", confirm_date.substring(5, 7));
			inMap.put("day", confirm_date.substring(8,10));
			int flag = matCommonMapper.existsMatYearMonthIsAccount(inMap);
			if(flag != 0){
				return "{\"error\":\"当月已结账不能确认！\",\"state\":\"false\"}";
			}
			
			//批量退货确认
			matAffiInCommonMapper.confirmAffiIn(inMap);

			return inMap.get("msg") == null ? "" : inMap.get("msg").toString();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			//throw new SysException("{\"error\":\"操作失败\"}");
			return "{\"error\":\"操作失败! 方法 confirmMatStorageBackBatch\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 添加常备材料退货<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象常备材料退货
		MatAffiIn matAffiInMain = queryByCode(entityMap);

		if (matAffiInMain != null) {

			int state = matAffiBackMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matAffiBackMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatStorageBack\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集常备材料退货<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matAffiBackMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matAffiBackMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 获取对象常备材料退货<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matAffiInMapper.queryMatAffiInByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取对象常备材料退货<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAffiDetailById(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String, Object>> list = matAffiBackMapper.queryMatAffiBackDetailById(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * @Description 
	 * 获取对象常备材料退货<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAffiDetailByCode(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String, Object>> list = matAffiBackMapper.queryMatAffiBackDetailByCode(entityMap);
		
		return matCommonService.getAffiDetailJsonByDetailList(JsonListMapUtil.toListMapLower(list));
	}
	
	/**
	 * @Description 
	 * 获取常备材料退货<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatCommonIn
	 * @throws DataAccessException
	*/
	@Override
	public  <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matAffiBackMapper.queryByUniqueness(entityMap);
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Description 入库单查询
	 * @param mapVo
	 * @param mode 
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryAffiIn(Map<String, Object> entityMap) throws DataAccessException {
	/*	SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){*/
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiBackMapper.queryAffiIn(entityMap);
			
			return ChdJson.toJsonLower(list);
		/*}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiBackMapper.queryAffiIn(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}*/
	}
	
	/**
	 * @Description 入库单查询
	 * @param mapVo
	 * @param mode 
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryMatAffiBackInSup(Map<String, Object> entityMap) throws DataAccessException {
	/*	SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){*/
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiBackMapper.queryMatAffiBackInSup(entityMap);
			
			return ChdJson.toJsonLower(list);
		/*}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiBackMapper.queryAffiIn(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}*/
	}

	/**
	 * @Description 入库单明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryAffiInDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiBackMapper.queryAffiInDetail(entityMap);
			
		return ChdJson.toJsonLower(list);
	}

	/**
	 * @Description 入库单明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryBackDetailByImp(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiBackMapper.queryBackDetailByImp(entityMap);
			
		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryMatAffiInBeforeOrNext(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			//定义入库单ID
			String in_id = "";
			
			//下一张
			if("before".equals(entityMap.get("type").toString())){
				in_id = matAffiInMapper.queryMatAffiInBefore(entityMap);
				if("".equals(in_id)){
					return "{\"msg\":\"已经是第一张单据了！\",\"state\":\"false\"}";
				}
			}else if("next".equals(entityMap.get("type").toString())){
				in_id = matAffiInMapper.queryMatAffiInNext(entityMap);
				if(in_id == null || "".equals(in_id)){
					return "{\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"操作失败 页面参数异常 请联系管理员！方法queryMatAffiInBeforeOrNext\"}";
			}
			
			return "{\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+","+
				entityMap.get("hos_id").toString()+","+
				entityMap.get("copy_code").toString()+","+
				in_id+","
				+"\"}";
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMatAffiInBeforeOrNext\"}";
		}	
	}
	
	@Override
	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiBackMapper.queryDetails(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matAffiBackMapper.queryDetails(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
}