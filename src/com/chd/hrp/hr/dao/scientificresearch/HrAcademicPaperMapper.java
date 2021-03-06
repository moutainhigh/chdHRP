package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicPaper;
/**
 * 学术论文发表
 * @author Administrator
 *
 */
public interface HrAcademicPaperMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrAcademicPaper> queryAcademicPaperById(Map<String, Object> entityMap);
    /**
     * 删除学术论文发表
     * @param entityList
     */
	void deleteAcademicPaper(List<HrAcademicPaper> entityList);
	

}
