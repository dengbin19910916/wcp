package com.midea.wcp.user;

import com.midea.wcp.commons.model.User;
import org.springframework.stereotype.Component;

@Component
public class DatabasePersistence implements Persistence {

    private final UserJpaRepository userJpaRepository;

    public DatabasePersistence(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public void save(String appId, User user) {
        userJpaRepository.save(user);
    }
}
