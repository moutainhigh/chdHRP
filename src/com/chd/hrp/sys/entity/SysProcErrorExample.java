package com.chd.hrp.sys.entity;

import java.util.ArrayList;
import java.util.List;

public class SysProcErrorExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public SysProcErrorExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSqlIdIsNull() {
            addCriterion("SQL_ID is null");
            return (Criteria) this;
        }

        public Criteria andSqlIdIsNotNull() {
            addCriterion("SQL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSqlIdEqualTo(String value) {
            addCriterion("SQL_ID =", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdNotEqualTo(String value) {
            addCriterion("SQL_ID <>", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdGreaterThan(String value) {
            addCriterion("SQL_ID >", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdGreaterThanOrEqualTo(String value) {
            addCriterion("SQL_ID >=", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdLessThan(String value) {
            addCriterion("SQL_ID <", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdLessThanOrEqualTo(String value) {
            addCriterion("SQL_ID <=", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdLike(String value) {
            addCriterion("SQL_ID like", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdNotLike(String value) {
            addCriterion("SQL_ID not like", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdIn(List<String> values) {
            addCriterion("SQL_ID in", values, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdNotIn(List<String> values) {
            addCriterion("SQL_ID not in", values, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdBetween(String value1, String value2) {
            addCriterion("SQL_ID between", value1, value2, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdNotBetween(String value1, String value2) {
            addCriterion("SQL_ID not between", value1, value2, "sqlId");
            return (Criteria) this;
        }

        public Criteria andModCodeIsNull() {
            addCriterion("MOD_CODE is null");
            return (Criteria) this;
        }

        public Criteria andModCodeIsNotNull() {
            addCriterion("MOD_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andModCodeEqualTo(String value) {
            addCriterion("MOD_CODE =", value, "modCode");
            return (Criteria) this;
        }

        public Criteria andModCodeNotEqualTo(String value) {
            addCriterion("MOD_CODE <>", value, "modCode");
            return (Criteria) this;
        }

        public Criteria andModCodeGreaterThan(String value) {
            addCriterion("MOD_CODE >", value, "modCode");
            return (Criteria) this;
        }

        public Criteria andModCodeGreaterThanOrEqualTo(String value) {
            addCriterion("MOD_CODE >=", value, "modCode");
            return (Criteria) this;
        }

        public Criteria andModCodeLessThan(String value) {
            addCriterion("MOD_CODE <", value, "modCode");
            return (Criteria) this;
        }

        public Criteria andModCodeLessThanOrEqualTo(String value) {
            addCriterion("MOD_CODE <=", value, "modCode");
            return (Criteria) this;
        }

        public Criteria andModCodeLike(String value) {
            addCriterion("MOD_CODE like", value, "modCode");
            return (Criteria) this;
        }

        public Criteria andModCodeNotLike(String value) {
            addCriterion("MOD_CODE not like", value, "modCode");
            return (Criteria) this;
        }

        public Criteria andModCodeIn(List<String> values) {
            addCriterion("MOD_CODE in", values, "modCode");
            return (Criteria) this;
        }

        public Criteria andModCodeNotIn(List<String> values) {
            addCriterion("MOD_CODE not in", values, "modCode");
            return (Criteria) this;
        }

        public Criteria andModCodeBetween(String value1, String value2) {
            addCriterion("MOD_CODE between", value1, value2, "modCode");
            return (Criteria) this;
        }

        public Criteria andModCodeNotBetween(String value1, String value2) {
            addCriterion("MOD_CODE not between", value1, value2, "modCode");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("TYPE =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("TYPE <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("TYPE >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TYPE >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("TYPE <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("TYPE <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("TYPE like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("TYPE not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("TYPE in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("TYPE not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("TYPE not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            addCriterion("DESC is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            addCriterion("DESC is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(String value) {
            addCriterion("DESC =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(String value) {
            addCriterion("DESC <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThan(String value) {
            addCriterion("DESC >", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualTo(String value) {
            addCriterion("DESC >=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThan(String value) {
            addCriterion("DESC <", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualTo(String value) {
            addCriterion("DESC <=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLike(String value) {
            addCriterion("DESC like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotLike(String value) {
            addCriterion("DESC not like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescIn(List<String> values) {
            addCriterion("DESC in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(List<String> values) {
            addCriterion("DESC not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(String value1, String value2) {
            addCriterion("DESC between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(String value1, String value2) {
            addCriterion("DESC not between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNull() {
            addCriterion("FILE_PATH is null");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNotNull() {
            addCriterion("FILE_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andFilePathEqualTo(String value) {
            addCriterion("FILE_PATH =", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotEqualTo(String value) {
            addCriterion("FILE_PATH <>", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThan(String value) {
            addCriterion("FILE_PATH >", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_PATH >=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThan(String value) {
            addCriterion("FILE_PATH <", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThanOrEqualTo(String value) {
            addCriterion("FILE_PATH <=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLike(String value) {
            addCriterion("FILE_PATH like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotLike(String value) {
            addCriterion("FILE_PATH not like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathIn(List<String> values) {
            addCriterion("FILE_PATH in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotIn(List<String> values) {
            addCriterion("FILE_PATH not in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathBetween(String value1, String value2) {
            addCriterion("FILE_PATH between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotBetween(String value1, String value2) {
            addCriterion("FILE_PATH not between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andErrorIsNull() {
            addCriterion("ERROR is null");
            return (Criteria) this;
        }

        public Criteria andErrorIsNotNull() {
            addCriterion("ERROR is not null");
            return (Criteria) this;
        }

        public Criteria andErrorEqualTo(String value) {
            addCriterion("ERROR =", value, "error");
            return (Criteria) this;
        }

        public Criteria andErrorNotEqualTo(String value) {
            addCriterion("ERROR <>", value, "error");
            return (Criteria) this;
        }

        public Criteria andErrorGreaterThan(String value) {
            addCriterion("ERROR >", value, "error");
            return (Criteria) this;
        }

        public Criteria andErrorGreaterThanOrEqualTo(String value) {
            addCriterion("ERROR >=", value, "error");
            return (Criteria) this;
        }

        public Criteria andErrorLessThan(String value) {
            addCriterion("ERROR <", value, "error");
            return (Criteria) this;
        }

        public Criteria andErrorLessThanOrEqualTo(String value) {
            addCriterion("ERROR <=", value, "error");
            return (Criteria) this;
        }

        public Criteria andErrorLike(String value) {
            addCriterion("ERROR like", value, "error");
            return (Criteria) this;
        }

        public Criteria andErrorNotLike(String value) {
            addCriterion("ERROR not like", value, "error");
            return (Criteria) this;
        }

        public Criteria andErrorIn(List<String> values) {
            addCriterion("ERROR in", values, "error");
            return (Criteria) this;
        }

        public Criteria andErrorNotIn(List<String> values) {
            addCriterion("ERROR not in", values, "error");
            return (Criteria) this;
        }

        public Criteria andErrorBetween(String value1, String value2) {
            addCriterion("ERROR between", value1, value2, "error");
            return (Criteria) this;
        }

        public Criteria andErrorNotBetween(String value1, String value2) {
            addCriterion("ERROR not between", value1, value2, "error");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated do_not_delete_during_merge Wed Apr 11 15:54:39 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table HRP.SYS_PROC_ERROR
     *
     * @mbggenerated Wed Apr 11 15:54:39 CST 2018
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}