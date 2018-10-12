package com.midea.wcp.wechat;

import org.springframework.util.Assert;

public interface WeChat {

    enum Url {

        ACCESS_TOKEN() {
            public String getUrl(String appid, String secret) {
                Assert.notNull(appid, "appid can not be null.");
                Assert.notNull(secret, "secret can not be null.");

                return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                        "&appid=" + appid +
                        "&secret=" + secret;
            }
        },

        CREATE_MENU() {
            public String getUrl(String accessToken) {
                return "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;
            }
        }
    }
}
