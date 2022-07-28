package com.da.service;

import com.da.entity.User;

import java.util.List;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-27
 * @Time: 10:01
 */
public interface UserService {
    List<User> list();

    void say();
}
