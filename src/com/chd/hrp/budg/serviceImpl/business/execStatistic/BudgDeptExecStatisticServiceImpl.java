/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.execStatistic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.business.execStatistic.BudgDeptExecStatisticMapper;
import com.chd.hrp.budg.entity.BudgWorkHosMonth;
import com.chd.hrp.budg.service.business.execStatistic.BudgDeptExecStatisticService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室月份业务预算
 * @Table:
 * BUDG_WORK_HOS_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("budgDeptExecStatisticService")
public class BudgDeptExecStatisticServiceImpl implements BudgDeptExecStatisticService {

	private static Logger logger = Logger.getLogger(BudgDeptExecStatisticServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgDeptExecStatisticMapper")
	private final BudgDeptExecStatisticMapper budgDeptExecStatisticMapper = null;
    
	/**
	 * @Description 
	 * 查询结果集科室月份业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgDeptExecStatisticMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgDeptExecStatisticMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
}
