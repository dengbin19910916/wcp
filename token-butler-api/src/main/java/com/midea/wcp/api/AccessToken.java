package com.midea.wcp.api;

import lombok.NonNull;

import java.io.Serializable;
import java.time.Instant;

/**
 * 调用微信接口的凭证。
 */
public class AccessToken implements Serializable {

    private final String value;
    private final Instant expirationTime;

    public AccessToken(@NonNull String accessToken, int expiresIn) {
        this.value = accessToken;
        this.expirationTime = Instant.now().plusSeconds(expiresIn - 300);   // 防止客户端获取的token立即失效，但不能解决并发实现的问题
    }

    /**
     * 返回凭证。
     *
     * @return 凭证
     */
    @SuppressWarnings("unused")
    public String value() {
        return value;
    }

    /**
     * 判断access_token是否过期。
     *
     * @return true - 已过期，false - 未过期。
     */
    public boolean isExpired() {
        return Instant.now().isAfter(expirationTime);
    }

    /**
     * 返回距离到期时间还剩多长时间。
     *
     * @return 剩余时间（秒）
     */
    @SuppressWarnings("unused")
    public int getExpiresIn() {
        return (int) (expirationTime.getEpochSecond() - Instant.now().getEpochSecond());
    }

    @Override
    public String toString() {
        return value;
    }
}