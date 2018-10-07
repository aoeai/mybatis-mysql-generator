package com.aoeai.tools.mybatis.bean.mysql;

/**
 * Mysql配置信息
 */
public class MysqlConfiguration {

    private String host = "localhost";
    private String port = "3306";
    private String user = "root";
    private String password;
    private String database;

    public String getHost() {
        return host;
    }

    public MysqlConfiguration setHost(String host) {
        this.host = host;
        return this;
    }

    public String getPort() {
        return port;
    }

    public MysqlConfiguration setPort(String port) {
        this.port = port;
        return this;
    }

    public String getUser() {
        return user;
    }

    public MysqlConfiguration setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MysqlConfiguration setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    public MysqlConfiguration setDatabase(String database) {
        this.database = database;
        return this;
    }

}
