package com.aoeai.tools.mybatis.service;

import com.aoeai.tools.mybatis.bean.mysql.Column;
import com.aoeai.tools.mybatis.bean.mysql.Table;
import com.aoeai.tools.mybatis.utils.FileTools;
import com.aoeai.tools.mybatis.utils.Tools;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

/**
 * 实体类服务
 */
@Service
public class JavaBeanService {

    @Autowired
    private FreemarkerService freemarkerService;

    /**
     * key:表名 value:表信息
     */
    private Map<String, Table> TABLE_INFO_MAP;

    @PostConstruct
    private void init(){
        TABLE_INFO_MAP = new HashMap<>();

        for (Map.Entry<String, Table> entry : ConfigService.getTableInfoMap().entrySet()) {
            Table table = entry.getValue();
            table.setClassName(Tools.getEntityClassName(table.getName()));
            table.setEntityPackageName(Tools.getEntityPackage());

            for (Column column : table.getColumns()) {
                column.setJavaFieldType(Tools.getPropertyType(column.getSqlFieldType()));
                column.setJavaFieldName(Tools.fixName(column.getSqlFieldName()));
            }

            TABLE_INFO_MAP.put(entry.getKey(), table);
        }
    }

    /**
     * 生成数据库实体
     *
     * @throws Exception
     */
    public void buildEntity() throws Exception {
        Template template = freemarkerService.getTemplate("bean_entity.ftl");

        for (Map.Entry<String, Table> entry : TABLE_INFO_MAP.entrySet()) {
            Table table = entry.getValue();
            table.setEntityClassName(Tools.getEntityClassName(table.getName()));
            table.setFilePath(ConfigService.getGeneratorRootPath()
                    + Tools.getJavaPathFromPackageName(Tools.getEntityPackage())
                    + table.getEntityClassName() + ".java");

            Map<String, Object> context = new HashMap<>();
            context.put("table", table);
            context.put("importList", getImportList(table.getColumns()));

            FileTools.buildFile(new File(table.getFilePath()), template, context);
        }
    }

    public Table getTable(String tableName){
        return TABLE_INFO_MAP.get(tableName);
    }

    public Map<String, Table> getTableInfoMap() {
        return TABLE_INFO_MAP;
    }

    /**
     * 实体类中导入的包
     * @param columns
     * @return
     */
    private Set<String> getImportList(List<Column> columns){
        Set set = new TreeSet();

        for (Column column : columns) {
            String type = column.getJavaFieldType();
            if(Tools.TYPE_DATE.equals(type)){
                set.add(Tools.JAVA_TYPE_MAP.get(type));
            }
            if(Tools.TYPE_BIGDECIMAL.equals(type)){
                set.add(Tools.JAVA_TYPE_MAP.get(type));
            }
        }

        return set;
    }
}
