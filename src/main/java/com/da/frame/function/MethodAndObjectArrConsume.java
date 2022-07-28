package com.da.frame.function;

import java.lang.reflect.Method;

/**
 * @author Da
 * @description 消费代理对象的method和args
 * @date 2022-07-28 10:54
 */
@FunctionalInterface
public interface MethodAndObjectArrConsume {
    Object exec(Method method, Object... args) throws Exception;
}
