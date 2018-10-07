package com.aoeai.tools.mybatis.bean.java;

/**
 * service接口和实现类信息
 */
public class ServiceClass {

    private Mapper mapper;

    /**
     * 工程根路径的包名
     */
    private String rootPackageName;

    /**
     * service接口包名
     */
    private String serviceInterfacePackageName;

    /**
     * service实现类包名
     */
    private String serviceImplPackageName;

    /**
     * service接口类名
     */
    private String interfaceClassName;

    /**
     * service接口类变量名（例如 UserService userService，userService就是这个）
     */
    private String interfaceVarClassName;

    /**
     * service实现类类名
     */
    private String implClassName;

    /**
     * 类注释
     */
    private String classComment;

    /**
     * service接口绝对路径
     */
    private String interfaceFilePath;

    /**
     * service实现类绝对路径
     */
    private String implFilePath;

    public Mapper getMapper() {
        return mapper;
    }

    public ServiceClass setMapper(Mapper mapper) {
        this.mapper = mapper;
        return this;
    }

    public String getRootPackageName() {
        return rootPackageName;
    }

    public ServiceClass setRootPackageName(String rootPackageName) {
        this.rootPackageName = rootPackageName;
        return this;
    }

    public String getServiceInterfacePackageName() {
        return serviceInterfacePackageName;
    }

    public ServiceClass setServiceInterfacePackageName(String serviceInterfacePackageName) {
        this.serviceInterfacePackageName = serviceInterfacePackageName;
        return this;
    }

    public String getServiceImplPackageName() {
        return serviceImplPackageName;
    }

    public ServiceClass setServiceImplPackageName(String serviceImplPackageName) {
        this.serviceImplPackageName = serviceImplPackageName;
        return this;
    }

    public String getInterfaceClassName() {
        return interfaceClassName;
    }

    public ServiceClass setInterfaceClassName(String interfaceClassName) {
        this.interfaceClassName = interfaceClassName;
        return this;
    }

    public String getImplClassName() {
        return implClassName;
    }

    public ServiceClass setImplClassName(String implClassName) {
        this.implClassName = implClassName;
        return this;
    }

    public String getClassComment() {
        return classComment;
    }

    public ServiceClass setClassComment(String classComment) {
        this.classComment = classComment;
        return this;
    }

    public String getInterfaceFilePath() {
        return interfaceFilePath;
    }

    public ServiceClass setInterfaceFilePath(String interfaceFilePath) {
        this.interfaceFilePath = interfaceFilePath;
        return this;
    }

    public String getImplFilePath() {
        return implFilePath;
    }

    public ServiceClass setImplFilePath(String implFilePath) {
        this.implFilePath = implFilePath;
        return this;
    }

    public String getInterfaceVarClassName() {
        return interfaceVarClassName;
    }

    public ServiceClass setInterfaceVarClassName(String interfaceVarClassName) {
        this.interfaceVarClassName = interfaceVarClassName;
        return this;
    }
}
