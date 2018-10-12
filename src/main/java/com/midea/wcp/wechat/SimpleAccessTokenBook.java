package com.midea.wcp.wechat;

import com.alibaba.fastjson.JSONObject;
import com.midea.wcp.api.AccessToken;
import com.midea.wcp.api.AccessTokenBook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@com.alibaba.dubbo.config.annotation.Service
@RestController
@RequestMapping("/access-token")
public class SimpleAccessTokenBook implements AccessTokenBook {

    private static final String APP_ID = "wx4459fc5695bd3569";
    private static final String APP_SECRET = "6cb276e478c056bae999774cfd008667";

    private volatile AccessToken accessToken;

    /**
     * 返回一个有效的access_token。
     */
    @GetMapping
    @Override
    public AccessToken get() {
        if (accessToken == null || accessToken.isExpired()) {
            synchronized (SimpleAccessTokenBook.class) {
                if (accessToken == null || accessToken.isExpired()) {
                    try {
                        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                                "&appid=" + APP_ID +
                                "&secret=" + APP_SECRET;
                        JSONObject jsonObject = getJSONObject(url);
                        accessToken = new AccessToken(jsonObject.getString("access_token"), jsonObject.getInteger("expires_in"));
                        return accessToken;
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException("获取access_token失败", e);
                    }
                }
            }
        }

        return accessToken;
    }

    private static JSONObject getJSONObject(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JSONObject.parseObject(response.body());
    }
}
