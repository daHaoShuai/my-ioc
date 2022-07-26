package com.da.frame;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 10:54
 * 扫描注解的ioc容器
 */
public class AnnotationAppContext extends DefaultFactory {
    //    要扫描的根路径
    private final String basePackageName;

    public AnnotationAppContext(Class<?> config) {
        System.out.println(config);
        basePackageName = config.getPackage().getName();
    }
}
