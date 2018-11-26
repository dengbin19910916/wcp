package com.midea.wcp.user;

import com.midea.wcp.commons.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchPersistence implements Persistence {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(String appId, User user) {
       userRepository.save(user);
    }
}
