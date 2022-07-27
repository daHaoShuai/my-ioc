package com.da.config;

import com.da.Dog;
import com.da.frame.annotation.Bean;
import com.da.frame.annotation.Configuration;
import com.da.frame.annotation.Value;
import com.da.po.User;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-27
 * @Time: 9:17
 */
@Configuration
public class MyConfig {

    @Value("配置类")
    private String name;

    @Value("${a}")
    private String a;

    @Bean
    public User getUser() {
        final User user = new User();
        user.name = "我是" + name + a + "的user";
        return user;
    }

    @Bean("config-dog")
    public Dog getDog() {
        final Dog dog = new Dog();
        dog.setName(name + a + "中的dog");
        return dog;
    }

    @Bean
    public String name() {
        return "hello world";
    }

}
