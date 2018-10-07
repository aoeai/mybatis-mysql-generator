package com.aoeai.tools.mybatis;

import com.aoeai.tools.mybatis.bean.mysql.MysqlConfiguration;
import com.aoeai.tools.mybatis.service.*;
import org.testng.annotations.Test;

import java.io.IOException;


public class GeneratorTest {

    private EntityService entityService;

    private MapperService mapperService;

    private ServiceFileService serviceFileService;

    private ControllerService controllerService;

    private void init() throws IOException {
        MysqlConfiguration mysqlConfiguration = new MysqlConfiguration();
        mysqlConfiguration.setHost("localhost")
                .setPort("3306")
                .setDatabase("uid")
                .setUser("root")
                .setPassword("root");
        ConfigService.build(mysqlConfiguration,
                "com.aoeai.mybatismysqltest",
                "entity", "mapper",
                "/Users/aoe/github/mybatis-mysql-test/src/main/");

        FreemarkerService freemarkerService = new FreemarkerService();

        entityService = new EntityService();
        entityService.setFreemarkerService(freemarkerService);

        mapperService = new MapperService();
        mapperService.setFreemarkerService(freemarkerService, entityService);

        serviceFileService = new ServiceFileService(freemarkerService, mapperService);
        controllerService = new ControllerService(freemarkerService, serviceFileService, mapperService);
    }

    /**
     * 生成所有代码
     */
    @Test
    public void buildAllTest() throws Exception {
        init();
        // 生成Java实体类
        entityService.buildEntityJava();

        // 生成Mapper
        mapperService.buildMaperJavaFile();
        mapperService.buildMaperXmlFile();

        // 生成Service
        serviceFileService.buildServiceInterface();
        serviceFileService.buildServiceImpl();

        // 生成Controller
        controllerService.buildController();
    }

}
