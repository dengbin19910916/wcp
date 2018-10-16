package com.midea.wcp.site.data;

import com.midea.wcp.site.model.Assistant;
import com.midea.wcp.site.model.projection.InlineRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(excerptProjection = InlineRole.class)
public interface ManagerRepository extends JpaRepository<Assistant, Integer>, JpaSpecificationExecutor<Assistant> {

    @RestResource
    Page<Assistant> findByUidLikeOrNameLike(@Param("uid") String uid, @Param("name") String name, Pageable pageable);
}
