﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.storage.query;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Description:
 * 入库明细查询
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface MedSupInStoreService{


	String queryMedSupInStore(Map<String, Object> mapVo);
	

}
