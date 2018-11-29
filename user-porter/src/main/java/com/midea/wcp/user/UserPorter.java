package com.midea.wcp.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.api.WechatException;
import com.midea.wcp.commons.Wechat;
import com.midea.wcp.commons.message.UserSyncMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class UserPorter {

    @Reference
    private TokenButler tokenButler;

    private final AmqpTemplate amqpTemplate;

    public UserPorter(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Retryable(value = WechatException.class, maxAttempts = 1)
    public String pull(String appId, String appSecret, String host, Integer port)
            throws IOException, InterruptedException {
        AccessToken accessToken = tokenButler.get(appId, appSecret, host, port);
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?" +
                "access_token=" + accessToken.value() +
                "&next_openid=";
        JsonObject usersJson = Wechat.getResponse(url);
        send(appId, appSecret, usersJson.get("data").getAsJsonObject().get("openid").getAsJsonArray(), host, port);
        String nextOpenId = usersJson.get("next_openid").getAsString();
        while (StringUtils.isEmpty(usersJson.get("next_openid").getAsString())) {
            url = "https://api.weixin.qq.com/cgi-bin/user/get?" +
                    "access_token=" + accessToken.value() +
                    "&next_openid=" + nextOpenId;
            usersJson = Wechat.getResponse(url);
            send(appId, appSecret, usersJson.get("data").getAsJsonObject().get("openid").getAsJsonArray(), host, port);
        }

        return usersJson.get("next_openid").getAsString();
    }

    private String getUrl(String accessToken, String nextOpenId) {
        return "https://api.weixin.qq.com/cgi-bin/user/get?" +
                "access_token=" + accessToken +
                (StringUtils.isEmpty(nextOpenId) ? "" : "&next_openid=" + nextOpenId);
    }

    private void send(String appId, String appSecret, JsonArray users, String host, Integer port) {
        users.iterator().forEachRemaining(user ->
                amqpTemplate.convertAndSend("wcp-sync-user", new UserSyncMessage(appId, appSecret, user.getAsString(), host, port)));
    }
}
