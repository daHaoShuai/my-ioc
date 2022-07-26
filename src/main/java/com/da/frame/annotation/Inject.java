package com.da.frame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 10:28
 * 用来给容器管理的类注入属性
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
    String value() default "";
}
