package com.midea.wcp.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserPorterService {

    @Reference
    private TokenButler tokenButler;
    private final UserPorter userPorter;

    @Autowired
    public UserPorterService(UserPorter userPorter) {
        this.userPorter = userPorter;
    }

    public void syncUser(String appId, String appSecret) throws IOException, InterruptedException {
        AccessToken accessToken = tokenButler.get(appId, appSecret);
        userPorter.pull(accessToken);
    }
}
