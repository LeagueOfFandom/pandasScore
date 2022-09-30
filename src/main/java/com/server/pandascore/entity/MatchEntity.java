package com.server.pandascore.entity;

import com.server.pandascore.dto.matchDto.sub.*;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Builder;
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

    @Column(name = "rescheduled")
    private Boolean rescheduled;

    @Type(type = "json")
    @Column(name = "winner", columnDefinition = "json")
    private Winner winner;

    @Column(name = "winner_type")
    private String winnerType;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Type(type = "json")
    @Column(name = "streams_list", columnDefinition = "json")
    private List<Stream> streamsList;

    @Column(name = "forfeit")
    private Boolean forfeit;

    @Column(name = "official_stream_url")
    private String officialStreamUrl;

    @Column(name = "number_of_games")
    private Long numberOfGames;

    @Type(type = "json")
    @Column(name = "oppontents", columnDefinition = "json")
    private List<Opponents> opponents;

    @Column(name = "tournament_id")
    private Long tournamentId;

    @Column(name = "name")
    private String name;

    @Type(type = "json")
    @Column(name = "league", columnDefinition = "json")
    private League league;

    @Type(type = "json")
    @Column(name = "tournament", columnDefinition = "json")
    private Tournament tournament;

    @Column(name = "begin_at")
    private LocalDateTime beginAt;

    @Column(name = "slug")
    private String slug;

    @Column(name = "status")
    private String status;

    @Column(name = "match_type")
    private String match_type;

    @Column(name = "live_embed_url")
    private String liveEmbedUrl;

    @Column(name = "winner_id")
    private Long winnerId;

    @Type(type = "json")
    @Column(name = "serie", columnDefinition = "json")
    private Serie serie;

    @Column(name = "detailed_stats")
    private Boolean detailed_stats;

    @Type(type = "json")
    @Column(name = "results", columnDefinition = "json")
    private List<Result> results;

    @Column(name = "draw")
    private Boolean draw;

    @Type(type = "json")
    @Column(name = "live", columnDefinition = "json")
    private Live live;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "serie_id")
    private Long serieId;

    @Column(name = "original_scheduled_at")
    private LocalDateTime originalScheduledAt;

    @Type(type = "json")
    @Column(name = "games", columnDefinition = "json")
    private List<Game> games;

    @Column(name = "league_id")
    private Long leagueId;


    @Builder
    public MatchEntity(Long id, Boolean rescheduled, Winner winner, String winnerType, LocalDateTime scheduledAt, List<Stream> streamsList, Boolean forfeit, String officialStreamUrl, Long numberOfGames, List<Opponents> opponents, Long tournamentId, String name, League league, Tournament tournament, LocalDateTime beginAt, String slug, String status, String match_type, String liveEmbedUrl, Long winnerId, Serie serie, Boolean detailed_stats, List<Result> results, Boolean draw, Live live, LocalDateTime endAt, LocalDateTime modifiedAt, Long serieId, LocalDateTime originalScheduledAt, List<Game> games, Long leagueId) {
        this.id = id;
        this.rescheduled = rescheduled;
        this.winner = winner;
        this.winnerType = winnerType;
        this.scheduledAt = scheduledAt;
        this.streamsList = streamsList;
        this.forfeit = forfeit;
        this.officialStreamUrl = officialStreamUrl;
        this.numberOfGames = numberOfGames;
        this.opponents = opponents;
        this.tournamentId = tournamentId;
        this.name = name;
        this.league = league;
        this.tournament = tournament;
        this.beginAt = beginAt;
        this.slug = slug;
        this.status = status;
        this.match_type = match_type;
        this.liveEmbedUrl = liveEmbedUrl;
        this.winnerId = winnerId;
        this.serie = serie;
        this.detailed_stats = detailed_stats;
        this.results = results;
        this.draw = draw;
        this.live = live;
        this.endAt = endAt;
        this.modifiedAt = modifiedAt;
        this.serieId = serieId;
        this.originalScheduledAt = originalScheduledAt;
        this.games = games;
        this.leagueId = leagueId;
    }
}
