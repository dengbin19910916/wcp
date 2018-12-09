package com.midea.wcp.user.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.midea.wcp.commons.message.OpenIdWrapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RabbitMQUtil {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQUtil(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToRabbitMQ(String appId, String appSecret, JsonArray openidArray, String host, Integer port, String Exchange) {
        List<String> openIds = new ArrayList<>();
        for (JsonElement openid : openidArray) {
            openIds.add(openid.getAsString());
        }
        rabbitTemplate.convertAndSend(Exchange, "WeChat", new OpenIdWrapper(openIds, appId, appSecret, host, port));
    }
}
