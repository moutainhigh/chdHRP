package com.chd.hrp.hr.entity.msg;

import java.util.Date;
import java.math.BigDecimal;

public class SysMsg {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SYS_MSG.ID
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SYS_MSG.TITLE
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	private String title;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SYS_MSG.BODY
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	private String body;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SYS_MSG.CREATIME
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	private Date creatime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SYS_MSG.MSGTYPE
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	private Short msgtype;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SYS_MSG.ROLEIDS
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	private String roleids;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SYS_MSG.USERIDS
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	private String userids;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SYS_MSG.GROUP_ID
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	private BigDecimal groupId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column SYS_MSG.HOS_ID
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	private BigDecimal hosId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SYS_MSG.ID
	 * @return  the value of SYS_MSG.ID
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SYS_MSG.ID
	 * @param id  the value for SYS_MSG.ID
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SYS_MSG.TITLE
	 * @return  the value of SYS_MSG.TITLE
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SYS_MSG.TITLE
	 * @param title  the value for SYS_MSG.TITLE
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SYS_MSG.BODY
	 * @return  the value of SYS_MSG.BODY
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public String getBody() {
		return body;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SYS_MSG.BODY
	 * @param body  the value for SYS_MSG.BODY
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SYS_MSG.CREATIME
	 * @return  the value of SYS_MSG.CREATIME
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public Date getCreatime() {
		return creatime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SYS_MSG.CREATIME
	 * @param creatime  the value for SYS_MSG.CREATIME
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SYS_MSG.MSGTYPE
	 * @return  the value of SYS_MSG.MSGTYPE
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public Short getMsgtype() {
		return msgtype;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SYS_MSG.MSGTYPE
	 * @param msgtype  the value for SYS_MSG.MSGTYPE
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public void setMsgtype(Short msgtype) {
		this.msgtype = msgtype;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SYS_MSG.ROLEIDS
	 * @return  the value of SYS_MSG.ROLEIDS
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public String getRoleids() {
		return roleids;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SYS_MSG.ROLEIDS
	 * @param roleids  the value for SYS_MSG.ROLEIDS
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public void setRoleids(String roleids) {
		this.roleids = roleids;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SYS_MSG.USERIDS
	 * @return  the value of SYS_MSG.USERIDS
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public String getUserids() {
		return userids;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SYS_MSG.USERIDS
	 * @param userids  the value for SYS_MSG.USERIDS
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public void setUserids(String userids) {
		this.userids = userids;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SYS_MSG.GROUP_ID
	 * @return  the value of SYS_MSG.GROUP_ID
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public BigDecimal getGroupId() {
		return groupId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SYS_MSG.GROUP_ID
	 * @param groupId  the value for SYS_MSG.GROUP_ID
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column SYS_MSG.HOS_ID
	 * @return  the value of SYS_MSG.HOS_ID
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public BigDecimal getHosId() {
		return hosId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column SYS_MSG.HOS_ID
	 * @param hosId  the value for SYS_MSG.HOS_ID
	 * @mbggenerated  Thu Jan 03 15:17:25 CST 2019
	 */
	public void setHosId(BigDecimal hosId) {
		this.hosId = hosId;
	}
}