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
