package com.midea.wcp.commons.model;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToMany;
import java.util.List;

@Data
@NoArgsConstructor
public class User {

    private String appId;
    private String openId;
    @ManyToMany(mappedBy = "users")
    private List<Account> accounts;
    @ManyToMany
    private List<Account.Tag> tags;

    public User(JsonObject user) {

    }
}
