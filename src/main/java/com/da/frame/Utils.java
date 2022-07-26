package com.da.frame;

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




}
