package com.midea.wcp.user.service.impl;

import com.midea.wcp.commons.model.User;
import com.midea.wcp.user.service.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompositePersistence implements Persistence {

    private final DatabasePersistence databasePersistence;
    private final ElasticsearchPersistence elasticsearchPersistence;

    @Autowired
    public CompositePersistence(DatabasePersistence databasePersistence, ElasticsearchPersistence elasticsearchPersistence) {
        this.databasePersistence = databasePersistence;
        this.elasticsearchPersistence = elasticsearchPersistence;
    }

    @Override
    public void save(String appId, List<User> userList) {
        databasePersistence.save(appId, userList);
        elasticsearchPersistence.save(appId, userList);
    }
}
