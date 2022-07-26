package com.da.po;

import com.da.Dog;
import com.da.frame.annotation.Component;
import com.da.frame.annotation.Inject;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 11:07
 */
@Component("my-user")
public class User {

    @Inject
    private Dog dog;

    @Inject("dog")
    private Dog dog1;

    public String name = "hello";

    public void say() {
        System.out.println(name + " " + dog + " " + dog1);
        dog.say();
    }
}
