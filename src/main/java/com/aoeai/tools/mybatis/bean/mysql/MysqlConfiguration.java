package com.aoeai.tools.mybatis.bean.mysql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Mysql配置信息
 */
@Service
public class MysqlConfiguration {

    @Value("${mmg.mysql.host}")
    private String host;

    @Value("${mmg.mysql.port}")
    private String port;

    @Value("${mmg.mysql.user}")
    private String user;

    @Value("${mmg.mysql.password}")
    private String password;

    @Value("${mmg.mysql.database}")
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
