package com.chd.hrp.ass.serviceImpl.assremould.land;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.assremould.land.AssRemouldAdetailLandMapper;
import com.chd.hrp.ass.dao.assremould.land.AssRemouldAsourceLandMapper;
import com.chd.hrp.ass.dao.card.AssCardLandMapper;
import com.chd.hrp.ass.dao.resource.AssResourceLandMapper;
import com.chd.hrp.ass.entity.assremould.AssRemouldAspecial;
import com.chd.hrp.ass.entity.assremould.land.AssRemouldAdetailLand;
import com.chd.hrp.ass.entity.change.AssChangeLand;
import com.chd.hrp.ass.service.assremould.land.AssRemouldADetailLandService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.serviceImpl.assremould.house.AssRemouldADetailHouseServiceImpl;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050805 资产改造记录(一般设备)
 * @Table:
 * ASS_REMOULD_R_Land
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 @Service("assRemouldADetailLandService")
public class AssRemouldADetailLandServiceImpl  implements AssRemouldADetailLandService{
	 private static Logger logger = Logger.getLogger(AssRemouldADetailLandServiceImpl.class);
	 //引入DAO操作
		@Resource(name = "assRemouldAdetailLandMapper")
		private final AssRemouldAdetailLandMapper assRemouldAdetailLandMapper = null;
		@Resource(name = "assRemouldAsourceLandMapper")
		private final AssRemouldAsourceLandMapper assRemouldAsourceLandMapper = null;
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			
			
			assRemouldAsourceLandMapper.deleteBatch(entityList);
			assRemouldAdetailLandMapper.deleteBatch(entityList);
			
		
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			
		}	
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssRemouldAspecial> list = (List<AssRemouldAspecial>)assRemouldAdetailLandMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssRemouldAspecial> list = (List<AssRemouldAspecial>)assRemouldAdetailLandMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AssRemouldAdetailLand> queryByDisANo(Map<String, Object> mapVo) {
	
		return assRemouldAdetailLandMapper.queryByDisANo(mapVo);
	}}
