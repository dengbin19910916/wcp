package com.midea.wcp.user;

import com.midea.wcp.commons.model.User;
import org.springframework.stereotype.Component;

@Component
public class CompositePersistence implements Persistence {

    private final DatabasePersistence databasePersistence;
    private final ElasticsearchPersistence elasticsearchPersistence;

    public CompositePersistence(DatabasePersistence databasePersistence, ElasticsearchPersistence elasticsearchPersistence) {
        this.databasePersistence = databasePersistence;
        this.elasticsearchPersistence = elasticsearchPersistence;
    }

    @Override
    public void save(String appId, User user) {
        databasePersistence.save(appId, user);
        elasticsearchPersistence.save(appId, user);
    }
}
