/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.books.subjaccount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.dao.AccLederMapper;
import com.chd.hrp.acc.dao.books.subjaccount.GroupAccSubjLedgerMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.dao.verification.AccNostroMapper;
import com.chd.hrp.acc.entity.AccSubjLedger;
import com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService;
import com.chd.hrp.acc.service.books.subjaccount.GroupAccSubjLedgerService;
 
@Service("groupAccSubjLedgerService")
public class GroupAccSubjLedgerServiceImpl implements GroupAccSubjLedgerService {

	private static Logger logger = Logger.getLogger(GroupAccSubjLedgerServiceImpl.class);


	@Resource(name = "groupAccSubjLedgerMapper")
	private final GroupAccSubjLedgerMapper groupAccSubjLedgerMapper = null;
	
	@Resource(name = "accLederMapper")
	private final AccLederMapper accLederMapper = null;

	@Resource(name = "accNostroMapper")
	private final AccNostroMapper accNostroMapper = null;
	
	@Resource(name = "accSubjMapper")
	private final AccSubjMapper accSubjMapper = null;
	
	/**
	 * 科目总账按月  查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccSubjLedger(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

		groupAccSubjLedgerMapper.collectGroupAccSubjLedger(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String,Object>>) entityMap.get("rst"));

	}

	/**
	 * 科目总账按月  普通打印
	 */
	@Override
	public List<Map<String, Object>> collectGroupAccSubjLedgerPrintDate(
			Map<String, Object> entityMap) throws DataAccessException {
		 
		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));
		
		groupAccSubjLedgerMapper.collectGroupAccSubjLedger(entityMap); 
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		 
		 return resList;
	}
	
	/**
	 * 科目总账按月  模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccSubjLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

		entityMap.put("rst", new ArrayList<Map<String, Object>>());

		groupAccSubjLedgerMapper.collectGroupAccSubjLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", (List<Map<String, Object>>)entityMap.get("rst"));
			
		return reMap;

	}
	
	/**
	 *  科目总账 按天 查询
	 * */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccSubjLedgerDetail(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

		groupAccSubjLedgerMapper.collectGroupAccSubjLedgerDetail(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String,Object>>) entityMap.get("rst"));

	}
	/**
	 * 科目总账按天  普通打印 
	 */
	@Override
	public List<Map<String, Object>> collectGroupAccSubjLedgerDetailPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

	    groupAccSubjLedgerMapper.collectGroupAccSubjLedgerDetail(entityMap);

	    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		 
		 return resList;
	}
	/**
	 * 科目总账按天  模板打印 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupAccSubjLedgerDetailPrint(Map<String, Object> entityMap) throws DataAccessException {

		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

		entityMap.put("rst", new ArrayList<Map<String, Object>>());

		groupAccSubjLedgerMapper.collectGroupAccSubjLedgerDetail(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", (List<Map<String, Object>>)entityMap.get("rst"));
		
		return reMap;

	}
	
	/**
	 * 三栏明细账  查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupThreeColumnLedgerDetail(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

		entityMap.put("rst", new ArrayList<Map<String, Object>>());

		/* entityMap.put("rst",OracleTypes.CURSOR); */

		groupAccSubjLedgerMapper.collectGroupThreeColumnLedgerDetail(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"));

	}
	/**
	 * 三栏明细账普通打印
	 */
	@Override
	public List<Map<String, Object>> collectGroupThreeColumnLedgerDetailPrintDate(
			Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

		entityMap.put("rst", new ArrayList<Map<String, Object>>());

		/* entityMap.put("rst",OracleTypes.CURSOR); */

		groupAccSubjLedgerMapper.collectGroupThreeColumnLedgerDetail(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> resList = (List<Map<String, Object>>)entityMap.get("rst");
		
		return resList;
	}
	
	 /**
	  * 三栏明细账 模板打印
	  */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectGroupThreeColumnLedgerDetailPrint(Map<String, Object> entityMap) throws DataAccessException {

		Map<String,Object> reMap=new HashMap<String,Object>();
		
		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

		entityMap.put("rst", new ArrayList<Map<String, Object>>());

		/* entityMap.put("rst",OracleTypes.CURSOR); */

		groupAccSubjLedgerMapper.collectGroupThreeColumnLedgerDetail(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		reMap.put("detail", (List<Map<String, Object>>)entityMap.get("rst"));
		
		return reMap;

	}

	@Override
	public String collectGroupBalanceLedgerDetail(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		StringBuffer typef= new StringBuffer(); //用于type_data  拼接
		StringBuffer unionf= new StringBuffer();//用于分类小计  拼接 例如 资产小计
		StringBuffer groupf= new StringBuffer();//用于type_data Group by 拼接
		if(!"".equals(entityMap.get("subj_level"))){
			typef.append("null as subj_level");
			unionf.append("null as subj_level");
			groupf.append(""); 
		}else {
			typef.append("tt.subj_level as subj_level");
			unionf.append("subj_level as subj_level");
			groupf.append(",tt.subj_level");
		}
		entityMap.put("typef", typef);
		entityMap.put("unionf", unionf);
		entityMap.put("groupf", groupf);
		
		reList=groupAccSubjLedgerMapper.collectGroupBalanceLedgerDetail(entityMap);  
		
		return ChdJson.toJson(reList);
		 
		/*TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJson((List<AccSubjLedger>) entityMap.get("rst"));*/

	}
	
	/*
	 * 科目余额表打印
	 */
	@Override
	public Map<String,Object> collectGroupBalanceLedgerDetailPrint(Map<String, Object> entityMap) throws DataAccessException {
		  
		Map<String,Object> reMap=new HashMap<String,Object>();

		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		StringBuffer typef= new StringBuffer(); //用于type_data  拼接
		StringBuffer unionf= new StringBuffer();//用于分类小计  拼接 例如 资产小计
		StringBuffer groupf= new StringBuffer();//用于type_data Group by 拼接
		if(!"".equals(entityMap.get("subj_level"))){
			typef.append("null as subj_level");
			unionf.append("null as subj_level");
			groupf.append(""); 
		}else {
			typef.append("tt.subj_level as subj_level");
			unionf.append("subj_level as subj_level");
			groupf.append(",tt.subj_level");
		}
		entityMap.put("typef", typef);
		entityMap.put("unionf", unionf);
		entityMap.put("groupf", groupf);
		reList=groupAccSubjLedgerMapper.collectGroupBalanceLedgerDetail(entityMap);  
		
		reMap.put("detail", reList);
		
		return reMap;
		 
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectGroupAccExpendFinancial(Map<String, Object> entityMap) throws DataAccessException {

		groupAccSubjLedgerMapper.collectGroupAccExpendFinancial(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("rst"));

	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccExpendFinancialPrint(Map<String, Object> entityMap) throws DataAccessException {

		groupAccSubjLedgerMapper.collectGroupAccExpendFinancial(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		
		return resList;

	}

	@Override
	public String queryGroupAccSubjLederDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		if("01".equals(entityMap.get("acc_month"))){
			entityMap.put("begin_month", "00");
		}else{
			String yearMonth = entityMap.get("acc_year").toString()+"-"+entityMap.get("acc_month").toString()+"-01";
			
	        Date date = DateUtil.stringToDate(yearMonth.toString(), "yyyy-MM-dd");
			Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        c.add(Calendar.MONTH, -1);
	        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
	        if(month.length()==1){
	        	month="0"+month;
	        }
	        entityMap.put("begin_month", month);
	        
		}
		
		return ChdJson.toJsonLower(groupAccSubjLedgerMapper.queryGroupAccSubjLederDetail(entityMap));
	}
	
	/**
	 * 科目余额明细表打印
	 */
	@Override
	public Map<String,Object> queryGroupAccSubjLederDetailPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		
		if("01".equals(entityMap.get("acc_month"))){
			entityMap.put("begin_month", "00");
		}else{
			String yearMonth = entityMap.get("acc_year").toString()+"-"+entityMap.get("acc_month").toString()+"-01";
			
	        Date date = DateUtil.stringToDate(yearMonth.toString(), "yyyy-MM-dd");
			Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        c.add(Calendar.MONTH, -1);
	        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
	        if(month.length()==1){
	        	month="0"+month;
	        }
	        entityMap.put("begin_month", month);
	        
		}
		
		List<Map<String,Object>> list=groupAccSubjLedgerMapper.queryGroupAccSubjLederDetail(entityMap);
		
		reMap.put("detail", list);
		
		return reMap;
	}
	
	@Override
	public String queryGroupAccSubjLederCheck(Map<String, Object> entityMap)
			throws DataAccessException {
		
		StringBuffer sf= new StringBuffer();
		
		StringBuffer gf= new StringBuffer();
		
		StringBuffer cf= new StringBuffer();
		
		StringBuffer tf= new StringBuffer();
		
		StringBuffer type_id1= new StringBuffer();
		
		StringBuffer type_id2= new StringBuffer();
		
		StringBuffer type_id3= new StringBuffer();
		
		StringBuffer type_id4= new StringBuffer();
		
		String sortname=null;
		if(entityMap.get("sortname")!=null){
			sortname=entityMap.get("sortname").toString();
			entityMap.remove("sortname");
		}
		
		Map<String,Object> map = accLederMapper.queryCheckItemTable(entityMap);
		
		if(map != null){

			map.put("COLUMN_CODE1",String.valueOf(map.get("COLUMN_CODE1")).toLowerCase());
			
			map.put("COLUMN_CODE2",String.valueOf(map.get("COLUMN_CODE2")).toLowerCase());
			
			map.put("COLUMN_CODE3",String.valueOf(map.get("COLUMN_CODE3")).toLowerCase());
			
			map.put("COLUMN_CODE4",String.valueOf(map.get("COLUMN_CODE4")).toLowerCase());
			
			if( !"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE1")))){
				
				if(map.get("TABLE1")!= null){
					
					if(StringUtil.isBlank(String.valueOf(map.get("COLUMN_CHECK1")))){
						
						map.put("COLUMN_CHECK1","");
						
					}else{
						map.put("COLUMN_CHECK1",map.get("COLUMN_CHECK1")+"_ID");
						
						sf.append(String.valueOf(map.get("COLUMN_CODE1")).toLowerCase()+",");
						
						sf.append(String.valueOf(map.get("COLUMN_NAME1")).toLowerCase()+",");
						
						sf.append(map.get("COLUMN_ID1")+",");
						  
						if(!"".equals(String.valueOf(map.get("COLUMN_NO1")))&& null != String.valueOf(map.get("COLUMN_NO1"))){
							
							sf.append(String.valueOf(map.get("COLUMN_NO1")).toLowerCase()+",");
							  
						}
						
						gf.append("value1,check1,");
						cf.append("c.value1,c.check1,");
						
						tf.append(" null as value1,null as check1,");
						
					}
					
					if("V_HOS_DICT".equals(String.valueOf(map.get("TABLE1"))) || "HOS_SOURCE".equals(String.valueOf(map.get("TABLE1")))){
						map.put("COLUMN_NO1","");
					}else{
						map.put("COLUMN_CHECK1_NO",map.get("COLUMN_CHECK1").toString().substring(0,6)+"_NO");
						
					}
					
					type_id1.append("  and  a."+map.get("COLUMN_ID1")+"="+entityMap.get("check_type_id1"));
					
				}
				
			}else{
				
				map.put("COLUMN_NO1","");
				
				sf.append("a."+String.valueOf(map.get("COLUMN_CODE1")).toLowerCase()+",");
				
				sf.append("a."+String.valueOf(map.get("COLUMN_NAME1")).toLowerCase()+",");
				
				sf.append("a."+map.get("COLUMN_ID1")+",");
				
				gf.append("value1,check1,");
				
				cf.append("c.value1,c.check1,");
				
				tf.append(" null as value1,null as check1,");
				
				type_id1.append("  and  a."+map.get("COLUMN_ID1")+"="+entityMap.get("check_type_id1"));
				
				
			}
			if( !"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE2")))){
				
				if(map.get("TABLE2")!= null){
					
					if(map.get("COLUMN_CHECK2") == null){
						
						map.put("COLUMN_CHECK2","");
						
					}else{
						
						map.put("COLUMN_CHECK2",map.get("COLUMN_CHECK2")+"_ID");
						
						sf.append(String.valueOf(map.get("COLUMN_CODE2")).toLowerCase()+",");
						
						sf.append(String.valueOf(map.get("COLUMN_NAME2")).toLowerCase()+",");
						
						sf.append(map.get("COLUMN_ID2")+",");
						  
						if(!"".equals(String.valueOf(map.get("COLUMN_NO2")))&& null != String.valueOf(map.get("COLUMN_NO2"))){
							
							sf.append(String.valueOf(map.get("COLUMN_NO2")).toLowerCase()+",");
							  
						}
						
						gf.append("value2,check2,");
						
						cf.append("c.value2,c.check2,");
						
						tf.append(" null as value2,null as check2,");
						
						
					}
					if("V_HOS_DICT".equals(String.valueOf(map.get("TABLE2"))) || "HOS_SOURCE".equals(String.valueOf(map.get("TABLE2")))){
						map.put("COLUMN_NO2","");
					}else{
						map.put("COLUMN_CHECK2_NO",map.get("COLUMN_CHECK2").toString().substring(0,6)+"_NO");
					}
					
					type_id2.append("  and  b."+map.get("COLUMN_ID2")+"="+entityMap.get("check_type_id2"));
					 
				}
				
			}else{
				
				map.put("COLUMN_NO2","");
				
				sf.append("b."+String.valueOf(map.get("COLUMN_CODE2")).toLowerCase()+",");
				
				sf.append("b."+String.valueOf(map.get("COLUMN_NAME2")).toLowerCase()+",");
				
				sf.append("b."+map.get("COLUMN_ID2")+",");
				
				gf.append("value2,check2,");
				
				cf.append("c.value2,c.check2,");
				
				tf.append(" null as value2,null as check2,");
				
				type_id2.append("  and  b."+map.get("COLUMN_ID2")+"="+entityMap.get("check_type_id2"));
				 
				
			}
			if( !"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE3")))){
				if(map.get("TABLE3")!= null){
					if(map.get("COLUMN_CHECK3") == null){
						map.put("COLUMN_CHECK3","");
					}else{
						map.put("COLUMN_CHECK3",map.get("COLUMN_CHECK3")+"_ID");
						
						sf.append(String.valueOf(map.get("COLUMN_CODE3")).toLowerCase()+",");
						
						sf.append(String.valueOf(map.get("COLUMN_NAME3")).toLowerCase()+",");
						
						sf.append(map.get("COLUMN_ID3")+","); 
						
						if(!"".equals(String.valueOf(map.get("COLUMN_NO3")))&& null != String.valueOf(map.get("COLUMN_NO3"))){
							
							sf.append(String.valueOf(map.get("COLUMN_NO3")).toLowerCase()+","); 
							
						}
						
						gf.append("value3,check3,");
						
						cf.append("c.value3,c.check3,");
						
						tf.append(" null as value3,null as check3,");
						
					}
					
					if("V_HOS_DICT".equals(String.valueOf(map.get("TABLE3"))) || "HOS_SOURCE".equals(String.valueOf(map.get("TABLE3")))){
						map.put("COLUMN_NO3","");
					}else{
						map.put("COLUMN_CHECK3_NO",map.get("COLUMN_CHECK3").toString().substring(0,6)+"_NO");
					}
					
					type_id3.append("  and  c."+map.get("COLUMN_ID3")+"="+entityMap.get("check_type_id3"));
					
				}
				
			}else{
				
				map.put("COLUMN_NO3","");
				
				sf.append("c."+String.valueOf(map.get("COLUMN_CODE3")).toLowerCase()+",");
				
				sf.append("c."+String.valueOf(map.get("COLUMN_NAME3")).toLowerCase()+",");
				
				sf.append("c."+map.get("COLUMN_ID3")+",");
				
				gf.append("value3,check3,");
				
				cf.append("c.value3,c.check3,");
				
				tf.append(" null as value3,null as check3,");
				 
				type_id3.append("  and  c."+map.get("COLUMN_ID3")+"="+entityMap.get("check_type_id3"));
				
			}
			if( !"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE4")))){
				if(map.get("TABLE4")!= null){
					if(map.get("COLUMN_CHECK4") == null){
						map.put("COLUMN_CHECK4","");
					}else{
						map.put("COLUMN_CHECK4",map.get("COLUMN_CHECK4")+"_ID");
						
						sf.append(String.valueOf(map.get("COLUMN_CODE4")).toLowerCase()+",");
						
						sf.append(String.valueOf(map.get("COLUMN_NAME4")).toLowerCase()+",");
						
						sf.append(map.get("COLUMN_ID4")+","); 
						
						if(!"".equals(String.valueOf(map.get("COLUMN_NO4")))&& null != String.valueOf(map.get("COLUMN_NO4"))){
							
							sf.append(String.valueOf(map.get("COLUMN_NO4")).toLowerCase()+","); 
							
						}
						gf.append("value4,check4,");
						
						cf.append("c.value4,c.check4,");
						
						tf.append(" null as value4,null as check4,");
					}
					if("V_HOS_DICT".equals(String.valueOf(map.get("TABLE4"))) || "HOS_SOURCE".equals(String.valueOf(map.get("TABLE4")))){
						map.put("COLUMN_NO4","");
					}else{
						map.put("COLUMN_CHECK4_NO",map.get("COLUMN_CHECK4").toString().substring(0,6)+"_NO");
					}

					type_id4.append("  and  d."+map.get("COLUMN_ID4")+"="+entityMap.get("check_type_id4"));
					
				}
				
			}else{
				
				map.put("COLUMN_NO4","");
				
				sf.append("d."+String.valueOf(map.get("COLUMN_CODE4")).toLowerCase()+",");
				
				sf.append("d."+String.valueOf(map.get("COLUMN_NAME4")).toLowerCase()+",");
				
				sf.append("d."+map.get("COLUMN_ID4")+",");
				
				gf.append("value4,check4,");
				
				cf.append("c.value4,c.check4,"); 
				
				tf.append(" null as value4,null as check4,");
				
				type_id4.append("  and  d."+map.get("COLUMN_ID4")+"="+entityMap.get("check_type_id4"));
				
			}
			
			map.put("querySql", gf.substring(0, gf.length()-1));
			
			map.put("groupSql", sf.substring(0, sf.length()-1));
			
			map.put("searchSql", cf.substring(0, cf.length()-1));
			
			map.put("totalSql", tf.substring(0, tf.length()-1));
			
			map.put("typeSql1", type_id1 );
			
			map.put("typeSql2", type_id2 );
			
			map.put("typeSql3", type_id3 );
			
			map.put("typeSql4", type_id4 );
			
			entityMap.putAll(map); 
		
		}
		
		if(sortname!=null){
			entityMap.put("sortname",sortname);
		}
		List<Map<String, Object>> list = groupAccSubjLedgerMapper.queryGroupAccSubjLederCheck(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	//科目余额表 辅助核算打印
	@Override
	public List<Map<String, Object>> queryGroupAccSubjLederCheckPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		StringBuffer sf= new StringBuffer();
		
		StringBuffer gf= new StringBuffer();
		
		StringBuffer cf= new StringBuffer();
		
		StringBuffer tf= new StringBuffer();
		
		StringBuffer type_id= new StringBuffer();
		
		String sortname=null;
		if(entityMap.get("sortname")!=null){
			sortname=entityMap.get("sortname").toString();
			entityMap.remove("sortname");
		}
		
		Map<String,Object> map = accLederMapper.queryCheckItemTable(entityMap);
		
		if(map != null){
			
			map.put("COLUMN_CODE1",String.valueOf(map.get("COLUMN_CODE1")).toLowerCase());
			
			map.put("COLUMN_CODE2",String.valueOf(map.get("COLUMN_CODE2")).toLowerCase());
			
			map.put("COLUMN_CODE3",String.valueOf(map.get("COLUMN_CODE3")).toLowerCase());
			
			map.put("COLUMN_CODE4",String.valueOf(map.get("COLUMN_CODE4")).toLowerCase());
			
			if( !"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE1")))){
				
				if(map.get("TABLE1")!= null){
					
					if(StringUtil.isBlank(String.valueOf(map.get("COLUMN_CHECK1")))){
						
						map.put("COLUMN_CHECK1","");
						
					}else{
						map.put("COLUMN_CHECK1",map.get("COLUMN_CHECK1")+"_ID");
						
						sf.append(String.valueOf(map.get("COLUMN_CODE1")).toLowerCase()+",");
						
						sf.append(String.valueOf(map.get("COLUMN_NAME1")).toLowerCase()+",");
						
						sf.append(map.get("COLUMN_ID1")+",");
						
						if(!"".equals(String.valueOf(map.get("COLUMN_NO1")))&& null != String.valueOf(map.get("COLUMN_NO1"))){
							
							sf.append(String.valueOf(map.get("COLUMN_NO1")).toLowerCase()+",");
							
						}
						
						gf.append("value1,check1,");
						cf.append("c.value1,c.check1,");
						
						tf.append(" null as value1,null as check1,");
						
					}
					
					if("V_HOS_DICT".equals(String.valueOf(map.get("TABLE1"))) || "HOS_SOURCE".equals(String.valueOf(map.get("TABLE1")))){
						map.put("COLUMN_NO1","");
					}else{
						map.put("COLUMN_CHECK1_NO",map.get("COLUMN_CHECK1").toString().substring(0,6)+"_NO");
						
					}
				}
				
			}else{
				
				map.put("COLUMN_NO1","");
				
				sf.append("a."+String.valueOf(map.get("COLUMN_CODE1")).toLowerCase()+",");
				
				sf.append("a."+String.valueOf(map.get("COLUMN_NAME1")).toLowerCase()+",");
				
				sf.append("a."+map.get("COLUMN_ID1")+",");
				
				gf.append("value1,check1,");
				
				cf.append("c.value1,c.check1,");
				
				tf.append(" null as value1,null as check1,");
				
				type_id.append("  and  a.check_type_id=" +entityMap.get("check_type_id1"));
				
				
			}
			if( !"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE2")))){
				
				if(map.get("TABLE2")!= null){
					
					if(map.get("COLUMN_CHECK2") == null){
						
						map.put("COLUMN_CHECK2","");
						
					}else{
						
						map.put("COLUMN_CHECK2",map.get("COLUMN_CHECK2")+"_ID");
						
						sf.append(String.valueOf(map.get("COLUMN_CODE2")).toLowerCase()+",");
						
						sf.append(String.valueOf(map.get("COLUMN_NAME2")).toLowerCase()+",");
						
						sf.append(map.get("COLUMN_ID2")+",");
						
						if(!"".equals(String.valueOf(map.get("COLUMN_NO2")))&& null != String.valueOf(map.get("COLUMN_NO2"))){
							
							sf.append(String.valueOf(map.get("COLUMN_NO2")).toLowerCase()+",");
							
						}
						
						gf.append("value2,check2,");
						
						cf.append("c.value2,c.check2,");
						
						tf.append(" null as value2,null as check2,");
						
						
					}
					if("V_HOS_DICT".equals(String.valueOf(map.get("TABLE2"))) || "HOS_SOURCE".equals(String.valueOf(map.get("TABLE2")))){
						map.put("COLUMN_NO2","");
					}else{
						map.put("COLUMN_CHECK2_NO",map.get("COLUMN_CHECK2").toString().substring(0,6)+"_NO");
					}
				}
				
			}else{
				
				map.put("COLUMN_NO2","");
				
				sf.append("b."+String.valueOf(map.get("COLUMN_CODE2")).toLowerCase()+",");
				
				sf.append("b."+String.valueOf(map.get("COLUMN_NAME2")).toLowerCase()+",");
				
				sf.append("b."+map.get("COLUMN_ID2")+",");
				
				gf.append("value2,check2,");
				
				cf.append("c.value2,c.check2,");
				
				tf.append(" null as value2,null as check2,");
				
				type_id.append("  and  a.check_type_id=" +entityMap.get("check_type_id2"));
				
				
			}
			if( !"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE3")))){
				if(map.get("TABLE3")!= null){
					if(map.get("COLUMN_CHECK3") == null){
						map.put("COLUMN_CHECK3","");
					}else{
						map.put("COLUMN_CHECK3",map.get("COLUMN_CHECK3")+"_ID");
						
						sf.append(String.valueOf(map.get("COLUMN_CODE3")).toLowerCase()+",");
						
						sf.append(String.valueOf(map.get("COLUMN_NAME3")).toLowerCase()+",");
						
						sf.append(map.get("COLUMN_ID3")+","); 
						
						if(!"".equals(String.valueOf(map.get("COLUMN_NO3")))&& null != String.valueOf(map.get("COLUMN_NO3"))){
							
							sf.append(String.valueOf(map.get("COLUMN_NO3")).toLowerCase()+","); 
							
						}
						
						gf.append("value3,check3,");
						
						cf.append("c.value3,c.check3,");
						
						tf.append(" null as value3,null as check3,");
						
					}
					
					if("V_HOS_DICT".equals(String.valueOf(map.get("TABLE3"))) || "HOS_SOURCE".equals(String.valueOf(map.get("TABLE3")))){
						map.put("COLUMN_NO3","");
					}else{
						map.put("COLUMN_CHECK3_NO",map.get("COLUMN_CHECK3").toString().substring(0,6)+"_NO");
					}
				}
				
			}else{
				
				map.put("COLUMN_NO3","");
				
				sf.append("c."+String.valueOf(map.get("COLUMN_CODE3")).toLowerCase()+",");
				
				sf.append("c."+String.valueOf(map.get("COLUMN_NAME3")).toLowerCase()+",");
				
				sf.append("c."+map.get("COLUMN_ID3")+",");
				
				gf.append("value3,check3,");
				
				cf.append("c.value3,c.check3,");
				
				tf.append(" null as value3,null as check3,");
				
				type_id.append("  and  a.check_type_id=" +entityMap.get("check_type_id3"));
				
			}
			if( !"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE4")))){
				if(map.get("TABLE4")!= null){
					if(map.get("COLUMN_CHECK4") == null){
						map.put("COLUMN_CHECK4","");
					}else{
						map.put("COLUMN_CHECK4",map.get("COLUMN_CHECK4")+"_ID");
						
						sf.append(String.valueOf(map.get("COLUMN_CODE4")).toLowerCase()+",");
						
						sf.append(String.valueOf(map.get("COLUMN_NAME4")).toLowerCase()+",");
						
						sf.append(map.get("COLUMN_ID4")+","); 
						
						if(!"".equals(String.valueOf(map.get("COLUMN_NO4")))&& null != String.valueOf(map.get("COLUMN_NO4"))){
							
							sf.append(String.valueOf(map.get("COLUMN_NO4")).toLowerCase()+","); 
							
						}
						gf.append("value4,check4,");
						
						cf.append("c.value4,c.check4,");
						
						tf.append(" null as value4,null as check4,");
					}
					if("V_HOS_DICT".equals(String.valueOf(map.get("TABLE4"))) || "HOS_SOURCE".equals(String.valueOf(map.get("TABLE4")))){
						map.put("COLUMN_NO4","");
					}else{
						map.put("COLUMN_CHECK4_NO",map.get("COLUMN_CHECK4").toString().substring(0,6)+"_NO");
					}
				}
				
			}else{
				
				map.put("COLUMN_NO4","");
				
				sf.append("d."+String.valueOf(map.get("COLUMN_CODE4")).toLowerCase()+",");
				
				sf.append("d."+String.valueOf(map.get("COLUMN_NAME4")).toLowerCase()+",");
				
				sf.append("d."+map.get("COLUMN_ID4")+",");
				
				gf.append("value4,check4,");
				
				cf.append("c.value4,c.check4,"); 
				
				tf.append(" null as value4,null as check4,");
				
				type_id.append("  and  a.check_type_id=" +entityMap.get("check_type_id4"));
				
			}
			
			map.put("querySql", gf.substring(0, gf.length()-1));
			
			map.put("groupSql", sf.substring(0, sf.length()-1));
			
			map.put("searchSql", cf.substring(0, cf.length()-1));
			
			map.put("totalSql", tf.substring(0, tf.length()-1));
			
			map.put("typeSql", type_id );
			
			entityMap.putAll(map); 
			
		}
		
		if(sortname!=null){
			entityMap.put("sortname",sortname);
		}
		List<Map<String, Object>> list = groupAccSubjLedgerMapper.queryGroupAccSubjLederCheck(entityMap);
		
		return list;
	}

	@Override
	public String queryGroupAccSubjByPlan(Map<String, Object> entityMap)
			throws DataAccessException {
		
		List<Map<String, Object>> list = groupAccSubjLedgerMapper.queryGroupAccSubjByPlan(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * 科目汇总查询统计凭证号
	 */
	@Override
	public String queryGroupAccVouchCountSum(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuffer stateSql = new StringBuffer();
		StringBuffer subjSql = new StringBuffer();
		if("1".equals(entityMap.get("is_state"))){
			stateSql.append(" and a.state >= 1 ");
		}
		if("99".equals(entityMap.get("is_state"))){
			stateSql.append(" and a.state = 99 ");
		}
		
		entityMap.put("stateSql", stateSql.toString());
		
		if("4".equals(entityMap.get("subj_select_flag"))){
			subjSql.append(" and c.subj_code in (").append(entityMap.get("subj_codes").toString()).append(") ");
		}else if("3".equals(entityMap.get("subj_select_flag"))){
			String[] ids = entityMap.get("subj_codes").toString().split(",");
			subjSql.append(" and c.subj_code between '").append(ids[0]).append("' and  '").append(ids[1]).append("'  ");
		}else if("2".equals(entityMap.get("subj_select_flag"))){
			subjSql.append(" and c.subj_code like '").append(entityMap.get("subj_codes").toString()).append("%' ");
		}else{
			subjSql.append("");
		}
		
		entityMap.put("subjSql", subjSql.toString());
		
		List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(groupAccSubjLedgerMapper.queryGroupAccVouchCountSum(entityMap));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		StringBuffer vouch_no = new StringBuffer();
		StringBuffer vouch_num = new StringBuffer();
		
		int a =0;
		for(Map<String,Object> map : list){
			int index = vouch_no.indexOf(map.get("vouch_type_short").toString());
			if(index<0){
				if(vouch_no.length()>0){
					vouch_no.append(",");
				}
				vouch_no.append(map.get("vouch_type_short").toString()).append("：  ").append(Integer.parseInt(map.get("vouch_min").toString()));
				vouch_no.append("至").append(Integer.parseInt(map.get("vouch_max").toString()));
			}
			
			if(a==0){
				vouch_num.append(" 凭证总张数:").append(map.get("vouch_num").toString()).append("、其中作废凭证张数为:").append(map.get("vouch_void").toString());
				vouch_num.append("、附原始单据张数: ");
				vouch_num.append(Integer.parseInt(map.get("vouch_att_sums").toString()));
			}
			a++;
		}
		
		return "{\"vouch_no\":\""+vouch_no.toString()+"\",\"vouch_num\":\""+vouch_num.toString()+"\"}";
	}
	
	/**
	 * 科目余额表 辅助核算明细
	 */
	@Override
	public String queryGroupAccSubjLedgerCheckDetail(Map<String, Object> entityMap) throws DataAccessException {
		//看科目挂有那个辅助核算
		StringBuffer columnName= new StringBuffer();
        StringBuffer  joinSql = new StringBuffer();
        StringBuffer  whereSql = new StringBuffer();
        StringBuffer showColumn = new StringBuffer();
		
        List<Map<String, Object>> sqlList = accNostroMapper.querySubjCheckColumnBySubjList(entityMap);
        
        int a = 1;
        for(Map<String, Object> sqlMap : sqlList){
        	
        	columnName.append(" ,dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_CODE")).append(" ||' '|| dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NAME").toString()).append(" as value").append(String.valueOf(a)).append(" ");	
        	showColumn.append(",value").append(String.valueOf(a).toString()).append(" ");
    		//是否是变更表
    		if(sqlMap.get("CHECK_TABLE")!=null && !"".equals(sqlMap.get("CHECK_TABLE").toString())){
    		    if(sqlMap.get("IS_CHANGE") != null &&  "1".equals(sqlMap.get("IS_CHANGE").toString())){
    		    	
    		    	joinSql.append(" left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd").append(String.valueOf(a)).append(" ");
    		    	joinSql.append(" on dd").append(String.valueOf(a)).append(".group_id = avc.group_id and dd").append(String.valueOf(a)).append(".hos_id = avc.hos_id  and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID");
    		    	joinSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NO")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK").toString()).append("_NO");	
    		    	if(!"".equals(entityMap.get("value1")) && !"undefined".equals(entityMap.get("value1").toString())){
    		    		//showColumn.append(",value1");
    					if(entityMap.get("value1").toString().contains(".")){
    						String[] s = entityMap.get("value1").toString().split("\\.");
    						whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
    						whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]);
    					}
    				}
    		    	if(!"".equals(entityMap.get("check_type_id1")) && null != entityMap.get("check_type_id1")){

    		    		//系统辅助核算不用加check_type_id
    		    		//whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id1").toString());

    				}
    		    	if(!"".equals(entityMap.get("value2")) && !"undefined".equals(entityMap.get("value2").toString())){
    		    		//showColumn.append(",value2");
    		    		if(entityMap.get("value2").toString().contains(".")){
    						String[] s = entityMap.get("value2").toString().split("\\.");
    						whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
    						whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]);
    					}
    				}
    		    	if(!"".equals(entityMap.get("check_type_id2")) && null != entityMap.get("check_type_id2")){

    		    		//系统辅助核算不用加check_type_id
    		    	//	whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id2").toString());

    				} 
    		    	if(!"".equals(entityMap.get("value3")) && !"undefined".equals(entityMap.get("value3").toString())){
    		    		//showColumn.append(",value3");
    					if(entityMap.get("value3").toString().contains(".")){
    						String[] s = entityMap.get("value3").toString().split("\\.");
    						whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
    						whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]);
    					}
    				}
    		    	if(!"".equals(entityMap.get("check_type_id3")) && null != entityMap.get("check_type_id3")){

    		    		//系统辅助核算不用加check_type_id
    		    	//	whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id3").toString());

    				}
    		    	if(!"".equals(entityMap.get("value4")) && !"undefined".equals(entityMap.get("value4").toString())){
    		    		//showColumn.append(",value4");
    					if(entityMap.get("value4").toString().contains(".")){
    						String[] s = entityMap.get("value4").toString().split("\\.");
    						whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
    						whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]);
    					}
    				}
    		    	if(!"".equals(entityMap.get("check_type_id4")) && null != entityMap.get("check_type_id4")){

    		    		//系统辅助核算不用加check_type_id
    		    		//whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id4").toString());

    				}
    		    	
    		    }else{
    			    //自定义辅助核算
    			    if(sqlMap.get("CHECK_TABLE").equals("ACC_CHECK_ITEM")){ 
    			       joinSql.append("  left join acc_check_item dd").append(String.valueOf(a)).append(" on dd").append(String.valueOf(a)).append(".group_id = avc.group_id and dd").append(String.valueOf(a)).append(".hos_id = avc.hos_id and dd").append(String.valueOf(a)).append(".copy_code = avc.copy_code and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK"));
    			       if(!"".equals(entityMap.get("value1")) && !"undefined".equals(entityMap.get("value1").toString())){
    			    	   if(!entityMap.get("value1").toString().contains(".")){
    			    		   whereSql.append(" and dd").append(String.valueOf(a)).append(".check_item_id = ").append(entityMap.get("value1").toString());
    			    	   }
    			    	   
    					}
    			       if(!"".equals(entityMap.get("check_type_id1")) && null != entityMap.get("check_type_id1")){

       		    		whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id1").toString());

       					}
    			       if(!"".equals(entityMap.get("value2")) && !"undefined".equals(entityMap.get("value2").toString())){
    			    	   if(!entityMap.get("value2").toString().contains(".")){
    			    		   whereSql.append(" and dd").append(String.valueOf(a)).append(".check_item_id = ").append(entityMap.get("value2").toString());
    			    	   }
    					}
    			       if(!"".equals(entityMap.get("check_type_id2")) && null != entityMap.get("check_type_id2")){

          		    		whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id2").toString());

          					}
    			       if(!"".equals(entityMap.get("value3")) && !"undefined".equals(entityMap.get("value3").toString())){
    			    	   if(!entityMap.get("value3").toString().contains(".")){
    			    		   whereSql.append(" and dd").append(String.valueOf(a)).append(".check_item_id = ").append(entityMap.get("value3").toString());
    			    	   }
    					}
    			       if(!"".equals(entityMap.get("check_type_id3")) && null != entityMap.get("check_type_id3")){

          		    		whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id3").toString());

          					}
    			       if(!"".equals(entityMap.get("value4")) && !"undefined".equals(entityMap.get("value4").toString())){
    			    	   if(!entityMap.get("value4").toString().contains(".")){
    			    		   whereSql.append(" and dd").append(String.valueOf(a)).append(".check_item_id = ").append(entityMap.get("value4").toString());
    			    	   }
    					}
    			       if(!"".equals(entityMap.get("check_type_id4")) && null != entityMap.get("check_type_id4")){

       		    		whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id4").toString());

       					}
    			    }else{
    			    	
    				   //资金来源、单位
    			       joinSql.append(" left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd").append(String.valueOf(a)).append(" ");
    			       joinSql.append(" on dd").append(String.valueOf(a)).append(".group_id = avc.group_id and dd").append(String.valueOf(a)).append(".hos_id = avc.hos_id and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID");
    			       if(!"".equals(entityMap.get("value1")) && !"undefined".equals(entityMap.get("value1").toString())){
    			    	   if(!entityMap.get("value1").toString().contains(".")){
    			    		   whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(entityMap.get("value1").toString());
    			    	   }
    			    	   
    					}
    			       if(!"".equals(entityMap.get("check_type_id1")) && null != entityMap.get("check_type_id1")){
    			    	 //资金来源不用加check_type_id
          		    		//whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id1").toString());

          				}
    			       if(!"".equals(entityMap.get("value2")) && !"undefined".equals(entityMap.get("value2").toString())){
    			    	   if(!entityMap.get("value2").toString().contains(".")){
    			    		   whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(entityMap.get("value2").toString());
    			    	   }
    			       }
    			       if(!"".equals(entityMap.get("check_type_id2")) && null != entityMap.get("check_type_id2")){
    			    	   //资金来源不用加check_type_id
          		    		//whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id2").toString());

          					}
    			       if(!"".equals(entityMap.get("value3")) && !"undefined".equals(entityMap.get("value3").toString())){
    			    	   if(!entityMap.get("value3").toString().contains(".")){
    			    		   whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(entityMap.get("value3").toString());
    			    	   }
    			       }
    			       if(!"".equals(entityMap.get("check_type_id3")) && null != entityMap.get("check_type_id3")){
    			    	   //资金来源不用加check_type_id
          		    		//whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id3").toString());

          					}
    			       if(!"".equals(entityMap.get("value4")) && !"undefined".equals(entityMap.get("value4").toString())){
    			    	   if(!entityMap.get("value4").toString().contains(".")){
    			    		   whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(entityMap.get("value4").toString());
    			    	   }
    					}
    			       if(!"".equals(entityMap.get("check_type_id4")) && null != entityMap.get("check_type_id4")){
    			    	   //资金来源不用加check_type_id
          		    		//whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id4").toString());

          					}
    			    }	
    		   }   
    		}
    		a++;
        }
       
	    entityMap.put("joinSql", joinSql.toString());
	    entityMap.put("columnName", columnName.toString());
	    entityMap.put("whereSql", whereSql.toString());
	    entityMap.put("showColumn", showColumn.toString());
	    
	    if("01".equals(entityMap.get("acc_month"))){
			entityMap.put("begin_month", "00");
		}else{
			String yearMonth = entityMap.get("acc_year").toString()+"-"+entityMap.get("acc_month").toString()+"-01";
			
	        Date date = DateUtil.stringToDate(yearMonth.toString(), "yyyy-MM-dd");
			Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        c.add(Calendar.MONTH, -1);
	        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
	        if(month.length()==1){
	        	month="0"+month;
	        }
	        entityMap.put("begin_month", month);
	        
		}
	    
		List<Map<String, Object>> list = groupAccSubjLedgerMapper.queryGroupAccSubjLedgerCheckDetail(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
	/**
	 * 科目余额表 辅助核算明细打印
	 */
	@Override
	public List<Map<String, Object>> queryGroupAccSubjLedgerCheckDetailPrint(Map<String, Object> entityMap) throws DataAccessException {
		//看科目挂有那个辅助核算
		StringBuffer columnName= new StringBuffer();
		StringBuffer  joinSql = new StringBuffer();
		StringBuffer  whereSql = new StringBuffer();
		StringBuffer showColumn = new StringBuffer();
		
		List<Map<String, Object>> sqlList = accNostroMapper.querySubjCheckColumnBySubjList(entityMap);
		
		int a = 1;
		for(Map<String, Object> sqlMap : sqlList){
			
			columnName.append(" ,dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_CODE")).append(" ||' '|| dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NAME").toString()).append(" as value").append(String.valueOf(a)).append(" ");	
			showColumn.append(",value").append(String.valueOf(a).toString()).append(" ");
			//是否是变更表
			if(sqlMap.get("CHECK_TABLE")!=null && !"".equals(sqlMap.get("CHECK_TABLE").toString())){
				if(sqlMap.get("IS_CHANGE") != null &&  "1".equals(sqlMap.get("IS_CHANGE").toString())){
					
					joinSql.append(" left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd").append(String.valueOf(a)).append(" ");
					joinSql.append(" on dd").append(String.valueOf(a)).append(".group_id = avc.group_id and dd").append(String.valueOf(a)).append(".hos_id = avc.hos_id  and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID");
					joinSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NO")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK").toString()).append("_NO");	
					if(!"".equals(entityMap.get("value1")) && !"undefined".equals(entityMap.get("value1").toString())){
						//showColumn.append(",value1");
						if(entityMap.get("value1").toString().contains(".")){
							String[] s = entityMap.get("value1").toString().split("\\.");
							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]);
						}
					}
					if(!"".equals(entityMap.get("check_type_id1")) && null != entityMap.get("check_type_id1")){
						
						//系统辅助核算不用加check_type_id
						//whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id1").toString());
						
					}
					if(!"".equals(entityMap.get("value2")) && !"undefined".equals(entityMap.get("value2").toString())){
						//showColumn.append(",value2");
						if(entityMap.get("value2").toString().contains(".")){
							String[] s = entityMap.get("value2").toString().split("\\.");
							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]);
						}
					}
					if(!"".equals(entityMap.get("check_type_id2")) && null != entityMap.get("check_type_id2")){
						
						//系统辅助核算不用加check_type_id
						//	whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id2").toString());
						
					} 
					if(!"".equals(entityMap.get("value3")) && !"undefined".equals(entityMap.get("value3").toString())){
						//showColumn.append(",value3");
						if(entityMap.get("value3").toString().contains(".")){
							String[] s = entityMap.get("value3").toString().split("\\.");
							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]);
						}
					}
					if(!"".equals(entityMap.get("check_type_id3")) && null != entityMap.get("check_type_id3")){
						
						//系统辅助核算不用加check_type_id
						//	whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id3").toString());
						
					}
					if(!"".equals(entityMap.get("value4")) && !"undefined".equals(entityMap.get("value4").toString())){
						//showColumn.append(",value4");
						if(entityMap.get("value4").toString().contains(".")){
							String[] s = entityMap.get("value4").toString().split("\\.");
							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]);
						}
					}
					if(!"".equals(entityMap.get("check_type_id4")) && null != entityMap.get("check_type_id4")){
						
						//系统辅助核算不用加check_type_id
						//whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id4").toString());
						
					}
					
				}else{
					//自定义辅助核算
					if(sqlMap.get("CHECK_TABLE").equals("ACC_CHECK_ITEM")){ 
						joinSql.append("  left join acc_check_item dd").append(String.valueOf(a)).append(" on dd").append(String.valueOf(a)).append(".group_id = avc.group_id and dd").append(String.valueOf(a)).append(".hos_id = avc.hos_id and dd").append(String.valueOf(a)).append(".copy_code = avc.copy_code and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK"));
						if(!"".equals(entityMap.get("value1")) && !"undefined".equals(entityMap.get("value1").toString())){
							if(!entityMap.get("value1").toString().contains(".")){
								whereSql.append(" and dd").append(String.valueOf(a)).append(".check_item_id = ").append(entityMap.get("value1").toString());
							}
							
						}
						if(!"".equals(entityMap.get("check_type_id1")) && null != entityMap.get("check_type_id1")){
							
							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id1").toString());
							
						}
						if(!"".equals(entityMap.get("value2")) && !"undefined".equals(entityMap.get("value2").toString())){
							if(!entityMap.get("value2").toString().contains(".")){
								whereSql.append(" and dd").append(String.valueOf(a)).append(".check_item_id = ").append(entityMap.get("value2").toString());
							}
						}
						if(!"".equals(entityMap.get("check_type_id2")) && null != entityMap.get("check_type_id2")){
							
							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id2").toString());
							
						}
						if(!"".equals(entityMap.get("value3")) && !"undefined".equals(entityMap.get("value3").toString())){
							if(!entityMap.get("value3").toString().contains(".")){
								whereSql.append(" and dd").append(String.valueOf(a)).append(".check_item_id = ").append(entityMap.get("value3").toString());
							}
						}
						if(!"".equals(entityMap.get("check_type_id3")) && null != entityMap.get("check_type_id3")){
							
							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id3").toString());
							
						}
						if(!"".equals(entityMap.get("value4")) && !"undefined".equals(entityMap.get("value4").toString())){
							if(!entityMap.get("value4").toString().contains(".")){
								whereSql.append(" and dd").append(String.valueOf(a)).append(".check_item_id = ").append(entityMap.get("value4").toString());
							}
						}
						if(!"".equals(entityMap.get("check_type_id4")) && null != entityMap.get("check_type_id4")){
							
							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id4").toString());
							
						}
					}else{
						
						//资金来源、单位
						joinSql.append(" left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd").append(String.valueOf(a)).append(" ");
						joinSql.append(" on dd").append(String.valueOf(a)).append(".group_id = avc.group_id and dd").append(String.valueOf(a)).append(".hos_id = avc.hos_id and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID");
						if(!"".equals(entityMap.get("value1")) && !"undefined".equals(entityMap.get("value1").toString())){
							if(!entityMap.get("value1").toString().contains(".")){
								whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(entityMap.get("value1").toString());
							}
							
						}
						if(!"".equals(entityMap.get("check_type_id1")) && null != entityMap.get("check_type_id1")){
							//资金来源不用加check_type_id
							//whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id1").toString());
							
						}
						if(!"".equals(entityMap.get("value2")) && !"undefined".equals(entityMap.get("value2").toString())){
							if(!entityMap.get("value2").toString().contains(".")){
								whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(entityMap.get("value2").toString());
							}
						}
						if(!"".equals(entityMap.get("check_type_id2")) && null != entityMap.get("check_type_id2")){
							//资金来源不用加check_type_id
							//whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id2").toString());
							
						}
						if(!"".equals(entityMap.get("value3")) && !"undefined".equals(entityMap.get("value3").toString())){
							if(!entityMap.get("value3").toString().contains(".")){
								whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(entityMap.get("value3").toString());
							}
						}
						if(!"".equals(entityMap.get("check_type_id3")) && null != entityMap.get("check_type_id3")){
							//资金来源不用加check_type_id
							//whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id3").toString());
							
						}
						if(!"".equals(entityMap.get("value4")) && !"undefined".equals(entityMap.get("value4").toString())){
							if(!entityMap.get("value4").toString().contains(".")){
								whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = ").append(entityMap.get("value4").toString());
							}
						}
						if(!"".equals(entityMap.get("check_type_id4")) && null != entityMap.get("check_type_id4")){
							//资金来源不用加check_type_id
							//whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id").append(" = ").append(entityMap.get("check_type_id4").toString());
							
						}
					}	
				}   
			}
			a++;
		}
		
		entityMap.put("joinSql", joinSql.toString());
		entityMap.put("columnName", columnName.toString());
		entityMap.put("whereSql", whereSql.toString());
		entityMap.put("showColumn", showColumn.toString());
		
		if("01".equals(entityMap.get("acc_month"))){
			entityMap.put("begin_month", "00");
		}else{
			String yearMonth = entityMap.get("acc_year").toString()+"-"+entityMap.get("acc_month").toString()+"-01";
			
			Date date = DateUtil.stringToDate(yearMonth.toString(), "yyyy-MM-dd");
			Calendar c = Calendar.getInstance();  
			c.setTime(date);  
			c.add(Calendar.MONTH, -1);
			String month = String.valueOf(c.get(Calendar.MONTH) + 1);
			if(month.length()==1){
				month="0"+month;
			}
			entityMap.put("begin_month", month);
			
		}
		
		List<Map<String, Object>> list = groupAccSubjLedgerMapper.queryGroupAccSubjLedgerCheckDetail(entityMap);
		
		return  list ; 
	}
	
	/**
	 * 科目汇总表明细
	 */
	@Override
	public String queryGroupAccVouchCountSumDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuffer stateSql = new StringBuffer();
		StringBuffer subjSql = new StringBuffer();
		StringBuffer levelSql = new StringBuffer();
		StringBuffer levelSql2 = new StringBuffer();
		
		if("99".equals(entityMap.get("subj_level"))){
			levelSql.append(" and c.is_last = 1 ");
		}
		if(!"99".equals(entityMap.get("subj_level"))){
			if(!"".equals(entityMap.get("subj_level"))){
				levelSql.append(" and c.subj_level = ").append(entityMap.get("subj_level"));
			}else{
				levelSql.append("");
			}
			
		}
		
		
		if("99".equals(entityMap.get("subj_level"))){
			levelSql2.append(" and  cc.is_last = 1 ");
		}
		if(!"99".equals(entityMap.get("subj_level"))){
			if(!"".equals(entityMap.get("subj_level"))){
				levelSql2.append(" and cc.subj_level <= ").append(entityMap.get("subj_level"));
			}else{
				levelSql2.append("");
			}
			
		}else{
			levelSql2.append("");
		}
			
		entityMap.put("levelSql2", levelSql2.toString());
		entityMap.put("levelSql", levelSql.toString());
		
		if("1".equals(entityMap.get("is_state"))){
			stateSql.append(" and a.state >= 1 ");
		}
		if("99".equals(entityMap.get("is_state"))){
			stateSql.append(" and a.state = 99 ");
		}
		
		entityMap.put("stateSql", stateSql.toString());
		
		if("4".equals(entityMap.get("subj_select_flag"))){ 
			// 账簿中通过subj_code 查询subj_id   用于 通过科目选择器选择多个科目  
			String subjMap =accSubjMapper.queryAccSubjByCodeSearchId(entityMap); 
			entityMap.put("subj_codes", subjMap) ;  
			subjSql.append(" and c.subj_id in (").append(entityMap.get("subj_codes").toString()).append(") "); 
		}else if("3".equals(entityMap.get("subj_select_flag"))){
			String[] ids = entityMap.get("subj_codes").toString().split(",");
			subjSql.append(" and c.subj_code between '").append(ids[0]).append("' and  '").append(ids[1]).append("'  ");
		}else if("2".equals(entityMap.get("subj_select_flag"))){
			subjSql.append(" and c.subj_code like '").append(entityMap.get("subj_codes").toString()).append("%' ");
		}else{ 
			subjSql.append("");
		}
		
		entityMap.put("subjSql", subjSql.toString());
		
		List<Map<String, Object>> list =  groupAccSubjLedgerMapper.queryGroupAccVouchCountSumDetail(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * 查询科目  按subj_code查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryGroupAccSubjTree(Map<String, Object> mapVo) throws DataAccessException {
			StringBuilder jsonResult = new StringBuilder();
			jsonResult.append("{Rows:[");
			List<Map<String, Object>> list = groupAccSubjLedgerMapper.queryGroupAccSubjTree(mapVo);
			if(list.size()>0){
				
				for (Map<String, Object> map : list) {
					if("top".equals(map.get("super_code"))){
					jsonResult.append("{'id' : '"+map.get("subj_code")+"' ,'name' : '"+map.get("subj_name")+"'},");

					}else{
					jsonResult.append("{'id' : '"+map.get("subj_code")+"' ,'name' : '"+map.get("subj_name")+"', 'pId': '"+map.get("super_code")+"'},");
					}
				}
				jsonResult.setCharAt(jsonResult.length()-1, ' ');
			}
			jsonResult.append("]}");
		return jsonResult.toString();
	}

	@Override
	public String queryGroupAccColumnsLedgerDetail(Map<String, Object> mapVo) {
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapVoS= new HashMap<String, Object>();
		Map<String,Object>entityMap=groupAccSubjLedgerMapper.queryGroupAccColumnsLedgerBegin(mapVo);
		List<Map<String,Object>>ListVo=groupAccSubjLedgerMapper.queryGroupAccColumnsLedgerDetail(mapVo);
		if(entityMap==null){
			mapVoS.put("end_os", 0);
		}else{
			entityMap.put("end_os", Double.parseDouble(entityMap.get("credit").toString())+Double.parseDouble(entityMap.get("debit").toString()));
			mapVoS.putAll(entityMap);
		}
		
		if(ListVo.size()>1){
			Map<String,Object>  map1=ListVo.get(0);
			Double amount_money = Double.parseDouble(mapVoS.get("end_os").toString())+Double.parseDouble(map1.get("amount_money").toString());
			map1.put("end_os",amount_money);
			returnList.add(map1);
			for (int i = 1 ; i<ListVo.size();i++) {
				Map<String,Object> map=ListVo.get(i);
				 if("借".equals(map.get("subj_dire"))){
					 amount_money=amount_money+Double.parseDouble(map.get("debit").toString())-Double.parseDouble(map.get("credit").toString());
					 map.put("end_os", amount_money);
				 }else{
					 amount_money=amount_money+Double.parseDouble(map.get("credit").toString())-Double.parseDouble(map.get("debit").toString());
					 map.put("end_os", amount_money);
				 }
				 map.put("j"+map.get("subj_code"), map.get("code_debit"));
				 map.put("d"+map.get("subj_code"), map.get("code_credit"));
				 
				 
				 returnList.add(map);
			}
		} 
		
		return ChdJson.toJsonLower(returnList);
	}
	
	
	


	@Override
	public Map<String, Object> collectGroupAccSubjLedgerSumMainPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();

		//List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
		
		StringBuffer stateSql = new StringBuffer();
		StringBuffer subjSql = new StringBuffer();
		StringBuffer levelSql = new StringBuffer();
		StringBuffer levelSql2 = new StringBuffer();
		
		if("99".equals(entityMap.get("subj_level"))){
			levelSql.append(" and c.is_last = 1 ");
		}
		if(!"99".equals(entityMap.get("subj_level"))){
			if(!"".equals(entityMap.get("subj_level"))){
				levelSql.append(" and c.subj_level = ").append(entityMap.get("subj_level"));
			}else{
				levelSql.append("");
			}
			
		}
		
		
		if("99".equals(entityMap.get("subj_level"))){
			levelSql2.append(" and  cc.is_last = 1 ");
		}
		if(!"99".equals(entityMap.get("subj_level"))){
			if(!"".equals(entityMap.get("subj_level"))){
				levelSql2.append(" and cc.subj_level <= ").append(entityMap.get("subj_level"));
			}else{
				levelSql2.append("");
			}
			
		}else{
			levelSql2.append("");
		}
			
		entityMap.put("levelSql2", levelSql2.toString());
		entityMap.put("levelSql", levelSql.toString());
		
		if("1".equals(entityMap.get("is_state"))){
			stateSql.append(" and a.state >= 1 ");
		}
		if("99".equals(entityMap.get("is_state"))){
			stateSql.append(" and a.state = 99 ");
		}
		
		entityMap.put("stateSql", stateSql.toString());
		
		if("4".equals(entityMap.get("subj_select_flag"))){ 
			// 账簿中通过subj_code 查询subj_id   用于 通过科目选择器选择多个科目  
			String subjMap =accSubjMapper.queryAccSubjByCodeSearchId(entityMap); 
			entityMap.put("subj_codes", subjMap) ;  
			subjSql.append(" and c.subj_id in (").append(entityMap.get("subj_codes").toString()).append(") "); 
		}else if("3".equals(entityMap.get("subj_select_flag"))){
			String[] ids = entityMap.get("subj_codes").toString().split(",");
			subjSql.append(" and c.subj_code between '").append(ids[0]).append("' and  '").append(ids[1]).append("'  ");
		}else if("2".equals(entityMap.get("subj_select_flag"))){
			subjSql.append(" and c.subj_code like '").append(entityMap.get("subj_codes").toString()).append("%' ");
		}else{ 
			subjSql.append("");
		}
		
		entityMap.put("subjSql", subjSql.toString());
		
		List<Map<String, Object>> reList =  groupAccSubjLedgerMapper.queryGroupAccVouchCountSumDetail(entityMap);
		
		//reList=groupAccSubjLedgerMapper.collectBalanceLedgerDetail(entityMap);  
		
		reMap.put("detail", ChdJson.toListLower(reList));
		
		return reMap;
	}

	@Override
	public List<Map<String, Object>> queryGroupAccColumnsLedgerDetailPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapVoS= new HashMap<String, Object>();
		Map<String,Object> entityMap1=groupAccSubjLedgerMapper.queryGroupAccColumnsLedgerBegin(entityMap);
		List<Map<String,Object>>ListVo=groupAccSubjLedgerMapper.queryGroupAccColumnsLedgerDetail(entityMap);
		if(entityMap1==null){
			mapVoS.put("end_os", 0);
		}else{
			entityMap1.put("end_os", Double.parseDouble(entityMap1.get("credit").toString())+Double.parseDouble(entityMap1.get("debit").toString()));
			mapVoS.putAll(entityMap1);
		}
		
		if(ListVo.size()>1){
			Map<String,Object>  map1=ListVo.get(0); 
			Double amount_money = Double.parseDouble(mapVoS.get("end_os").toString())+Double.parseDouble(map1.get("amount_money").toString());
			map1.put("end_os",amount_money);
			returnList.add(map1);
			for (int i = 1 ; i<ListVo.size();i++) {
				Map<String,Object> map=ListVo.get(i);
				 if("借".equals(map.get("subj_dire"))){
					 amount_money=amount_money+Double.parseDouble(map.get("debit").toString())-Double.parseDouble(map.get("credit").toString());
					 map.put("end_os", amount_money);
				 }else{
					 amount_money=amount_money+Double.parseDouble(map.get("credit").toString())-Double.parseDouble(map.get("debit").toString());
					 map.put("end_os", amount_money);
				 }
				 map.put("j"+map.get("subj_code"), map.get("code_debit"));
				 map.put("d"+map.get("subj_code"), map.get("code_credit"));
				 
				 
				 returnList.add(map);
			}
		} 
		
		return  returnList ;
	}

	
	 
	//科目余额表 普通打印
	@Override
	public List<Map<String, Object>> collectGroupBalanceLedgerDetailPrintDate( Map<String, Object> entityMap) throws DataAccessException {
		Map<String,Object> reMap=new HashMap<String,Object>();
		StringBuffer typef= new StringBuffer(); //用于type_data  拼接
		StringBuffer unionf= new StringBuffer();//用于分类小计  拼接 例如 资产小计
		StringBuffer groupf= new StringBuffer();//用于type_data Group by 拼接
		if(!"".equals(entityMap.get("subj_level"))){
			typef.append("null as subj_level");
			unionf.append("null as subj_level");
			groupf.append(""); 
		}else {
			typef.append("tt.subj_level as subj_level");
			unionf.append("subj_level as subj_level");
			groupf.append(",tt.subj_level");
		}
		entityMap.put("typef", typef);
		entityMap.put("unionf", unionf);
		entityMap.put("groupf", groupf);
		List<Map<String, Object>> reList= groupAccSubjLedgerMapper.collectGroupBalanceLedgerDetail(entityMap);  
		return reList;
	}
	
	

}