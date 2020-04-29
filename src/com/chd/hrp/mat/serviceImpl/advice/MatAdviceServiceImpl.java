package com.chd.hrp.mat.serviceImpl.advice;

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

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.mat.dao.advice.MatAdviceMapper;
import com.chd.hrp.mat.dao.advice.MatAdviceRefAffiOutMapper;
import com.chd.hrp.mat.dao.advice.MatAdviceRefOutMapper;
import com.chd.hrp.mat.dao.advice.MatAdviceRefSpeOutMapper;
import com.chd.hrp.mat.dao.affi.out.MatAffiOutCommonMapper;
import com.chd.hrp.mat.dao.base.MatAffiBalanceMapper;
import com.chd.hrp.mat.dao.base.MatAffiBatchMapper;
import com.chd.hrp.mat.dao.base.MatAffiFifoMapper;
import com.chd.hrp.mat.dao.base.MatAffiInvHoldMapper;
import com.chd.hrp.mat.dao.base.MatAffiOutMapper;
import com.chd.hrp.mat.dao.base.MatBatchBalanceMapper;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatFifoBalanceMapper;
import com.chd.hrp.mat.dao.base.MatInCommonMapper;
import com.chd.hrp.mat.dao.base.MatInvBalanceMapper;
import com.chd.hrp.mat.dao.base.MatInvHoldMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.base.MatNoOtherMapper;
import com.chd.hrp.mat.dao.base.MatOutResourceMapper;
import com.chd.hrp.mat.dao.initial.MatInitChargeMapper;
import com.chd.hrp.mat.dao.storage.out.MatOutDetailMapper;
import com.chd.hrp.mat.dao.storage.out.MatOutMainMapper;
import com.chd.hrp.mat.dao.storage.special.MatSpecialMapper;
import com.chd.hrp.mat.entity.MatAdvice;
import com.chd.hrp.mat.entity.MatBatchBalance;
import com.chd.hrp.mat.entity.MatFifoBalance;
import com.chd.hrp.mat.entity.MatInvBalance;
import com.chd.hrp.mat.entity.MatInvHold;
import com.chd.hrp.mat.entity.MatOutDetail;
import com.chd.hrp.mat.service.advice.MatAdviceService;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.github.pagehelper.PageInfo;

@Service("matAdviceService")
public class MatAdviceServiceImpl implements MatAdviceService {
	private static Logger logger = Logger.getLogger(MatAdviceServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "matAdviceMapper")
	private final MatAdviceMapper matAdviceMapper = null;

	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;

	@Resource(name = "matAffiOutMapper")
	private final MatAffiOutMapper matAffiOutMapper = null;
	
	@Resource(name = "matOutMainMapper")
	private final MatOutMainMapper matOutMainMapper = null;
	
	@Resource(name = "matOutDetailMapper")
	private final MatOutDetailMapper matOutDetailMapper = null;
	
	@Resource(name = "matOutResourceMapper")
	private final MatOutResourceMapper matOutResourceMapper = null;

	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;

	@Resource(name = "matAdviceRefOutMapper")
	private final MatAdviceRefOutMapper matAdviceRefOutMapper = null;

	@Resource(name = "matAdviceRefAffiOutMapper")
	private final MatAdviceRefAffiOutMapper matAdviceRefAffiOutMapper = null;
	
	@Resource(name = "matAdviceRefSpeOutMapper")
	private final MatAdviceRefSpeOutMapper matAdviceRefSpeOutMapper = null;

	@Resource(name = "matAffiOutCommonMapper")
	private final MatAffiOutCommonMapper matAffiOutCommonMapper = null;

	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;

	@Resource(name = "matInitChargeMapper")
	private final MatInitChargeMapper matInitChargeMapper = null;

	@Resource(name = "matAffiFifoMapper")
	private final MatAffiFifoMapper matAffiFifoMapper = null;

	@Resource(name = "matAffiInvHoldMapper")
	private final MatAffiInvHoldMapper matAffiInvHoldMapper = null;

	@Resource(name = "matAffiBatchMapper")
	private final MatAffiBatchMapper matAffiBatchMapper = null;

	@Resource(name = "matAffiBalanceMapper")
	private final MatAffiBalanceMapper matAffiBalanceMapper = null;
	
	@Resource(name = "matSpecialMapper")
	private final MatSpecialMapper matSpecialMapper = null;
	
	@Resource(name = "matInCommonMapper")
	private final MatInCommonMapper matInCommonMapper = null;
	
	@Resource(name = "matNoOtherMapper")
	private final MatNoOtherMapper matNoOtherMapper = null;
	
	@Resource(name = "matBatchBalanceMapper")
	private final MatBatchBalanceMapper matBatchBalanceMapper = null ;
	@Resource(name = "matInvHoldMapper")
	private final MatInvHoldMapper matInvHoldMapper = null ;
	
	@Resource(name = "matFifoBalanceMapper")
	private final MatFifoBalanceMapper matFifoBalanceMapper = null ;
	
	@Resource(name = "matInvBalanceMapper")
	private final MatInvBalanceMapper matInvBalanceMapper = null ;

