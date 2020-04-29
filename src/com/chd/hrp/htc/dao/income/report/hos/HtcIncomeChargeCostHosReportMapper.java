package com.chd.hrp.htc.dao.income.report.hos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface HtcIncomeChargeCostHosReportMapper extends SqlMapper{
	
	
	 public List<Map<String,Object>> queryHtcIncomeChargeCostHosReport(Map<String,Object> entityMap)throws DataAccessException;
	    
	 public List<Map<String,Object>> queryHtcIncomeChargeCostHosReport(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
