﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.tran.dept;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.tran.dept.AssTranDeptDetailInassets;
/**
 * 
 * @Description:
 * 050901 资产转移明细(科室到科室)_无形资产
 * @Table:
 * ASS_TRAN_DEPT_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssTranDeptDetailInassetsMapper extends SqlMapper{
	List<AssTranDeptDetailInassets> queryByTranNo(Map<String,Object> entityMap)throws DataAccessException;
}
