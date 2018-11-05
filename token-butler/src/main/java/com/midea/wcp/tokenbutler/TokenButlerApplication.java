package com.midea.wcp.tokenbutler;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubboConfig
@SpringBootApplication
public class TokenButlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenButlerApplication.class, args);
    }
}
