/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.assdisposal.special;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.assdisposal.special.AssDisposalApplyDetailSpecialMapper;
import com.chd.hrp.ass.dao.assdisposal.special.AssDisposalArMapSpecialMapper;
import com.chd.hrp.ass.dao.assdisposal.special.AssDisposalRecordDetailSpecialMapper;
import com.chd.hrp.ass.dao.assdisposal.special.AssDisposalRecordSourceSpecialMapper;
import com.chd.hrp.ass.dao.assdisposal.special.AssDisposalRecordSpecialMapper;
import com.chd.hrp.ass.dao.card.AssCardSpecialMapper;
import com.chd.hrp.ass.dao.resource.AssResourceSpecialMapper;
import com.chd.hrp.ass.entity.assdisposal.special.AssDisposalApplyDetailSpecial;
import com.chd.hrp.ass.entity.assdisposal.special.AssDisposalRecordSpecial;
import com.chd.hrp.ass.entity.resource.AssResourceSpecial;
import com.chd.hrp.ass.service.assdisposal.special.AssDisposalRecordSpecialService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 051001资产处置明细(专用设备)
 * @Table: ASS_DISPOSAL_A_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
@Service("assDisposalRecordSpecialService")
public class AssDisposalRecordSpecialServiceImpl implements AssDisposalRecordSpecialService {    
 
