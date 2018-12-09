package com.midea.wcp.user;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubboConfiguration
@SpringBootApplication
@MapperScan("com.midea.wcp.user.mybatis.mapper")
public class UserPorterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserPorterApplication.class, args);
    }
}
