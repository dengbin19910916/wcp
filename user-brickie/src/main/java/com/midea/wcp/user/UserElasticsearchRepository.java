package com.midea.wcp.user;

import com.midea.wcp.commons.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserElasticsearchRepository extends ElasticsearchRepository<User, String> {
}
