package com.midea.wcp.user.jpa.repository;

import com.midea.wcp.user.jpa.model.TestJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TestSyncDao extends JpaRepository<TestJpa, Integer> {
    TestJpa findTestJpaById(Integer id);

    @Query(value = "select * from cp_account where id = ?1 and name = ?2", nativeQuery = true)
    TestJpa findBySql(Integer id, String name);

    @Query(value = "insert into ", nativeQuery = true)
    int insertBySql();
}
