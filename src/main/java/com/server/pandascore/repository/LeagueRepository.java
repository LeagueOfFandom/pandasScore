package com.server.pandascore.repository;

import com.server.pandascore.dto.leagueDto.sub.Series;
import com.server.pandascore.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Long> {

    @Query("select l.latestSeriesId from LeagueEntity l")
    List<Long> findAllLatestSeriesId();
    @Query("select l.series from LeagueEntity l")
    Long findIdByLatestSeriesId(Long latestSeriesId);

    @Query("select l.id from LeagueEntity l")
    List<Long> findAllId();
}
