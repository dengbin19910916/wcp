package com.midea.wcp.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.commons.Wechat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wechat/users")
public class UserPorterController {

    @Reference
    private TokenButler tokenButler;

    private final UserPorterService userPorterService;

    public UserPorterController(UserPorterService userPorterService) {
        this.userPorterService = userPorterService;
    }

    @PostMapping
    @PutMapping
    public void syncUser(@RequestParam String appId, @RequestParam String appSecret) throws IOException, InterruptedException {
        userPorterService.syncUser(appId, appSecret);
    }

    @GetMapping
    public List<String> pull(@RequestParam String appId, @RequestParam String appSecret) throws IOException, InterruptedException {
        List<String> openIds = new ArrayList<>();
        AccessToken accessToken = tokenButler.get(appId, appSecret);
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?" +
                "access_token=" + accessToken.value() +
                "&next_openid=";
        JsonObject usersJson = Wechat.getResponse(url);
        JsonArray users = usersJson.get("data").getAsJsonObject().get("openid").getAsJsonArray();
        users.iterator().forEachRemaining(user -> openIds.add(user.getAsString()));
        String nextOpenId = usersJson.get("next_openid").getAsString();
        while (StringUtils.isEmpty(usersJson.get("next_openid").getAsString())) {
            url = "https://api.weixin.qq.com/cgi-bin/user/get?" +
                    "access_token=" + accessToken.value() +
                    "&next_openid=" + nextOpenId;
            usersJson = Wechat.getResponse(url);
            users.iterator().forEachRemaining(user -> openIds.add(user.getAsString()));
        }

        return openIds;
    }
}
