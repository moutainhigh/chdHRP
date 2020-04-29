/*
 *
 */package com.chd.hrp.htcg.service.info;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.info.HtcgDrugDict;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcgDrugDictService {
    
	public String addHtcgDrugDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgDrugDict(List<Map<String,Object>> list)throws DataAccessException;
	
	public String queryHtcgDrugDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcgDrugDict queryHtcgDrugDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcgDrugDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgDrugDict(List<Map<String,Object>> list)throws DataAccessException;
	
	public String updateHtcgDrugDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String importHtcgDrugDict(Map<String, Object> entityMap) throws DataAccessException;
	
}