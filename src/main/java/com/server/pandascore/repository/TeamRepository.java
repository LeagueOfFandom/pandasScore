package com.server.pandascore.repository;

import com.server.pandascore.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    TeamEntity findByIdAndSeriesId(Long id, Long series_id);
}
