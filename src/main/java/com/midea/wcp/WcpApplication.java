package com.midea.wcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class WcpApplication {

    public static void main(String[] args) {
        SpringApplication.run(WcpApplication.class, args);
    }
}
