package com.aoeai.tools.mybatis.service;

import com.aoeai.tools.mybatis.bean.mysql.MysqlConfiguration;
import com.aoeai.tools.mybatis.bean.mysql.Table;
import com.aoeai.tools.mybatis.utils.MySqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

/**
 * 配置信息服务
 */
@Slf4j
@Service
public class ConfigService {

    private final static String CURRENT_WORKING_DIRECTORY = System.getProperty("user.dir");

    @Autowired
    private static MysqlConfiguration mysqlConfiguration;

    @Autowired
    private MySqlUtil mySqlUtil;

    /**
     * key:表名 value:表信息
     */
    private static Map<String, Table> TABLE_INFO_MAP;

    /**
     * 实体类的包名后缀
     */
    @Value("${mmg.config.entityPackageSuffix}")
    private static String entityPackageSuffix;

    /**
     * Mapper类的包名后缀
     */
    @Value("${mmg.config.mapperPackageSuffix}")
    private static String mapperPackageSuffix;

    /**
     * 生成Java文件时需要过滤掉的表名前缀
     */
    private static String[] FILTER_TABLE_PREFIX;

    /**
     * 生成文件的主文件夹路径
     */
    @Value("${mmg.config.generatorRootPath}")
    private static String generatorRootPath;

    /**
     * 工程根路径的包名
     */
    @Value("${mmg.config.rootPackageName}")
    private static String rootPackageName;

    /**
     * 方法名前缀-保存
     */
    private static String METHOD_SAVE_PREFIX = "save";

    /**
     * 方法名前缀-查询
     */
    private static String METHOD_SELECT_PREFIX = "select";

   @PostConstruct
    private void init() {
        TABLE_INFO_MAP = mySqlUtil.getDatabaseInfo();
        generatorRootPath = StringUtils.isBlank(generatorRootPath)
                ? CURRENT_WORKING_DIRECTORY + "/target/build/" : generatorRootPath;

        log.info("代码生成根目录 " + ConfigService.generatorRootPath);
    }

    public static String getCurrentWorkingDirectory() {
        return CURRENT_WORKING_DIRECTORY;
    }

    public static MysqlConfiguration getMysqlConfiguration() {
        return mysqlConfiguration;
    }

    public static String getEntityPackageSuffix() {
        return entityPackageSuffix;
    }

    public static String getMapperPackageSuffix() {
        return mapperPackageSuffix;
    }

    public static String[] getFilterTablePrefix() {
        return FILTER_TABLE_PREFIX;
    }

    public static String getGeneratorRootPath() {
        return generatorRootPath;
    }

    public static String getRootPackageName() {
        return rootPackageName;
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
