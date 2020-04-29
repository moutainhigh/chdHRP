package com.chd.hrp.htc.dao.task.business;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.business.HtcSrouceTest;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcSrouceTestMapper extends SqlMapper{

	public List<HtcSrouceTest> queryHtcSrouceTest(Map<String, Object> entityMap) throws DataAccessException;
	public List<HtcSrouceTest> queryHtcSrouceTest(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public Map<String, Object> checkHtcSrouceTest(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public List<HtcSrouceTest> queryHtcSrouceTestCostItem(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HtcSrouceTest> queryHtcSrouceTestCostItem(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public List<HtcSrouceTest> queryHtcSrouceTestCostItemSource(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HtcSrouceTest> queryHtcSrouceTestCostItemSource(Map<String, Object> entityMap) throws DataAccessException;

}
