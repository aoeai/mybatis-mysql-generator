package com.aoeai.tools.mybatis.service;

import org.springframework.stereotype.Service;

/**
 * 用sql生成代码
 */
@Service
public class GenerateCodeWithSqlService {

    private JavaBeanService javaBeanService;

    private MapperService mapperService;

    private ServiceFileService serviceFileService;

    private ControllerService controllerService;

    public GenerateCodeWithSqlService setJavaBeanService(JavaBeanService javaBeanService) {
        this.javaBeanService = javaBeanService;
        return this;
    }

    public GenerateCodeWithSqlService setMapperService(MapperService mapperService) {
        this.mapperService = mapperService;
        return this;
    }

    public GenerateCodeWithSqlService setServiceFileService(ServiceFileService serviceFileService) {
        this.serviceFileService = serviceFileService;
        return this;
    }

    public GenerateCodeWithSqlService setControllerService(ControllerService controllerService) {
        this.controllerService = controllerService;
        return this;
    }

    public void build(String sql){

    }
}
