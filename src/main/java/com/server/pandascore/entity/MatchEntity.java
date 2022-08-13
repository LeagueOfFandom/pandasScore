package com.server.pandascore.entity;

import com.server.pandascore.dto.matchDto.sub.*;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "match_list")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class MatchEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Type(type = "json")
    @Column(name = "tournament", columnDefinition = "json")
    private Tournament tournament;

    @Column(name = "league_id")
    private Long league_id;

    @Column(name = "original_schedule_at")
    private LocalDateTime original_scheduled_at;

    @Column(name = "begin_at")
    private LocalDateTime begin_at;

    @Column(name = "winner_id")
    private Long winner_id;

    @Column(name = "status")
    private String status;

    @Column(name = "end_at")
    private LocalDateTime end_at;

    @Type(type = "json")
    @Column(name = "live", columnDefinition = "json")
    private Live live;

    @Type(type = "json")
    @Column(name = "results", columnDefinition = "json")
    private List<Result> results;

    @Type(type = "json")
    @Column(name = "games", columnDefinition = "json")
    private List<Game> games;

    @Type(type = "json")
    @Column(name = "streams_list", columnDefinition = "json")
    private List<Stream> streams_list;

    @Type(type = "json")
    @Column(name = "opponents", columnDefinition = "json")
    private List<Opponents> opponents;

    public MatchEntity(Long id, Tournament tournament, Long league_id, LocalDateTime original_scheduled_at, LocalDateTime begin_at, Long winner_id, String status, LocalDateTime end_at, Live live, List<Result> results, List<Game> games, List<Stream> streams_list, List<Opponents> opponents) {
        this.id = id;
        this.tournament = tournament;
        this.league_id = league_id;
        this.original_scheduled_at = original_scheduled_at;
        this.begin_at = begin_at;
        this.winner_id = winner_id;
        this.status = status;
        this.end_at = end_at;
        this.live = live;
        this.results = results;
        this.games = games;
        this.streams_list = streams_list;
        this.opponents = opponents;
    }
}
