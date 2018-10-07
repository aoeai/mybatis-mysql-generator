package com.aoeai.tools.mybatis.bean.java;

public class ControllerClass {

    private ServiceClass serviceClass;

    /**
     * Controller包名
     */
    private String packageName;

    /**
     * 类名
     */
    private String className;

    /**
     * 类注释
     */
    private String classComment;

    /**
     * Controller存储的绝对路径
     */
    private String filePath;

    public ServiceClass getServiceClass() {
        return serviceClass;
    }

    public ControllerClass setServiceClass(ServiceClass serviceClass) {
        this.serviceClass = serviceClass;
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public ControllerClass setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public ControllerClass setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getClassComment() {
        return classComment;
    }

    public ControllerClass setClassComment(String classComment) {
        this.classComment = classComment;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public ControllerClass setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }
}
