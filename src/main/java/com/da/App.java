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
        final Dog dog = context.getBean("dog", Dog.class);
        final User user = (User) context.getBean("my-user");
        dog.say();
        user.say();
    }
}
