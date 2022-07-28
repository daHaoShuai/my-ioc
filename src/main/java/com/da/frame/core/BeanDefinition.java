package com.da.frame.core;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 10:52
 * bean定义实现类
 */
public class BeanDefinition {

    private final String name;
    private final Class<?> clz;

    public BeanDefinition(String name, Class<?> clz) {
        this.name = name;
        this.clz = clz;
    }

    public String getName() {
        return this.name;
    }

    public Class<?> getClz() {
        return this.clz;
    }
}
