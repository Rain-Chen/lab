package com.qianyan.lab.core.repo.bean;

public class SysEntityDef {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_entity_def.entity_def_id
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    private Integer entityDefId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_entity_def.entity_name
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    private String entityName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_entity_def.entity_level
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    private Integer entityLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_entity_def.primary_field
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    private String primaryField;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_entity_def.primary_seq_name
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    private String primarySeqName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_entity_def.entity_desc
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    private String entityDesc;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_entity_def.entity_def_id
     *
     * @return the value of sys_entity_def.entity_def_id
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    public Integer getEntityDefId() {
        return entityDefId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_entity_def.entity_def_id
     *
     * @param entityDefId the value for sys_entity_def.entity_def_id
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    public void setEntityDefId(Integer entityDefId) {
        this.entityDefId = entityDefId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_entity_def.entity_name
     *
     * @return the value of sys_entity_def.entity_name
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_entity_def.entity_name
     *
     * @param entityName the value for sys_entity_def.entity_name
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName == null ? null : entityName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_entity_def.entity_level
     *
     * @return the value of sys_entity_def.entity_level
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    public Integer getEntityLevel() {
        return entityLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_entity_def.entity_level
     *
     * @param entityLevel the value for sys_entity_def.entity_level
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    public void setEntityLevel(Integer entityLevel) {
        this.entityLevel = entityLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_entity_def.primary_field
     *
     * @return the value of sys_entity_def.primary_field
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    public String getPrimaryField() {
        return primaryField;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_entity_def.primary_field
     *
     * @param primaryField the value for sys_entity_def.primary_field
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    public void setPrimaryField(String primaryField) {
        this.primaryField = primaryField == null ? null : primaryField.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_entity_def.primary_seq_name
     *
     * @return the value of sys_entity_def.primary_seq_name
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    public String getPrimarySeqName() {
        return primarySeqName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_entity_def.primary_seq_name
     *
     * @param primarySeqName the value for sys_entity_def.primary_seq_name
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    public void setPrimarySeqName(String primarySeqName) {
        this.primarySeqName = primarySeqName == null ? null : primarySeqName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_entity_def.entity_desc
     *
     * @return the value of sys_entity_def.entity_desc
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    public String getEntityDesc() {
        return entityDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_entity_def.entity_desc
     *
     * @param entityDesc the value for sys_entity_def.entity_desc
     *
     * @mbggenerated Wed Jan 20 13:15:48 CST 2016
     */
    public void setEntityDesc(String entityDesc) {
        this.entityDesc = entityDesc == null ? null : entityDesc.trim();
    }
}