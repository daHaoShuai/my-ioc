package com.da.config;

import com.da.frame.annotation.Bean;
import com.da.frame.annotation.Configuration;

/**
 * @author Da
 * @description 测试配置类
 * @date 2022-07-27 18:27
 */
@Configuration
public class MyConfig {

    @Bean("hello")
    public String hello() {
        return "hello world";
    }

}
