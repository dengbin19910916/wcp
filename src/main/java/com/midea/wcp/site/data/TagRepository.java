package com.midea.wcp.site.data;

import com.midea.wcp.site.model.User;
import com.midea.wcp.site.model.projection.InlineUsersCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = InlineUsersCount.class)
public interface TagRepository extends JpaRepository<User.Tag, Integer> {
}
