package com.midea.wcp.data;

import com.midea.wcp.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    @RestResource(path = "/name", rel = "/name")
    Manager findByNameLike(@Param("name") String name);
}
