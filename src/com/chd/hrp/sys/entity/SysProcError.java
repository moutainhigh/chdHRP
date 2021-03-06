package com.chd.hrp.sys.entity;

public class SysProcError {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HRP.SYS_PROC_ERROR.SQL_ID
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    private String sqlId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HRP.SYS_PROC_ERROR.MOD_CODE
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    private String modCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HRP.SYS_PROC_ERROR.TYPE
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HRP.SYS_PROC_ERROR.DESC
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    private String note;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HRP.SYS_PROC_ERROR.FILE_PATH
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    private String filePath;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HRP.SYS_PROC_ERROR.ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    private String error;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HRP.SYS_PROC_ERROR.SQL_ID
     *
     * @return the value of HRP.SYS_PROC_ERROR.SQL_ID
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public String getSqlId() {
        return sqlId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HRP.SYS_PROC_ERROR.SQL_ID
     *
     * @param sqlId the value for HRP.SYS_PROC_ERROR.SQL_ID
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HRP.SYS_PROC_ERROR.MOD_CODE
     *
     * @return the value of HRP.SYS_PROC_ERROR.MOD_CODE
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public String getModCode() {
        return modCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HRP.SYS_PROC_ERROR.MOD_CODE
     *
     * @param modCode the value for HRP.SYS_PROC_ERROR.MOD_CODE
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public void setModCode(String modCode) {
        this.modCode = modCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HRP.SYS_PROC_ERROR.TYPE
     *
     * @return the value of HRP.SYS_PROC_ERROR.TYPE
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HRP.SYS_PROC_ERROR.TYPE
     *
     * @param type the value for HRP.SYS_PROC_ERROR.TYPE
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HRP.SYS_PROC_ERROR.DESC
     *
     * @return the value of HRP.SYS_PROC_ERROR.DESC
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HRP.SYS_PROC_ERROR.FILE_PATH
     *
     * @return the value of HRP.SYS_PROC_ERROR.FILE_PATH
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public String getFilePath() {
        return filePath;
    }

    public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HRP.SYS_PROC_ERROR.FILE_PATH
     *
     * @param filePath the value for HRP.SYS_PROC_ERROR.FILE_PATH
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HRP.SYS_PROC_ERROR.ERROR
     *
     * @return the value of HRP.SYS_PROC_ERROR.ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public String getError() {
        return error;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HRP.SYS_PROC_ERROR.ERROR
     *
     * @param error the value for HRP.SYS_PROC_ERROR.ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public void setError(String error) {
        this.error = error;
    }
}