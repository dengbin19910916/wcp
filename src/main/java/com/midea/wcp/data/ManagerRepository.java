package com.midea.wcp.data;

import com.midea.wcp.domain.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RestResource;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    @RestResource(path = "/name", description = @Description("根据名称查询协作员"))
    Page<Manager> findByNameLike(@Param("name") String name, Pageable pageable);
}
