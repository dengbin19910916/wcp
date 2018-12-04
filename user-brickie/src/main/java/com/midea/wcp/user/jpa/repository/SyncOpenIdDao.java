package com.midea.wcp.user.jpa.repository;

import com.midea.wcp.user.jpa.model.SyncOpenId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SyncOpenIdDao extends JpaRepository<SyncOpenId, Integer> {
    @Override
    <S extends SyncOpenId> S save(S s);

    @Override
    <S extends SyncOpenId> List<S> saveAll(Iterable<S> iterable);
}
