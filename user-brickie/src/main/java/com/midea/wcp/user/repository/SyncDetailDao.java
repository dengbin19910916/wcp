package com.midea.wcp.user.repository;

import com.midea.wcp.user.model.SyncDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SyncDetailDao extends JpaRepository<SyncDetail, Integer> {
    @Override
    <S extends SyncDetail> S save(S entity);

    @Override
    <S extends SyncDetail> List<S> saveAll(Iterable<S> iterable);
}
