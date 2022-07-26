package com.da.frame;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 10:31
 * bean工厂
 */
public interface BeanFactory {
    Object getBean(String beanName);

    <T> T getBean(final String beanName, final Class<T> clz);
}
