package com.da.frame.core;

/**
 * @author Da
 * @description 创建bean时在注入属性的前和后的回调接口
 * @date 2022-07-28 10:06
 */
public interface BeanPostProcessor {
    /**
     * 属性注入前
     */
    default Object postProcessorBeforeInitialization(final String beanName, final Object bean) {
        return bean;
    }

    /**
     * 属性注入后
     */
    Object postProcessorAfterInitialization(final String beanName, final Object bean);
}
