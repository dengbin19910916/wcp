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
    public Queue openidSync() {
        return new Queue("openid-sync");
    }

    @Bean
    public TopicExchange sync() {
        return new TopicExchange("sync");
    }

    @Bean
    public Binding bindingSync() {
        return BindingBuilder.bind(openidSync()).to(sync()).with("WeChat");
    }


    //拉取所有detail，第一次用
    @Bean
    public Queue openidDetail() {
        return new Queue("openid-detail");
    }

    @Bean
    public TopicExchange detail() {
        return new TopicExchange("detail");
    }

    @Bean
    public Binding bindingDetail() {
        return BindingBuilder.bind(openidDetail()).to(detail()).with("WeChat");
    }

    //程序对比
//    @Bean
//    public Queue openidContrast() {
//        return new Queue("openid-contrast");
//    }
//
//    @Bean
//    public TopicExchange contrast() {
//        return new TopicExchange("contrast");
//    }
//
//    @Bean
//    public Binding bindingContrast() {
//        return BindingBuilder.bind(openidContrast()).to(contrast()).with("WeChat");
//    }

}
