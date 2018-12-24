package com.midea.wcp.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.commons.Wechat;
import com.midea.wcp.commons.message.OpenIdWrapper;
import com.midea.wcp.commons.model.User;
import com.midea.wcp.user.service.impl.CompositePersistence;
import com.midea.wcp.user.service.impl.DatabasePersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class UserBrickie {

    @Reference
    private TokenButler tokenButler;

    private final CompositePersistence compositePersistence;
    private final DatabasePersistence databasePersistence;

    @Autowired
    public UserBrickie(CompositePersistence compositePersistence, DatabasePersistence databasePersistence) {
        this.compositePersistence = compositePersistence;
        this.databasePersistence = databasePersistence;
    }

    @RabbitListener(queues = "openIdQueue")
    public void openIdQueueListener(OpenIdWrapper openIDWrapper) {
        databasePersistence.saveOpenid(openIDWrapper);
    }

    /**
     * 从微信拉取openid的信息，交由CompositePersistence处理
     */
    @RabbitListener(queues = "getDetailQueue")
    public void persistence(OpenIdWrapper openIDWrapper) {
        List<User> userList = new ArrayList<>();
        if (openIDWrapper.getOpenIds() == null) {
            return;
        }
        for (String openId : openIDWrapper.getOpenIds()) {
            User result = getUserDetail(openId, openIDWrapper.getAppId(), openIDWrapper.getAppSecret(), openIDWrapper.getHost(), openIDWrapper.getPort());
            if (result != null) {
                result.setAppId(openIDWrapper.getAppId());
                userList.add(result);
            }
        }
        if (userList.size() != 0) {
            compositePersistence.process(openIDWrapper.getAppId(), userList);
        }

    }

    private Object JsonToObject(JsonObject jsonObject, Class bean) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, bean);
    }

    private User getUserDetail(String openId, String appId, String appSecret, String host, int port) {
        try {
            AccessToken accessToken = tokenButler.get(appId, appSecret, host, port);
            String url = "https://api.weixin.qq.com/cgi-bin/user/info?" +
                    "access_token=" + accessToken.value() + "&openid=" + openId + "&lang=zh_CN";
            JsonObject userDetails = Wechat.getResponse(url, host, port);
            log.info(userDetails != null ? userDetails.toString() : "空");
            return (User) JsonToObject(userDetails, User.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }


}
