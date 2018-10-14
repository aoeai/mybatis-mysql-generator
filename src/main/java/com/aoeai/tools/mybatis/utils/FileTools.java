package com.aoeai.tools.mybatis.utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class FileTools {

    /**
     * 根据模板生成文件
     * @param file
     * @param template
     * @param content
     * @throws IOException
     * @throws TemplateException
     */
    public static void buildFile(File file, Template template, Object content) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        template.process(content, writer);

        FileUtils.writeStringToFile(file,
                writer.toString(), "UTF-8");
    }

    /**
     * 拷贝文件
     * @param srcDir 源文件
     * @param destDir 目标文件
     * @throws IOException
     */
    public static void copy(final File srcDir, final File destDir) throws IOException {
        FileUtils.copyDirectory(srcDir, destDir);
    }

}
