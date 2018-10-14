package com.aoeai.tools.mybatis;

import com.aoeai.tools.mybatis.service.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneratorTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private JavaBeanService javaBeanService;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private ServiceFileService serviceFileService;

    @Autowired
    private ControllerService controllerService;

    @Autowired
    private AddService addService;

    @BeforeClass
    private void init() {
        // 自定义：保存时数据初始化
        List<String> saveDataInitList = new ArrayList<>();
        saveDataInitList.add("setCreated(date);");
        saveDataInitList.add("setLaunchDate(date);");
        saveDataInitList.add("setModified(date);");
        serviceFileService.setSaveDataInitList(saveDataInitList);

        // 自定义：更新时数据初始化
        List<String> updateDataInitList = new ArrayList<>();
        updateDataInitList.add("setCreated(null);");
        updateDataInitList.add("setModified(new Date());");
        serviceFileService.setUpdateDataInitList(updateDataInitList);
    }

    /**
     * 生成所有代码
     */
    @Test
    public void buildAllTest() throws Exception {
        // 生成Java实体类
        javaBeanService.buildEntity();

        // 生成Mapper
        mapperService.buildMaperJavaFile();
        mapperService.buildMaperXmlFile();

        // 生成Service
        serviceFileService.buildServiceInterface();
        serviceFileService.buildServiceImpl();

        // 生成Controller
        controllerService.buildController();
    }

    @Test
    public void buildMapperTest() throws Exception{
        // 生成Mapper
        mapperService.buildMaperJavaFile();
        mapperService.buildMaperXmlFile();

        // 生成Service
        serviceFileService.buildServiceInterface();
        serviceFileService.buildServiceImpl();

        // 生成Controller
        controllerService.buildController();
    }

    @Test
    public void GenerateCodeWithSqlServiceTest(){
        GenerateCodeWithSqlService generateCodeWithSqlService = new GenerateCodeWithSqlService();
        generateCodeWithSqlService.setJavaBeanService(javaBeanService);
        generateCodeWithSqlService.setMapperService(mapperService);
        generateCodeWithSqlService.setServiceFileService(serviceFileService);
        generateCodeWithSqlService.setControllerService(controllerService);
    }

    @Test
    public void AddServiceTest() throws Exception {
        addService.addServiceInterface();
    }

}
