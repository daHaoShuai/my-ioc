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

    public String name = "hello";

    public void say() {
        System.out.println(name + " " + dog);
    }
}
