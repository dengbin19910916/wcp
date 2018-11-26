package com.midea.wcp.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.commons.Wechat;
import com.midea.wcp.commons.message.UserSyncMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/wechat/users")
public class UserPorterController {

    @Reference
    private TokenButler tokenButler;

    private final UserPorterService userPorterService;
    private final RabbitTemplate rabbitTemplate;

    public UserPorterController(UserPorterService userPorterService, RabbitTemplate rabbitTemplate) {
        this.userPorterService = userPorterService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    @PutMapping
    public void syncUser(@RequestParam String appId, @RequestParam String appSecret,
                         @RequestParam(required = false) String host, @RequestParam(required = false) Integer port)
            throws IOException, InterruptedException {
        userPorterService.syncUser(appId, appSecret, host, port);
    }

    @GetMapping
    public String pull(@RequestParam String appId, @RequestParam String appSecret,
                       @RequestParam(required = false) String host, @RequestParam(required = false) Integer port)
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

    private void send(String appId, String appSecret, JsonArray users, String host, Integer port) {
        users.iterator().forEachRemaining(user -> {
            rabbitTemplate.convertAndSend(new UserSyncMessage(appId, appSecret, user.getAsString(), host, port));
        });
    }
}
