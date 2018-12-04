package com.midea.wcp.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.commons.Wechat;
import com.midea.wcp.commons.message.OpenIdWrapper;
import com.midea.wcp.commons.model.User;
import com.midea.wcp.user.service.CleanUser;
import com.midea.wcp.user.service.SyncUser;
import com.midea.wcp.user.service.impl.CompositePersistence;
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
    private final SyncUser syncUser;
    private final CleanUser cleanUser;

    @Autowired
    public UserBrickie(CompositePersistence compositePersistence, SyncUser syncUser,
                       CleanUser cleanUser) {
        this.compositePersistence = compositePersistence;
        this.syncUser = syncUser;
        this.cleanUser = cleanUser;
    }

    @RabbitListener(queues = "openid-detail")
    public void persistence(OpenIdWrapper openIDWrapper) {
        try {
            List<User> userList = new ArrayList<>();
            for (String openId : openIDWrapper.getOpenIds()) {
                AccessToken accessToken = tokenButler.get(openIDWrapper.getAppId(), openIDWrapper.getAppSecret(),
                        openIDWrapper.getHost(), openIDWrapper.getPort());
                String url = "https://api.weixin.qq.com/cgi-bin/user/info?" +
                        "access_token=" + accessToken.value() + "&openid=" + openId + "&lang=zh_CN";

                JsonObject userDetails = null;

                userDetails = Wechat.getResponse(url, openIDWrapper.getHost(), openIDWrapper.getPort());


                log.info(userDetails != null ? userDetails.toString() : "空");
                User result = (User) JsonToObject(userDetails, User.class);
                userList.add(result);
            }
            compositePersistence.process(openIDWrapper.getAppId(), userList);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Object JsonToObject(JsonObject jsonObject, Class bean) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, bean);
    }


    @RabbitListener(queues = "openid-sync")
    public void saveUserToDB(OpenIdWrapper openIDWrapper) {
        syncUser.saveOpenid(openIDWrapper);
    }


}
