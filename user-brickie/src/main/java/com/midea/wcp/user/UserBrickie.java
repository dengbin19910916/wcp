package com.midea.wcp.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.commons.Wechat;
import com.midea.wcp.commons.message.UserSyncMessage;
import com.midea.wcp.commons.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RabbitListener(queues = "wcp-sync-user")
@Component
public class UserBrickie {

    private Gson gson = new Gson();

    @Reference(check = false)
    private TokenButler tokenButler;

    private final CompositePersistence compositePersistence;

    public UserBrickie(CompositePersistence compositePersistence) {
        this.compositePersistence = compositePersistence;
    }

    @RabbitHandler
    public void persistence(UserSyncMessage message) throws IOException, InterruptedException {
        AccessToken accessToken = tokenButler.get(message.getAppId(), message.getAppSecret(), message.getHost(), message.getPort());
        String uri = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + accessToken.value();

        JsonObject result = Wechat.postResponse(uri, getBody(message.getOpenIds()), message.getHost(), message.getPort());
        JsonArray usersJson = result.get("user_info_list").getAsJsonArray();
        usersJson.forEach(jsonElement -> {
            User user = new User(message.getAppId(), jsonElement.getAsJsonObject());
            compositePersistence.save(message.getAppId(), user);
        });
    }

    private String getBody(List<String> openIds) {
        Map<String, List<Map<String, String>>> body = new HashMap<>();
        List<Map<String, String>> users = new ArrayList<>(openIds.size());
        for (String openId : openIds) {
            Map<String, String> user = new HashMap<>(2);
            user.put("openid", openId);
            user.put("lang", "zh_CN");
            users.add(user);
        }
        body.put("user_list", users);
        return gson.toJson(body);
    }
}
