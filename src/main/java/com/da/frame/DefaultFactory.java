package com.da.frame;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 10:33
 * 默认bean工厂的实现
 */
public class DefaultFactory implements BeanFactory {

    //    保存bean名字和bean的定义
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    //    保存创建好的单例bean
    private final Map<String, Object> beanMap = new ConcurrentHashMap<>();

    //    注册bean的信息
    protected void registerBeanDefinition(final String beanName, final BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public Object getBean(String beanName) {
//        判断bean池中有没有对应的key
        if (beanMap.containsKey(beanName)) {
            return beanMap.get(beanName);
        }
//        判断bean定义池中有没有对应的key
        if (beanDefinitionMap.containsKey(beanName)) {
//            创建bean
            Object bean = createBean(beanDefinitionMap.get(beanName));
//            加到bean池中,下一次就不用继续创建了
            beanMap.put(beanName, bean);
            return bean;
        }
        throw new IocException("容器中没有找到" + beanName + "的定义");
    }

    //    根据bean定义创建bean
    private Object createBean(final BeanDefinition beanDefinition) {

        return null;
    }

    @Override
    public <T> T getBean(String beanName, Class<T> clz) {
        return null;
    }
}
