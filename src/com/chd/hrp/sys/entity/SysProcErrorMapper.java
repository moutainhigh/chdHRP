package com.chd.hrp.sys.entity;

import com.chd.hrp.sys.entity.SysProcError;
import com.chd.hrp.sys.entity.SysProcErrorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysProcErrorMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    int countByExample(SysProcErrorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    int deleteByExample(SysProcErrorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    int deleteByPrimaryKey(String sqlId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    int insert(SysProcError record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    int insertSelective(SysProcError record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    List<SysProcError> selectByExample(SysProcErrorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    SysProcError selectByPrimaryKey(String sqlId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    int updateByExampleSelective(@Param("record") SysProcError record, @Param("example") SysProcErrorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    int updateByExample(@Param("record") SysProcError record, @Param("example") SysProcErrorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    int updateByPrimaryKeySelective(SysProcError record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    int updateByPrimaryKey(SysProcError record);
}