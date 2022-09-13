package com.server.pandascore.entity;


import com.server.pandascore.dto.statsDto.sub.Total;
import com.server.pandascore.dto.teamsDetailDto.sub.Status;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "team_ranking")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class TeamRankingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long TeamRankingId;

    @Column(name="year")
    private String year;

    @Column(name="season")
    private String season;

    @Column(name="league")
    private String league;

    @Column(name = "series_id")
    private Long seriesId;

//    @Column(name = "rank")
//    private Long rank;

    @Column(name = "team_name")
    private String teamName;

    @Type(type = "json")
    @Column(name = "total", columnDefinition = "json")
    private Total total;

    @Column(name="matches_win_rate")
    private String matchesWinRate;

    @Column(name="games_win_rate")
    private String gamesWinRate;

    @Column(name="points")
    private Long points;

    public TeamRankingEntity(String year, String season, String league, Long SeriesId, String teamName, Total total, String matchesWinRate, String gamesWinRate, Long points){
        this.year = year;
        this.season = season;
        this.league = league;
        this.seriesId = SeriesId;
        this.teamName = teamName;
        this.total = total;
        this.matchesWinRate = matchesWinRate;
        this.gamesWinRate = gamesWinRate;
        this.points = points;
    }
}
