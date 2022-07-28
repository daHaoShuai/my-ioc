package com.da;

import com.da.frame.core.AnnotationAppContext;
import com.da.frame.core.AppContext;
import com.da.service.UserService;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 11:01
 */
final public class App {

    public static void main(String[] args) {
        final AnnotationAppContext context = new AnnotationAppContext(App.class);
        final UserService userService = context.getBean("userService", UserService.class);
        userService.list().forEach(System.out::println);
        userService.say();
        AppContext appContext = context.getBean("context", AppContext.class);
        System.out.println(appContext);
    }
}
