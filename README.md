# 按自己的理解写一个简单的ioc容器

```java
package com.da;

import com.da.frame.core.AnnotationAppContext;
import com.da.po.User;

public class App {
    public static void main(String[] args) {
        final AnnotationAppContext context = new AnnotationAppContext(App.class);
        final Dog dog = context.getBean("dog", Dog.class);
        final User user = (User) context.getBean("my-user");
        dog.say(); // hello world hello
        user.say();
        // hello com.da.Dog@4769b07b
        // hello world hello
    }
}
```

> 使用@Component交给容器管理,用@Inject注入(没有值就尝试使用属性名字查找bean)

```java
package com.da.po;

import com.da.Dog;
import com.da.frame.annotation.Component;
import com.da.frame.annotation.Inject;

@Component("my-user")
public class User {

    @Inject
    private Dog dog;

    public String name = "hello";

    public void say() {
        System.out.println(name + " " + dog);
        dog.say();
    }
}
```

```java
package com.da;

import com.da.frame.annotation.Component;
import com.da.frame.annotation.Inject;
import com.da.po.User;

@Component
public class Dog {

    @Inject("my-user")
    private User user;

    public void say() {
        System.out.println("hello world " + user.name);
    }
}
```

> 使用@Value注入值

```java
package com.da;

import com.da.frame.annotation.Component;
import com.da.frame.annotation.Inject;
import com.da.frame.annotation.Value;
import com.da.po.User;

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
                '}';
    }
}
```
> 使用配置类注入bean,如果@Bean没有指定值,默认使用方法返回类型的名字首字母小写作为key
```java
package com.da.config;

import com.da.Dog;
import com.da.frame.annotation.Bean;
import com.da.frame.annotation.Configuration;
import com.da.frame.annotation.Value;
import com.da.po.User;

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
```
```java
import com.da.frame.core.AnnotationAppContext;
import com.da.po.User;

public class App {
    public static void main(String[] args) {
        final AnnotationAppContext context = new AnnotationAppContext(App.class);
        context.getBean("my-user", User.class).say();
        final Dog dog = context.getBean("dog", Dog.class);
        final Dog dog1 = context.getBean("config-dog", Dog.class);
        System.out.println(dog);
        System.out.println(dog1);
        final String name = context.getBean("string", String.class);
        System.out.println(name);
    }
}
```
