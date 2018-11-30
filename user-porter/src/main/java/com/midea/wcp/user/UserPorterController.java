package com.midea.wcp.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.commons.Wechat;
import com.midea.wcp.commons.message.OpenIdWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wechat/users")
@Slf4j
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
    public void pull(@RequestParam String appId, @RequestParam String appSecret,
                     @RequestParam(required = false) String host, @RequestParam(required = false) Integer port)
            throws IOException, InterruptedException {
        AccessToken accessToken;
        String url;

        String urlTemplate = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=_accessToken_&next_openid=";
        String nextOpenId = "";

        do {
            accessToken = tokenButler.get(appId, appSecret, host, port);
            url = urlTemplate.replace("_accessToken_", accessToken.value()) + nextOpenId;
            log.info("WeChatToken:{}", accessToken.value());
            JsonObject usersJson = Wechat.getResponse(url);
            nextOpenId = usersJson.get("next_openid").getAsString();
            if (!StringUtils.isEmpty(nextOpenId)) {
                send(appId, appSecret, usersJson.get("data").getAsJsonObject().get("openid").getAsJsonArray(), host, port);
            }
        } while (!StringUtils.isEmpty(nextOpenId));
    }

    private void send(String appId, String appSecret, JsonArray openidArray, String host, Integer port) {
        List<String> openIds = new ArrayList<>();
        for (JsonElement openid : openidArray) {
            openIds.add(openid.getAsString());
        }
        rabbitTemplate.convertAndSend("sync-user", "WeChat", new OpenIdWrapper(openIds, appId, appSecret, host, port));
    }
}
