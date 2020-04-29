/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.storage.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.storage.query.MedInSupCountToFimMapper;
import com.chd.hrp.med.service.storage.query.MedInDetailToFimService;
import com.github.pagehelper.PageInfo;


/**
 * 
 * @Description:
 * 入库明细查询
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medInDetailToFimService")
public class MedInDetailToFimServiceImpl implements MedInDetailToFimService {

	private static Logger logger = Logger.getLogger(MedInDetailToFimServiceImpl.class);
	
 
	@Resource(name = "medInSupCountToFimMapper")
	private final MedInSupCountToFimMapper medInSupCountToFimMapper = null;
 

	@Override
	public String queryMedInSupCount(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
    
			 
			
			List<Map<String, Object>> list = medInSupCountToFimMapper.queryMedInSupCount(entityMap);
			List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
			LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
				Map<String,Object>map=list.get(i);
				typeToMoneymap.put((String)map.get("fim_type_code"), map.get("money"));
				
				if(returnDataMap.containsKey(map.get("sup_code")))
				{
					//根据部门編碼取出取出之前存入（或运算后）的总金额
					Map sumMoney=returnDataMap.get(map.get("sup_code"));
					//总金额
					Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
					//最新一条数据的金额
					Double count2= Double.parseDouble(map.get("money").toString());
					//两条数据相加修改amount_money
					sumMoney.put("amount_money", count+count2);
					sumMoney.putAll(typeToMoneymap);
					//将运算后的数据从新封装
					returnDataMap.put((String) map.get("sup_code"), sumMoney);
						
					 
				}else{
					//将转换后的数据填充到通过sql语句查询出来的map中
					 map.putAll(typeToMoneymap);
					 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
					 returnDataMap.put((String) map.get("sup_code"), map);
					 Map sumMoney= returnDataMap.get(map.get("sup_code"));
					//取出当前部门的金额填入到amount_money中
					 sumMoney.put("amount_money",map.get("money"));
					 returnDataMap.put((String) map.get("sup_code"), sumMoney);
				}
			}
			for (String  j : returnDataMap.keySet()) {
				
				overloadinglist.add(returnDataMap.get(j));
			}
			return ChdJson.toJson(overloadinglist);
		}
 



	
}
