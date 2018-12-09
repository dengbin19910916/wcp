package com.midea.wcp.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
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

    @Reference
    private TokenButler tokenButler;

    private final RabbitMQUtil rabbitMQUtil;

    @Autowired
    public UserPorter(RabbitMQUtil rabbitMQUtil) {
        this.rabbitMQUtil = rabbitMQUtil;
    }


    @Override
    public int pullOpenId(String appId, String appSecret, String host, Integer port) {
        try {
            String urlTemplate = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=_accessToken_&next_openid=";
            String nextOpenId = "";

            int total = 0;

            do {
                AccessToken accessToken = tokenButler.get(appId, appSecret, host, port);
                String url = urlTemplate.replace("_accessToken_", accessToken.value()) + nextOpenId;
                log.info("WeChatToken:{}", accessToken.value());
                JsonObject usersJson = Wechat.getResponse(url, host, port);
                if (usersJson == null) {
                    break;
                }
                int count = usersJson.get("count").getAsInt();
                if (count == 0) {
                    //最后一次，保存粉丝总数total并退出循环
                    total = usersJson.get("total").getAsInt();
                    break;
                }
                rabbitMQUtil.sendToRabbitMQ(appId, appSecret,
                        usersJson.get("data").getAsJsonObject().get("openid").getAsJsonArray(),
                        host, port, "openIdExchange");
                nextOpenId = usersJson.get("next_openid").getAsString();
            } while (true);
            return total;

        } catch (IOException | InterruptedException e) {
            log.error("调用微信接口获取所有openId时出错，同步任务停止.appId:{}", appId);
            e.printStackTrace();
            return 0;
        }

    }


}
