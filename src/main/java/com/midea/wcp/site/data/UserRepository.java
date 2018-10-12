package com.midea.wcp.site.data;

import com.midea.wcp.site.model.User;
import com.midea.wcp.site.model.projection.InlineTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = InlineTags.class)
public interface UserRepository extends JpaRepository<User, Integer> {
}
