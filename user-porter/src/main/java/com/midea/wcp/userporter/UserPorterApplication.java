package com.midea.wcp.userporter;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class UserPorterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserPorterApplication.class, args);
    }
}
