package com.midea.wcp.user.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    //拉取当前完整openid到数据库表对比用
    @Bean
    public Queue openIdQueue() {
        return new Queue("openIdQueue");
    }

    @Bean
    public TopicExchange openIdExchange() {
        return new TopicExchange("openIdExchange");
    }

    @Bean
    public Binding bindingSync() {
        return BindingBuilder.bind(openIdQueue()).to(openIdExchange()).with("WeChat");
    }


    //拉取所有detail
    @Bean
    public Queue getDetailQueue() {
        return new Queue("getDetailQueue");
    }

    @Bean
    public TopicExchange getDetailExchange() {
        return new TopicExchange("getDetailExchange");
    }

    @Bean
    public Binding bindingDetail() {
        return BindingBuilder.bind(getDetailQueue()).to(getDetailExchange()).with("WeChat");
    }


}
