/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.storage.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 入库明细查询
 * @Table:
 *  
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatInSupCountToFimMapper extends SqlMapper{
 
	/**
	 * @Description 
	 * 供应商采购汇总查询
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatInSupCount(Map<String, Object> entityMap);
	/**
	 * 查询供应商发生业务的 财务分类类型
	 * @param mapVo 
	 * @return
	 */
	public List<Map<String,Object>> queryOccurMatFimTypeDict(Map<String, Object> mapVo);
	
	

	/**
	 * @Description 
	 * 供应商单据数
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatInSupCountAmount(Map<String, Object> entityMap);

}
