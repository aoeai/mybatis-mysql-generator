package com.aoeai.tools.mybatis.service;

import com.aoeai.tools.mybatis.bean.java.ControllerClass;
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
 * Controller类生成
 */
@Service
public class ControllerService {

    @Autowired
    private FreemarkerService freemarkerService;

    @Autowired
    private ServiceFileService serviceFileService;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private Tools tools;

    @Autowired
    private ConfigService configService;

    /**
     * key:table名称 value:ControllerClass
     */
    private Map<String, ControllerClass> controllerClassMap;

    @PostConstruct
    private void init(){
        controllerClassMap = new HashMap<>();

        for (Map.Entry<String, ServiceClass> entry : serviceFileService.getServiceClassMap().entrySet()) {
            ServiceClass serviceClass = entry.getValue();
            String tableName = serviceClass.getMapper().getTableName();
            Table table = mapperService.getTableInfoMap().get(tableName);
            ControllerClass controllerClass = new ControllerClass();

            controllerClass.setClassComment(table.getComment() + "控制器");
            controllerClass.setClassName(tools.getControllerClassName(tableName));
            controllerClass.setPackageName(tools.getControllerPackage());
            controllerClass.setServiceClass(serviceClass);
            controllerClass.setFilePath(configService.getGeneratorRootPath()
                    + tools.getJavaPathFromPackageName(tools.getControllerPackage())
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

            Mapper mapper = controllerClass.getServiceClass().getMapper();
            context.put("mapper", mapper);

            Table table = mapperService.getTableInfoMap().get(mapper.getTableName());
            context.put("primaryKeyColumns", table.getPrimaryKeyColumns());
            context.put("primaryKeyGetMethodList", primaryKeyGetMethodList(table.getPrimaryKeyColumns()));

            context.put("methodSavePrefix", configService.getMethodSavePrefix());
            context.put("methodSelectPrefix", configService.getMethodSelectPrefix());

            FileTools.buildFile(new File(controllerClass.getFilePath()), templateJava, context);
        }
    }

    /**
     * 返回所有主键的get方法
     * @param primaryKeyColumns
     * @return
     */
    private List<Map<String, String>> primaryKeyGetMethodList(List<Column> primaryKeyColumns){
        List<Map<String, String>> list = new ArrayList<>();
        for (Column column : primaryKeyColumns) {
            Map<String, String> map = new HashMap<>();
            map.put("method", "get" + StringUtils.capitalize(column.getJavaFieldName()) + "()");
            map.put("javaFieldName", column.getJavaFieldName());
            list.add(map);
        }

        return list;
    }
}
