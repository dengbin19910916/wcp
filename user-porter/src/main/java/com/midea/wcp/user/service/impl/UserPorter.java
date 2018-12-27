package com.midea.wcp.user.service.impl;

//import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.JsonObject;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.commons.Wechat;
import com.midea.wcp.user.util.RabbitMQUtil;
import com.midea.wcp.user.service.PullOpenId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("UserPorterNew")
@Slf4j
public class UserPorter implements PullOpenId {

    /*@Reference
    private TokenButler tokenButler;*/

    private final RabbitMQUtil rabbitMQUtil;

    @Autowired
    public UserPorter(RabbitMQUtil rabbitMQUtil) {
        this.rabbitMQUtil = rabbitMQUtil;
    }


    @Override
    public int pullOpenId(String appId, String appSecret, String host, Integer port, String appKey) {
        String urlTemplate = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=_accessToken_&next_openid=";
        String nextOpenId = "";

        int total = 0;

        do {
            /*AccessToken accessToken = tokenButler.get(appId, appSecret, host, port);
            String url = urlTemplate.replace("_accessToken_", accessToken.value()) + nextOpenId;
            log.info("WeChatToken:{}", accessToken.value());*/
            String accessToken = Wechat.getAccessTokenFromWcp(appKey);
            if (accessToken == null) {
                return 0;
            }
            String url = urlTemplate.replace("_accessToken_", accessToken) + nextOpenId;
//            log.info("WeChatToken:{}", accessToken);

            JsonObject usersJson;
            try {
                usersJson = Wechat.getResponse(url, host, port);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
            if (usersJson == null) {
                break;
            } else if (usersJson.has("errcode")) {
                log.error("调用微信拉取用户列表接口返回错误:{}", usersJson.toString());
                return 0;
            }
            int count = usersJson.get("count").getAsInt();
            if (count == 0) {
                //最后一次，保存粉丝总数total并退出循环
                total = usersJson.get("total").getAsInt();
                break;
            }
            rabbitMQUtil.sendToRabbitMQ(appId, appSecret,
                    usersJson.get("data").getAsJsonObject().get("openid").getAsJsonArray(),
                    host, port, "openIdExchange", appKey);
            nextOpenId = usersJson.get("next_openid").getAsString();
        } while (true);

        return total;
    }


}
