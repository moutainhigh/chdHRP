/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.dao.storage.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 库存分布查询 
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedStockRoutingMapper extends SqlMapper{


	/**
	 * @Description 
	 * 库存分布查询 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedStorageQueryStockRouting(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 库存分布查询 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedStorageQueryStockRouting(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	


}
