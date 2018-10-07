package com.aoeai.tools.mybatis.bean.java;

public class Mapper {

    /**
     * 工程根路径的包名
     */
    private String rootPackageName;

    /**
     * Mapper包名
     */
    private String mapperPackageName;

    /**
     * 类名
     */
    private String className;

    /**
     * 类注释
     */
    private String classComment;

    /**
     * 实体类包名
     */
    private String entityPackageName;

    /**
     * 实体类Bean名称
     */
    private String entityBeanName;

    /**
     * 实体类变量名
     */
    private String entityBeanVarName;

    private String resultMapId;

    /**
     * 表名
     */
    private String tableName;

    /**
     * Mapper的变量名（例如 UserMapper userMapper，userMapper就是这个值）
     */
    private String varName;

    public String getMapperPackageName() {
        return mapperPackageName;
    }

    public Mapper setMapperPackageName(String mapperPackageName) {
        this.mapperPackageName = mapperPackageName;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public Mapper setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getClassComment() {
        return classComment;
    }

    public Mapper setClassComment(String classComment) {
        this.classComment = classComment;
        return this;
    }

    public String getEntityBeanName() {
        return entityBeanName;
    }

    public Mapper setEntityBeanName(String entityBeanName) {
        this.entityBeanName = entityBeanName;
        return this;
    }

    public String getEntityBeanVarName() {
        return entityBeanVarName;
    }

    public Mapper setEntityBeanVarName(String entityBeanVarName) {
        this.entityBeanVarName = entityBeanVarName;
        return this;
    }

    public String getResultMapId() {
        return resultMapId;
    }

    public Mapper setResultMapId(String resultMapId) {
        this.resultMapId = resultMapId;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public Mapper setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getRootPackageName() {
        return rootPackageName;
    }

    public Mapper setRootPackageName(String rootPackageName) {
        this.rootPackageName = rootPackageName;
        return this;
    }

    public String getEntityPackageName() {
        return entityPackageName;
    }

    public Mapper setEntityPackageName(String entityPackageName) {
        this.entityPackageName = entityPackageName;
        return this;
    }

    public String getVarName() {
        return varName;
    }

    public Mapper setVarName(String varName) {
        this.varName = varName;
        return this;
    }
}
