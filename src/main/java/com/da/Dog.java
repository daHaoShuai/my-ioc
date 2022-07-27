package com.da;

import com.da.frame.annotation.Component;
import com.da.frame.annotation.Inject;
import com.da.frame.annotation.Value;
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
    @Value("大黄")
    private String name;
    @Value("10")
    private Integer age;
    @Value("10")
    private Long aLong;
    @Value("2.0")
    private Float aFloat;
    @Value("3.3")
    private Double aDouble;
    @Value("true")
    private Boolean aBoolean;
    @Value("${a}")
    private String a;
    @Value("${b}")
    private int b;
    @Value("${c}")
    private double c;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "user=" + user +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", aLong=" + aLong +
                ", aFloat=" + aFloat +
                ", aDouble=" + aDouble +
                ", aBoolean=" + aBoolean +
                ", a='" + a + '\'' +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}
