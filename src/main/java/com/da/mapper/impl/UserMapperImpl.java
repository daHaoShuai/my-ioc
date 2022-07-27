package com.da.mapper.impl;

import com.da.entity.User;
import com.da.frame.annotation.Component;
import com.da.frame.annotation.Value;
import com.da.mapper.UserMapper;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-27
 * @Time: 10:07
 */
@Component("userMapper")
public class UserMapperImpl implements UserMapper {

    @Value("${name}")
    private String name;

    @Override
    public List<User> list() {
        return Arrays.asList(
                new User("one", 10),
                new User(name, 18),
                new User("two", 20),
                new User("three", 30)
        );
    }
}
