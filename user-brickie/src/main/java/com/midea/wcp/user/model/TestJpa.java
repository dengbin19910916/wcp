package com.midea.wcp.user.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
//@NamedQuery(name = "", query = "")
@Table(name = "cp_account")
public class TestJpa implements Serializable {

    private static final long serialVersionUID = -7548493553498407695L;

    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "corp_id")
    private String corp_id;
    @Column(name = "service_domain")
    private String service_domain;
    @Column(name = "security_key")
    private String security_key;
    @Column(name = "proxy_host")
    private String proxy_host;
    @Column(name = "proxy_port")
    private String proxy_port;
    @Column(name = "proxy_user")
    private String proxy_user;
    @Column(name = "proxy_password")
    private String proxy_password;
    @Column(name = "created_at")
    private Date created_at;
    @Column(name = "updated_at")
    private Date updated_at;
}
