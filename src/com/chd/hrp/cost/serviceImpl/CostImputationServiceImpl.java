/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostImputationMapper;
import com.chd.hrp.cost.entity.CostImputation;
import com.chd.hrp.cost.service.CostImputationService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 科室材料支出明细表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costImputationService")
public class CostImputationServiceImpl implements CostImputationService {

	private static Logger logger = Logger.getLogger(CostImputationServiceImpl.class);

	@Resource(name = "costImputationMapper")
	private final CostImputationMapper costImputationMapper = null;

	/**
	 * @Description 科室材料归集<BR>
	 *              查询queryCostImputation分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostImputation(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostImputation> list = costImputationMapper.queryCostImputation(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<CostImputation> list = costImputationMapper.queryCostImputation(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

}
