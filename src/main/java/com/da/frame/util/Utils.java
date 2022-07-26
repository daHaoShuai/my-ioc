package com.da.frame.util;

import com.da.frame.exception.IocException;
import com.da.frame.function.OneToOne;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 10:48
 * 工具类
 */
public class Utils {

    /**
     * 获取类加载器
     *
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 把包名中的.换成/
     *
     * @param packageName 包名
     * @return 处理好的路径
     */
    public static String packageToPath(String packageName) {
        if (isNotBlank(packageName)) {
            return packageName.replaceAll("\\.", "/");
        }
        throw new IocException("传入的参数有误");
    }

    /**
     * 把路径的 \\ 换成 .
     *
     * @param path 路径
     * @return 处理好的路径
     */
    public static String pathTopackage(String path) {
        if (isNotBlank(path)) {
            return path.replaceAll("\\\\", ".");
        }
        throw new IocException("传入的参数有误");
    }

    /**
     * 判断字符串是空白
     */
    public static boolean isBlank(String str) {
        return !isNotBlank(str);
    }

    /**
     * 判断字符串不是空白
     */
    public static boolean isNotBlank(String str) {
        return null != str && !str.equals("");
    }

    /**
     * 获取打包后的文件
     */
    public static File getResourceFile(String baseScanPath) {
        final ClassLoader classLoader = getClassLoader();
        final URL url = classLoader.getResource(baseScanPath);
        assert url != null;
        return new File(url.getFile());
    }

    /**
     * 获取打包后的文件
     */
    public static Path getResourcePath(String baseScanPath) {
        return Paths.get(getResourceFile(baseScanPath).getAbsolutePath());
    }

    /**
     * 扫描出指定路径符合条件的所有文件
     *
     * @param path 要扫描的路径
     * @param fun  处理条件
     * @return 文件路径集合
     */
    public static List<String> getDirFileInJudge(Path path, OneToOne<String> fun) {
        try {
            if (null != path && null != fun) {
                return Files.walk(path)
                        .map(Path::toFile)
                        .map(file -> fun.exec(file.getAbsolutePath()))
                        .filter(s -> !s.equals(""))
                        .collect(Collectors.toList());
            }
            throw new IocException("要扫描的路径和判断条件不能为null");
        } catch (IOException e) {
            throw new IocException(e.getMessage());
        }
    }

    /**
     * 加载class文件
     *
     * @param className className
     * @return 加载成功的Class
     */
    public static Class<?> loadClassFile(String className) {
        try {
            if (isNotBlank(className)) {
                return getClassLoader().loadClass(className);
            }
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建class的实例
     *
     * @param clz 要实例化的class
     * @return class的实例
     */
    public static Object newInstance(Class<?> clz) {
        try {
            if (null != clz) {
                return clz.getConstructor().newInstance();
            }
            throw new IocException("传入的要实例化的bean为null");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new IocException("bean创建失败: " + e.getMessage());
        }
    }
}
