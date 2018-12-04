package com.midea.wcp.user.service;

import com.midea.wcp.commons.model.User;

import java.util.List;

public interface DetailHandler {
    void process(String appId, List<User> userList);
}
