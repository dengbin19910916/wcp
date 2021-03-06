package com.midea.wcp.tokenbutler;

import com.google.gson.JsonObject;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.commons.Wechat;
import com.midea.wcp.commons.WechatException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@com.alibaba.dubbo.config.annotation.Service
@Component
public class SingleTokenButler implements TokenButler {

    private volatile ConcurrentHashMap<String, AccessToken> accessTokens = new ConcurrentHashMap<>();

    private final CuratorFramework client;

    public SingleTokenButler(CuratorFramework client) {
        this.client = client;
    }

    @SneakyThrows
    @Override
    public AccessToken get(String appId, String appSecret,
                           String host, Integer port) {
        AccessToken accessToken = accessTokens.get(appId);
        if (accessToken == null || accessToken.isExpired()) {
            InterProcessLock lock = new InterProcessMutex(client, "/wcp/lock/accessToken/" + appId);
            if (lock.acquire(0, TimeUnit.SECONDS))
                if (accessToken == null || accessToken.isExpired()) {
                    try {
                        String uri = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                                "&appid=" + appId +
                                "&secret=" + appSecret;
                        JsonObject tokenJson = Wechat.getResponse(uri, host, port);

                        if (tokenJson.has("errcode")) {
                            log.error("获取access_token失败，{}", tokenJson.toString());
                            throw new WechatException(tokenJson);
                        }
                        accessToken = new AccessToken(tokenJson.get("access_token").getAsString(), tokenJson.get("expires_in").getAsInt());
                        accessTokens.put(appId, accessToken);
                        return accessToken;
                    } catch (IOException | InterruptedException e) {
                        log.error(appId + "[" + appSecret + "]获取access_token失败", e);
                        throw new RuntimeException(appId + "获取access_token失败", e);
                    }
                }
        }
        return accessToken;
    }
}
