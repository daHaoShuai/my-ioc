package com.da.frame.core;

import com.da.frame.annotation.Bean;
import com.da.frame.annotation.Inject;
import com.da.frame.annotation.Value;
import com.da.frame.exception.IocException;
import com.da.frame.util.Utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
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
    //    配置文件中的信息
    private final Map<String, String> configInfoMap = new HashMap<>();

    public DefaultFactory() {
//        扫描配置文件
        final Path configFile = Utils.getResourcePath("app.properties");
//        如果有配置文件才去扫描
        if (null != configFile) {
            //        如果配置文件存在才扫描
            if (Files.exists(configFile)) {
                try {
                    //                读取配置文件
                    Files.readAllLines(configFile)
                            //                        用=分割键值对
                            .stream().map(line -> line.split("="))
                            //                        过滤出只有2个值的数组
                            .filter(arr -> arr.length == 2)
                            //                        添加到配置信息Map中
                            .forEach(arr -> configInfoMap.put(arr[0], arr[1]));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IocException("读取配置文件出错" + e.getMessage());
                }
            }
        }
    }

    //    注册bean的信息
    protected void registerBeanDefinition(final String beanName, final BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    //    给配置类注册bean
    protected void registerBeanMap(final String beanName, final Object bean) {
        beanMap.put(beanName, bean);
    }

    //    注册配置类的bean
    protected void registerConfigBean(final Class<?> clz) {
//            实例化当前配置类对象
        final Object configBean = Utils.newInstance(clz);
//        给配置类的属性注入值
        injectClzField(clz, configBean);
//            扫描配置类上标记了@Bean注解的方法
        for (Method method : clz.getDeclaredMethods()) {
            try {
                method.setAccessible(true);
                if (method.isAnnotationPresent(Bean.class)) {
//                        判断@Bean注解中有没有给这个类起名字,没有就用方法的返回类型首字母小写作为bean的名字
                    String beanName = method.getAnnotation(Bean.class).value();
                    if (Utils.isBlank(beanName)) {
                        beanName = method.getReturnType().getSimpleName();
                        beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
                    }
//                        实例化bean(这里就没有依赖注入了)
                    final Object bean = method.invoke(configBean);
                    this.registerBeanMap(beanName, bean);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new IocException("创建" + clz.getSimpleName() + "上的bean实例出错");
            } finally {
                method.setAccessible(false);
            }
        }
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
                String value = field.getAnnotation(Value.class).value();
//                判断是不是需要从配置文件中获取数据
                if (value.startsWith("${") && value.endsWith("}")) {
//                    如果有配置文件才会从配置文件中去找值
                    if (configInfoMap.size() > 0) {
                        value = value.substring(2, value.length() - 1);
//                        如果配置map中有当前key对应的值就取出赋值
                        if (configInfoMap.containsKey(value)) {
                            final String infoValue = configInfoMap.get(value);
                            field.set(o, Utils.conv(infoValue, field.getType()));
                        } else {
                            throw new IocException("注入值" + value + "时出错,请检查配置文件内容");
                        }
                    }
                } else {
                    field.set(o, Utils.conv(value, field.getType()));
                }
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
