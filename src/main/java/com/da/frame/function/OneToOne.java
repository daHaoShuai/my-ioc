package com.da.frame.function;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 11:31
 * 传入一个值返回一个值
 */
@FunctionalInterface
public interface OneToOne<T> {
    T exec(T t);
}
