package com.midea.wcp.site.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 公众号。
 */
@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String appid;

    private String name;

    @Enumerated
    private Type type;

    @ManyToMany
    private List<User> users;

    @ManyToMany(mappedBy = "accounts")
    private List<Role> roles;

    public enum Type {
        /**
         * 订阅号。
         */
        SUBSCRIPTION,
        /**
         * 服务号。
         */
        SERVICE
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", users=" + users +
                '}';
    }
}
