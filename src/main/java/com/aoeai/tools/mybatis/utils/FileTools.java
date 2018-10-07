package com.aoeai.tools.mybatis.utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class FileTools {

    public static void buildFile(File file, Template template, Object content) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        template.process(content, writer);

        FileUtils.writeStringToFile(file,
                writer.toString(), "UTF-8");
    }

}