	private static Logger logger = Logger.getLogger(AssDisposalRecordSpecialServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assDisposalRecordSpecialMapper")
	private final AssDisposalRecordSpecialMapper assDisposalRecordSpecialMapper = null;
	
	@Resource(name = "assDisposalRecordDetailSpecialMapper")
	private final AssDisposalRecordDetailSpecialMapper assDisposalRecordDetailSpecialMapper = null;
	
	@Resource(name = "assDisposalApplyDetailSpecialMapper")
	private final AssDisposalApplyDetailSpecialMapper assDisposalApplyDetailSpecialMapper = null;
	
	@Resource(name = "assDisposalRecordSourceSpecialMapper")
	private final AssDisposalRecordSourceSpecialMapper assDisposalRecordSourceSpecialMapper = null;
	
	
	@Resource(name = "assDisposalArMapSpecialMapper")
	private final AssDisposalArMapSpecialMapper assDisposalArMapSpecialMapper = null;
	
	@Resource(name = "assCardSpecialMapper")
	private final AssCardSpecialMapper assCardSpecialMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;
	
	@Resource(name = "assResourceSpecialMapper")
	private final AssResourceSpecialMapper assResourceSpecialMapper = null;
	
	/**
	 * @Description 添加051001资产处置明细(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象051001资产处置明细(专用设备)
		AssDisposalRecordSpecial assDisposalAspecial = queryByCode(entityMap);

		if (assDisposalAspecial != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assDisposalRecordSpecialMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加051001资产处置明细(专用设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assDisposalRecordSpecialMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新051001资产处置明细(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assDisposalRecordSpecialMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新051001资产处置明细(专用设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assDisposalRecordSpecialMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除051001资产处置明细(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assDisposalRecordSpecialMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除051001资产处置明细(专用设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			assDisposalRecordSourceSpecialMapper.deleteBatch(entityList);
	    	assDisposalRecordDetailSpecialMapper.deleteBatch(entityList);
			assDisposalRecordSpecialMapper.deleteBatch(entityList);
			assDisposalArMapSpecialMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加051001资产处置明细(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listSourceVo = new ArrayList<Map<String, Object>>();
		
		//主表
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("dis_r_no", entityMap.get("dis_r_no"));
		
		Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
		vCreateDateMap.put("group_id", entityMap.get("group_id"));
		vCreateDateMap.put("hos_id", entityMap.get("hos_id"));
		vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
		vCreateDateMap.put("ass_nature", "02");
		vCreateDateMap.put("use_state", "1,2,3,4,5,6");
		vCreateDateMap.put("in_date", entityMap.get("create_date"));
		
		entityMap.put("state", "0");
		entityMap.put("create_date", entityMap.get("create_date"));
		List<AssDisposalRecordSpecial> list = (List<AssDisposalRecordSpecial>) assDisposalRecordSpecialMapper.queryExists(mapVo);
		try {
			boolean flag = true;
			if (list.size() > 0) {
				assDisposalRecordSpecialMapper.update(entityMap);
			} else {
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_DISPOSAL_R_SPECIAL");
				String dis_r_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("dis_r_no", dis_r_no);
				assDisposalRecordSpecialMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("ass_card_no", detailVo.get("ass_card_no").toString());
				vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));
				
				Integer createDateExists = hrpAssSelectMapper.queryAssExistsSpecialCard(vCreateDateMap);
				if(createDateExists == 0){
					flag = false;
					break;
				}
				
				
				String create_date = entityMap.get("create_date").toString();

				detailVo.put("ass_year", create_date.substring(0, create_date.indexOf("-")));

				detailVo.put("ass_month",
						create_date.substring(create_date.indexOf("-") + 1, create_date.lastIndexOf("-")));

				detailVo.put("ass_naturs", "02");

				String results = collectAssDisposalRecordSpecial(detailVo);

				JSONObject jsonObj = JSONObject.parseObject(results);

				if (jsonObj.containsKey("msg")) {
					transactionManager.rollback(status);
					return jsonObj.toJSONString();
				}

				JSONArray cardArray = JSONArray.parseArray(jsonObj.get("Card").toString());

				JSONObject cardObj = JSONObject.parseObject(cardArray.get(0).toString());

				detailVo.put("dis_r_no", entityMap.get("dis_r_no"));

				detailVo.put("price", detailVo.get("price").toString());

				detailVo.put("now_depre", cardObj.get("NowDepre").toString());

				detailVo.put("now_manage_depre", cardObj.get("NowManageDepre").toString());

				detailVo.put("add_depre",cardObj.get("AddDepre").toString());

				detailVo.put("add_manage_depre", cardObj.get("AddManageDepre").toString());

				detailVo.put("cur_money", cardObj.get("Cur").toString());

				detailVo.put("fore_money", cardObj.get("Fore").toString());
				
				detailVo.put("add_depre_month", cardObj.get("AddAccMonth").toString());

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}
				
				if (detailVo.get("dispose_cost") != null && !detailVo.get("dispose_cost").equals("")) {
					detailVo.put("dispose_cost", detailVo.get("dispose_cost"));
				} else {
					detailVo.put("dispose_cost", 0);
				}
				
				if (detailVo.get("dispose_income") != null && !detailVo.get("dispose_income").equals("")) {
					detailVo.put("dispose_income", detailVo.get("dispose_income"));
				} else {
					detailVo.put("dispose_income", 0);
				}
				
				if (detailVo.get("dispose_tax") != null && !detailVo.get("dispose_tax").equals("")) {
					detailVo.put("dispose_tax", detailVo.get("dispose_tax"));
				} else {
					detailVo.put("dispose_tax", 0);
				}

				JSONArray sourceArray = JSONArray.parseArray(jsonObj.get("Source").toString());

				List<AssResourceSpecial> resourceList = assResourceSpecialMapper.queryByAssCardNo(detailVo);
				for (AssResourceSpecial resSource : resourceList) {
					Map<String, Object> mapSource = new HashMap<String, Object>();
					mapSource.put("group_id", resSource.getGroup_id());
					mapSource.put("hos_id", resSource.getHos_id());
					mapSource.put("copy_code", resSource.getCopy_code());
					mapSource.put("dis_r_no", entityMap.get("dis_r_no"));
					mapSource.put("ass_card_no", resSource.getAss_card_no());
					mapSource.put("source_id", resSource.getSource_id());
					mapSource.put("price", resSource.getPrice());
					for (int i = 0; i < sourceArray.size(); i++) {
						JSONObject job = sourceArray.getJSONObject(i);
						if (resSource.getSource_id() == Long.parseLong(job.get("ID").toString())) {
							mapSource.put("now_depre", job.get("NowDepre").toString());
							mapSource.put("add_depre", job.get("AddDepre").toString());
							mapSource.put("cur_money", job.get("Cur").toString());
							mapSource.put("fore_money", job.get("Fore").toString());
							mapSource.put("now_manage_depre", job.get("NowManageDepre").toString());
							mapSource.put("add_manage_depre", job.get("AddManageDepre").toString());
							mapSource.put("add_depre_month", job.get("AddAccMonth").toString());
							
						}
					}

					if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
						mapSource.put("note", detailVo.get("note"));
					} else {
						mapSource.put("note", "");
					}
					listSourceVo.add(mapSource);
				}
				
				listVo.add(detailVo);
			}
			
			if(!flag){
				transactionManager.rollback(status);
				return "{\"warn\":\"存在尚未入账的卡片不能进行处置.\"}";
			}
			
			assDisposalRecordSourceSpecialMapper.delete(entityMap);
			assDisposalRecordDetailSpecialMapper.delete(entityMap);
			assDisposalRecordDetailSpecialMapper.addBatch(listVo);
			if (listSourceVo.size() > 0) {
				assDisposalRecordSourceSpecialMapper.addBatch(listSourceVo);
			}
			//entityMap.put("back_money", back_money);
			//assBackSpecialMapper.updateBackMoney(entityMap);
			transactionManager.commit(status);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"dis_r_no\":\"" + entityMap.get("dis_r_no").toString()+"\"}";
		} catch (Exception e) {
			transactionManager.rollback(status);
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}
	
	public String collectAssDisposalRecordSpecial(Map<String, Object> entityMap) {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

		assDisposalRecordSpecialMapper.collectAssDisposalRecordSpecial(entityMap);

		String ass_AppCode = entityMap.get("ass_AppCode").toString();
		String ass_ErrTxt = "";
		if (entityMap.get("ass_ErrTxt") != null && !entityMap.get("ass_ErrTxt").equals("")) {
			ass_ErrTxt = entityMap.get("ass_ErrTxt").toString();
		}

		if ("0".equals(ass_AppCode)) {
			// 计算成功！提交事务
			transactionManager.commit(status);
			return entityMap.get("ass_json_str").toString();

		} else if ("-1".equals(ass_AppCode) || "100".equals(ass_AppCode)) {
			// 计算失败！回滚事务
			transactionManager.rollback(status);

			return "{\"msg\":\"" + ass_ErrTxt + "\",\"state\":\"" + ass_AppCode + "\"}";
		}

		if (!"".equals(entityMap.get("ass_ErrTxt").toString()) && null != entityMap.get("ass_ErrTxt").toString()) {

			ass_ErrTxt = entityMap.get("ass_ErrTxt").toString();

		}
		return "{\"msg\":\"" + ass_ErrTxt + "\",\"state\":\"" + ass_AppCode + "\"}";

	}


	/**
	 * @Description 查询结果集051001资产处置明细(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssDisposalRecordSpecial> list = (List<AssDisposalRecordSpecial>) assDisposalRecordSpecialMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssDisposalRecordSpecial> list = (List<AssDisposalRecordSpecial>) assDisposalRecordSpecialMapper
					.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象051001资产处置明细(专用设备)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assDisposalRecordSpecialMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取051001资产处置明细(专用设备)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssDisposalRecordSpecial
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assDisposalRecordSpecialMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取051001资产处置明细(专用设备)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssDisposalRecordSpecial>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assDisposalRecordSpecialMapper.queryExists(entityMap);
	}

	@Override
	public String updateConfirmDisposalRecordSpecial(List<Map<String, Object>> listVo,
			List<Map<String, Object>> listCardVo) throws DataAccessException {
		try {
			assDisposalRecordSpecialMapper.updateBatchConfirm(listVo);
			assCardSpecialMapper.updateDisposalRecordConfirm(listCardVo);
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	
//	查询未确认数据单号  打印专用
	@Override
	public List<String> queryState(Map<String, Object> entityMap) throws DataAccessException {
		 return assDisposalRecordSpecialMapper.queryState(entityMap);
		  
	}
	
	//出库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public Map<String,Object> assDisposalRecordSpecialByPrintTemlate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		AssDisposalRecordSpecialMapper assDisposalRecordSpecialMapper = (AssDisposalRecordSpecialMapper)context.getBean("assDisposalRecordSpecialMapper");
		
		
		if("1".equals(String.valueOf(entityMap.get("p_num")))){
			//查询移库主表批量
			List<Map<String,Object>> map = assDisposalRecordSpecialMapper.queryAssDisRecordSpecialPrintTemlateByMainBatch(entityMap);
			//查询移库明细表
			List<Map<String,Object>> list = assDisposalRecordSpecialMapper.queryAssDisRecordSpecialPrintTemlateByDetail(entityMap);
			
			reMap.put("main", map);
			reMap.put("detail", list);
			
			return reMap;
		}else{
			//查询移库主表
			Map<String,Object> map = assDisposalRecordSpecialMapper.queryAssDisRecordSpecialPrintTemlateByMain(entityMap);
			//查询移库明细表
			List<Map<String,Object>> list = assDisposalRecordSpecialMapper.queryAssDisRecordSpecialPrintTemlateByDetail(entityMap);
			
			reMap.put("main", map);
			reMap.put("detail", list);
			
			return reMap;
		}
		
	}

	@Override
	public String initAssCheckSpecial(Map<String, Object> entityMap) throws DataAccessException {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listSourceVo = new ArrayList<Map<String, Object>>();
		try {
			
			String carno = entityMap.get("dis_a_no").toString();
			String car[] = carno.split(",");
			final List<String> ids = new ArrayList<String>();
			for (int i = 0; i < car.length; i++) {
				ids.add(car[i]);
				
			}
			entityMap.put("dis_a_no", ids);
			
		List<AssDisposalApplyDetailSpecial> 	assDisposalAdetailSpecial=(List<AssDisposalApplyDetailSpecial>) assDisposalApplyDetailSpecialMapper.query(entityMap);
			entityMap.put("bill_table", "ASS_DISPOSAL_R_SPECIAL");
			String dis_r_no = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("dis_r_no", dis_r_no);
			entityMap.put("state", 0);
			entityMap.put("note", "引入处置申请");
			assDisposalRecordSpecialMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			
			for (String temp : carno.toString().split(",")) {// 循环生成单据的明细ID
				Map<String, Object> planApplyMapvo = new HashMap<String, Object>();
				planApplyMapvo.put("group_id", entityMap.get("group_id"));
				planApplyMapvo.put("hos_id", entityMap.get("hos_id"));
				planApplyMapvo.put("copy_code", entityMap.get("copy_code"));
				planApplyMapvo.put("dis_r_no", dis_r_no);
				planApplyMapvo.put("ais_r_no", temp);

				assDisposalRecordSpecialMapper.addAssPlanDeptImportBid(planApplyMapvo);
			}
			
			Map<String, Object> maps = new HashMap<String, Object>();
			
			if(null!=assDisposalAdetailSpecial&&0!=assDisposalAdetailSpecial.size()){
				maps.put("group_id", assDisposalAdetailSpecial.get(0).getGroup_id());
				maps.put("hos_id", assDisposalAdetailSpecial.get(0).getHos_id());
				maps.put("copy_code", assDisposalAdetailSpecial.get(0).getCopy_code());
				maps.put("dis_r_no", dis_r_no);
				maps.put("dis_a_no", assDisposalAdetailSpecial.get(0).getDis_a_no());
				maps.put("dispose_type", entityMap.get("dispose_type"));
			}
			
			for (AssDisposalApplyDetailSpecial assDisposalApplyDetailSpecial : assDisposalAdetailSpecial) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("group_id", assDisposalApplyDetailSpecial.getGroup_id());
				
				map.put("hos_id", assDisposalApplyDetailSpecial.getHos_id());
				
				map.put("copy_code", assDisposalApplyDetailSpecial.getCopy_code());
				
				map.put("dis_r_no", dis_r_no);
				
				map.put("dis_a_no", assDisposalApplyDetailSpecial.getDis_a_no());
				
				map.put("dispose_type", entityMap.get("dispose_type"));
				
				map.put("ass_card_no", assDisposalApplyDetailSpecial.getAss_card_no());
				
				map.put("price", assDisposalApplyDetailSpecial.getPrice());

				map.put("now_depre", assDisposalApplyDetailSpecial.getNow_depre());

				map.put("now_manage_depre", assDisposalApplyDetailSpecial.getNow_manage_depre());

				map.put("add_depre",assDisposalApplyDetailSpecial.getAdd_depre());

				map.put("add_manage_depre", assDisposalApplyDetailSpecial.getAdd_manage_depre());

				map.put("cur_money",assDisposalApplyDetailSpecial.getCur_money());

				map.put("fore_money", assDisposalApplyDetailSpecial.getFore_money());
				
				map.put("dispose_cost", "0");
				
				map.put("dispose_income", "0");
				
				map.put("dispose_tax", "0");
				
				map.put("note", "");
				
				String create_date = entityMap.get("create_date").toString();

				map.put("ass_year", create_date.substring(0, create_date.indexOf("-")));

				map.put("ass_month",
						create_date.substring(create_date.indexOf("-") + 1, create_date.lastIndexOf("-")));

				map.put("ass_naturs", "02");

				String results = collectAssDisposalRecordSpecial(map);

				JSONObject jsonObj = JSONObject.parseObject(results);

				if (jsonObj.containsKey("msg")) {
					transactionManager.rollback(status);
					return jsonObj.toJSONString();
				}
				
				
				
				JSONArray sourceArray = JSONArray.parseArray(jsonObj.get("Source").toString());

				List<AssResourceSpecial> resourceList = assResourceSpecialMapper.queryByAssCardNo(map);
				for (AssResourceSpecial resSource : resourceList) {
					Map<String, Object> mapSource = new HashMap<String, Object>();
					mapSource.put("group_id", resSource.getGroup_id());
					mapSource.put("hos_id", resSource.getHos_id());
					mapSource.put("copy_code", resSource.getCopy_code());
					mapSource.put("dis_r_no", entityMap.get("dis_r_no"));
					mapSource.put("ass_card_no", resSource.getAss_card_no());
					mapSource.put("source_id", resSource.getSource_id());
					mapSource.put("price", resSource.getPrice());
					for (int i = 0; i < sourceArray.size(); i++) {
						JSONObject job = sourceArray.getJSONObject(i);
						if (resSource.getSource_id() == Long.parseLong(job.get("ID").toString())) {
							mapSource.put("now_depre", job.get("NowDepre").toString());
							mapSource.put("add_depre", job.get("AddDepre").toString());
							mapSource.put("cur_money", job.get("Cur").toString());
							mapSource.put("fore_money", job.get("Fore").toString());
							mapSource.put("now_manage_depre", job.get("NowManageDepre").toString());
							mapSource.put("add_manage_depre", job.get("AddManageDepre").toString());
							mapSource.put("add_depre_month", job.get("AddAccMonth").toString());
							
						}
					}

					if (map.get("note") != null && !map.get("note").equals("")) {
						mapSource.put("note", map.get("note"));
					} else {
						mapSource.put("note", "");
					}
					listSourceVo.add(mapSource);
				}
				listVo.add(map);
			}
			
			
			assDisposalRecordSourceSpecialMapper.delete(entityMap);
			assDisposalRecordDetailSpecialMapper.delete(entityMap);
			assDisposalRecordDetailSpecialMapper.addBatch(listVo);
			assDisposalArMapSpecialMapper.add(maps);
			if (listSourceVo.size() > 0) {
				assDisposalRecordSourceSpecialMapper.addBatch(listSourceVo);
			}
			//entityMap.put("back_money", back_money);
			//assBackSpecialMapper.updateBackMoney(entityMap);
			transactionManager.commit(status);
			
			return "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + entityMap.get("group_id") + "|"
			+ entityMap.get("hos_id") + "|" + entityMap.get("copy_code") + "|" + dis_r_no + "|"+ carno+ "|1\"}";
		} catch (Exception e) {

			transactionManager.rollback(status);
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		
		}
		
		
	}
   /**
    * 删除关系表
    */
	@Override
	public String deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo) throws DataAccessException {
		try {
			
			assDisposalRecordSpecialMapper.deleteBatchAssApplyPlanMap(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	
		
	}

	@Override
	public String queryDetails(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
	
		sysPage = (SysPage) entityMap.get("sysPage");
	
		if (sysPage.getTotal() == -1) {
	
			List<Map<String, Object>> list = (List<Map<String, Object>>) assDisposalRecordSpecialMapper.queryDetails(entityMap);
	
			return ChdJson.toJson(list);
	
		} else {
	
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	
			List<Map<String, Object>> list = (List<Map<String, Object>>) assDisposalRecordSpecialMapper.queryDetails(entityMap, rowBounds);
	
			PageInfo page = new PageInfo(list);
	
			return ChdJson.toJson(list, page.getTotal());
	
		}
	}

	@Override
	public String updateUnConfirmDisposalRecordSpecial(List<Map<String, Object>> listVo,
			List<Map<String, Object>> listCardVo) throws DataAccessException {
		try {
			assDisposalRecordSpecialMapper.updateBatchUnConfirm(listVo);
			assCardSpecialMapper.updateDisposalRecordUnConfirm(listCardVo);
			return "{\"msg\":\"取消确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
}
