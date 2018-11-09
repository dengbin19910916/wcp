package com.midea.wcp.user;

import com.midea.wcp.commons.model.User;

import com.alibaba.dubbo.config.annotation.Reference;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.commons.Wechat;
import com.midea.wcp.commons.message.UserSyncMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@RabbitListener(queues = "wechat-user")
@Component
public class UserBrickie {

    @Reference
    private TokenButler tokenButler;

    private final DatabasePersistence databasePersistence;
    private final ElasticsearchPersistence elasticsearchPersistence;

    public UserBrickie(DatabasePersistence databasePersistence, ElasticsearchPersistence elasticsearchPersistence) {
        this.databasePersistence = databasePersistence;
        this.elasticsearchPersistence = elasticsearchPersistence;
    }

    //    @RabbitHandler
    public void persistence(UserSyncMessage message) throws IOException, InterruptedException {
        AccessToken accessToken = tokenButler.get(message.getAppId(), message.getAppSecret());
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?" +
                "access_token=" + accessToken.value() +
                "&openid=" + message.getOpenId() +
                "&lang=zh_CN";
        User user = new User(Wechat.getResponse(url));
        databasePersistence.save(message.getAppId(), user);
        elasticsearchPersistence.save(message.getAppId(), user);
    }
}
