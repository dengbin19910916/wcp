package com.midea.wcp.tokenbutler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.TokenButler;
import com.midea.wcp.api.WechatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@com.alibaba.dubbo.config.annotation.Service
@Component
public class SingleTokenButler implements TokenButler {

    private volatile ConcurrentHashMap<String, AccessToken> accessTokens = new ConcurrentHashMap<>();

    private final HttpClient client;

    public SingleTokenButler(HttpClient client) {
        this.client = client;
    }

    /**
     * 返回一个有效的access_token。
     */
    @Override
    public AccessToken get(String appId, String appSecret) {
        AccessToken accessToken = accessTokens.get(appId);
        if (accessToken == null || accessToken.isExpired()) {
            synchronized (this) {
                if (accessToken == null || accessToken.isExpired()) {
                    try {
                        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                                "&appid=" + appId +
                                "&secret=" + appSecret;
                        JsonObject tokenJson = getAccessTokenJson(url);

                        if (tokenJson.has("errcode")) {
                            log.error("获取access_token失败，{}", tokenJson.toString());
                            throw new WechatException(tokenJson);
                        }
                        accessToken = new AccessToken(tokenJson.get("access_token").getAsString(), tokenJson.get("expires_in").getAsInt());
                        accessTokens.put(appId, accessToken);
                        return accessToken;
                    } catch (IOException | InterruptedException e) {
                        log.error("{}[{}]获取access_token失败", appId, appSecret);
                        throw new RuntimeException(appId + "获取access_token失败", e);
                    }
                }
            }
        }

        return accessToken;
    }

    private JsonObject getAccessTokenJson(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new JsonParser().parse(response.body()).getAsJsonObject();
    }
}
