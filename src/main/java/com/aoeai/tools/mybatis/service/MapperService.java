package com.aoeai.tools.mybatis.service;

import com.aoeai.tools.mybatis.bean.java.Mapper;
import com.aoeai.tools.mybatis.bean.mysql.Column;
import com.aoeai.tools.mybatis.bean.mysql.Table;
import com.aoeai.tools.mybatis.utils.FileTools;
import com.aoeai.tools.mybatis.utils.Tools;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperService {

    private FreemarkerService freemarkerService;

    private EntityService entityService;

    /**
     * key:表名 value:表信息
     */
    private Map<String, Table> TABLE_INFO_MAP;

    /**
     * key:表名 value:Mapper信息
     */
    private Map<String, Mapper> mapperXmlMap;

    public MapperService setFreemarkerService(FreemarkerService freemarkerService, EntityService entityService) {
        this.freemarkerService = freemarkerService;
        this.entityService = entityService;
        init();
        return this;
    }

    private void init(){
        TABLE_INFO_MAP = new HashMap<>();
        mapperXmlMap = new HashMap<>();

        for (Map.Entry<String, Table> entry : entityService.getTableInfoMap().entrySet()) {
            Table table = entry.getValue();
            String entityBeanName = Tools.getEntityClassName(table.getName());
            String className = entityBeanName + "Mapper";

            Mapper mapper = new Mapper();
            mapper.setTableName(table.getName());
            mapper.setClassName(className);
            mapper.setEntityBeanName(entityBeanName);
            mapper.setResultMapId(Tools.getStartSmallName(entityBeanName + "Map"));
            mapper.setEntityBeanVarName(StringUtils.uncapitalize(entityBeanName));
            mapper.setMapperPackageName(Tools.getMapperPackage());
            mapper.setRootPackageName(ConfigService.getRootPackageName());
            mapper.setEntityPackageName(Tools.getEntityPackage());
            mapper.setClassComment(table.getComment());
            mapper.setVarName(StringUtils.uncapitalize(className));

            mapperXmlMap.put(mapper.getTableName(), mapper);

            // 生成Mapper XML文件
            List<Column> primaryKeyColumns = new ArrayList<>();
            List<Column> updateColumns = new ArrayList<>();
            for (Column column : table.getColumns()) {
                if (column.isPrimaryKey()) {
                    primaryKeyColumns.add(column);
                } else {
                    updateColumns.add(column);
                }
            }

            table.setPrimaryKeyColumns(primaryKeyColumns);
            table.setUpdateColumns(updateColumns);

            TABLE_INFO_MAP.put(table.getName(), table);
        }
    }

    /**
     * 生成mybatis mapper Java文件
     * @throws IOException
     * @throws TemplateException
     */
    public void buildMaperJavaFile() throws IOException, TemplateException {
        Template templateJava = freemarkerService.getTemplate("mapper_java.ftl");

        for (Map.Entry<String, Mapper> entry : mapperXmlMap.entrySet()) {
            Mapper mapper = entry.getValue();
            Map<String, Object> context = new HashMap<>();
            context.put("mapper", mapper);
            context.put("methodSavePrefix", ConfigService.getMethodSavePrefix());
            context.put("methodSelectPrefix", ConfigService.getMethodSelectPrefix());

            FileTools.buildFile(new File(
                    ConfigService.getBuildPath()
                            + Tools.getJavaPathFromPackageName(Tools.getMapperPackage())
                            + mapper.getClassName() + ".java"), templateJava, context);
        }
    }

    /**
     * 生成mybatis mapper XML文件
     * @throws IOException
     * @throws TemplateException
     */
    public void buildMaperXmlFile() throws IOException, TemplateException {
        Template templateXml = freemarkerService.getTemplate("mapper_xml.ftl");

        for (Map.Entry<String, Table> entry : TABLE_INFO_MAP.entrySet()) {
            Table table = entry.getValue();
            Mapper mapper = mapperXmlMap.get(table.getName());

            Map<String, Object> context = new HashMap<>();
            context.put("mapper", mapper);
            context.put("table", table);
            context.put("methodSavePrefix", ConfigService.getMethodSavePrefix());
            context.put("methodSelectPrefix", ConfigService.getMethodSelectPrefix());

            FileTools.buildFile(new File(
                    ConfigService.getBuildPath()
                            + Tools.getMapperXmlPath()
                            + Tools.getMapperXmlName(mapper.getClassName()) + ".xml"), templateXml, context);
        }
    }

    public Table getTable(String tableName){
        return TABLE_INFO_MAP.get(tableName);
    }

    public Mapper getMapper(String tableName){
        return mapperXmlMap.get(tableName);
    }

    public Map<String, Table> getTableInfoMap() {
        return TABLE_INFO_MAP;
    }
}
