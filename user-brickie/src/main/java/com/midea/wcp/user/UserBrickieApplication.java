package com.midea.wcp.user;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDubboConfiguration
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.midea.wcp.user.jpa.repository"})
@EntityScan("com.midea.wcp.user.jpa.model")
@MapperScan("com.midea.wcp.user.mybatis.mapper")
public class UserBrickieApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserBrickieApplication.class, args);
    }
}
