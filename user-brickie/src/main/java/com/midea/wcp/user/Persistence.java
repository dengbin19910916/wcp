package com.midea.wcp.user;

import com.midea.wcp.commons.model.User;

public interface Persistence {

    void save(String appId, User user);
}
