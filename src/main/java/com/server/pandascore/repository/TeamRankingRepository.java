package com.server.pandascore.repository;

import com.server.pandascore.entity.TeamRankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRankingRepository extends JpaRepository<TeamRankingEntity, Long> {
}
