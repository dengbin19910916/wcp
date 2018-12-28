package com.midea.wcp.user.service;

public interface CleanUser {
    void cleanOpenIdTable(String table);

    void cleanDuplicateOpenId(String table);

    void setSubZero2One(String originTable, String compareTable);

    void setSubOne2Zero(String originTable, String compareTable);

    void pullNullData(String originTable, String compareTable, String appId, String appSecret, String host, Integer port, String appKey);

    Integer countOpenId(String table);

    Integer setSubNull2Zero(String originTable);
}
