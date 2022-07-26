package com.da.frame.core;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 10:52
 * bean定义实现类
 */
public class BeanDefinition {

    private String name;
    private Class<?> clz;

    public BeanDefinition(String name, Class<?> clz) {
        this.name = name;
        this.clz = clz;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }


    public String getName() {
        return this.name;
    }

    public Class<?> getClz() {
        return this.clz;
    }
}
