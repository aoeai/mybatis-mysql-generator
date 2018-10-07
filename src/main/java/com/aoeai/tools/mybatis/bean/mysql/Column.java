package com.aoeai.tools.mybatis.bean.mysql;

/**
 * 列
 */
public class Column {

    /**
     * Sql字段名
     */
    private String sqlFieldName;

    /**
     * java字段名
     */
    private String javaFieldName;

    /**
     * Sql字段类型
     */
    private String sqlFieldType;

    /**
     * Java字段类型
     */
    private String javaFieldType;

    /**
     * 是否主键 true:是
     */
    private boolean isPrimaryKey;

    /**
     *
     */
    private String extra;

    /**
     * 允许为空 true:是
     */
    private boolean isNullable;

    /**
     * 注释
     */
    private String comment;

    public String getSqlFieldName() {
        return sqlFieldName;
    }

    public Column setSqlFieldName(String sqlFieldName) {
        this.sqlFieldName = sqlFieldName;
        return this;
    }

    public String getSqlFieldType() {
        return sqlFieldType;
    }

    public Column setSqlFieldType(String sqlFieldType) {
        this.sqlFieldType = sqlFieldType;
        return this;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public Column setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
        return this;
    }

    public String getExtra() {
        return extra;
    }

    public Column setExtra(String extra) {
        this.extra = extra;
        return this;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public Column setNullable(boolean nullable) {
        isNullable = nullable;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Column setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getJavaFieldName() {
        return javaFieldName;
    }

    public Column setJavaFieldName(String javaFieldName) {
        this.javaFieldName = javaFieldName;
        return this;
    }

    public String getJavaFieldType() {
        return javaFieldType;
    }

    public Column setJavaFieldType(String javaFieldType) {
        this.javaFieldType = javaFieldType;
        return this;
    }
}
