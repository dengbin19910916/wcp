package com.midea.wcp.user.service;

import com.midea.wcp.commons.model.User;

import java.util.List;

public interface Persistence {
    void save(String appId, List<User> userList);
}
