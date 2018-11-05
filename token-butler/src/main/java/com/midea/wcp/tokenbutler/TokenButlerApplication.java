package com.midea.wcp.tokenbutler;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubboConfiguration
@SpringBootApplication
public class TokenButlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenButlerApplication.class, args);
    }
}
