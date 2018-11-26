package com.midea.wcp.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.commons.Wechat;
import com.midea.wcp.commons.message.UserSyncMessage;
import com.midea.wcp.commons.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RabbitListener(queues = "wechat-user")
@Component
public class UserBrickie {

    @Reference
    private TokenButler tokenButler;

    private final CompositePersistence compositePersistence;

    public UserBrickie(CompositePersistence compositePersistence) {
        this.compositePersistence = compositePersistence;
    }

    @RabbitHandler
    public void persistence(UserSyncMessage message) throws IOException, InterruptedException {
        AccessToken accessToken = tokenButler.get(message.getAppId(), message.getAppSecret(), message.getHost(), message.getPort());
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?" +
                "access_token=" + accessToken.value() +
                "&openid=" + message.getOpenId() +
                "&lang=zh_CN";
        User user = new User(Wechat.getResponse(url));
        compositePersistence.save(message.getAppId(), user);
    }
}
