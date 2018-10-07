package com.aoeai.tools.mybatis.bean.mysql;

import java.util.List;

/**
 * 数据库表
 */
public class Table {

	/**
	 * 表名
	 */
	private String name;

	/**
	 * 表注释
	 */
	private String comment;

	/**
	 * 类名
	 */
	private String className;

	/**
	 * 所有列集合
	 */
	private List<Column> columns;

	/**
	 * 主键列集合
	 */
	private List<Column> primaryKeyColumns;

	/**
	 * update语句可更新列集合
	 */
	private List<Column> updateColumns;

	/**
	 * 实体类包名
	 */
	private String entityPackageName;

	/**
	 * 实体类类名（例如User，只包含类名）
	 */
	private String entityClassName;

	/**
	 * 实体类的绝对路径
	 */
	private String filePath;

	public String getName() {
		return name;
	}

	public Table setName(String name) {
		this.name = name;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public Table setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public String getClassName() {
		return className;
	}

	public Table setClassName(String className) {
		this.className = className;
		return this;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public Table setColumns(List<Column> columns) {
		this.columns = columns;
		return this;
	}

	public List<Column> getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}

	public Table setPrimaryKeyColumns(List<Column> primaryKeyColumns) {
		this.primaryKeyColumns = primaryKeyColumns;
		return this;
	}

	public List<Column> getUpdateColumns() {
		return updateColumns;
	}

	public Table setUpdateColumns(List<Column> updateColumns) {
		this.updateColumns = updateColumns;
		return this;
	}

	public String getEntityPackageName() {
		return entityPackageName;
	}

	public Table setEntityPackageName(String entityPackageName) {
		this.entityPackageName = entityPackageName;
		return this;
	}

	public String getFilePath() {
		return filePath;
	}

	public Table setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public Table setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
		return this;
	}
}
