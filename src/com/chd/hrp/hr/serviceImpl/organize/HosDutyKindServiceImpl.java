package com.chd.hrp.hr.serviceImpl.organize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.organize.HosDutyKindMapper;
import com.chd.hrp.hr.entity.orangnize.HosDutyLevel;
import com.chd.hrp.hr.entity.orangnize.HosDutyKind;
import com.chd.hrp.hr.service.organize.HosDutyKindService;
import com.github.pagehelper.PageInfo;

@Service("hosDutyKindService")
public class HosDutyKindServiceImpl implements HosDutyKindService {

	private static Logger logger = Logger.getLogger(HosDutyKindServiceImpl.class);

	@Resource(name = "hosDutyKindMapper")
	private final HosDutyKindMapper hosDutyKindMapper = null;

	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 增加岗位分类
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		//获取对象职务等级
		List<HosDutyKind> list = (List<HosDutyKind>)hosDutyKindMapper.queryStationKindByCode(entityMap);

		if (list.size() > 0) {
			for(HosDutyKind kind : list){
				if (kind.getKind_code().equals(entityMap.get("kind_code"))) {
					return "{\"error\":\"编码：" + kind.getKind_code().toString() + "已存在.\"}";
				}
				if (kind.getKind_name().equals(entityMap.get("kind_name"))) {
					return "{\"error\":\"名称：" + kind.getKind_name().toString() + "已存在.\"}";
				}
			}
		}
			
		try {
			hosDutyKindMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	@Override
	public HosDutyKind queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hosDutyKindMapper.queryByCode(entityMap);
	}

	/**
	 * 删除
	 */
	@Override
	public String deleteBatchStationKind(List<HosDutyKind> entityList) throws DataAccessException {
		try {
			hosDutyKindMapper.deleteBatchHrStationKind(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 修改保存
	 */
	@Override
	public String updateHrStationKind(Map<String, Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			if ("1".equals(entityMap.get("is_flag"))) {
				List<HosDutyKind> list = (List<HosDutyKind>) hosDutyKindMapper.queryStationKindByName(entityMap);
				if (list.size() > 0) {
					for (HosDutyKind kind : list) {
						if (kind.getKind_name().equals(entityMap.get("kind_name"))) {
							return "{\"error\":\"名称：" + kind.getKind_name().toString() + "已存在.\"}";
						}
					}
				}
			}

			hosDutyKindMapper.update(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 查询所有岗位
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryHrStationKind(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HosDutyKind> list = (List<HosDutyKind>) hosDutyKindMapper.query(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HosDutyKind> list = (List<HosDutyKind>) hosDutyKindMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryHosEmpKindTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HosDutyKind> storeTypeList = (List<HosDutyKind>) hosDutyKindMapper.query(entityMap);
		for (HosDutyKind storeType : storeTypeList) {
			treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
		}
		treeJson.append("]");
		return treeJson.toString();
	}
	
	/**
	 * 导入数据
	 */
	@Override
	public String importDate(Map<String, Object> entityMap) throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		/**
		 * 职务类别
		 */
		Map<String, Object> kindMap = new HashMap<String, Object>();
		List<Map<String,Object>> kindList = hosDutyKindMapper.queryByCodeKind(entityMap);
		for(Map<String,Object> kind : kindList){
			kindMap.put(kind.get("id").toString(), kind.get("id").toString());
			kindMap.put(kind.get("text").toString(), kind.get("id").toString());
		}
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					
					//过滤空行
					if("".equals(map.get("kind_code").get(1)) || "".equals(map.get("kind_name").get(1))){
						continue;
					}
					
					if("null".equals(map.get("kind_code").get(1)) || "null".equals(map.get("kind_name").get(1))){
						continue;
					}
					
					saveMap.put("kind_code", map.get("kind_code").get(1));
					saveMap.put("kind_name", map.get("kind_name").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("kind_name").get(1)));
					saveMap.put("wbx_code", StringTool.toWuBi(map.get("kind_name").get(1)));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					if("null".equals(map.get("note").get(1))){
						saveMap.put("note", null);
					}else{
						saveMap.put("note", map.get("note").get(1));
					}
					
					if(!"".equals(kindMap.get(saveMap.get("kind_code"))) && kindMap.get(saveMap.get("kind_code")) != null){
						failureMsg.append("<br/>职务类别编码: "+saveMap.get("kind_code")+" 已存在; ");
						failureNum++;
						continue; 
					}
					
					if(!"".equals(kindMap.get(saveMap.get("kind_name").toString())) && kindMap.get(saveMap.get("kind_name")) != null){
						failureMsg.append("<br/>职务类别名称: "+saveMap.get("kind_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					
					/*HosDutyKind type = hosDutyKindMapper.queryByCode(saveMap);
					if(type != null){
						failureMsg.append("<br/>类别代码 "+map.get("kind_code").get(1)+" 已存在; ");
						failureNum++;
						continue;
					}*/
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hosDutyKindMapper.addBatch(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

	@Override
	public List<HosDutyKind> queryListDutyKind(List<HosDutyKind> listVo) {
		return hosDutyKindMapper.queryListDutyKind(listVo);
	}

	@Override
	public List<Map<String, Object>> queryDutyKindByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		return  hosDutyKindMapper.queryDutyKindByPrint(entityMap);
	}

}
