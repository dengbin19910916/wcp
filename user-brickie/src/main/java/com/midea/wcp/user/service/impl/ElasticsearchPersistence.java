package com.midea.wcp.user.service.impl;

import com.midea.wcp.commons.model.User;
import com.midea.wcp.user.service.Persistence;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticsearchPersistence implements Persistence {

    @Override
    public void save(String appId, List<User> userList) {
    }
}
