package com.midea.wcp.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.commons.InvalidTokenException;
import com.midea.wcp.commons.Wechat;
import com.midea.wcp.commons.message.UserSyncMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserPorter {

    @Reference
    private TokenButler tokenButler;

    private final AmqpTemplate amqpTemplate;

    public UserPorter(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Retryable(value = InvalidTokenException.class, maxAttempts = 1)
    String pull(String appId, String appSecret, String host, Integer port)
            throws IOException, InterruptedException {
        AccessToken accessToken = tokenButler.get(appId, appSecret, host, port);

        String uri = getUri(accessToken.value(), null);
        JsonObject usersJson = Wechat.getResponse(uri, host, port);
        send(appId, appSecret, usersJson.get("data").getAsJsonObject().get("openid").getAsJsonArray(), host, port);

        String nextOpenId = usersJson.get("next_openid").getAsString();
        while (!StringUtils.isEmpty(nextOpenId)) {
            uri = getUri(accessToken.value(), nextOpenId);
            usersJson = Wechat.getResponse(uri, host, port);
            send(appId, appSecret, usersJson.get("data").getAsJsonObject().get("openid").getAsJsonArray(), host, port);
        }

        return usersJson.get("next_openid").getAsString();
    }

    private String getUri(String accessToken, String nextOpenId) {
        return "https://api.weixin.qq.com/cgi-bin/user/get?" +
                "access_token=" + accessToken +
                (StringUtils.isEmpty(nextOpenId) ? "" : "&next_openid=" + nextOpenId);
    }

    private void send(String appId, String appSecret, JsonArray users, String host, Integer port) {
        int count = 0;
        List<String> openIds = new ArrayList<>(100);
        for (int i = 0; i < users.size(); i++) {
            openIds.add(users.get(count++).getAsString());
            if (count == 100 || count == users.size() - 1) {
                amqpTemplate.convertAndSend("wcp-sync-user", new UserSyncMessage(appId, appSecret, openIds, host, port));
                count = 0;
            }
        }
    }
}
