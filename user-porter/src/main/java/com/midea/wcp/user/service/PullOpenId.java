package com.midea.wcp.user.service;

public interface PullOpenId {
    int pullOpenId(String appId, String appSecret, String host, Integer port,String appKey);
}
