package com.midea.wcp.user;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue user() {
        return new Queue("user");
    }


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("sync-user");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(user()).to(topicExchange()).with("WeChat");
    }

}
