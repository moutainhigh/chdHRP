﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assremould;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assremould.AssRemouldAdetail;
import com.chd.hrp.ass.entity.resource.AssResourceSpecial;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REMOULD_A_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldAdetailMapper extends SqlMapper{

	List<AssResourceSpecial> queryByAssCardNo(Map<String, Object> detailVo);

	List<Map<String, Object>> queryAssRemouldADict(Map<String, Object> entityMap);


	List<AssRemouldAdetail> queryByDisANo(Map<String, Object> mapVo);
	
}
