package com.server.pandascore.repository;

import com.server.pandascore.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Long> {
    @Query("select l.latest_series_id from LeagueEntity l")
    List<Long> findAllLatestSeriesId();
}
