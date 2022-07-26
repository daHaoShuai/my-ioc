package com.da.frame.core;

import com.da.frame.annotation.Component;
import com.da.frame.util.Utils;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

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
        basePackageName = config.getPackage().getName();
        init();
    }

    private void init() {
//        获取要扫描的文件路径
        final String baseScanPath = Utils.packageToPath(basePackageName);
        Path path = Utils.getResourcePath(baseScanPath);
//        在文件夹中过滤出.class结尾的文件
        List<String> files = Utils.getDirFileInJudge(path, s -> s.endsWith(".class") ? s : "");
        handlerClassFile(files);
    }

    //    处理符合条件的class文件为bean的定义加到bean定义池中
    private void handlerClassFile(List<String> files) {
        files.stream().map(Utils::pathTopackage)
//                把路径变成能加载的className
                .map(s -> s.substring(s.indexOf(basePackageName), s.lastIndexOf(".")))
//                加载class
                .map(Utils::loadClassFile)
                .filter(Objects::nonNull)
//                获取有Component注解标记的类
                .filter(clz -> clz.isAnnotationPresent(Component.class))
                .map(clz -> new BeanDefinition(clz.getSimpleName(), clz))
                .forEach(beanDefinition -> this.registerBeanDefinition(beanDefinition.getName(), beanDefinition));
    }
}
