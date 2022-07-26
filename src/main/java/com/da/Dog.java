package com.da;

import com.da.frame.annotation.Component;
import com.da.frame.annotation.Inject;
import com.da.po.User;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 11:08
 */
@Component
public class Dog {

    @Inject("my-user")
    private User user;

    public void say() {
        System.out.println("hello world " + user.name);
    }
}
