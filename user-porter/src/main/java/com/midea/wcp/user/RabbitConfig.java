package com.midea.wcp.user;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue user() {
        return new Queue("wcp-sync-user");
    }
}
