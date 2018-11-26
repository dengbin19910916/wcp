package com.midea.wcp.api;

/**
 * 管理所有公众号访问微信的access_token。
 */
public interface TokenButler {

    /**
     * 返回一个用来访问微信接口的access_token。
     *
     * @param appId     公众号ID
     * @param appSecret 公众号密钥
     * @param host      代理服务器的地址
     * @param port      代理服务器的端口
     * @return access_token最少具有300秒的有效期
     */
    AccessToken get(String appId, String appSecret,
                    String host, Integer port);
}
