package com.chd.hrp.prm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.hpm.entity.AphiEmp;
import com.chd.hrp.prm.dao.PrmDeptDictMapper;
import com.chd.hrp.prm.dao.PrmEmpDictMapper;
import com.chd.hrp.prm.dao.PrmEmpDutyMapper;
import com.chd.hrp.prm.dao.PrmEmpMapper;
import com.chd.hrp.prm.entity.PrmDeptDict;
import com.chd.hrp.prm.entity.PrmEmp;
import com.chd.hrp.prm.entity.PrmEmpDuty;
import com.chd.hrp.prm.service.PrmEmpService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("prmEmpService")
public class PrmEmpServiceImpl implements PrmEmpService {

	private static Logger logger = Logger.getLogger(PrmEmpServiceImpl.class);
	@Resource(name = "prmEmpDictMapper")
	private final PrmEmpDictMapper prmEmpDictMapper = null;

	@Resource(name = "prmDeptDictMapper")
	private final PrmDeptDictMapper prmDeptDictMapper = null;
	@Resource(name = "prmEmpMapper")
	private final PrmEmpMapper prmEmpMapper = null;
	@Resource(name = "prmEmpDutyMapper")
	private final PrmEmpDutyMapper prmEmpDutyMapper = null;
	
	/**
	 * 
	 */
	@Override
	public String addBatchPrmEmp(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmEmpMapper.addBatchPrmEmp(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmEmpKpiValue\"}";

		}
	}

