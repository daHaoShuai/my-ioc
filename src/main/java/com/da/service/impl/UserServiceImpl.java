package com.da.service.impl;

import com.da.entity.User;
import com.da.frame.annotation.Component;
import com.da.frame.annotation.Inject;
import com.da.frame.core.BeanPostProcessor;
import com.da.frame.util.Utils;
import com.da.mapper.UserMapper;
import com.da.service.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-27
 * @Time: 10:02
 */
@Component("userService")
public class UserServiceImpl implements UserService, BeanPostProcessor {

    @Inject
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public Object postProcessorAfterInitialization(String beanName, Object bean) {
//        可以在这2个方法中实现代理逻辑,返回当前对象的代理对象
        return Utils.createProxyObj(bean, (method, args) -> {
            System.out.println(method.getName() + "方法执行前");
            Object invoke = method.invoke(bean, args);
            System.out.println(method.getName() + "方法执行后");
            return invoke;
        });
    }
}
