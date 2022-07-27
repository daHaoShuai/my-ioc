package com.da;

import com.da.frame.core.AnnotationAppContext;
import com.da.po.User;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 11:01
 */
public class App {
    public static void main(String[] args) {
        final AnnotationAppContext context = new AnnotationAppContext(App.class);
        context.getBean("my-user", User.class).say();
        final Dog dog = context.getBean("dog", Dog.class);
        final Dog dog1 = context.getBean("config-dog", Dog.class);
        System.out.println(dog);
        System.out.println(dog1);
        final String name = context.getBean("string", String.class);
        System.out.println(name);
    }
}
