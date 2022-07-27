package com.da.service.impl;

import com.da.entity.User;
import com.da.frame.annotation.Component;
import com.da.frame.annotation.Inject;
import com.da.mapper.UserMapper;
import com.da.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-27
 * @Time: 10:02
 */
@Component("userService")
public class UserServiceImpl implements UserService {

    @Inject
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }
}
