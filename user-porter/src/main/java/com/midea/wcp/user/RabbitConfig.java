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
    public Queue gkd() {
        return new Queue("gkd");
    }

    @Bean
    public TopicExchange topicSyncUser() {
        return new TopicExchange("sync-user");
    }

    @Bean
    public Binding bindingSync() {
        return BindingBuilder.bind(gkd()).to(topicSyncUser()).with("WeChat");
    }


    @Bean
    public Queue user() {
        return new Queue("user");
    }

    @Bean
    public TopicExchange topicGetDetail() {
        return new TopicExchange("get-detail");
    }

    @Bean
    public Binding bindingDetail() {
        return BindingBuilder.bind(user()).to(topicGetDetail()).with("WeChat");
    }
}
