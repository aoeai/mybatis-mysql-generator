package com.aoeai.tools.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用sql生成代码
 */
@Service
public class GenerateCodeWithSqlService {

    @Autowired
    private JavaBeanService javaBeanService;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private ServiceFileService serviceFileService;

    @Autowired
    private ControllerService controllerService;

    public void build(String sql){

    }
}
