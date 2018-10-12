package com.midea.wcp.site.data;

import com.midea.wcp.site.model.Role;
import com.midea.wcp.site.model.projection.InlineAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = InlineAccounts.class)
public interface RoleRepository extends JpaRepository<Role, Integer> {
}