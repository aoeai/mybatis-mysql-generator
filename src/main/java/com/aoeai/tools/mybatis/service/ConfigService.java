package com.aoeai.tools.mybatis.service;

import com.aoeai.tools.mybatis.bean.mysql.MysqlConfiguration;
import com.aoeai.tools.mybatis.bean.mysql.Table;
import com.aoeai.tools.mybatis.utils.MySqlUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 配置信息服务
 */
public class ConfigService {

    private final static String CURRENT_WORKING_DIRECTORY = System.getProperty("user.dir");

    private static MysqlConfiguration MYSQL_CONFIG;

    /**
     * key:表名 value:表信息
     */
    private static Map<String, Table> TABLE_INFO_MAP;

    /**
     * 实体类的包名后缀
     */
    private static String ENTITY_PACKAGE_SUFFIX;

    /**
     * Mapper类的包名后缀
     */
    private static String MAPPER_PACKAGE_SUFFIX;

    /**
     * 生成Java文件时需要过滤掉的表名前缀
     */
    private static String[] FILTER_TABLE_PREFIX;

    /**
     * 生成文件的主文件夹路径
     */
    private static String BUILD_PATH;

    /**
     * 工程根路径的包名
     */
    private static String ROOT_PACKAGE_NAME;

    /**
     * 方法名前缀-保存
     */
    private static String METHOD_SAVE_PREFIX = "save";

    /**
     * 方法名前缀-查询
     */
    private static String METHOD_SELECT_PREFIX = "select";

    private ConfigService() {

    }

    /**
     * @param mysqlConfiguration
     * @param packageName
     * @param entitySuffix
     * @param mapperSuffix
     * @param generatorRootPath
     * @return
     * @throws IOException
     */
    public static ConfigService build(MysqlConfiguration mysqlConfiguration, String packageName,
                                      String entitySuffix, String mapperSuffix, String generatorRootPath) throws IOException {
        ConfigService configService = new ConfigService();
        MYSQL_CONFIG = mysqlConfiguration;
        TABLE_INFO_MAP = MySqlUtil.getDatabaseInfo(MYSQL_CONFIG);
        BUILD_PATH = StringUtils.isBlank(generatorRootPath) ? CURRENT_WORKING_DIRECTORY + "/target/build/" : generatorRootPath;
        ROOT_PACKAGE_NAME = packageName;
        ENTITY_PACKAGE_SUFFIX = entitySuffix;
        MAPPER_PACKAGE_SUFFIX = mapperSuffix;

        System.out.println("代码生成根目录 " + BUILD_PATH);

        return configService;
    }

    public static String getCurrentWorkingDirectory() {
        return CURRENT_WORKING_DIRECTORY;
    }

    public static MysqlConfiguration getMysqlConfig() {
        return MYSQL_CONFIG;
    }

    public static String getEntityPackageSuffix() {
        return ENTITY_PACKAGE_SUFFIX;
    }

    public static String getMapperPackageSuffix() {
        return MAPPER_PACKAGE_SUFFIX;
    }

    public static String[] getFilterTablePrefix() {
        return FILTER_TABLE_PREFIX;
    }

    public static String getBuildPath() {
        return BUILD_PATH;
    }

    public static String getRootPackageName() {
        return ROOT_PACKAGE_NAME;
    }

    public static Map<String, Table> getTableInfoMap() {
        return TABLE_INFO_MAP;
    }

    public static String getMethodSavePrefix() {
        return METHOD_SAVE_PREFIX;
    }

    public static String getMethodSelectPrefix() {
        return METHOD_SELECT_PREFIX;
    }

    public static void setMethodSavePrefix(String methodSavePrefix) {
        METHOD_SAVE_PREFIX = methodSavePrefix;
    }

    public static void setMethodSelectPrefix(String methodSelectPrefix) {
        METHOD_SELECT_PREFIX = methodSelectPrefix;
    }


}
