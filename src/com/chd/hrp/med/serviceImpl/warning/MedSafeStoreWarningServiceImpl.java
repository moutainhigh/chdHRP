package com.chd.hrp.med.serviceImpl.warning;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.warning.MedSafeStoreWarningMapper;
import com.chd.hrp.med.service.warning.MedSafeStoreWarningService;
import com.github.pagehelper.PageInfo;

@Service("medSafeStoreWarningService")
public class MedSafeStoreWarningServiceImpl implements MedSafeStoreWarningService {

	private static Logger logger = Logger.getLogger(MedSafeStoreWarningServiceImpl.class);

	@Resource(name = "medSafeStoreWarningMapper")
	private final MedSafeStoreWarningMapper medSafeStoreWarningMapper = null;

	// 查询安全库存预警
	@Override
	public String queryMedSafeStoreWarning(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
		
		 sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
		
		List<Map<String,Object>> list = medSafeStoreWarningMapper.queryMedSafeStoreWarning(entityMap);
		
		 return ChdJson.toJson(list);
		
		 }else{
		
		 RowBounds rowBounds = new RowBounds(sysPage.getPage(),
		 sysPage.getPagesize());
		
		 List<Map<String,Object>> list =
				 medSafeStoreWarningMapper.queryMedSafeStoreWarning(entityMap, rowBounds);
		
		 PageInfo page = new PageInfo(list);
		
		 return ChdJson.toJson(list, page.getTotal());
		
		 }
	}
	//超高限预警 查询
	@Override
	public String queryMedHighStoreWarning(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		 sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
		
		List<Map<String,Object>> list = medSafeStoreWarningMapper.queryMedHighStoreWarning(entityMap);
		
		 return ChdJson.toJson(list);
		
		 }else{
		
		 RowBounds rowBounds = new RowBounds(sysPage.getPage(),
		 sysPage.getPagesize());
		
		 List<Map<String,Object>> list = medSafeStoreWarningMapper.queryMedHighStoreWarning(entityMap, rowBounds);
		
		 PageInfo page = new PageInfo(list);
		
		 return ChdJson.toJson(list, page.getTotal());
		
		 }
	}
	
	//短缺货预警 查询
	@Override
	public String queryMedShortStoreWarning(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		 sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
		
		List<Map<String,Object>> list = medSafeStoreWarningMapper.queryMedShortStoreWarning(entityMap);
		
		 return ChdJson.toJson(list);
		
		 }else{
		
		 RowBounds rowBounds = new RowBounds(sysPage.getPage(),
		 sysPage.getPagesize());
		
		 List<Map<String,Object>> list = medSafeStoreWarningMapper.queryMedShortStoreWarning(entityMap, rowBounds);
		
		 PageInfo page = new PageInfo(list);
		
		 return ChdJson.toJson(list, page.getTotal());
		
		 }
	}

}
