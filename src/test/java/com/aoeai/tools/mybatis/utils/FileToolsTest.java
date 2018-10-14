package com.aoeai.tools.mybatis.utils;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class FileToolsTest {

    @Test
    public void copyTest() throws IOException {
        File srcDir = new File("/Users/aoe/github/mybatis-mysql-generator-test/src");
        File destDir = new File(System.getProperty("user.dir") + "/target/backup/");

        FileTools.copy(srcDir, destDir);
    }

}
