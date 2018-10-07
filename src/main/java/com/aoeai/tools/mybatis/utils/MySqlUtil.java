package com.aoeai.tools.mybatis.utils;

import com.aoeai.tools.mybatis.bean.mysql.Column;
import com.aoeai.tools.mybatis.bean.mysql.MysqlConfiguration;
import com.aoeai.tools.mybatis.bean.mysql.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MySqlUtil {

    /**
     * 获取数据库信息
     * @param config
     * @return key:表名 value:表信息
     */
    public static Map<String, Table> getDatabaseInfo(MysqlConfiguration config) {
        String sql = "select table_name , column_name ,  column_type , column_key , extra , is_nullable ,column_comment, "
                + "( select tables.table_comment from tables where tables.table_name = columns.table_name and tables.table_schema = '"
                + config.getDatabase()
                + "') as table_comment"
                + " from columns where table_schema='"
                + config.getDatabase() + "' order by table_name";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Map<String, Table> tablesMap = new LinkedHashMap<>();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://"
                            + config.getHost() + ":" + config.getPort()
                            + "/information_schema", config.getUser(),
                    config.getPassword());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String tableName = resultSet.getString("table_name");

                Table table = tablesMap.get(tableName);

                if (table == null) {
                    table = new Table();
                    table.setName(tableName);
                    table.setComment(resultSet
                            .getString("table_comment"));
                    table.setColumns(new ArrayList<>());
                }

                Column column = new Column();
                column.setSqlFieldName(resultSet.getString("column_name"));
                column.setSqlFieldType(resultSet.getString("column_type"));
                column.setPrimaryKey(resultSet.getString("column_key").equals("PRI"));
                column.setExtra(resultSet.getString("extra"));
                column.setNullable("YES".equals(resultSet.getString("is_nullable")));
                column.setComment(resultSet.getString("column_comment"));
                table.getColumns().add(column);

                tablesMap.put(tableName, table);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace(System.err);
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }

                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }

                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return tablesMap;
    }
}
