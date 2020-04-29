/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.dao.vouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.entity.AccVouchWei;

/**
 * @Title. @Description. 凭证分册<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AccVouchWeiMapper extends SqlMapper {

	/**
	 * @Description 凭证分册<BR>
	 *              添加AccVouchWei
	 * @param AccVouchWei
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addAccVouchWei(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证分册<BR>
	 *              修改AccVouchWei
	 * @param AccVouchWei
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAccVouchWei(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证分册<BR>
	 *              修改AccVouchWei
	 * @param AccVouchWei
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteAccVouchWei(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证分册<BR>
	 *              查询AccVouchWei分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouchWei>
	 * @throws DataAccessException
	 */
	public List<AccVouchWei> queryAccVouchWei(Map<String, Object> entityMap) throws DataAccessException;

	public AccVouchWei queryAccVouchWeiByCode(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccSubjByVouchWei(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccSubjByVouchWei(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccSubjByVouchBT(Map<String, Object> entityMap) throws DataAccessException;

}
