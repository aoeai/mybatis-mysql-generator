package com.aoeai.tools.mybatis.service;

import com.aoeai.tools.mybatis.bean.java.ServiceClass;
import com.aoeai.tools.mybatis.bean.mysql.Table;
import com.aoeai.tools.mybatis.utils.FileTools;
import com.aoeai.tools.mybatis.utils.Tools;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * service接口、实现类服务
 */
public class ServiceFileService {

    private FreemarkerService freemarkerService;

    private MapperService mapperService;

    /**
     * key:table名称 value:ServiceClass
     */
    private Map<String, ServiceClass> serviceClassMap;

    public ServiceFileService(FreemarkerService freemarkerService, MapperService mapperService) {
        this.freemarkerService = freemarkerService;
        this.mapperService = mapperService;
        init();
    }

    private void init(){
        serviceClassMap = new HashMap<>();

        for (Map.Entry<String, Table> entry : mapperService.getTableInfoMap().entrySet()) {
            Table table = entry.getValue();
            ServiceClass serviceClass = new ServiceClass();
            String tableName = table.getName();

            serviceClass.setMapper(mapperService.getMapper(tableName));
            serviceClass.setInterfaceClassName(Tools.getServiceInterfaceClassName(tableName));
            serviceClass.setInterfaceVarClassName(StringUtils.uncapitalize(serviceClass.getInterfaceClassName()));
            serviceClass.setImplClassName(Tools.getServiceImplClassName(tableName));
            serviceClass.setRootPackageName(mapperService.getMapper(tableName).getRootPackageName());
            serviceClass.setClassComment(table.getComment() + "服务");
            serviceClass.setServiceInterfacePackageName(Tools.getServiceInterfacePackage());
            serviceClass.setServiceImplPackageName(Tools.getServiceImplPackage());
            serviceClass.setInterfaceFilePath(ConfigService.getBuildPath()
                    + Tools.getJavaPathFromPackageName(Tools.getServiceInterfacePackage())
                    + serviceClass.getInterfaceClassName() + ".java");
            serviceClass.setImplFilePath(ConfigService.getBuildPath()
                    + Tools.getJavaPathFromPackageName(Tools.getServiceImplPackage())
                    + serviceClass.getImplClassName() + ".java");

            serviceClassMap.put(tableName, serviceClass);
        }
    }

    /**
     * 生成service接口
     * @throws IOException
     * @throws TemplateException
     */
    public void buildServiceInterface() throws IOException, TemplateException {
        Template templateJava = freemarkerService.getTemplate("service_interface_java.ftl");

        for (Map.Entry<String, ServiceClass> entry : serviceClassMap.entrySet()) {
            ServiceClass serviceClass = entry.getValue();
            Map<String, Object> context = new HashMap<>();
            context.put("service", serviceClass);
            context.put("mapper", serviceClass.getMapper());
            context.put("methodSavePrefix", ConfigService.getMethodSavePrefix());
            context.put("methodSelectPrefix", ConfigService.getMethodSelectPrefix());

            FileTools.buildFile(new File(serviceClass.getInterfaceFilePath()), templateJava, context);
        }
    }

    /**
     * 生成service实现类
     * @throws IOException
     * @throws TemplateException
     */
    public void buildServiceImpl() throws IOException, TemplateException {
        Template templateJava = freemarkerService.getTemplate("service_impl_java.ftl");

        for (Map.Entry<String, ServiceClass> entry : serviceClassMap.entrySet()) {
            ServiceClass serviceClass = entry.getValue();
            Map<String, Object> context = new HashMap<>();
            context.put("service", serviceClass);
            context.put("mapper", serviceClass.getMapper());
            context.put("methodSavePrefix", ConfigService.getMethodSavePrefix());
            context.put("methodSelectPrefix", ConfigService.getMethodSelectPrefix());

            FileTools.buildFile(new File(serviceClass.getImplFilePath()), templateJava, context);
        }
    }

    public Map<String, ServiceClass> getServiceClassMap() {
        return serviceClassMap;
    }
}
