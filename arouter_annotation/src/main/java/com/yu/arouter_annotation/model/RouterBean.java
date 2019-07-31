package com.yu.arouter_annotation.model;



public class RouterBean {
    private String path;
    private Class clazz;

    public RouterBean() {
    }

    public RouterBean(String path, Class clazz) {
        this.path = path;
        this.clazz = clazz;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}

