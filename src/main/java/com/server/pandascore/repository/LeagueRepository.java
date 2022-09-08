package com.server.pandascore.repository;

import com.server.pandascore.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Long> {
    @Query(value = "select * from league where name = ?1", nativeQuery = true)
    LeagueEntity findByLeagueName(String name);
}
