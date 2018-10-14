package com.aoeai.tools.mybatis.service;

import com.aoeai.tools.mybatis.bean.java.Mapper;
import com.aoeai.tools.mybatis.bean.java.ServiceClass;
import com.aoeai.tools.mybatis.bean.mysql.Column;
import com.aoeai.tools.mybatis.bean.mysql.Table;
import com.aoeai.tools.mybatis.utils.FileTools;
import com.aoeai.tools.mybatis.utils.Tools;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * service接口、实现类服务
 */
@Service
public class ServiceFileService {

    @Autowired
    private FreemarkerService freemarkerService;

    @Autowired
    private MapperService mapperService;

    /**
     * key:table名称 value:ServiceClass
     */
    private Map<String, ServiceClass> serviceClassMap;

    /**
     * 保存时数据初始化
     */
    private List<String> saveDataInitList;

    /**
     * 更新时数据初始化
     */
    private List<String> updateDataInitList;

    @PostConstruct
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
            serviceClass.setInterfaceFilePath(ConfigService.getGeneratorRootPath()
                    + Tools.getJavaPathFromPackageName(Tools.getServiceInterfacePackage())
                    + serviceClass.getInterfaceClassName() + ".java");
            serviceClass.setImplFilePath(ConfigService.getGeneratorRootPath()
                    + Tools.getJavaPathFromPackageName(Tools.getServiceImplPackage())
                    + serviceClass.getImplClassName() + ".java");

            serviceClassMap.put(tableName, serviceClass);
        }

        saveDataInitList = new ArrayList<>();
        saveDataInitList.add("setAddTime(date)");
        saveDataInitList.add("setUpdateTime(date)");
        saveDataInitList.add("setDelete(1)");

        updateDataInitList = new ArrayList<>();
        updateDataInitList.add("setAddTime(null)");
        updateDataInitList.add("setUpdateTime(new Date())");
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
            Mapper mapper = serviceClass.getMapper();
            context.put("mapper", mapper);
            context.put("table", mapperService.getTableInfoMap().get(mapper.getTableName()));
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
            Mapper mapper = serviceClass.getMapper();
            context.put("mapper", mapper);
            Table table = mapperService.getTableInfoMap().get(mapper.getTableName());
            context.put("table", table);
            context.put("primaryKeySetNullList", primaryKeySetNullList(table.getPrimaryKeyColumns()));
            context.put("methodSavePrefix", ConfigService.getMethodSavePrefix());
            context.put("methodSelectPrefix", ConfigService.getMethodSelectPrefix());
            context.put("saveDataInitList", saveDataInitList);
            context.put("updateDataInitList", updateDataInitList);

            FileTools.buildFile(new File(serviceClass.getImplFilePath()), templateJava, context);
        }
    }

    public Map<String, ServiceClass> getServiceClassMap() {
        return serviceClassMap;
    }

    public List<String> getSaveDataInitList() {
        return saveDataInitList;
    }

    public ServiceFileService setSaveDataInitList(List<String> saveDataInitList) {
        this.saveDataInitList = saveDataInitList;
        return this;
    }

    public List<String> getUpdateDataInitList() {
        return updateDataInitList;
    }

    public ServiceFileService setUpdateDataInitList(List<String> updateDataInitList) {
        this.updateDataInitList = updateDataInitList;
        return this;
    }

    /**
     * (保存时)所有主键默认值设置为null
     * @param primaryKeyColumns
     * @return
     */
    private List<String> primaryKeySetNullList(List<Column> primaryKeyColumns){
        List<String> list = new ArrayList<>();
        for (Column column : primaryKeyColumns) {
            list.add("set" + StringUtils.capitalize(column.getJavaFieldName()) + "(null);");
        }

        return list;
    }

}
