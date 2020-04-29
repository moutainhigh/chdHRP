package com.chd.hrp.mat.serviceImpl.warning;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.warning.MatSupCertWarningMapper;
import com.chd.hrp.mat.service.warning.MatInvCertWarningService;
import com.chd.hrp.mat.service.warning.MatSupCertWarningService;
import com.github.pagehelper.PageInfo;

@Service("matSupCertWarningService")
public class MatSupCertWarningServiceImpl implements MatSupCertWarningService {

	private static Logger logger = Logger.getLogger(MatSupCertWarningServiceImpl.class);

	@Resource(name = "matSupCertWarningMapper")
	private final MatSupCertWarningMapper matSupCertWarningMapper = null;

	// 查询材料效期预警
	@Override
	public String queryMatSupCertWarning(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		List<Map<String, Object>> warnTypes;
		
		List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
		
		if(entityMap.get("warn_type_code") == null || entityMap.get("warn_type_code").toString().equals("")){
			warnTypes = matSupCertWarningMapper.queryWarnType(entityMap);
			for(Map<String, Object> warnType : warnTypes){
				entityMap.put("warn_way", warnType.get("WARN_WAY").toString());
				if(warnType.get("WARN_WAY").toString().equals("2") || warnType.get("WARN_WAY").toString().equals("4"))
					entityMap.put("days", warnType.get("DAYS"));
				List<Map<String, Object>> partResult = matSupCertWarningMapper.queryMatSupCertWarnByDays(entityMap);
				for(Map<String, Object> oneResult : partResult)
					oneResult.put("warn_way", warnType.get("WARN_WAY").toString());
				result.addAll(partResult);
			}
			
			Set<String> duplicateSet = new HashSet<>();
			for(Iterator<Map<String, Object>> iterator = result.iterator(); iterator.hasNext(); ){
				Map<String, Object> tempMap = (Map<String, Object>)iterator.next();
				if(duplicateSet.contains(tempMap.get("cert_code").toString()))
					iterator.remove();
				else
					duplicateSet.add(tempMap.get("cert_code").toString());
			}
			
			return ChdJson.toJson(result);
		}
		
		warnTypes = matSupCertWarningMapper.queryWarnType(entityMap);
		Map<String, Object> warnType = warnTypes.get(0);
		entityMap.put("warn_way", warnType.get("WARN_WAY").toString());
		if(warnType.get("WARN_WAY").toString().equals("2") || warnType.get("WARN_WAY").toString().equals("4"))
			entityMap.put("days", warnType.get("DAYS"));
		result = matSupCertWarningMapper.queryMatSupCertWarnByDays(entityMap);
		for(Map<String, Object> oneResult : result)
			oneResult.put("warn_way", warnType.get("WARN_WAY").toString());
		return ChdJson.toJson(result);
//		if (sysPage.getTotal()==-1){
//		
//		List<Map<String,Object>> list = matSupCertWarningMapper.queryMatSupCertWarning(entityMap);
//		
//		 return ChdJson.toJson(list);
//		
//		 }else{
//		
//		 RowBounds rowBounds = new RowBounds(sysPage.getPage(),
//		 sysPage.getPagesize());
//		
//		 List<Map<String,Object>> list =matSupCertWarningMapper.queryMatSupCertWarning(entityMap, rowBounds);
//		
//		 PageInfo page = new PageInfo(list);
//		
//		 return ChdJson.toJson(list, page.getTotal());
//		
//		 }
	}

}
