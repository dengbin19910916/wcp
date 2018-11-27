package com.midea.wcp.user;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.WechatException;
import com.midea.wcp.commons.Wechat;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.stream.StreamSupport;

@Component
public class UserPorter {

    private final AmqpTemplate amqpTemplate;

    public UserPorter(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Retryable(value = WechatException.class, maxAttempts = 1)
    public void pull(AccessToken accessToken) throws IOException, InterruptedException {
        JsonObject usersJson = Wechat.getResponse(getUrl(accessToken.value(), null));
        JsonArray users = usersJson.get("data").getAsJsonObject().get("openid").getAsJsonArray();
        StreamSupport.stream(users.spliterator(), true).forEach(user -> amqpTemplate.convertAndSend(user.getAsString()));
        String nextOpenId = usersJson.get("next_openid").getAsString();
        while (StringUtils.isEmpty(usersJson.get("next_openid").getAsString())) {
            usersJson = Wechat.getResponse(getUrl(accessToken.value(), nextOpenId));
            StreamSupport.stream(users.spliterator(), true).forEach(user -> {
                amqpTemplate.convertAndSend(user.getAsString());
            });
        }
    }

    private String getUrl(String accessToken, String nextOpenId) {
        return "https://api.weixin.qq.com/cgi-bin/user/get?" +
                "access_token=" + accessToken +
                (StringUtils.isEmpty(nextOpenId) ? "" : "&next_openid=" + nextOpenId);
    }
}
