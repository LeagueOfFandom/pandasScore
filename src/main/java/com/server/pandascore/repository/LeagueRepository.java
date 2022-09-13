package com.server.pandascore.repository;

import com.server.pandascore.dto.leagueDto.sub.Series;
import com.server.pandascore.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Long> {
    @Query(value = "select json_search(series, 'one', ?2) from league where name = ?1", nativeQuery = true)
    String findSeries(String name, String FullName);

    @Query(value = "select json_value(series, ?2) from league where name = ?1", nativeQuery = true)
    Long findSeriesId(String name, String Id);

    @Query("select l.latest_series_id from LeagueEntity l")
    List<Long> findAllLatestSeriesId();
}
