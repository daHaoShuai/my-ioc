package com.da.frame.core;

import com.da.frame.annotation.Inject;
import com.da.frame.annotation.Value;
import com.da.frame.exception.IocException;
import com.da.frame.util.Utils;

import java.lang.reflect.Field;
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
    //    缓存创建好的bean实例
    private final Map<String, Object> cacheBeanMap = new ConcurrentHashMap<>();

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
        final Class<?> clz = beanDefinition.getClz();
//        先创建当前类的实例
        final Object bean = Utils.newInstance(clz);
//        缓存一下当前的实例
        cacheBeanMap.put(beanDefinition.getName(), bean);
//        给实例注入属性依赖
        injectClzField(clz, bean);
        return bean;
    }

    //    给实例注入属性依赖
    private void injectClzField(Class<?> clz, Object bean) {
        //        获取要实例化的类上的所有属性
        final Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
//            处理每个属性
            injectField(field, bean);
        }
    }

    //    给属性注入值
    private void injectField(Field field, Object o) {
        field.setAccessible(true);
        try {
//            判断是不是需要注入依赖的属性
            if (field.isAnnotationPresent(Inject.class)) {
                String value = field.getAnnotation(Inject.class).value();
//                    如果注解为空的时候说明要有属性名字注入的容器中的类
                if (Utils.isBlank(value)) {
                    value = field.getName();
                }
//                    从容器中获取bean
                Object bean;
//                    先从缓存中拿bean对象
                if (cacheBeanMap.containsKey(value)) {
                    bean = cacheBeanMap.get(value);
                } else {
//                        不行再去创建
                    bean = getBean(value);
                }
//                   给属性设置值
                field.set(o, bean);
            }
//            注入基本属性
            else if (field.isAnnotationPresent(Value.class)) {
                final String value = field.getAnnotation(Value.class).value();
                field.set(o, Utils.conv(value, field.getType()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IocException(e.getMessage());
        } finally {
            field.setAccessible(false);
        }
    }

    @SuppressWarnings("unchecked")//忽略强转类型的警告
    @Override
    public <T> T getBean(String beanName, Class<T> clz) {
        return (T) getBean(beanName);
    }
}
