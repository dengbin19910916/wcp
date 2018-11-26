package com.midea.wcp.commons.model;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;

import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.List;

@Data
@NoArgsConstructor
public class User {

    @javax.persistence.Id
    @Id
    private String appId;

    private String openId;

    @Transient
    @ManyToMany(mappedBy = "users")
    private List<Account> accounts;

    @Transient
    @ManyToMany
    private List<Account.Tag> tags;

    public User(JsonObject user) {

    }
}
