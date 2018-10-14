package com.aoeai.tools.mybatis.service;

import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;

/**
 * 新增方法
 */
@Service
public class AddService {

    @Autowired
    private FreemarkerService freemarkerService;

    public void addServiceInterface() throws Exception {
        Template template = freemarkerService.getTemplate("add_service_interface.ftl");
        StringWriter writer = new StringWriter();
        template.process(new HashMap<String, String>(), writer);

        autoReplace("/Users/aoe/github/mybatis-mysql-generator-test/src/main/java/com/aoeai/mybatismysqltest/service/WorkerNodeService.java",
                "//mybatis-mysql-generator修改文件标记", template.toString());
    }

    /**
     * 文件内容替换
     * 来源：https://blog.csdn.net/yinghuacao_dong/article/details/79675795
     *
     * @param filePath
     * @param oldstr
     * @param newStr
     */
    private static void autoReplace(String filePath, String oldstr, String newStr) {
        File file = new File(filePath);
        Long fileLength = file.length();
        byte[] fileContext = new byte[fileLength.intValue()];
        FileInputStream in = null;
        PrintWriter out = null;
        try {
            in = new FileInputStream(filePath);
            in.read(fileContext);
            // 避免出现中文乱码
            String str = new String(fileContext, "utf-8");
            str = str.replace(oldstr, newStr);
            out = new PrintWriter(filePath);
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
