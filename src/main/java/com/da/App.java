package com.da;

import com.da.frame.core.AnnotationAppContext;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 11:01
 */
public class App {
    public static void main(String[] args) {
        final AnnotationAppContext context = new AnnotationAppContext(App.class);
        final Dog dog = context.getBean("Dog", Dog.class);
        dog.say();
    }
}
