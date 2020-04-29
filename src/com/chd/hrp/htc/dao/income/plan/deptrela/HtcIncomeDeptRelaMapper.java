package com.chd.hrp.htc.dao.income.plan.deptrela;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.income.plan.deptrela.HtcIncomeDeptRela;
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

public interface HtcIncomeDeptRelaMapper extends SqlMapper{
	
	public int addHtcIncomeDeptRela(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcIncomeDeptRela> queryHtcIncomeDeptRela(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcIncomeDeptRela> queryHtcIncomeDeptRela(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
    public HtcIncomeDeptRela queryHtcIncomeDeptRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
    
	public int deleteBatchHtcIncomeDeptRela(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateHtcIncomeDeptRela(Map<String,Object> entityMap)throws DataAccessException;
}
