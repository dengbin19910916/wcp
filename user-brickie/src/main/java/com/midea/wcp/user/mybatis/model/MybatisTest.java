package com.midea.wcp.user.mybatis.model;

import lombok.Data;

import java.util.Date;

@Data
public class MybatisTest {

    private int id;
    private String name;
    private String corp_id;
    private String service_domain;
    private String security_key;
    private String proxy_host;
    private String proxy_port;
    private String proxy_user;
    private String proxy_password;
    private Date created_at;
    private Date updated_at;
}
