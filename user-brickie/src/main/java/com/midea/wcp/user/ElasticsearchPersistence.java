package com.midea.wcp.user;

import com.midea.wcp.commons.model.User;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchPersistence implements Persistence {

    @Override
    public void save(String appId, User user) {
    }
}
