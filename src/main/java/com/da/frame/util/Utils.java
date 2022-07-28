package com.da.frame.util;

import com.da.frame.exception.IocException;
import com.da.frame.function.OneToOne;
import com.da.frame.function.MethodAndObjectArrConsume;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
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
    public static String pathToPackage(String path) {
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
        if (null != url) {
            return new File(url.getFile());
        }
        return null;
    }

    /**
     * 获取打包后的文件
     */
    public static Path getResourcePath(String baseScanPath) {
        if (getResourceFile(baseScanPath) != null) {
            return Paths.get(Objects.requireNonNull(getResourceFile(baseScanPath)).getAbsolutePath());
        }
        return null;
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
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
            throw new IocException("bean创建失败: " + e.getMessage());
        }
    }

    //    用来把String转成其他数据类型
    private static final Map<String, Function<String, Object>> typeMap = new HashMap<>();

    static {
        typeMap.put("java.lang.String", str -> str);
        typeMap.put("byte", Byte::valueOf);
        typeMap.put("java.lang.Byte", Byte::valueOf);
        typeMap.put("boolean", Boolean::valueOf);
        typeMap.put("java.lang.Boolean", Boolean::valueOf);
        typeMap.put("short", Short::valueOf);
        typeMap.put("java.lang.Short", Short::valueOf);
        typeMap.put("char", str -> str.charAt(0));
        typeMap.put("java.lang.Character", str -> str.charAt(0));
        typeMap.put("int", Integer::valueOf);
        typeMap.put("java.lang.Integer", Integer::valueOf);
        typeMap.put("long", Long::valueOf);
        typeMap.put("java.lang.Long", Long::valueOf);
        typeMap.put("float", Float::valueOf);
        typeMap.put("java.lang.Float", Float::valueOf);
        typeMap.put("double", Double::valueOf);
        typeMap.put("java.lang.Double", Double::valueOf);
    }

    /**
     * 把String类型转成相应的格式
     *
     * @param value 要转换的String
     * @param type  要转换成的类型
     * @return 转换好的类型
     */
    public static Object conv(String value, Class<?> type) {
        if (isNotBlank(value) && null != type) {
            return typeMap.get(type.getName()).apply(value);
        }
        throw new IocException("类型转换失败");
    }

    /**
     * 创建代理对象
     *
     * @param obj 要代理的对象
     * @param fun 代理逻辑
     * @return 代理对象
     */
    public static Object createProxyObj(Object obj, MethodAndObjectArrConsume fun) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(), (proxy, method, args) -> fun.exec(method, args));
    }
}
