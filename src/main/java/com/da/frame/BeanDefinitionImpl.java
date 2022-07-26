package com.da.frame;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 10:52
 * bean定义实现类
 */
public class BeanDefinitionImpl implements BeanDefinition {

    private String name;
    private Class<?> clz;

    public void setName(String name) {
        this.name = name;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Class<?> getClz() {
        return this.clz;
    }
}
