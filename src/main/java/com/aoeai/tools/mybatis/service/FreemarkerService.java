package com.aoeai.tools.mybatis.service;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.File;
import java.io.IOException;

public class FreemarkerService {

    /**
     * freemarker配置
     */
    private static Configuration FREEMARKER_CFG;

    public FreemarkerService() throws IOException {
        FREEMARKER_CFG = new Configuration(Configuration.getVersion());
        FREEMARKER_CFG.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir") + "/src/main/resources/templates/"));
        FREEMARKER_CFG.setObjectWrapper(new DefaultObjectWrapper(Configuration.getVersion()));
    }

    public Template getTemplate(String name) throws IOException {
        return FREEMARKER_CFG.getTemplate(name);
    }
}
