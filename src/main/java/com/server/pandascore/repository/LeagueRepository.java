package com.server.pandascore.repository;

import com.server.pandascore.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Long> {
}
