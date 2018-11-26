package com.midea.wcp.user;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubboConfiguration
@SpringBootApplication
public class UserBrickieApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserBrickieApplication.class, args);
    }
}