	@Override
	public String addMatAdvice(Map<String, Object> entityMap) throws DataAccessException {
		MatAdvice matAdvice = queryMatAdviceByCode(entityMap);

		if (matAdvice != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = matAdviceMapper.addMatAdvice(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatAdvice\"}";

		}
	}

	@Override
	public String addBatchMatAdvice(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			matAdviceMapper.addBatchMatAdvice(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatAdviceRefOut\"}";

		}
	}

	@Override
	public String updateMatAdvice(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = matAdviceMapper.updateMatAdvice(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMatAdvice\"}";

		}
	}

	@Override
	public String updateBatchMatAdvice(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			matAdviceMapper.updateBatchMatAdvice(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatAdvice\"}";

		}
	}

	@Override
	public String deleteMatAdvice(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = matAdviceMapper.deleteMatAdvice(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatAdvice\"}";

		}
	}

	@Override
	public String deleteBatchMatAdvice(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			matAdviceMapper.deleteBatchMatAdvice(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatAdvice\"}";

		}
	}

	@Override
	public String queryMatAdvice(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<MatAdvice> list = matAdviceMapper.queryMatAdvice(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<MatAdvice> list = matAdviceMapper.queryMatAdvice(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public MatAdvice queryMatAdviceByCode(Map<String, Object> entityMap) throws DataAccessException {
		return matAdviceMapper.queryMatAdviceByCode(entityMap);
	}

	@Override
	public MatAdvice queryMatAdviceByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return matAdviceMapper.queryMatAdviceByUniqueness(entityMap);
	}

	@Override
	public String initMatAdvice(Map<String, Object> entityMap) throws DataAccessException {
		if(entityMap.get("busi_code").toString().equals("01")){
			return initMatAffiOutAdvice(entityMap);
		}else if(entityMap.get("busi_code").toString().equals("02")){
			return initMatOutAdvice(entityMap);
		}else{
			return initSpecialAdvice(entityMap);
		}
	}

	// 材料出库
	/**
	 * @param entityMap
	 * @return
	 */
	public String initMatOutAdvice(Map<String, Object> entityMap) {
		List<Map<String, Object>> adviceRefOutList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> hxFlagList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> confirmVoList = new ArrayList<Map<String, Object>>();
		int is_order_dept = Integer.valueOf(MyConfig.getSysPara("04029").toString());
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
		try {
			String receive_ids = "";
			
			for (Map is_flag : detail) {
				if (is_flag.get("hx_flag") != null && !is_flag.get("hx_flag").toString().equals("")) {
					if (Integer.parseInt(is_flag.get("hx_flag").toString()) != 1) {
						receive_ids = receive_ids + is_flag.get("receive_id").toString() + ",";
					} else {
						receive_ids = "0,";
					}
				} else {
					receive_ids = receive_ids + is_flag.get("receive_id").toString() + ",";
				}
			}
	
			receive_ids = receive_ids.substring(0, receive_ids.lastIndexOf(","));
	
			entityMap.put("receive_ids", receive_ids);
			entityMap.put("is_order_dept", is_order_dept);
			
			List<MatAdvice> matAdviceList = matAdviceMapper.queryMatAdviceByInit(entityMap);// 主表
			List<MatAdvice> matAdviceDetailList = matAdviceMapper.queryMatAdviceByInitDetail(entityMap);// 明细表
			
			if (matAdviceList.size() > 0) {
				for (MatAdvice matAdvice : matAdviceList) {
					List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
					Map<String, Object> confirmVo = new HashMap<String, Object>();
					Map<String, Object> mainVo = new HashMap<String, Object>();
					mainVo.put("group_id", SessionManager.getGroupId());
					mainVo.put("hos_id", SessionManager.getHosId());
					mainVo.put("copy_code", SessionManager.getCopyCode());
					String[] date = DateUtil.dateToString(matAdvice.getCharge_date()).split("-");

					mainVo.put("year", date[0]);
					mainVo.put("month", date[1]);
					mainVo.put("day", date[2]);  //用于生成单号

					mainVo.put("maker", SessionManager.getUserId());

					mainVo.put("checker", SessionManager.getUserId());
					mainVo.put("check_date", new Date());

					mainVo.put("dept_id", matAdvice.getDept_id());
					mainVo.put("dept_no", matAdvice.getDept_no());
					
					mainVo.put("bus_type_code_in", "3");
					
					mainVo.put("store_id", matAdvice.getStore_id());
					mainVo.put("store_no", matAdvice.getStore_no());
					
					mainVo = defaultValue(mainVo);
					
					int flag = 1;
					// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
					flag = matCommonMapper.existsAccYearMonthCheck(mainVo);
					if(flag == 0){
						return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
					}
					
					//(2)、判断当前编制日期所在期间是否结账，若已结账提示：当月已结账不能保存！			
					flag = matCommonMapper.existsMatStoreIsAccount(mainVo);
					if(flag != 0){
						return "{\"error\":\"添加失败，当月已结账不能保存！\",\"state\":\"false\"}";
					}
					
					// 查询序列
					Long out_id = matOutMainMapper.queryMatOutMainSeq();
					mainVo.put("out_id", out_id);
					// 获取出库单号
					mainVo.put("table_code", "MAT_OUT_MAIN");
					String out_no = matCommonService.getMatNextNo(mainVo);
					if(out_no.indexOf("error") > 0){
						return out_no;
					}
					mainVo.put("out_no", out_no);
					
					for(MatAdvice matAdviceDetail : matAdviceDetailList){
						Map<String, Object> detailVo = defaultDetailValue();
						Map<String, Object> refVo = new HashMap<String, Object>();
						detailVo.put("group_id", mainVo.get("group_id"));
						detailVo.put("hos_id", mainVo.get("hos_id"));
						detailVo.put("copy_code", mainVo.get("copy_code"));
						detailVo.put("out_id", mainVo.get("out_id"));
						detailVo.put("out_no", mainVo.get("out_no"));
						detailVo.put("inv_id", matAdviceDetail.getInv_id());
						detailVo.put("inv_no", matAdviceDetail.getInv_no());
						detailVo.put("batch_no", matAdviceDetail.getBatch_no());
						detailVo.put("price", matAdviceDetail.getCharge_price());
						detailVo.put("amount", matAdviceDetail.getOut_num());
						detailVo.put("amount_money", matAdviceDetail.getOut_num() * matAdviceDetail.getCharge_price());
						detailVo.put("bar_code", matAdviceDetail.getBar_code());
						detailVo.put("out_detail_id", matAdviceMapper.queryMatOutDetailNextval(detailVo));
						
						detailList.add(detailVo);
						refVo.put("group_id", detailVo.get("group_id"));
						refVo.put("hos_id", detailVo.get("hos_id"));
						refVo.put("copy_code", detailVo.get("copy_code"));
						refVo.put("receive_id", matAdviceDetail.getReceive_id());
						refVo.put("out_id", detailVo.get("out_id"));
						refVo.put("out_detail_id", detailVo.get("out_detail_id"));
						adviceRefOutList.add(refVo);
					}
					
					//判断即时库存是否充足(防止多人同时操作同一材料，额外加的判断)
					String invEnough = matCommonMapper.existsMatStockInvIsEnough(detailList);
					if(invEnough != null && !"".equals(invEnough)){
						return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
					}
					
					
					Map<String, Object> mapVo = new HashMap<String, Object>();
					mapVo.put("group_id", mainVo.get("group_id"));
					mapVo.put("hos_id", mainVo.get("hos_id"));
					mapVo.put("copy_code", mainVo.get("copy_code"));
					mapVo.put("year", mainVo.get("year"));
					mapVo.put("month", mainVo.get("month"));
					mapVo.put("out_id", mainVo.get("out_id"));
					mapVo.put("state", 3);
					mapVo.put("confirmer", SessionManager.getUserId());
					mapVo.put("confirm_date", new Date());
					confirmVoList.add(mapVo);
					
					matOutMainMapper.add(mainVo);// 保存主表

					matAdviceMapper.addMatOutBatch(detailList);// 保存明细表
					
					mainVo.put("source_money", mainVo);
					
					matOutResourceMapper.add(mainVo);// 保存资金来源
				}
			}else{
				return "{\"error\":\"生成失败 没有要生成的单据\"}";
			}
			
			confirmOutMatOutMain(confirmVoList);//出库确认
			int result = matAdviceRefOutMapper.addBatchMatAdviceRefOut(adviceRefOutList);// 医嘱核销和出库单对应关系表
			if (result > 0) {
				for (Map temp : detail) {
					Map<String, Object> hxFlagMap = new HashMap<String, Object>();
					hxFlagMap.put("message", "核销成功");
					hxFlagMap.put("hx_flag", 1);
					hxFlagMap.put("group_id", temp.get("group_id"));
					hxFlagMap.put("hos_id", temp.get("hos_id"));
					hxFlagMap.put("copy_code", temp.get("copy_code"));
					hxFlagMap.put("receive_id", temp.get("receive_id"));
					hxFlagList.add(hxFlagMap);
				}
				matAdviceMapper.updateBatchMatAdviceByHxFlag(hxFlagList);
			}
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			for (Map temp : detail) {
				Map<String, Object> hxFlagMap = new HashMap<String, Object>();
				hxFlagMap.put("message", "系统错误，核销失败");
				hxFlagMap.put("hx_flag", 0);
				hxFlagMap.put("group_id", temp.get("group_id"));
				hxFlagMap.put("hos_id", temp.get("hos_id"));
				hxFlagMap.put("copy_code", temp.get("copy_code"));
				hxFlagMap.put("receive_id", temp.get("receive_id"));
				hxFlagList.add(hxFlagMap);
			}
			matAdviceMapper.updateBatchMatAdviceByHxFlag(hxFlagList);
			e.printStackTrace();
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 initMatAdvice\"}";
		}
	}
	
	// 专购品
	public String initSpecialAdvice(Map<String, Object> entityMap) {
		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> adviceRefOutList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> hxFlagList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> confirmVoList = new ArrayList<Map<String, Object>>();
		int is_order_dept = Integer.valueOf(MyConfig.getSysPara("04029").toString());
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
		try {
			
			String receive_ids = "";
	
			for (Map is_flag : detail) {
				if (is_flag.get("hx_flag") != null && !is_flag.get("hx_flag").toString().equals("")) {
					if (Integer.parseInt(is_flag.get("hx_flag").toString()) != 1) {
						receive_ids = receive_ids + is_flag.get("receive_id").toString() + ",";
					} else {
						receive_ids = "0,";
					}
				} else {
					receive_ids = receive_ids + is_flag.get("receive_id").toString() + ",";
				}
			}
	
			receive_ids = receive_ids.substring(0, receive_ids.lastIndexOf(","));
	
			entityMap.put("receive_ids", receive_ids);
			entityMap.put("is_order_dept", is_order_dept);
			
			List<MatAdvice> matAdviceList = matAdviceMapper.queryMatAdviceByInit(entityMap);// 主表
			List<MatAdvice> matAdviceDetailList = matAdviceMapper.queryMatAdviceByInitDetail(entityMap);// 明细表
			if (matAdviceList.size() > 0) {
				for (MatAdvice matAdvice : matAdviceList) {
					Map<String, Object> mainVo = new HashMap<String, Object>();
					mainVo.put("group_id", SessionManager.getGroupId());
					mainVo.put("hos_id", SessionManager.getHosId());
					mainVo.put("copy_code", SessionManager.getCopyCode());
					String[] date = DateUtil.dateToString(matAdvice.getCharge_date()).split("-");
	
					mainVo.put("year", date[0]);
					mainVo.put("month", date[1]);
					mainVo.put("day", date[2]);  //用于生成单号
	
					mainVo.put("maker", SessionManager.getUserId());
					//入库 编制日期 
					mainVo.put("in_date",matAdvice.getCharge_date());
					//即入即出  出库编制日期 
					mainVo.put("out_date", matAdvice.getCharge_date());
					//专购品主表 编制日期
					mainVo.put("make_date",matAdvice.getCharge_date());
					
					/* 专购品主表 、专购品 入库主表、专购品 出库库主表   业务类型 bus_type_code 配置*/
					//专购品主表  业务类型 bus_type_code（01：正常  02：冲账）
					mainVo.put("bus_type_code_sepcial", "01");
					
					//专购品 入库主表  业务类型 bus_type_code（47  专购品入库）
					mainVo.put("bus_type_code_in", "47");
					
					//专购品 出库库主表  业务类型 bus_type_code（49  专购品出库）
					mainVo.put("bus_type_code_out", "49");
					
					int flag = matCommonMapper.existsAccYearMonthCheck(mainVo);
					if(flag == 0){
						return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
					}
					
					mainVo.put("special_no", "自动生成");
					
					//自动生成 专购品单据号
					Map<String,Object> specialMap = new HashMap<String,Object>();
					specialMap.put("group_id", mainVo.get("group_id"));
					specialMap.put("hos_id", mainVo.get("hos_id"));
					specialMap.put("copy_code", mainVo.get("copy_code"));
					specialMap.put("year", mainVo.get("year"));
					specialMap.put("month", mainVo.get("month"));
					specialMap.put("day", mainVo.get("day"));  //用于生成单号
					specialMap.put("store_id", matAdvice.getStore_id());
					specialMap.put("table_code", "MAT_SPECIAL_MAIN");
					specialMap.put("bus_type_code", "47");
					specialMap.put("prefixe", "");
					if("自动生成".equals(mainVo.get("special_no").toString())){
						mainVo.put("special_no", matCommonService.getMatNextNo(specialMap));
					}
					//专购品单据ID
					mainVo.put("special_id", matSpecialMapper.queryMatSpecialNextval());
					
					//自动生成 专购品入库单号
					Map<String,Object> inMap = new HashMap<String,Object>();
					inMap.put("group_id", mainVo.get("group_id"));
					inMap.put("hos_id", mainVo.get("hos_id"));
					inMap.put("copy_code", mainVo.get("copy_code"));
					inMap.put("year", mainVo.get("year"));
					inMap.put("month", mainVo.get("month"));
					inMap.put("day", mainVo.get("day"));  //用于生成单号
					inMap.put("store_id", matAdvice.getStore_id());
					inMap.put("table_code", "MAT_IN_MAIN");
					inMap.put("bus_type_code", "47");
					inMap.put("prefixe", "");
					mainVo.put("in_no", matCommonService.getMatNextNo(inMap));
					
					//取出主表ID（自增序列）
					mainVo.put("in_id", matInCommonMapper.queryMatInMainSeq());
					//专购品入库主表  申请科室 对应  专购品主表领料科室
					mainVo.put("app_dept", matAdvice.getDept_id());
					//要添加专购品出库主表的Id
					mainVo.put("out_id", matOutMainMapper.queryMatOutMainSeq());
					
					//自动生成 专购品出库单号 
					Map<String,Object> outMap = new HashMap<String,Object>();
					outMap.put("group_id", mainVo.get("group_id"));
					outMap.put("hos_id", mainVo.get("hos_id"));
					outMap.put("copy_code", mainVo.get("copy_code"));
					outMap.put("year", mainVo.get("year"));
					outMap.put("month", mainVo.get("month"));
					outMap.put("day", mainVo.get("day"));  //用于生成单号
					outMap.put("store_id", matAdvice.getStore_id());
					outMap.put("table_code", "MAT_OUT_MAIN");
					outMap.put("bus_type_code", "49");
					outMap.put("prefixe", "");
					mainVo.put("out_no", matCommonService.getMatNextNo(outMap));
					
					//状态   0:验收; 1:未审核；2审核；3入库确认；4财务记帐。
					mainVo.put("state", 1);
					
					
					Map<String,Object> barCodeMap = new HashMap<String,Object>();
					barCodeMap.put("group_id", mainVo.get("group_id"));
					barCodeMap.put("hos_id", mainVo.get("hos_id"));
					barCodeMap.put("type_code", 1);
					String bar_code = matNoOtherMapper.queryMatNoOther(barCodeMap);//获取当前个体码
					//如果不存在则插入
					if(bar_code == null || "".equals(bar_code)){
						bar_code = "000000000000";
						barCodeMap.put("max_no", bar_code);
						matNoOtherMapper.insertMatNoOther(barCodeMap);
					}
					String init_bar_code = bar_code;
					
					///*用于查询批次----begin-----*/
					Map<String,Object> batchSnMap = new HashMap<String,Object>();
					batchSnMap.put("group_id", mainVo.get("group_id"));
					batchSnMap.put("hos_id", mainVo.get("hos_id"));
					batchSnMap.put("copy_code", mainVo.get("copy_code"));
					batchSnMap.put("c_max", "batch_sn");
					batchSnMap.put("table_name", "mat_in_detail");
					batchSnMap.put("c_name", "inv_id");//查询批次所用
					batchSnMap.put("c_name1", "batch_no");//查询批次所用
					
					
					Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
					String invBatchKey = "";
					float money_sum = 0;//记录明细总金额
					for (MatAdvice matAdviceDetail : matAdviceDetailList) {
						Map<String,Object> detailMap = new HashMap<String,Object>();
						Map<String, Object> refVo = new HashMap<String, Object>();
						detailMap.put("group_id", mainVo.get("group_id"));
						detailMap.put("hos_id", mainVo.get("hos_id"));
						detailMap.put("copy_code", mainVo.get("copy_code"));
						detailMap.put("special_id", mainVo.get("special_id"));//专购品主表 单据ID
						detailMap.put("special_no", mainVo.get("special_no"));//专购品主表  单据号
						detailMap.put("detail_id", matSpecialMapper.queryMatSpecialDetailSeq());//专购品明细表id
						detailMap.put("in_id", mainVo.get("in_id"));//专购品入库主表ID
						detailMap.put("in_no", mainVo.get("in_no"));//专购品入库主表  入库单号
						detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//专购品入库明细表id
						detailMap.put("out_id", mainVo.get("out_id"));//专购品出库主表ID
						detailMap.put("out_no", mainVo.get("out_no"));//专购品出库主表 出库单号
						detailMap.put("out_detail_id", matSpecialMapper.queryMatOutDetailSeq());//专购品入库明细表id
						detailMap.put("inv_id", matAdviceDetail.getInv_id());
						detailMap.put("inv_no", matAdviceDetail.getInv_no());
						detailMap.put("batch_no", matAdviceDetail.getBatch_no());
						detailMap.put("price", matAdviceDetail.getCharge_price());
						detailMap.put("amount", matAdviceDetail.getOut_num());
						detailMap.put("amount_money", matAdviceDetail.getOut_num() * matAdviceDetail.getCharge_price());
						detailMap.put("bar_code", matAdviceDetail.getBar_code());
						money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
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
						
						detailMap.put("sell_price",  "0");//零售价
						detailMap.put("sell_money",  "0");//零售金额
						detailMap.put("allot_price",  "0");//调拨价
						detailMap.put("allot_money",  "0");//调拨金额
						detailMap.put("pack_price",  "0");//包装单价
						detailMap.put("sn", "-");
						detailMap.put("is_per_bar", "0");//是否个体码(默认否)
						
						if("0".equals(detailMap.get("is_per_bar").toString())){
							//System.out.println(jsonObj.get("inv_name").toString()+"不生成个体码");
							if(!ChdJson.validateJSON(matAdviceDetail.getBar_code())){
								detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
							}else{
								detailMap.put("bar_code",  matAdviceDetail.getBar_code());//个体码
							}
							//该条明细数据添加到List中
							detailList.add(detailMap);
						}
						
						refVo.put("group_id", detailMap.get("group_id"));
						refVo.put("hos_id", detailMap.get("hos_id"));
						refVo.put("copy_code", detailMap.get("copy_code"));
						refVo.put("receive_id", matAdviceDetail.getReceive_id());
						refVo.put("out_id", detailMap.get("out_id"));
						refVo.put("out_detail_id", detailMap.get("detail_id"));
						adviceRefOutList.add(refVo);
						
					}
					
					//更新个体码
					if(!init_bar_code.equals(bar_code)){
						barCodeMap.put("max_no", bar_code);
						matNoOtherMapper.updateMatNoOther(barCodeMap);
					}
					
					//新增专购品主表数据
					matSpecialMapper.addSpecialMain(mainVo);
					
					//新增专购品入库主表数据
					matSpecialMapper.addInMain(mainVo);
					
					//新增专购品出库主表数据
					matSpecialMapper.addOutMain(mainVo);
					
					//批量新增专购品明细数据
					matSpecialMapper.addMatSpecialDetailBatch(detailList);
					
					//批量新增专购品入库明细数据
					matInCommonMapper.addMatInDetailBatch(detailList);
					
					//批量新增专购品出库明细数据
					matSpecialMapper.addMatOutDetailBatch(detailList);
					
					//维护专购品  与出入库对应关系
					Map<String,Object> relaMap = new HashMap<String,Object>();
					relaMap.put("group_id", mainVo.get("group_id"));
					relaMap.put("hos_id", mainVo.get("hos_id"));
					relaMap.put("copy_code", mainVo.get("copy_code"));
					relaMap.put("special_id", mainVo.get("special_id"));
					relaMap.put("special_no", mainVo.get("special_no"));
					relaMap.put("out_id", mainVo.get("out_id"));
					relaMap.put("out_no", mainVo.get("out_no"));
					relaMap.put("in_id", mainVo.get("in_id"));
					relaMap.put("in_no", mainVo.get("in_no"));
					matSpecialMapper.addMatSpecialRela(relaMap);
					
					//新增入库资金来源表
					if(mainVo.get("money_sum") == null){
						mainVo.put("source_money", money_sum);
					}
					matInCommonMapper.insertMatInResource(mainVo);
					
					//新增出库资金来源表
					matOutResourceMapper.add(mainVo);
					
					
					
					Map<String, Object> mapVo=new HashMap<String, Object>();
					mapVo.put("group_id", mainVo.get("group_id"));
					mapVo.put("hos_id", mainVo.get("hos_id"));
					mapVo.put("copy_code", mainVo.get("copy_code"));
					mapVo.put("out_id", mainVo.get("out_id"));
					mapVo.put("year", mainVo.get("year"));
					mapVo.put("month", mainVo.get("month"));
					mapVo.put("store_id", mainVo.get("store_id"));
					
					
					Map<String,Object> data = matSpecialMapper.queryMatInOutData(mapVo);
					mapVo.put("in_id", data.get("in_id"));
					mapVo.put("in_no", data.get("in_no"));
					mapVo.put("out_id", data.get("out_id"));
					mapVo.put("out_no", data.get("out_no"));
					
					//根据 专购品主表Id 查询 专购品明细
					List<Map<String,Object>> detailLists = matSpecialMapper.querySpecialDetail(mapVo);
					for(Map<String,Object> item : detailLists){
						mapVo.put("inv_id", item.get("inv_id"));
						mapVo.put("batch_sn", item.get("batch_sn"));
						mapVo.put("batch_no", item.get("batch_no"));
						mapVo.put("bar_code", item.get("bar_code"));
						mapVo.put("price", item.get("price"));
						mapVo.put("sale_price", item.get("sale_price"));
						mapVo.put("in_amount", item.get("amount"));
						mapVo.put("in_money", item.get("amount_money"));
						mapVo.put("in_sale_money", "0");
						mapVo.put("out_amount", item.get("amount"));
						mapVo.put("out_money", item.get("amount_money"));
						mapVo.put("out_sale_money", "0");
						mapVo.put("inva_date", item.get("inva_date"));
						mapVo.put("disinfect_date", item.get("disinfect_date"));
						mapVo.put("location_id", item.get("location_id"));
						mapVo.put("left_amount", "0");
						mapVo.put("left_money", "0");
						mapVo.put("left_sale_money", "0");
						mapVo.put("remove_zero_error", "0");
						mapVo.put("sale_zero_error", "0");
						mapVo.put("cur_amount", "0");
						mapVo.put("cur_money", "0");
					}
					mapVo.put("state", 3);
					mapVo.put("confirmer", SessionManager.getUserId());
					mapVo.put("confirm_date", date);
					
					confirmVoList.add(mapVo);
				}
				
			}else{
				return "{\"error\":\"生成失败 没有要生成的单据\"}";
			}
			confirmMatSpecialBatch(confirmVoList);
			int result = matAdviceRefSpeOutMapper.addBatchMatAdviceRefSpeOut(adviceRefOutList);// 医嘱核销和出库单对应关系表
			if (result > 0) {
				for (Map temp : detail) {
					Map<String, Object> hxFlagMap = new HashMap<String, Object>();
					hxFlagMap.put("message", "核销成功");
					hxFlagMap.put("hx_flag", 1);
					hxFlagMap.put("group_id", temp.get("group_id"));
					hxFlagMap.put("hos_id", temp.get("hos_id"));
					hxFlagMap.put("copy_code", temp.get("copy_code"));
					hxFlagMap.put("receive_id", temp.get("receive_id"));
					hxFlagList.add(hxFlagMap);
				}
				matAdviceMapper.updateBatchMatAdviceByHxFlag(hxFlagList);
			}
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			for (Map temp : detail) {
				Map<String, Object> hxFlagMap = new HashMap<String, Object>();
				hxFlagMap.put("message", "系统错误，核销失败");
				hxFlagMap.put("hx_flag", 0);
				hxFlagMap.put("group_id", temp.get("group_id"));
				hxFlagMap.put("hos_id", temp.get("hos_id"));
				hxFlagMap.put("copy_code", temp.get("copy_code"));
				hxFlagMap.put("receive_id", temp.get("receive_id"));
				hxFlagList.add(hxFlagMap);
			}
			matAdviceMapper.updateBatchMatAdviceByHxFlag(hxFlagList);
			e.printStackTrace();
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 initMatAdvice\"}";
		}
		
		
	}

	// 代销出库生成
	public String initMatAffiOutAdvice(Map<String, Object> entityMap) {
		
		List<Map<String, Object>> mainList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> adviceRefOutList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> hxFlagList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> confirmVoList = new ArrayList<Map<String, Object>>();
		int is_order_dept = Integer.valueOf(MyConfig.getSysPara("04029").toString());
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

		String receive_ids = "";

		for (Map is_flag : detail) {
			if (is_flag.get("hx_flag") != null && !is_flag.get("hx_flag").toString().equals("")) {
				if (Integer.parseInt(is_flag.get("hx_flag").toString()) != 1) {
					receive_ids = receive_ids + is_flag.get("receive_id").toString() + ",";
				} else {
					receive_ids = "0,";
				}
			} else {
				receive_ids = receive_ids + is_flag.get("receive_id").toString() + ",";
			}
		}

		receive_ids = receive_ids.substring(0, receive_ids.lastIndexOf(","));

		entityMap.put("receive_ids", receive_ids);
		entityMap.put("is_order_dept", is_order_dept);

		List<MatAdvice> matAdviceList = matAdviceMapper.queryMatAdviceByInit(entityMap);// 主表
		List<MatAdvice> matAdviceDetailList = matAdviceMapper.queryMatAdviceByInitDetail(entityMap);// 明细表

		if (matAdviceList.size() > 0) {
			for (MatAdvice matAdvice : matAdviceList) {
				Map<String, Object> confirmVo = new HashMap<String, Object>();
				Map<String, Object> mainVo = new HashMap<String, Object>();
				mainVo.put("group_id", SessionManager.getGroupId());
				mainVo.put("hos_id", SessionManager.getHosId());
				mainVo.put("copy_code", SessionManager.getCopyCode());
				String[] date = DateUtil.dateToString(matAdvice.getCharge_date()).split("-");

				mainVo.put("year", date[0]);
				mainVo.put("month", date[1]);
				mainVo.put("day", date[2]);  //用于生成单号

				mainVo.put("maker", SessionManager.getUserId());

				mainVo.put("checker", SessionManager.getUserId());
				mainVo.put("check_date", new Date());

				mainVo.put("dept_id", matAdvice.getDept_id());
				mainVo.put("dept_no", matAdvice.getDept_no());

				if (matAdvice.getCharge_date() != null) {
					mainVo.put("out_date", matAdvice.getCharge_date());
				}

				mainVo.put("bus_type_code", "28");
				// detailVo.put("brief", "代销出库生成");
				mainVo.put("maker", SessionManager.getUserId());
				mainVo.put("table_code", "MAT_AFFI_OUT");
				mainVo.put("store_id", matAdvice.getStore_id());
				mainVo.put("store_no", matAdvice.getStore_no());
				mainVo.put("is_dir", "0");
				mainVo.put("state", "2");

				// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
				int flag = matCommonMapper.existsAccYearMonthCheck(mainVo);
				if (flag == 0) {
					return "{\"error\":\"所选期间不存在请配置！\",\"state\":\"false\"}";
				}
				// 判断当前编制日期所在期间是否结账，若已结账提示：当月已结账不能保存！
				flag = matCommonMapper.existsMatStoreIsAccount(mainVo);
				if (flag != 0) {
					return "{\"error\":\"当月已结账不能生成！\",\"state\":\"false\"}";
				}
				
				// 取出主表ID（自增序列）
				mainVo.put("out_id", matAffiOutMapper.queryMatAffiOutMainSeq());
				mainVo.put("out_no", matCommonService.getMatNextNo(mainVo));

				confirmVo.put("group_id", SessionManager.getGroupId());
				confirmVo.put("hos_id", SessionManager.getHosId());
				confirmVo.put("copy_code", SessionManager.getCopyCode());
				confirmVo.put("out_id", mainVo.get("out_id"));
				confirmVo.put("confirmer", SessionManager.getUserId());
				confirmVo.put("state", 3); // 入库确认状态
				confirmVo.put("confirm_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
				confirmVoList.add(confirmVo);
				mainVo = defaultValue(mainVo);
				// 自动生成代销代销出库单据号
				mainList.add(mainVo);
				for (MatAdvice matAdviceDetail : matAdviceDetailList) {
					Map<String, Object> detailVo = new HashMap<String, Object>();
					Map<String, Object> refVo = new HashMap<String, Object>();
					detailVo.put("group_id", mainVo.get("group_id"));
					detailVo.put("hos_id", mainVo.get("hos_id"));
					detailVo.put("copy_code", mainVo.get("copy_code"));
					detailVo.put("out_id", mainVo.get("out_id"));
					detailVo.put("out_no", mainVo.get("out_no"));
					detailVo.put("inv_id", matAdviceDetail.getInv_id());
					detailVo.put("inv_no", matAdviceDetail.getInv_no());
					detailVo.put("batch_no", matAdviceDetail.getBatch_no());
					detailVo.put("price", matAdviceDetail.getCharge_price());
					detailVo.put("amount", matAdviceDetail.getOut_num());
					detailVo.put("amount_money", matAdviceDetail.getOut_num() * matAdviceDetail.getCharge_price());
					detailVo.put("bar_code", matAdviceDetail.getBar_code());
					detailVo.put("detail_id", matAffiOutMapper.queryMatAffiOutDetailNextval(detailVo));
					detailList.add(detailVo);
					refVo.put("group_id", detailVo.get("group_id"));
					refVo.put("hos_id", detailVo.get("hos_id"));
					refVo.put("copy_code", detailVo.get("copy_code"));
					refVo.put("receive_id", matAdviceDetail.getReceive_id());
					refVo.put("out_id", detailVo.get("out_id"));
					refVo.put("out_detail_id", detailVo.get("detail_id"));
					adviceRefOutList.add(refVo);
				}

			}
		} else {
			return "{\"error\":\"生成失败 没有要生成的单据\"}";
		}

		try {
			matAffiOutMapper.addMatAffiOutMainBatch(mainList);// 代销出库主表
			matAffiOutMapper.addMatAffiOutDetailInit(detailList);// 代销出库明细表
			boolean flag = confirmMatAffiOutCommon(confirmVoList);// 代销出库确认
			int result = matAdviceRefAffiOutMapper.addBatchMatAdviceRefAffiOut(adviceRefOutList);// 医嘱核销和出库单对应关系表
			if (result > 0) {
				for (Map temp : detail) {
					Map<String, Object> hxFlagMap = new HashMap<String, Object>();
					hxFlagMap.put("message", "核销成功");
					hxFlagMap.put("hx_flag", 1);
					hxFlagMap.put("group_id", temp.get("group_id"));
					hxFlagMap.put("hos_id", temp.get("hos_id"));
					hxFlagMap.put("copy_code", temp.get("copy_code"));
					hxFlagMap.put("receive_id", temp.get("receive_id"));
					hxFlagList.add(hxFlagMap);
				}
				matAdviceMapper.updateBatchMatAdviceByHxFlag(hxFlagList);
			}
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			for (Map temp : detail) {
				Map<String, Object> hxFlagMap = new HashMap<String, Object>();
				hxFlagMap.put("message", "系统错误，核销失败");
				hxFlagMap.put("hx_flag", 0);
				hxFlagMap.put("group_id", temp.get("group_id"));
				hxFlagMap.put("hos_id", temp.get("hos_id"));
				hxFlagMap.put("copy_code", temp.get("copy_code"));
				hxFlagMap.put("receive_id", temp.get("receive_id"));
				hxFlagList.add(hxFlagMap);
			}
			matAdviceMapper.updateBatchMatAdviceByHxFlag(hxFlagList);
			e.printStackTrace();
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 initMatAdvice\"}";
		}
	}
	
	//材料出库确认
	public synchronized String confirmOutMatOutMain(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			//出库判断 (1)
			String date = DateUtil.dateToString(new Date(), "yyyy/MM/dd");
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			
			List<Map<String, Object>> listUpdateFifoBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listUpdateBatchBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listUpdateInvHold = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listUpdateInvBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listAddBatchBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listAddInvBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			
			StringBuffer invMsg = new StringBuffer();//存放库存不足的材料信息
			boolean is_enough = true;  //库存是否充足
			
			//获取所选单据中的所有材料
			List<MatOutDetail> detailList = matOutDetailMapper.queryMatOutDetailForConfirm(entityMap);
			//查询帐表所需
			List<Map<String, Object>> invList = new ArrayList<Map<String,Object>>();
			
			//存放明细数据用于判断
			Map<String,Map<String,Object>> mapFifoBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapBatchBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapInvHold = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapInvBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			
			//累计相同材料的数量和金额
			Map<String,Double> sum_amount_map = new HashMap<String,Double>();
			Map<String,Double> sum_amount_money = new HashMap<String,Double>();		
			Map<String,Double> sum_sale_money = new HashMap<String,Double>();				
			
			for(MatOutDetail mod : detailList){
				//材料编码和名称用于库存不足的提示
				Map<String,Object> mapDetail = new HashMap<String,Object>();
				Map<String, Object> invMap = new HashMap<String, Object>();
				mapDetail.put("group_id", mod.getGroup_id());
				mapDetail.put("hos_id", mod.getHos_id());
				mapDetail.put("copy_code", mod.getCopy_code());
				mapDetail.put("store_id", mod.getStore_id());
				mapDetail.put("inv_id", mod.getInv_id());
				mapDetail.put("inv_code", mod.getInv_code());
				mapDetail.put("inv_name", mod.getInv_name());
				mapDetail.put("batch_no", mod.getBatch_no());
				mapDetail.put("batch_sn", mod.getBatch_sn());
				mapDetail.put("bar_code", mod.getBar_code());
				mapDetail.put("price", mod.getPrice() == null ? 0 : mod.getPrice());
				mapDetail.put("amount", mod.getAmount() == null ? 0 : mod.getAmount());
				mapDetail.put("amount_money", mod.getAmount_money() == null ? 0 : mod.getAmount_money());
				mapDetail.put("sale_price", mod.getSale_price() == null ? 0 : mod.getSale_price());
				mapDetail.put("sale_money", mod.getSale_money() == null ? 0 : mod.getSale_money());
				//mapDetail.put("location_id", mod.getLocation_id());
				
				invMap.put("group_id", mod.getGroup_id());
				invMap.put("hos_id", mod.getHos_id());
				invMap.put("copy_code", mod.getCopy_code());
				invMap.put("year", year);
				invMap.put("month", month);
				invMap.put("store_id", mod.getStore_id());
				invMap.put("inv_id", mod.getInv_id());
				invMap.put("batch_no", mod.getBatch_no());
				invMap.put("batch_sn", mod.getBatch_sn());
				invMap.put("bar_code", mod.getBar_code());
				invMap.put("location_id", mod.getLocation_id());
				
				invList.add(invMap);
				
				//Map中存放<各帐表主键, 具体材料信息的map>用于判断
				mapFifoBalance.put(mod.getStore_id().toString()+mod.getInv_id()+mod.getBatch_no()+mod.getBatch_sn()+mod.getBar_code(), mapDetail);
				mapInvHold.put(mod.getStore_id().toString()+mod.getInv_id(), mapDetail);
				//下面这两张表是区分年月的
				mapDetail.put("year", year);
				mapDetail.put("month", month);
				mapBatchBalance.put(year+month+mod.getStore_id()+mod.getInv_id()+mod.getBatch_no()+mod.getBatch_sn()+mod.getBar_code(), mapDetail);
				mapInvBalance.put(year+month+mod.getStore_id()+mod.getInv_id(), mapDetail);
				
				//根据材料ID累计数量
				if(sum_amount_map.get(mod.getStore_id()+"-"+mod.getInv_id()) != null){
					sum_amount_map.put(mod.getStore_id()+"-"+mod.getInv_id(), sum_amount_map.get(mod.getStore_id()+"-"+mod.getInv_id())+mod.getAmount());
				}else{
					sum_amount_map.put(mod.getStore_id()+"-"+mod.getInv_id(),mod.getAmount());
				}
				//根据材料ID累计金额
				if(sum_amount_money.get(mod.getStore_id()+"-"+mod.getInv_id()) != null){
					sum_amount_money.put(mod.getStore_id()+"-"+mod.getInv_id(), sum_amount_money.get(mod.getStore_id()+"-"+mod.getInv_id())+mod.getAmount_money());
				}else{
					sum_amount_money.put(mod.getStore_id()+"-"+mod.getInv_id(), mod.getAmount_money());
				}
				//根据材料ID累计金额
				if(sum_sale_money.get(mod.getStore_id()+"-"+mod.getInv_id()) != null){
					sum_sale_money.put(mod.getStore_id()+"-"+mod.getInv_id(), sum_sale_money.get(mod.getStore_id()+"-"+mod.getInv_id())+mod.getSale_money());
				}else{
					sum_sale_money.put(mod.getStore_id()+"-"+mod.getInv_id(), mod.getSale_money());
				}
			}

			//------------------------------查询账表 取出相应数据 并且组织账表数据 MAT_FIFO_BALANCE----------------------------------
			List<MatFifoBalance> listFifoBalance = (List<MatFifoBalance>)matFifoBalanceMapper.queryByInvList(invList);
			
			Map<String,MatFifoBalance> mfb_mb = new HashMap<String,MatFifoBalance>();//所有 根据条件 取出表（MAT_FIFO_BALANCE）的数据都放到map里面
			
			for(MatFifoBalance mfb : listFifoBalance){
				String key  = mfb.getStore_id().toString()+mfb.getInv_id()+mfb.getBatch_no()+mfb.getBatch_sn()+mfb.getBar_code();
				mfb_mb.put(key, mfb);
			}
			
			for (String key : mapFifoBalance.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
				
				Map<String,Object> mapBatch = new HashMap<String,Object>();
				if(mfb_mb.get(key) !=null){
					Map<String,Object> map = mapFifoBalance.get(key);
					mapBatch.putAll(map);
					
					MatFifoBalance mfb = (MatFifoBalance)mfb_mb.get(key);
					
					double amount = (Double)map.get("amount");
					double amount_money = (Double)map.get("amount_money");
					double sale_money = (Double)map.get("sale_money");
					/*判断实际库存是否充足-------------------begin--------------------------*/
					//所选单据材料合计数量 > 帐表材料剩余数量 ? 库存不足 : 库存充足
					if(amount > mfb.getLeft_amount()){
						invMsg.append(map.get("inv_code")).append(" ").append(map.get("inv_name")).append(",");
						if(is_enough){
							is_enough = false;
						}
					}
					/*判断实际库存是否充足-------------------end----------------------------*/
					if(is_enough){
						mapBatch.put("out_amount", amount+mfb.getOut_amount());//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", amount_money+mfb.getOut_money());//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", sale_money+mfb.getOut_sale_money());//出库批发金额 等于出库批发金额 + 当前出库批发金额
						mapBatch.put("left_amount", mfb.getLeft_amount() - amount);//剩余数量 等于剩余的数量 - 当前出库的数量
						mapBatch.put("left_money", mfb.getLeft_money() - amount_money);//剩余金额 等于剩余金额 - 当前出库金额
						mapBatch.put("left_sale_money", mfb.getLeft_sale_money() - sale_money);//剩余数量 等于剩余数量 - 当前出库数量
						//以下字段的计算方式有待讨论
						if(mfb.getLeft_amount() - amount == 0){
							mapBatch.put("remove_zero_error", mfb.getIn_money() - (Double)mapBatch.get("out_money"));//拆零误差 等于入库金额 - 出库金额
							mapBatch.put("sale_zero_error", mfb.getIn_sale_money() - (Double)mapBatch.get("out_sale_money"));//批发拆零误差 等于入库批发金额 - 出库批发金额
						}
						listUpdateFifoBalance.add(mapBatch);
					}
				}else{
					//帐表中没有该材料信息，提示库存不足！
					invMsg.append(mapFifoBalance.get(key).get("inv_code")).append(" ").append(mapFifoBalance.get(key).get("inv_name")).append(",");
					if(is_enough){
						is_enough = false;
					}
				}
			}
			
			//如果材料出现库存物资不足的就不需要组装、判断另外几个帐表了
			if(is_enough){
				//------------------------------查询账表 取出相应数据 并且组织账表数据MAT_BATCH_BALANCE----------------------------------
				List<MatBatchBalance> listBatchBalance = (List<MatBatchBalance>)matBatchBalanceMapper.queryByInvList(invList);

				Map<String,MatBatchBalance> mbb_mb = new HashMap<String,MatBatchBalance>();//所有 根据条件 取出表（MAT_BATCH_BALANCE）的数据都放到map里面
				
				for(MatBatchBalance mbb : listBatchBalance){
					String key = mbb.getYear().toString()+mbb.getMonth()+mbb.getStore_id()+mbb.getInv_id()+mbb.getBatch_no()+mbb.getBatch_sn()+mbb.getBar_code();
					mbb_mb.put(key, mbb);
				}
				
				for (String key : mapBatchBalance.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
					Map<String,Object> mapBatch = new HashMap<String,Object>();

					Map<String,Object> map = mapBatchBalance.get(key);
					mapBatch.putAll(map);
					
					double amount = (Double)map.get("amount");
					double amount_money = (Double)map.get("amount_money");
					double sale_money = (Double)map.get("sale_money");
					
					if(mbb_mb.get(key) !=null){
						MatBatchBalance mbb = (MatBatchBalance)mbb_mb.get(key);
						mapBatch.put("out_amount", amount+mbb.getOut_amount());//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", amount_money+mbb.getOut_money());//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", sale_money+mbb.getOut_sale_money());//出库批发金额 等于出库的批发金额 + 当前出库的批发金额
						mapBatch.put("y_out_amount", amount+mbb.getOut_amount());//剩余数量 等于剩余的数量 - 当前出库的数量
						mapBatch.put("y_out_money", amount_money+mbb.getOut_money());//剩余数量 等于剩余的金额 - 当前出库的金额
						mapBatch.put("y_out_sale_money", sale_money+mbb.getOut_sale_money());//剩余批发金额 等于剩余的批发金额 - 当前出库的批发金额
						//以下字段的计算方式有待讨论
						if(mbb.getIn_amount() - (Double)mapBatch.get("out_amount") == 0){
							mapBatch.put("remove_zero_error", mbb.getIn_money() - (Double)mapBatch.get("out_money"));//拆零误差 等于入库金额 - 出库金额
							mapBatch.put("sale_zero_error", mbb.getIn_sale_money() - (Double)mapBatch.get("out_sale_money"));//批发拆零误差 等于入库批发金额 - 出库批发金额
						}
						
						listUpdateBatchBalance.add(mapBatch);
					}else{
						mapBatch.put("end_amount", 0);
						mapBatch.put("end_money", 0);
						mapBatch.put("end_sale_money", 0);
						mapBatch.put("begin_amount", 0);
						mapBatch.put("begin_money", 0);
						mapBatch.put("begin_sale_money", 0);
						mapBatch.put("in_amount", 0);
						mapBatch.put("in_money", 0);
						mapBatch.put("in_sale_money", 0);
						mapBatch.put("out_amount", amount);
						mapBatch.put("out_money", amount_money);
						mapBatch.put("out_sale_money", sale_money);
						mapBatch.put("y_in_amount", 0);
						mapBatch.put("y_in_money", 0);
						mapBatch.put("y_in_sale_money", 0);
						mapBatch.put("y_out_amount", amount);
						mapBatch.put("y_out_money", amount_money);
						mapBatch.put("y_out_sale_money", sale_money);
						mapBatch.put("location_id", mapBatch.get("location_id") == null ? 0 : mapBatch.get("location_id"));
						//以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);
						mapBatch.put("sale_zero_error", 0);
						
						listAddBatchBalance.add(mapBatch);
					}
				}
				
				//------------------------------查询账表 取出相应数据 并且组织账表数据MAT_INV_HOLD----------------------------------
				List<MatInvHold> listInvHold = (List<MatInvHold>)matInvHoldMapper.queryByInvList(invList);

				Map<String,MatInvHold> mih_mb = new HashMap<String,MatInvHold>();//所有 根据条件 取出表（MAT_INV_HOLD）的数据都放到map里面
				
				for(MatInvHold mih : listInvHold){
					String key = mih.getStore_id().toString()+mih.getInv_id();
					mih_mb.put(key, mih);
				}
				
				for (String key : mapInvHold.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					
					if(mih_mb.get(key) !=null){
						Map<String,Object> map = mapInvHold.get(key);
						mapBatch.putAll(map);
						
						MatInvHold mih = (MatInvHold)mih_mb.get(key);
						
						mapBatch.put("cur_amount", mih.getCur_amount()-(Double)sum_amount_map.get(mih.getStore_id()+"-"+mih.getInv_id()));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("cur_money", mih.getCur_money()-(Double)sum_amount_money.get(mih.getStore_id()+"-"+mih.getInv_id()));//出库金额 等于出库的金额 + 当前出库的金额
						
						listUpdateInvHold.add(mapBatch);
					}
				}

				//------------------------------查询账表 取出相应数据 并且组织账表数据MAT_INV_Batch----------------------------------
				List<MatInvBalance> listInvBalance = (List<MatInvBalance>)matInvBalanceMapper.queryByInvList(invList);
				
				Map<String,MatInvBalance> mib_mb = new HashMap<String,MatInvBalance>();//所有 根据条件 取出表（MAT_INV_HOLD）的数据都放到map里面
				for(MatInvBalance mib : listInvBalance){
					String key = mib.getYear().toString()+mib.getMonth()+mib.getStore_id()+mib.getInv_id();
					mib_mb.put(key, mib);
				}
				
				for (String key : mapInvBalance.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					Map<String,Object> map = mapInvBalance.get(key);
					
					mapBatch.putAll(map);
					
					if(mib_mb.get(key) !=null){
						MatInvBalance mib = (MatInvBalance)mib_mb.get(key);
						mapBatch.put("out_amount", (Double)sum_amount_map.get(mib.getStore_id()+"-"+mib.getInv_id())+mib.getOut_amount());//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", (Double)sum_amount_money.get(mib.getStore_id()+"-"+mib.getInv_id())+mib.getOut_money());//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", (Double)sum_sale_money.get(mib.getStore_id()+"-"+mib.getInv_id())+mib.getOut_sale_money());//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("y_out_amount", (Double)sum_amount_map.get(mib.getStore_id()+"-"+mib.getInv_id())+mib.getOut_amount());//剩余数量 等于剩余的数量 - 当前出库的数量
						mapBatch.put("y_out_money", (Double)sum_amount_money.get(mib.getStore_id()+"-"+mib.getInv_id())+mib.getOut_money());//剩余数量 等于剩余的金额 - 当前出库的金额
						mapBatch.put("y_out_sale_money", (Double)sum_sale_money.get(mib.getStore_id()+"-"+mib.getInv_id())+mib.getOut_sale_money());//剩余数量 等于剩余的金额 - 当前出库的金额
						//以下字段的计算方式有待讨论
						if(mib.getIn_amount() - (Double)mapBatch.get("out_amount") == 0){
							mapBatch.put("remove_zero_error", mib.getIn_money() - (Double)mapBatch.get("out_money"));//拆零误差 等于入库金额 - 出库金额
							mapBatch.put("sale_zero_error", mib.getIn_sale_money() - (Double)mapBatch.get("out_sale_money"));//批发拆零误差 等于入库批发金额 - 出库批发金额
						}
							
						listUpdateInvBalance.add(mapBatch);
					}else{
						mapBatch.put("end_amount", 0);
						mapBatch.put("end_money", 0);
						mapBatch.put("end_sale_money", 0);
						mapBatch.put("begin_amount", 0);
						mapBatch.put("begin_money", 0);
						mapBatch.put("begin_sale_money", 0);
						mapBatch.put("in_amount", 0);
						mapBatch.put("in_money", 0);
						mapBatch.put("in_sale_money", 0);
						mapBatch.put("out_amount", (Double)sum_amount_map.get(String.valueOf(map.get("store_id"))+"-"+String.valueOf(map.get("inv_id"))));
						mapBatch.put("out_money",  (Double)sum_amount_money.get(String.valueOf(map.get("store_id"))+"-"+String.valueOf(map.get("inv_id"))));
						mapBatch.put("out_sale_money",  (Double)sum_sale_money.get(String.valueOf(map.get("store_id"))+"-"+String.valueOf(map.get("inv_id"))));
						mapBatch.put("y_in_amount", 0);
						mapBatch.put("y_in_money", 0);
						mapBatch.put("y_in_sale_money", 0);
						mapBatch.put("y_out_amount", (Double)sum_amount_map.get(String.valueOf(map.get("store_id"))+"-"+String.valueOf(map.get("inv_id"))));
						mapBatch.put("y_out_money",  (Double)sum_amount_money.get(String.valueOf(map.get("store_id"))+"-"+String.valueOf(map.get("inv_id"))));
						mapBatch.put("y_out_sale_money",  (Double)sum_sale_money.get(String.valueOf(map.get("store_id"))+"-"+String.valueOf(map.get("inv_id"))));
						//以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);
						mapBatch.put("sale_zero_error", 0);
						
						mapBatch.put("location_id", mapBatch.get("location_id") == null ? 0 : mapBatch.get("location_id"));
						
						listAddInvBalance.add(mapBatch);
					}
				}
			}
			if(!is_enough){
				//材料库存不足提示
				return "{\"error\":\""+invMsg.substring(0, invMsg.length()-1).toString()+"库存物资不足!\"}";
			}
			
			if(listUpdateFifoBalance.size()>0){matFifoBalanceMapper.updateBatch(listUpdateFifoBalance);}
			if(listUpdateBatchBalance.size() >0){matBatchBalanceMapper.updateBatch(listUpdateBatchBalance);}
			if(listAddBatchBalance.size() >0){matBatchBalanceMapper.addBatch(listAddBatchBalance);}
			if(listUpdateInvHold.size() >0){matInvHoldMapper.updateBatch(listUpdateInvHold);}
			if(listUpdateInvBalance.size() >0){matInvBalanceMapper.updateBatch(listUpdateInvBalance);}
			if(listAddInvBalance.size()>0){matInvBalanceMapper.addBatch(listAddInvBalance);}
			
			matOutMainMapper.updateBatch(entityMap);//修改状态为出库确认

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"出库确认失败 数据库异常 请联系管理员! 方法 confirmOutMatOutMain\"}";
		}
		
		return "{\"msg\":\"出库确认成功.\",\"state\":\"true\"}";
	}

	//专购品 出入库 确认
	public String confirmMatSpecialBatch(List<Map<String, Object>> listVo) throws DataAccessException {

		//批量添加出入库结存表 MAT_FIFO_BALANCE 用List
		List<Map<String,Object>> addFifo = new ArrayList<Map<String,Object>>();
		//批量修改出入库结存表 MAT_FIFO_BALANCE 用List
		List<Map<String,Object>> updateFifo = new ArrayList<Map<String,Object>>();
		//记录操作MAT_FIFO_BALANCE的数据，防止重复添加或修改
		Map<String, Map<String, Object>> invFifoBalanceMap = new HashMap<String, Map<String,Object>>();
		
		//批量添加物资材料结余表MAT_INV_BALANCE 用List
		List<Map<String,Object>> addInv = new ArrayList<Map<String,Object>>();
		//批量修改物资材料结余表MAT_INV_BALANCE 用List
		List<Map<String,Object>> updateInv = new ArrayList<Map<String,Object>>();
		//记录操作MAT_INV_BALANCE的数据，防止重复添加或修改
		Map<String, Map<String, Object>> invBalanceMap = new HashMap<String, Map<String,Object>>();
		
		//批量添加物资批次结余表 MAT_BATCH_BALANCE 用List
		List<Map<String,Object>> addBatch = new ArrayList<Map<String,Object>>();
		//批量修改物资批次结余表 MAT_BATCH_BALANCE 用List
		List<Map<String,Object>> updateBatch = new ArrayList<Map<String,Object>>();
		//记录操作MAT_BATCH_BALANCE的数据，防止重复添加或修改
		Map<String, Map<String, Object>> invBatchBalanceMap = new HashMap<String, Map<String,Object>>();
		
		//批量添加物资材料结存表 MAT_INV_HOLD 用List
		List<Map<String,Object>> addInvHold = new ArrayList<Map<String,Object>>();
		//记录操作MAT_INV_HOLD的数据，防止重复添加或修改
		Map<String, Map<String, Object>> invHoldMap = new HashMap<String, Map<String,Object>>();
		
		// 批量添加 材料灭菌日期表	 MAT_BATCH_DISINFECT
		List<Map<String,Object>> addDisinfect = new ArrayList<Map<String,Object>>();
		//记录操作MAT_BATCH_DESINFECT的数据，防止重复添加或修改
		Map<String, Map<String, Object>> invBatchDisMap = new HashMap<String, Map<String,Object>>();

		// 批量添加 材料期效表  MAT_BATCH_VALIDITY
		List<Map<String,Object>> addVal = new ArrayList<Map<String,Object>>();
		//记录操作MAT_BATCH_DESINFECT的数据，防止重复添加或修改
		Map<String, Map<String, Object>> invBatchValMap = new HashMap<String, Map<String,Object>>();
		
		//Map中的key键
		String invBatchKey, invBatchBarKey, invKey;
		
		//临时Map
		Map<String, Object> invMap;
		try {	
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005").toString());
			
			List<Map<String, Object>> detailList = JsonListMapUtil.toListMapLower(matSpecialMapper.querySpecialDetailForConfrim(listVo));
			
			for(Map<String,Object> item : detailList){
				invKey = item.get("store_id").toString() + item.get("inv_id").toString() + item.get("location_id").toString();
				invBatchKey = item.get("inv_id").toString() + item.get("batch_no").toString();
				invBatchBarKey = invKey + item.get("batch_no") + item.get("batch_sn").toString() + item.get("bar_code").toString();
				
				/*******处理MAT_FIFO_BALANCE********BEGIN***************************/
				//查询 出入库结存表 MAT_FIFO_BALANCE 是否存在该记录（存在则修改该记录 否则就添加）
				if(invFifoBalanceMap.containsKey("add" + invBatchBarKey)){
					//用于添加
					invMap = invFifoBalanceMap.get("add" +invBatchBarKey);
					invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));

					invFifoBalanceMap.put("add" + invBatchBarKey, invMap);
				}else if(invFifoBalanceMap.containsKey("update" + invBatchBarKey)){
					//用于修改
					invMap = invFifoBalanceMap.get("update" + invBatchBarKey);
					invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));

					invFifoBalanceMap.put("update" + invBatchBarKey, invMap);
				}else{
					invMap = JsonListMapUtil.toMapLower(matSpecialMapper.queryFifoExists(item));
					if(invMap.get("group_id") == null){
						invMap.put("group_id", item.get("group_id"));
						invMap.put("hos_id", item.get("hos_id"));
						invMap.put("copy_code", item.get("copy_code"));
						invMap.put("store_id", item.get("store_id"));
						invMap.put("inv_id", item.get("inv_id"));
						invMap.put("batch_no", item.get("batch_no"));
						invMap.put("batch_sn", item.get("batch_sn"));
						invMap.put("bar_code", item.get("bar_code"));
						invMap.put("price", item.get("price"));
						invMap.put("sale_price", item.get("sale_price"));
						invMap.put("in_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("in_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("in_sale_money", item.get("sale_money"));
						invMap.put("out_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("out_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("out_sale_money", item.get("sale_money"));
						invMap.put("left_amount", "0");
						invMap.put("left_money", "0");
						invMap.put("left_sale_money", "0");
						invMap.put("remove_zero_error", "0");
						invMap.put("sale_zero_error", "0");
						invMap.put("location_id", item.get("location_id"));
						invFifoBalanceMap.put("add" + invBatchBarKey, invMap);
					}else{
						invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString())));
						invMap.put("in_sale_money", NumberUtil.add(Double.valueOf(invMap.get("in_sale_money").toString()), Double.valueOf(item.get("sale_money").toString())));
						invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
						invMap.put("out_sale_money", NumberUtil.add(Double.valueOf(invMap.get("out_sale_money").toString()), Double.valueOf(item.get("sale_money").toString())));
						invFifoBalanceMap.put("update" + invBatchBarKey, invMap);
					}
				}
				/*******处理MAT_FIFO_BALANCE********END*****************************/
				
				/*******处理MAT_BATCH_BALANCE******BEGIN***************************/
				//查询 物资批次结余表 MAT_BATCH_BALANCE 是否存在该记录（存在则修改该记录 否则就添加
				if(invBatchBalanceMap.containsKey("add" + invBatchBarKey)){
					invMap = invBatchBalanceMap.get("add" + invBatchBarKey);
					invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
					invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					
					invBatchBalanceMap.put("add" + invBatchBarKey, invMap);
				}else if(invBatchBalanceMap.containsKey("update" + invBatchBarKey)){
					//用于修改
					invMap = invBatchBalanceMap.get("update" + invBatchBarKey);
					invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
					invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					
					invBatchBalanceMap.put("update" + invBatchBarKey, invMap);
				}else{
					invMap = matSpecialMapper.queryBatchExists(item);
					if(invMap.get("group_id") == null ){
						invMap.put("group_id", item.get("group_id"));
						invMap.put("hos_id", item.get("hos_id"));
						invMap.put("copy_code", item.get("copy_code"));
						invMap.put("year", item.get("year"));
						invMap.put("month", item.get("month"));
						invMap.put("store_id", item.get("store_id"));
						invMap.put("inv_id", item.get("inv_id"));
						invMap.put("batch_no", item.get("batch_no"));
						invMap.put("batch_sn", item.get("batch_sn"));
						invMap.put("bar_code", item.get("bar_code"));
						invMap.put("begin_amount", "0");
						invMap.put("begin_money", "0");
						invMap.put("in_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("in_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("in_sale_money", item.get("sale_money"));
						invMap.put("out_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("out_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("out_sale_money", item.get("sale_money"));
						invMap.put("end_amount", "0");
						invMap.put("end_money", "0");
						invMap.put("end_sale_money", "0");
						invMap.put("remove_zero_error", "0");
						invMap.put("sale_zero_error", "0");
						invMap.put("y_in_sale_money", "0");
						invMap.put("y_out_sale_money", "0");
						invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("location_id", item.get("location_id"));
						
						invBatchBalanceMap.put("add" + invBatchBarKey, invMap);
					}else{
						invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
						invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						
						invBatchBalanceMap.put("update" + invBatchBarKey, invMap);
					}
				}
				/*******处理MAT_BATCH_BALANCE******END*****************************/
				
				/*******处理MAT_INV_BALANCE*********BEGIN***************************/
				//查询 物资材料结余表MAT_INV_BALANCE 是否存在该记录（存在则修改该记录 否则就添加）
				if(invBalanceMap.containsKey("add" + invKey)){
					invMap = invBalanceMap.get("add" + invKey);
					invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
					invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					
					invBalanceMap.put("add" + invKey, invMap);
				}else if(invBalanceMap.containsKey("update" + invKey)){
					//用于修改
					invMap = invBalanceMap.get("update" + invKey);
					invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
					invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					
					invBalanceMap.put("update" + invKey, invMap);
				}else{
					invMap = matSpecialMapper.queryInvExists(item);
					if(invMap.get("group_id") == null ){
						invMap.put("group_id", item.get("group_id"));
						invMap.put("hos_id", item.get("hos_id"));
						invMap.put("copy_code", item.get("copy_code"));
						invMap.put("year", item.get("year"));
						invMap.put("month", item.get("month"));
						invMap.put("store_id", item.get("store_id"));
						invMap.put("inv_id", item.get("inv_id"));
						invMap.put("begin_amount", "0");
						invMap.put("begin_money", "0");
						invMap.put("in_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("in_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("in_sale_money", item.get("sale_money"));
						invMap.put("out_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("out_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("out_sale_money", item.get("sale_money"));
						invMap.put("end_amount", "0");
						invMap.put("end_money", "0");
						invMap.put("end_sale_money", "0");
						invMap.put("remove_zero_error", "0");
						invMap.put("sale_zero_error", "0");
						invMap.put("y_in_sale_money", "0");
						invMap.put("y_out_sale_money", "0");
						invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("location_id", item.get("location_id"));
						
						invBalanceMap.put("add" + invKey, invMap);
					}else{
						invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
						invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));

						invBalanceMap.put("update" + invKey, invMap);
					}
				}
				/*******处理MAT_INV_BALANCE*********END*****************************/
				
				/*******处理MAT_INV_HOLD*************BEGIN***************************/
				//查询 物资材料结存表 MAT_INV_HOLD 是否存在该记录（存在则不修改 否则就添加
				if(invHoldMap.containsKey(invKey)){
					invMap = invHoldMap.get(invKey);
					invMap.put("cur_amount", NumberUtil.add(Double.valueOf(invMap.get("cur_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("cur_money", NumberUtil.add(Double.valueOf(invMap.get("cur_money").toString()), Double.valueOf(item.get("amount_money").toString())));
					
					invHoldMap.put(invKey, invMap);
				}else{
					Long invHold = matSpecialMapper.queryInvHoldExists(item);
					if(invHold == null || invHold == 0 ){
						invMap = new HashMap<String, Object>();

						invMap.put("group_id", item.get("group_id"));
						invMap.put("hos_id", item.get("hos_id"));
						invMap.put("copy_code", item.get("copy_code"));
						invMap.put("year", item.get("year"));
						invMap.put("month", item.get("month"));
						invMap.put("store_id", item.get("store_id"));
						invMap.put("inv_id", item.get("inv_id"));
						invMap.put("cur_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("cur_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("location_id", item.get("location_id"));
						invMap.put("imme_amount", "0");
						
						invHoldMap.put(invKey, invMap);
					}
				}
				/*******处理MAT_INV_HOLD*************END*****************************/
				
				//查询 材料期效表  MAT_BATCH_VALIDITY 是否存在该记录（存在则不修改 否则就添加）
				if("1".equals(String.valueOf(item.get("is_quality"))) && !"-".equals(String.valueOf(item.get("batch_no")))){
					if(!invBatchValMap.containsKey(invBatchKey)){
						Long invVal = matSpecialMapper.queryValidityExists(item);
						if(invVal == 0 && item.get("inva_date") != null && item.get("inva_date") != ""){
							invMap = new HashMap<String, Object>();
							invMap.put("group_id", item.get("group_id"));
							invMap.put("hos_id", item.get("hos_id"));
							invMap.put("copy_code", item.get("copy_code"));
							invMap.put("inv_id", item.get("inv_id"));
							invMap.put("batch_no", item.get("batch_no"));
							invMap.put("inva_date", item.get("inva_date"));
							
							invBatchValMap.put(invBatchKey, invMap);
						}
					}
				}
				
				//查询 材料灭菌日期表MAT_BATCH_DISINFECT 是否存在该记录（存在则不修改 否则就添加）
				if("1".equals(String.valueOf(item.get("is_disinfect"))) && !"-".equals(String.valueOf(item.get("batch_no")))){
					if(!invBatchDisMap.containsKey(invBatchKey)){
						Long invDis = matSpecialMapper.queryDisinfectExists(item);
						if(invDis == 0 && item.get("distinfect_date") != null && item.get("distinfect_date") != "" ){
							invMap = new HashMap<String, Object>();
							invMap.put("group_id", item.get("group_id"));
							invMap.put("hos_id", item.get("hos_id"));
							invMap.put("copy_code", item.get("copy_code"));
							invMap.put("inv_id", item.get("inv_id"));
							invMap.put("batch_no", item.get("batch_no"));
							invMap.put("distinfect_date", item.get("distinfect_date"));
							
							invBatchDisMap.put(invBatchKey, invMap);
						}
					}
				}
			}
			
			//专购品批量  确认
			matSpecialMapper.confirmMatSpecial(listVo);
			
			//专购品批量入库确认
			matSpecialMapper.confirmMatSpecialIn(listVo);
			
			//专购品批量出库确认
			matSpecialMapper.confirmMatSpecialOut(listVo);

			//维护 出入库结存表 MAT_FIFO_BALANCE
			for (String key : invFifoBalanceMap.keySet()) {
				if(key.startsWith("add")){
					addFifo.add(invFifoBalanceMap.get(key));
				}else{
					updateFifo.add(invFifoBalanceMap.get(key));
				}
			}
			if(addFifo.size()>0 || updateFifo.size()>0){
				if(addFifo.size()>0){
					matFifoBalanceMapper.addBatch(addFifo);
				}
				if(updateFifo.size()>0){
					matFifoBalanceMapper.updateBatch(updateFifo);
				}
			}else{
				throw new SysException("{\"error\":\"确认失败！帐表查询不出数据\"}");
			}
			
			//维护 物资批次结余表 MAT_BATCH_BALANCE
			for (String key : invBatchBalanceMap.keySet()) {
				if(key.startsWith("add")){
					addBatch.add(invBatchBalanceMap.get(key));
				}else{
					updateBatch.add(invBatchBalanceMap.get(key));
				}
			}
			if(addBatch.size()>0 || updateBatch.size()>0){
				if(addBatch.size()>0){
					matBatchBalanceMapper.addBatch(addBatch);
				}else if(updateBatch.size()>0){
					matBatchBalanceMapper.updateBatch(updateBatch);
				}
			}else{
				throw new SysException("{\"error\":\"确认失败！帐表查询不出数据\"}");
			}
			
			//维护 物资材料结余表MAT_INV_BALANCE
			for (String key : invBalanceMap.keySet()) {
				if(key.startsWith("add")){
					addInv.add(invBalanceMap.get(key));
				}else{
					updateInv.add(invBalanceMap.get(key));
				}
			}
			if(addInv.size()>0 || updateInv.size()>0){
				if(addInv.size()>0){
					matInvBalanceMapper.addBatch(addInv);
				}else if(updateInv.size()>0){
					matInvBalanceMapper.updateBatch(updateInv);
				}
			}else{
				throw new SysException("{\"error\":\"确认失败！帐表查询不出数据\"}");
			}
			
			// 维护 物资材料结存表 MAT_INV_HOLD
			for (String key : invHoldMap.keySet()) {
				addInvHold.add(invHoldMap.get(key));
			}
			if(addInvHold.size()>0){
				matInvHoldMapper.addBatch(addInvHold);
			}
			
			// 维护 材料灭菌日期表  MAT_BATCH_DISINFECT
			for (String key : invBatchDisMap.keySet()) {
				addDisinfect.add(invBatchDisMap.get(key));
			}
			if(addDisinfect.size()>0){
				matSpecialMapper.addBatchDisinfect(addDisinfect);
			}
			
			// 维护 材料期效表  MAT_BATCH_VALIDITY 
			for (String key : invBatchValMap.keySet()) {
				addVal.add(invBatchValMap.get(key));
			}
			if(addVal.size()>0){
				matSpecialMapper.addBatchValidity(addVal);
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"确认失败！方法 confirmMatSpecialBatch\"}");
		}	
	}
	
	//代销出库确认
	public boolean confirmMatAffiOutCommon(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			/*
			 * (1)、判断当前编制日期所在期间是否结账，若已结账提示：当月已结账不能保存！ (2)、只有审核状态的单据允许出库确认 (页面判断)
			 * (3)、除30代销退库业务之外，其他出库业务 判断材料出库数量是否大于当前库存，若大于，提示：库存不足，出库确认失败！
			 * (4)、30代销退库业务：判断材料出库数量是否能容纳这么多退库数量。 (5)、按照先进先出算法，将出库单数量写入物流出出库结存表
			 * MAT_FIFO_BALANCE的出库数量中； (6)、同时，将当月出库数量累加到库存结余表：物资批次结余表
			 * MAT_BATCH_BALANCE；物资材料结存表 MAT_BATCH_HOLD；代销材料结余表：MAT_AFFI_BALANCE
			 * (7)、修改出库单状态为出库确认（state=3） ，以及确认人，确认日期，出库日期 。
			 */
			// 出库判断 (1)
			String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);

			List<Map<String, Object>> listUpdateFifoBalance = new ArrayList<Map<String, Object>>();// 保存修改账表数据
			List<Map<String, Object>> listUpdateBatchBalance = new ArrayList<Map<String, Object>>();// 保存修改账表数据
			List<Map<String, Object>> listUpdateInvHold = new ArrayList<Map<String, Object>>();// 保存修改账表数据
			List<Map<String, Object>> listUpdateInvBalance = new ArrayList<Map<String, Object>>();// 保存修改账表数据
			List<Map<String, Object>> listAddBatchBalance = new ArrayList<Map<String, Object>>();// 保存修改账表数据
			List<Map<String, Object>> listAddInvBalance = new ArrayList<Map<String, Object>>();// 保存修改账表数据
			List<Map<String, Object>> listMainUpdate = new ArrayList<Map<String, Object>>();// 保存修改账表数据

			StringBuffer invMsg = new StringBuffer();// 存放库存不足的材料信息
			boolean is_enough = true; // 库存是否充足

			// 获取所选单据中的所有材料
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			detailList = (List<Map<String, Object>>) matAffiOutCommonMapper.queryMatAffiOutDetailForConfirm(entityList);
			// 查询帐表所需
			List<Map<String, Object>> invList = new ArrayList<Map<String, Object>>();

			// 存放明细数据用于判断
			Map<String, Map<String, Object>> matAffiFifo = new HashMap<String, Map<String, Object>>();// 存放明细数据
			Map<String, Map<String, Object>> mapBatchBalance = new HashMap<String, Map<String, Object>>();// 存放明细数据
			Map<String, Map<String, Object>> mapInvHold = new HashMap<String, Map<String, Object>>();// 存放明细数据
			Map<String, Map<String, Object>> mapInvBalance = new HashMap<String, Map<String, Object>>();// 存放明细数据

			// 累计相同材料的数量和金额
			Map<Long, Double> sum_amount_map = new HashMap<Long, Double>();
			Map<Long, Double> sum_amount_money = new HashMap<Long, Double>();
			Map<Long, Double> sum_sale_money = new HashMap<Long, Double>();
			// System.out.println("******* detailList:"+detailList.size());
			for (Map<String, Object> mod : detailList) {
				// 材料编码和名称用于库存不足的提示
				Map<String, Object> mapDetail = new HashMap<String, Object>();
				Map<String, Object> invMap = new HashMap<String, Object>();

				mapDetail.put("group_id", mod.get("group_id"));
				mapDetail.put("hos_id", mod.get("hos_id"));
				mapDetail.put("copy_code", mod.get("copy_code"));
				mapDetail.put("store_id", mod.get("store_id"));
				mapDetail.put("inv_id", mod.get("inv_id"));
				mapDetail.put("inv_code", mod.get("inv_code"));
				mapDetail.put("inv_name", mod.get("inv_name"));
				mapDetail.put("batch_no", mod.get("batch_no"));
				mapDetail.put("batch_sn", mod.get("batch_sn"));
				mapDetail.put("bar_code", mod.get("bar_code"));
				mapDetail.put("price", mod.get("price") == null ? 0 : mod.get("price"));
				mapDetail.put("amount", mod.get("amount") == null ? 0 : mod.get("amount"));
				mapDetail.put("amount_money", mod.get("amount_money") == null ? 0 : mod.get("amount_money"));
				mapDetail.put("sale_price", mod.get("sale_price") == null ? 0 : mod.get("sale_price"));
				mapDetail.put("sale_money", mod.get("sale_money") == null ? 0 : mod.get("sale_money"));
				// mapDetail.put("location_id", mod.getLocation_id());

				invMap.put("group_id", mod.get("group_id"));
				invMap.put("hos_id", mod.get("hos_id"));
				invMap.put("copy_code", mod.get("copy_code"));
				invMap.put("year", year);
				invMap.put("month", month);
				invMap.put("store_id", mod.get("store_id"));
				invMap.put("inv_id", mod.get("inv_id"));
				invMap.put("batch_no", mod.get("batch_no"));
				invMap.put("batch_sn", mod.get("batch_sn"));
				invMap.put("bar_code", mod.get("bar_code"));
				// invMap.put("location_id", mod.getLocation_id());

				invList.add(invMap);

				// Map中存放<各帐表主键, 具体材料信息的map>用于判断

				matAffiFifo.put(mod.get("store_id").toString() + mod.get("inv_id") + mod.get("batch_no")
						+ mod.get("batch_sn") + mod.get("bar_code"), mapDetail);
				mapInvHold.put(mod.get("store_id").toString() + mod.get("inv_id"), mapDetail);
				// 下面这两张表是区分年月的
				mapDetail.put("year", year);
				mapDetail.put("month", month);
				mapBatchBalance.put(year + month + mod.get("store_id") + mod.get("inv_id") + mod.get("batch_no")
						+ mod.get("batch_sn") + mod.get("bar_code"), mapDetail);
				mapInvBalance.put(year + month + mod.get("store_id") + mod.get("inv_id"), mapDetail);

				// 根据材料ID累计数量
				if (sum_amount_map.get(mod.get("inv_id")) != null) {
					sum_amount_map.put(Long.parseLong(mod.get("inv_id").toString() + mod.get("store_id")),
							Double.parseDouble(sum_amount_map.get(mod.get("inv_id")).toString() + mod.get("store_id"))
									+ Double.parseDouble(mod.get("amount").toString()));
				} else {
					sum_amount_map.put(Long.parseLong(mod.get("inv_id").toString() + mod.get("store_id")),
							Double.parseDouble(mod.get("amount").toString()));
				}

				// 根据材料ID累计金额
				if (sum_amount_money.get(mod.get("inv_id")) != null) {
					sum_amount_map.put(Long.parseLong(mod.get("inv_id").toString() + mod.get("store_id")),
							Double.parseDouble(sum_amount_map.get(mod.get("inv_id")).toString() + mod.get("store_id"))
									+ Double.parseDouble(mod.get("amount_money").toString()));
				} else {
					sum_amount_money.put(Long.parseLong(mod.get("inv_id").toString() + mod.get("store_id")),
							Double.parseDouble(mod.get("amount_money").toString()));
				}
				// 根据材料ID累计金额
				if (sum_sale_money.get(mod.get("inv_id")) != null) {
					sum_amount_map.put(Long.parseLong(mod.get("inv_id").toString() + mod.get("store_id")),
							Double.parseDouble(sum_amount_map.get(mod.get("inv_id")).toString() + mod.get("store_id"))
									+ Double.parseDouble(mod.get("sale_money").toString()));
				} else {
					sum_sale_money.put(Long.parseLong(mod.get("inv_id").toString() + mod.get("store_id")),
							Double.parseDouble(mod.get("sale_money").toString()));
				}

			}

			// ------------------------------查询账表 取出相应数据 并且组织账表数据
			// MAT_FIFO_BALANCE//mat_affi_fifo
			// ----------------------------------
			List<Map<String, Object>> listFifoBalance = (List<Map<String, Object>>) matAffiFifoMapper
					.queryByInvList(invList);
			Map<String, Map<String, Object>> mfb_mb = new HashMap<String, Map<String, Object>>();// 所有
																									// 根据条件
																									// 取出表（MAT_FIFO_BALANCE）的数据都放到map里面
			for (Map<String, Object> mfb : listFifoBalance) {
				String key = mfb.get("store_id").toString() + mfb.get("inv_id") + mfb.get("batch_no")
						+ mfb.get("batch_sn") + mfb.get("bar_code");
				mfb_mb.put(key, mfb);
			}

			for (String key : matAffiFifo.keySet()) {// 循环当前材料的明细 key查询条件 取相应的值
				Map<String, Object> mapBatch = new HashMap<String, Object>();
				if (mfb_mb.get(key) != null) {
					Map<String, Object> map = matAffiFifo.get(key);
					mapBatch.putAll(map);
					// 选中的单据中材料 map 与 mfb(账表中的材料) 中数量作对比
					Map<String, Object> mfb = mfb_mb.get(key);

					double amount = Double.parseDouble(map.get("amount").toString());
					double amount_money = Double.parseDouble(map.get("amount_money").toString());
					double sale_money = Double.parseDouble(map.get("sale_money").toString());
					/*
					 * 判断实际库存是否充足-------------------begin-----------------------
					 * ---
					 */
					// 所选单据材料合计数量 > 帐表材料剩余数量 ? 库存不足 : 库存充足
					if (amount > Double.parseDouble(mfb.get("left_amount").toString())) {
						invMsg.append(map.get("inv_code")).append(" ").append(map.get("inv_name")).append(",");
						if (is_enough) {
							is_enough = false;
						}
					}
					/*
					 * 判断实际库存是否充足-------------------end-------------------------
					 * ---
					 */
					if (is_enough) {
						// 库存充足
						mapBatch.put("out_amount", amount + Double.parseDouble(mfb.get("out_amount").toString()));// 出库数量
																													// 等于出库的数量
																													// +
																													// 当前出库的数量
						mapBatch.put("out_money", amount_money + Double.parseDouble(mfb.get("out_money").toString()));// 出库金额
																														// 等于出库的金额
																														// +
																														// 当前出库的金额
						mapBatch.put("out_sale_money",
								sale_money + Double.parseDouble(mfb.get("out_sale_money").toString()));// 出库批发金额
																										// 等于出库批发金额
																										// +
																										// 当前出库批发金额
						mapBatch.put("left_amount", Double.parseDouble(mfb.get("left_amount").toString()) - amount);// 剩余数量
																													// 等于剩余的数量
																													// -
																													// 当前出库的数量
						mapBatch.put("left_money", Double.parseDouble(mfb.get("left_money").toString()) - amount_money);// 剩余金额
																														// 等于剩余金额
																														// -
																														// 当前出库金额
						mapBatch.put("left_sale_money",
								Double.parseDouble(mfb.get("left_sale_money").toString()) - sale_money);// 剩余数量
																										// 等于剩余数量
																										// -
																										// 当前出库数量
						// 以下字段的计算方式有待讨论
						if (Double.parseDouble(mfb.get("left_amount").toString()) - amount == 0) {
							mapBatch.put("remove_zero_error",
									Double.parseDouble(mfb.get("in_money").toString() + mfb.get("store_id"))
											- Double.parseDouble(mapBatch.get("out_money").toString()));// 拆零误差
																										// 等于入库金额
																										// -
																										// 出库金额
							mapBatch.put("sale_zero_error",
									Double.parseDouble(mfb.get("in_sale_money").toString() + mfb.get("store_id"))
											- Double.parseDouble(mapBatch.get("out_sale_money").toString()));// 批发拆零误差
																												// 等于入库批发金额
																												// -
																												// 出库批发金额
						}
						listUpdateFifoBalance.add(mapBatch);
					}
				} else {
					// 帐表中没有该材料信息，提示库存不足！
					invMsg.append(matAffiFifo.get(key).get("inv_code")).append(" ")
							.append(matAffiFifo.get(key).get("inv_name")).append(",");
					if (is_enough) {
						is_enough = false;
					}
				}
			}

			// 如果材料出现库存物资不足的就不需要组装、判断另外几个帐表了
			if (is_enough) {
				// ------------------------------查询账表 取出相应数据
				// 并且组织账表数据MAT_BATCH_BALANCE//
				// mat_affi_batch----------------------------------
				List<Map<String, Object>> listBatchBalance = (List<Map<String, Object>>) matAffiBatchMapper
						.queryByInvList(invList);

				Map<String, Map<String, Object>> mbb_mb = new HashMap<String, Map<String, Object>>();// 所有
																										// 根据条件
																										// 取出表（MAT_BATCH_BALANCE）的数据都放到map里面

				for (Map<String, Object> mbb : listBatchBalance) {
					String key = mbb.get("year").toString() + mbb.get("month") + mbb.get("store_id") + mbb.get("inv_id")
							+ mbb.get("batch_no") + mbb.get("batch_sn") + mbb.get("bar_code");
					;
					mbb_mb.put(key, mbb);
				}

				for (String key : mapBatchBalance.keySet()) {// 循环当前材料的明细
																// key查询条件 取相应的值
					Map<String, Object> mapBatch = new HashMap<String, Object>();

					Map<String, Object> map = mapBatchBalance.get(key);
					mapBatch.putAll(map);

					double amount = Double.parseDouble(map.get("amount").toString());
					double amount_money = Double.parseDouble(map.get("amount_money").toString());
					double sale_money = Double.parseDouble(map.get("sale_money").toString());

					if (mbb_mb.get(key) != null) {
						Map<String, Object> mbb = mbb_mb.get(key);
						mapBatch.put("out_amount", amount + Double.parseDouble(mbb.get("out_amount").toString()));// 出库数量
																													// 等于出库的数量
																													// +
																													// 当前出库的数量
						mapBatch.put("out_money", amount_money + Double.parseDouble(mbb.get("out_money").toString()));// 出库金额
																														// 等于出库的金额
																														// +
																														// 当前出库的金额
						mapBatch.put("out_sale_money",
								sale_money + Double.parseDouble(mbb.get("out_sale_money").toString()));// 出库批发金额
																										// 等于出库的批发金额
																										// +
																										// 当前出库的批发金额
						mapBatch.put("y_out_amount", amount + Double.parseDouble(mbb.get("out_amount").toString()));// 剩余数量
																													// 等于剩余的数量
																													// -
																													// 当前出库的数量
						mapBatch.put("y_out_money", amount_money + Double.parseDouble(mbb.get("out_money").toString()));// 剩余数量
																														// 等于剩余的金额
																														// -
																														// 当前出库的金额
						mapBatch.put("y_out_sale_money",
								sale_money + Double.parseDouble(mbb.get("out_sale_money").toString()));// 剩余批发金额
																										// 等于剩余的批发金额
																										// -
																										// 当前出库的批发金额
						// 以下字段的计算方式有待讨论
						if (Double.parseDouble(mbb.get("in_amount").toString())
								- Double.parseDouble(mapBatch.get("out_amount").toString()) == 0) {
							mapBatch.put("remove_zero_error", Double.parseDouble(mbb.get("in_money").toString())
									- Double.parseDouble(mapBatch.get("out_money").toString()));// 拆零误差
																								// 等于入库金额
																								// -
																								// 出库金额
							mapBatch.put("sale_zero_error", Double.parseDouble(mbb.get("in_sale_money").toString())
									- Double.parseDouble(mapBatch.get("out_sale_money").toString()));// 批发拆零误差
																										// 等于入库批发金额
																										// -
																										// 出库批发金额
						}

						listUpdateBatchBalance.add(mapBatch);
					} else {
						mapBatch.put("y_begin_amount", 0);
						mapBatch.put("y_begin_money", 0);
						mapBatch.put("y_begin_sale_money", 0);
						mapBatch.put("begin_amount", 0);
						mapBatch.put("begin_money", 0);
						mapBatch.put("begin_sale_money", 0);
						mapBatch.put("in_amount", 0);
						mapBatch.put("in_money", 0);
						mapBatch.put("in_sale_money", 0);
						mapBatch.put("out_amount", amount);
						mapBatch.put("out_money", amount_money);
						mapBatch.put("out_sale_money", sale_money);
						mapBatch.put("y_in_amount", 0);
						mapBatch.put("y_in_money", 0);
						mapBatch.put("y_in_sale_money", 0);
						mapBatch.put("y_out_amount", amount);
						mapBatch.put("y_out_money", amount_money);
						mapBatch.put("y_out_sale_money", sale_money);
						mapBatch.put("location_id",
								mapBatch.get("location_id") == null ? 0 : mapBatch.get("location_id"));
						// 以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);
						mapBatch.put("sale_zero_error", 0);

						listAddBatchBalance.add(mapBatch);
					}
				}

				// ------------------------------查询账表 取出相应数据
				// 并且组织账表数据MAT_INV_HOLD//
				// mat_affi_inv_hold----------------------------------
				List<Map<String, Object>> listInvHold = (List<Map<String, Object>>) matAffiInvHoldMapper
						.queryByInvList(invList);
				Map<String, Map<String, Object>> mih_mb = new HashMap<String, Map<String, Object>>();// 所有
																										// 根据条件
																										// 取出表（MAT_INV_HOLD）的数据都放到map里面
				for (Map<String, Object> mih : listInvHold) {
					String key = mih.get("store_id").toString() + mih.get("inv_id");
					mih_mb.put(key, mih);
				}

				for (String key : mapInvHold.keySet()) {// 循环当前材料的明细 key查询条件
														// 取相应的值
					Map<String, Object> mapBatch = new HashMap<String, Object>();
					if (mih_mb.get(key) != null) {
						Map<String, Object> map = mapInvHold.get(key);
						mapBatch.putAll(map);
						Map<String, Object> mih = mih_mb.get(key);

						mapBatch.put("cur_amount",
								Double.parseDouble(mih.get("cur_amount").toString()) - Double.parseDouble(sum_amount_map
										.get(Long.parseLong(mih.get("inv_id").toString() + mih.get("store_id")))
										.toString()));// 出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("cur_money",
								Double.parseDouble(mih.get("cur_money").toString())
										- Double.parseDouble(sum_amount_money
												.get(Long.parseLong(mih.get("inv_id").toString() + mih.get("store_id")))
												.toString()));// 出库金额 等于出库的金额 +
																// 当前出库的金额

						listUpdateInvHold.add(mapBatch);
					}
				}

				// ------------------------------查询账表 取出相应数据
				// 并且组织账表数据MAT_INV_Batch
				// mat_affi_balance----------------------------------
				List<Map<String, Object>> listInvBalance = (List<Map<String, Object>>) matAffiBalanceMapper
						.queryByInvList(invList);

				Map<String, Map<String, Object>> mib_mb = new HashMap<String, Map<String, Object>>();// 所有
																										// 根据条件
																										// 取出表（MAT_INV_HOLD）的数据都放到map里面
				for (Map<String, Object> mib : listInvBalance) {
					String key = mib.get("year").toString() + mib.get("month") + mib.get("store_id")
							+ mib.get("inv_id");
					mib_mb.put(key, mib);
				}

				for (String key : mapInvBalance.keySet()) {// 循环当前材料的明细 key查询条件
															// 取相应的值

					Map<String, Object> mapBatch = new HashMap<String, Object>();
					Map<String, Object> map = mapInvBalance.get(key);

					mapBatch.putAll(map);

					if (mib_mb.get(key) != null) {
						Map<String, Object> mib = mib_mb.get(key);

						mapBatch.put("out_amount",
								Double.parseDouble(sum_amount_map
										.get(Long.parseLong(mib.get("inv_id").toString() + mib.get("store_id")))
										.toString()) + Double.parseDouble(mib.get("out_amount").toString()));// 出库数量
																												// 等于出库的数量
																												// +
																												// 当前出库的数量
						mapBatch.put("out_money",
								Double.parseDouble(sum_amount_money
										.get(Long.parseLong(mib.get("inv_id").toString() + mib.get("store_id")))
										.toString()) + Double.parseDouble(mib.get("out_money").toString()));// 出库金额
																											// 等于出库的金额
																											// +
																											// 当前出库的金额
						mapBatch.put("out_sale_money",
								Double.parseDouble(sum_sale_money
										.get(Long.parseLong(mib.get("inv_id").toString() + mib.get("store_id")))
										.toString()) + Double.parseDouble(mib.get("out_sale_money").toString()));// 出库金额
																													// 等于出库的金额
																													// +
																													// 当前出库的金额
						mapBatch.put("y_out_amount",
								Double.parseDouble(sum_amount_map
										.get(Long.parseLong(mib.get("inv_id").toString() + mib.get("store_id")))
										.toString()) + Double.parseDouble(mib.get("out_amount").toString()));// 剩余数量
																												// 等于剩余的数量
																												// -
																												// 当前出库的数量
						mapBatch.put("y_out_money",
								Double.parseDouble(sum_amount_money
										.get(Long.parseLong(mib.get("inv_id").toString() + mib.get("store_id")))
										.toString()) + Double.parseDouble(mib.get("out_money").toString()));// 剩余数量
																											// 等于剩余的金额
																											// -
																											// 当前出库的金额
						mapBatch.put("y_out_sale_money",
								Double.parseDouble(sum_sale_money
										.get(Long.parseLong(mib.get("inv_id").toString() + mib.get("store_id")))
										.toString()) + Double.parseDouble(mib.get("out_sale_money").toString()));// 剩余数量
																													// 等于剩余的金额
																													// -
																													// 当前出库的金额
						// 以下字段的计算方式有待讨论
						if (Double.parseDouble(mib.get("in_amount").toString())
								- Double.parseDouble(mapBatch.get("out_amount").toString()) == 0) {
							mapBatch.put("remove_zero_error", Double.parseDouble(mib.get("in_money").toString())
									- Double.parseDouble(mapBatch.get("out_money").toString()));// 拆零误差
																								// 等于入库金额
																								// -
																								// 出库金额
							mapBatch.put("sale_zero_error", Double.parseDouble(mib.get("in_sale_money").toString())
									- Double.parseDouble(mapBatch.get("out_sale_money").toString()));// 批发拆零误差
																										// 等于入库批发金额
																										// -
																										// 出库批发金额
						}

						listUpdateInvBalance.add(mapBatch);
					} else {
						mapBatch.put("y_begin_amount", 0);
						mapBatch.put("y_begin_money", 0);
						mapBatch.put("y_begin_sale_money", 0);
						mapBatch.put("begin_amount", 0);
						mapBatch.put("begin_money", 0);
						mapBatch.put("begin_sale_money", 0);
						mapBatch.put("in_amount", 0);
						mapBatch.put("in_money", 0);
						mapBatch.put("in_sale_money", 0);
						mapBatch.put("out_amount", Double.parseDouble(sum_amount_map
								.get(Long.parseLong(map.get("inv_id").toString() + map.get("store_id"))).toString()));
						mapBatch.put("out_money", Double.parseDouble(sum_amount_money
								.get(Long.parseLong(map.get("inv_id").toString() + map.get("store_id"))).toString()));
						mapBatch.put("out_sale_money", Double.parseDouble(sum_sale_money
								.get(Long.parseLong(map.get("inv_id").toString() + map.get("store_id"))).toString()));
						mapBatch.put("y_in_amount", 0);
						mapBatch.put("y_in_money", 0);
						mapBatch.put("y_in_sale_money", 0);
						mapBatch.put("y_out_amount", Double.parseDouble(sum_amount_map
								.get(Long.parseLong(map.get("inv_id").toString() + map.get("store_id"))).toString()));
						mapBatch.put("y_out_money", Double.parseDouble(sum_amount_money
								.get(Long.parseLong(map.get("inv_id").toString() + map.get("store_id"))).toString()));
						mapBatch.put("y_out_sale_money", Double.parseDouble(sum_sale_money
								.get(Long.parseLong(map.get("inv_id").toString() + map.get("store_id"))).toString()));
						// 以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);
						mapBatch.put("sale_zero_error", 0);

						mapBatch.put("location_id",
								mapBatch.get("location_id") == null ? 0 : mapBatch.get("location_id"));

						listAddInvBalance.add(mapBatch);
					}
				}
			}
			if (!is_enough) {
				// 材料库存不足提示
				return false;
			}

			for (Map<String, Object> map : entityList) {
				Map<String, Object> mainMap = new HashMap<String, Object>();
				mainMap.put("group_id", map.get("group_id"));
				mainMap.put("hos_id", map.get("hos_id"));
				mainMap.put("copy_code", map.get("copy_code"));
				mainMap.put("out_id", map.get("out_id"));
				mainMap.put("state", map.get("state"));
				mainMap.put("confirmer", map.get("confirmer"));
				mainMap.put("confirm_date", DateUtil.stringToDate(map.get("confirm_date").toString(), "yyyy-MM-dd"));

				listMainUpdate.add(mainMap);
			}

			if (listUpdateFifoBalance.size() > 0) {
				matAffiFifoMapper.updateBatch(listUpdateFifoBalance);
			}
			if (listUpdateBatchBalance.size() > 0) {
				matAffiBatchMapper.updateBatch(listUpdateBatchBalance);
			}
			if (listAddBatchBalance.size() > 0) {
				matAffiBatchMapper.addBatch(listAddBatchBalance);
			}
			if (listUpdateInvHold.size() > 0) {
				matAffiInvHoldMapper.updateBatch(listUpdateInvHold);
			}
			if (listUpdateInvBalance.size() > 0) {
				matAffiBalanceMapper.updateBatch(listUpdateInvBalance);
			}
			if (listAddInvBalance.size() > 0) {
				matAffiBalanceMapper.addBatch(listAddInvBalance);
			}
			if (listMainUpdate.size() > 0) {
				matAffiOutMapper.updateBatchMatAffiOutMain(listMainUpdate);// 修改状态为出库确认
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
			// return "{\"error\":\"出库确认失败 数据库异常 请联系管理员! 方法
			// confirmOutMatOutMain\"}";
		}

	}

	public Map<String, Object> defaultValue(Map<String, Object> mapVo) {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", mapVo.get("group_id"));
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", mapVo.get("hos_id"));
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", mapVo.get("copy_code"));
		}

		if (mapVo.get("year") == null) {
			mapVo.put("year", "");
		}

		if (mapVo.get("month") == null) {
			mapVo.put("month", "");
		}

		if (mapVo.get("bus_type_code") == null) {
			mapVo.put("bus_type_code", "");
		}

		if (mapVo.get("store_id") == null) {
			mapVo.put("store_id", "");
		}

		if (mapVo.get("store_no") == null) {
			mapVo.put("store_no", "");
		}

		if (mapVo.get("brief") == null) {
			mapVo.put("brief", "");
		}

		if (mapVo.get("out_date") == null) {
			mapVo.put("out_date", "");
		}

		if (mapVo.get("dept_id") == null) {
			mapVo.put("dept_id", "");
		}

		if (mapVo.get("dept_no") == null) {
			mapVo.put("dept_no", "");
		}

		if (mapVo.get("dept_emp") == null) {
			mapVo.put("dept_emp", "");
		}

		if (mapVo.get("maker") == null) {
			mapVo.put("maker", "");
		}

		if (mapVo.get("checker") == null) {
			mapVo.put("checker", "");
		}

		if (mapVo.get("check_date") == null) {
			mapVo.put("check_date", "");
		}

		if (mapVo.get("confirmer") == null) {
			mapVo.put("confirmer", "");
		}

		if (mapVo.get("confirm_date") == null) {
			mapVo.put("confirm_date", "");
		}

		if (mapVo.get("state") == null) {
			mapVo.put("state", "1");
		}

		if (mapVo.get("his_flag") == null) {
			mapVo.put("his_flag", "");
		}

		if (mapVo.get("use_state") == null) {
			mapVo.put("use_state", "0");
		}

		if (mapVo.get("is_dir") == null) {
			mapVo.put("is_dir", "0");
		}

		if (mapVo.get("proj_id") == null) {
			mapVo.put("proj_id", "");
		}

		return mapVo;
	}

	
	// 返回用用于保存的默认值
		public Map<String, Object> defaultDetailValue() {

			Map<String, Object> mapDetailVo = new HashMap<String, Object>();

			mapDetailVo.put("group_id", "0");
			mapDetailVo.put("hos_id", "0");
			mapDetailVo.put("copy_code", "");
			mapDetailVo.put("out_id", "0");
			mapDetailVo.put("out_no", "");
			// mapDetailVo.put("out_detail_id", "");
			mapDetailVo.put("inv_id", "0");
			mapDetailVo.put("inv_no", "0");
			mapDetailVo.put("batch_sn", "0");
			mapDetailVo.put("batch_no", "");
			mapDetailVo.put("price", "0");
			mapDetailVo.put("amount", "0");
			mapDetailVo.put("sale_price", "0");
			mapDetailVo.put("sale_money", "0");
			mapDetailVo.put("sell_price", "0");
			mapDetailVo.put("sell_money", "0");
			mapDetailVo.put("allot_price", "0");
			mapDetailVo.put("allot_money", "0");
			mapDetailVo.put("amount_money", "0");
			mapDetailVo.put("num_exchange", "0");
			mapDetailVo.put("pack_price", "0");
			mapDetailVo.put("num", "0");
			mapDetailVo.put("bar_code", "");
			mapDetailVo.put("is_per_bar", "0");
			mapDetailVo.put("sn", "");
			mapDetailVo.put("inva_date", "");
			mapDetailVo.put("disinfect_date", "");
			mapDetailVo.put("location_id", "0");
			mapDetailVo.put("note", "");
			mapDetailVo.put("pack_code", "");

			return mapDetailVo;
		}
}