	/**
	 * 
	 */
	@Override
	public String queryPrmEmp(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmEmp> list = prmEmpMapper.queryPrmEmp(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmEmp> list = prmEmpMapper.queryPrmEmp(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 查询EmpByCode where emp_id
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmEmp queryPrmEmpByCode(Map<String, Object> entityMap) throws DataAccessException {

		return prmEmpMapper.queryPrmEmpByCode(entityMap);

	}

	/**
	 * @Description 查询EmpByCode where emp_code
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmEmp queryPrmEmpByCodeEmp(Map<String, Object> entityMap) throws DataAccessException {

		return prmEmpMapper.queryPrmEmpByCodeEmp(entityMap);

	}

	@Override
	public String updatePrmEmp(Map<String, Object> entityMap) throws DataAccessException {

		try {

			// 修改职工 非变更表
			prmEmpMapper.updatePrmEmp(entityMap);

			// 修改职工 变更表停用状态
			prmEmpDictMapper.updatePrmEmpDictState(entityMap);

			entityMap.put("emp_no", prmEmpDictMapper.queryPrmEmpDictSeqNextVal());

			// 新增职工 变更表记录
			prmEmpDictMapper.addPrmEmpDict(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}
	}

	@Override
	public String deleteBatchPrmEmp(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			prmEmpMapper.deleteBatchPrmEmp(entityList);

			for (Map<String, Object> map : entityList) {

				prmEmpDictMapper.updatePrmEmpDictState(map);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败 \"}");

		}
	}

	@Override
	public String addPrmEmp(Map<String, Object> entityMap) throws DataAccessException {
		try {

			PrmEmp prmEmp = prmEmpMapper.queryPrmEmpByCodeEmp(entityMap);

			if (prmEmp != null) {
				return "{\"error\":\"职工编码已存在 \"}";
			}

			int emp_id = prmEmpMapper.queryPrmEmpSeqNextVal();

			entityMap.put("dept_id", entityMap.get("dept_id"));
			entityMap.put("dept_no", entityMap.get("dept_no"));
			entityMap.put("emp_id", emp_id);
			/*
			 * entityMap.put("note", "新增"); entityMap.put("user_code",
			 * SessionManager.getUserCode()); entityMap.put("create_date",
			 * DateUtil.getCurrenDate("yyyy-MM-dd"));
			 */
			entityMap.put("is_stop", 0);
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("emp_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("emp_name").toString()));

			prmEmpMapper.addPrmEmp(entityMap);

			entityMap.put("emp_no", prmEmpDictMapper.queryPrmEmpDictSeqNextVal());
			prmEmpDictMapper.addPrmEmpDict(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("添加失败 数据库异常");
		}
	}

	@Override
	public String updateBatchPrmEmp(List<Map<String, Object>> entityList) throws DataAccessException {

		List<Map<String, Object>> listEmpforUpdate = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> listEmpDictforUpdate = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> listEmpDictforInsert = new ArrayList<Map<String, Object>>();

		try {
			List<Map<String, Object>> list = entityList;

			for (Map<String, Object> map : list) {

				Map<String, Object> mapEmpforUpdate = map;

				mapEmpforUpdate.put("is_stop", '0');

				listEmpforUpdate.add(mapEmpforUpdate);

				Map<String, Object> mapEmpDictforUpdate = new HashMap<String, Object>();

				mapEmpDictforUpdate.put("group_id", map.get("group_id"));

				mapEmpDictforUpdate.put("hos_id", map.get("hos_id"));

				mapEmpDictforUpdate.put("copy_code", map.get("copy_code"));

				mapEmpDictforUpdate.put("emp_id", map.get("emp_id"));

				mapEmpDictforUpdate.put("is_stop", '1');

				listEmpDictforUpdate.add(mapEmpDictforUpdate);

				Map<String, Object> mapEmpDictforInsert = map;

				mapEmpDictforInsert.put("is_stop", '0');

				listEmpDictforInsert.add(mapEmpDictforInsert);

			}

			prmEmpMapper.updateBatchPrmEmp(listEmpforUpdate);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 addPrmEmpKpiValue\"}";

		}
	}

	@Override
	public String initBatchPrmEmp(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String rbtnl = (String) entityMap.get("rbtnl");

			if ("all".equals(rbtnl)) {
				entityMap.put("is_zl", "0");
				prmEmpMapper.delete(entityMap);
			} else {
				entityMap.put("is_zl", "1");
			}

			prmEmpMapper.initBatchPrmEmp(entityMap);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmEmpKpiValue\"}";

		}
	}

	@Override
	public String importPrmEmp(Map<String, Object> map) throws DataAccessException {

		try {

			String columns = map.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);
			if (jsonColumns == null || jsonColumns.size() == 0) {
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}

			String content = map.get("content").toString();
			List<Map<String, List<String>>> allDataList = SpreadTableJSUtil.toListMap(content, 1);
			if (allDataList == null || allDataList.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}

			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());

			// 查询 职工字典 List
			List<PrmEmp> empList = prmEmpMapper.queryPrmEmpAll(entityMap);

			// 查询科室字典 List
			List<PrmDeptDict> deptList = prmDeptDictMapper.queryPrmDeptDict(entityMap);

			// 查询职务 List
			List<PrmEmpDuty> empDutyList = prmEmpDutyMapper.queryPrmEmpDuty(entityMap);

			// 用于存储查询targetList中的AphiTarget对象,以键值对的形式存储,用于判断指标是否存在
			Map<String, PrmEmp> empMap = new HashMap<String, PrmEmp>();

			// 用于存储查询deptList中的AphiDeptDict对象,以键值对的形式存储,用于判断科室是否存在
			Map<String, PrmDeptDict> deptMap = new HashMap<String, PrmDeptDict>();

			// 用于储存查询empDutyList中的AphiEmpDuty对象,以键值对的形式存储,用于判断职务是否存在
			Map<String, PrmEmpDuty> empDutyMap = new HashMap<String, PrmEmpDuty>();

			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();

			// 存储保存数据List
			List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
			
			// 存储保存数据List
			List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();

			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();

			// 将指标List存入Map 键:emp_code 值:AphiEmp
			for (PrmEmp emp : empList) {
				empMap.put(emp.getEmp_code(), emp);
			}

			// 将科室List存入Map 键:dept_name 值:AphiDeptDict
			for (PrmDeptDict dept : deptList) {
				deptMap.put(dept.getDept_name(), dept);
			}

			// 将职工List存入Map 键:duty_code 值:AphiEmpDuty
			for (PrmEmpDuty empDuty : empDutyList) {
				empDutyMap.put(empDuty.getDuty_name(), empDuty);
			}

			// 遍历导入数据
			for (Map<String, List<String>> item : allDataList) {

				List<String> emp_code = item.get("职工编码");
				List<String> emp_name = item.get("职工名称");
				List<String> dept_name = item.get("科室名称");
				List<String> duty_name = item.get("职务名称");
				List<String> is_stop = item.get("是否停用(0或1 0:否,1:是)");
				List<String> is_acc = item.get("是否奖金发放(0或1 0:否,1:是)");

				if (emp_code.get(1) == null && emp_name == null) {
					break;
				}

				if (emp_code.get(1) == null) {
					return "{\"warn\":\"职工编码为空！\",\"state\":\"false\",\"row_cell\":\"" + emp_code.get(0) + "\"}";
				} /*else {
					if (empMap.get(emp_code.get(1)) != null) {
						return "{\"warn\":\"" + emp_code.get(1) + ",职工编码已存在！\",\"state\":\"false\",\"row_cell\":\"" + emp_code.get(0) + "\"}";
					}
				}*/

				if (emp_name.get(1) == null) {
					return "{\"warn\":\"职工名称为空！\",\"state\":\"false\",\"row_cell\":\"" + emp_name.get(0) + "\"}";
				}

				if (dept_name.get(1) == null) {
					return "{\"warn\":\"科室名称为空！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
				} else {
					if (deptMap.get(dept_name.get(1)) == null) {
						return "{\"warn\":\"" + dept_name.get(1) + ",科室名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
					}
				}

				if (duty_name.get(1) == null) {
					return "{\"warn\":\"职务名称为空！\",\"state\":\"false\",\"row_cell\":\"" + duty_name.get(0) + "\"}";
				} else {
					if (empDutyMap.get(duty_name.get(1)) == null) {
						return "{\"warn\":\"" + duty_name.get(1) + ",职务名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + duty_name.get(0) + "\"}";
					}
				}

				if (is_stop.get(1) == null) {
					return "{\"warn\":\"是否停用为空！\",\"state\":\"false\",\"row_cell\":\"" + is_stop.get(0) + "\"}";
				}

				if (!"0".equals(is_stop.get(1)) && !"1".equals(is_stop.get(1))) {
					return "{\"warn\":\"是否停用只能输入0或1！\",\"state\":\"false\",\"row_cell\":\"" + is_stop.get(0) + "\"}";
				}

				if (is_acc.get(1) == null) {
					return "{\"warn\":\"是否奖金发放为空！\",\"state\":\"false\",\"row_cell\":\"" + is_acc.get(0) + "\"}";
				}

				if (!"0".equals(is_acc.get(1)) && !"1".equals(is_acc.get(1))) {
					return "{\"warn\":\"是否奖金发放只能输入0或1！\",\"state\":\"false\",\"row_cell\":\"" + is_acc.get(0) + "\"}";
				}

				// 以职工编码+职工名称+科室名称+职务名称为键值,判断导入数据是否重复
				String key = emp_code.get(1) + dept_name.get(1);
				if (exitMap.get(key) != null) {
					err_sb.append(emp_code.get(1) + "职工编码," + emp_name.get(1) + "职工名称," + dept_name.get(1) + "科室名称," + duty_name.get(1) + "职务名称 ");
				} else {
					exitMap.put(key, key);
				}
				
				// 添加数据Map
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("group_id", SessionManager.getGroupId());
				dataMap.put("hos_id", SessionManager.getHosId());
				dataMap.put("copy_code", SessionManager.getCopyCode());
				dataMap.put("emp_code", emp_code.get(1));
				dataMap.put("emp_name", emp_name.get(1));
				dataMap.put("dept_id", deptMap.get(dept_name.get(1)).getDept_id());
				dataMap.put("dept_no", deptMap.get(dept_name.get(1)).getDept_no());
				dataMap.put("duty_code", empDutyMap.get(duty_name.get(1)).getDuty_code());
				// addMap.put("target_name",
				// dictMap.get(target_code.get(1)).getTarget_name());
				dataMap.put("dept_code", deptMap.get(dept_name.get(1)).getDept_code());
				dataMap.put("spell_code", StringTool.toPinyinShouZiMu(emp_name.get(1).toString()));
				dataMap.put("wbx_code", StringTool.toWuBi(emp_name.get(1).toString()));
				dataMap.put("is_stop", is_stop.get(1));
				dataMap.put("is_acc", is_acc.get(1));
				
				if (empMap.get(emp_code.get(1)) == null) {
					addList.add(dataMap);
				}else{
					dataMap.put("emp_id", empMap.get(emp_code.get(1)).getEmp_id());
					updateList.add(dataMap);
				}
			}

			if (err_sb.length() > 0) {// 重复数据是否存在
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}
			
			if(updateList.size() > 0 ){
				for(Map<String,Object> updateMap : updateList){
					// 修改职工 非变更表
					prmEmpMapper.updatePrmEmp(updateMap);
					// 修改职工 变更表停用状态
					prmEmpDictMapper.updatePrmEmpDictState(updateMap);
					updateMap.put("emp_no", prmEmpDictMapper.queryPrmEmpDictSeqNextVal());
					// 新增职工 变更表记录
					prmEmpDictMapper.addPrmEmpDict(updateMap);
				}
			}
			
			if(addList.size() > 0 ){
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> addEmpMap : addList) {
					int emp_id = prmEmpMapper.queryPrmEmpSeqNextVal();
					addEmpMap.put("emp_id", emp_id);
					list.add(addEmpMap);
				}
				prmEmpMapper.addBatchPrmEmp(list);
				prmEmpDictMapper.addBatchPrmEmpDict(list);
			}
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}

	@Override
	public String queryPrmEmpDict(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmEmp> list = prmEmpMapper.queryPrmEmpDict(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmEmp> list = prmEmpMapper.queryPrmEmpDict(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
}
