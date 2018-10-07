package com.aoeai.tools.mybatis.service;

import com.aoeai.tools.mybatis.bean.java.ControllerClass;
import com.aoeai.tools.mybatis.bean.java.ServiceClass;
import com.aoeai.tools.mybatis.bean.mysql.Table;
import com.aoeai.tools.mybatis.utils.FileTools;
import com.aoeai.tools.mybatis.utils.Tools;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller类生成
 */
public class ControllerService {

    private FreemarkerService freemarkerService;

    private ServiceFileService serviceFileService;

    private MapperService mapperService;

    /**
     * key:table名称 value:ControllerClass
     */
    private Map<String, ControllerClass> controllerClassMap;

    public ControllerService(FreemarkerService freemarkerService, ServiceFileService serviceFileService,
                             MapperService mapperService) {
        this.freemarkerService = freemarkerService;
        this.serviceFileService = serviceFileService;
        this.mapperService = mapperService;
        init();
    }

    private void init(){
        controllerClassMap = new HashMap<>();

        for (Map.Entry<String, ServiceClass> entry : serviceFileService.getServiceClassMap().entrySet()) {
            ServiceClass serviceClass = entry.getValue();
            String tableName = serviceClass.getMapper().getTableName();
            Table table = mapperService.getTableInfoMap().get(tableName);
            ControllerClass controllerClass = new ControllerClass();

            controllerClass.setClassComment(table.getComment() + "控制器");
            controllerClass.setClassName(Tools.getControllerClassName(tableName));
            controllerClass.setPackageName(Tools.getControllerPackage());
            controllerClass.setServiceClass(serviceClass);
            controllerClass.setFilePath(ConfigService.getBuildPath()
                    + Tools.getJavaPathFromPackageName(Tools.getControllerPackage())
                    + controllerClass.getClassName() + ".java");

            controllerClassMap.put(tableName, controllerClass);
        }
    }

    /**
     * 生成service接口
     * @throws IOException
     * @throws TemplateException
     */
    public void buildController() throws IOException, TemplateException {
        Template templateJava = freemarkerService.getTemplate("controller_java.ftl");

        for (Map.Entry<String, ControllerClass> entry : controllerClassMap.entrySet()) {
            ControllerClass controllerClass = entry.getValue();
            Map<String, Object> context = new HashMap<>();
            context.put("controller", controllerClass);
            context.put("service", controllerClass.getServiceClass());
            context.put("mapper", controllerClass.getServiceClass().getMapper());
            context.put("methodSavePrefix", ConfigService.getMethodSavePrefix());
            context.put("methodSelectPrefix", ConfigService.getMethodSelectPrefix());

            FileTools.buildFile(new File(controllerClass.getFilePath()), templateJava, context);
        }
    }
}
