package com.server.pandascore.dto.matchDto;

import com.server.pandascore.dto.matchDto.sub.*;
import com.server.pandascore.entity.MatchEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MatchDto {

    private Boolean rescheduled;
    private String winner;
    private String winner_type;
    private LocalDateTime scheduled_at;
    private List<Stream> streams_list;
    private Boolean forfeit;
    private String official_stream_url;
    private Long id;
    private Long number_of_games;
    private List<Opponents> opponents;
    private String videogame_version;
    private Long tournament_id;
    private String name;
    private League league;
    private Tournament tournament;
    private LocalDateTime begin_at;
    private String slug;
    private String status;
    private String match_type;
    private String live_embed_url;
    private Long winner_id;
    private Serie serie;
    private Boolean detailed_stats;
    private List<Result> results;
    private Boolean draw;
    private Live live;
    private LocalDateTime end_at;
    private LocalDateTime modified_at;
    private Long serie_id;
    private LocalDateTime original_scheduled_at;
    private List<Game> games;
    private Long league_id;

    public MatchEntity toEntity(){
        return MatchEntity.builder()
                .rescheduled(rescheduled)
                .winner(winner)
                .winnerType(winner_type)
                .scheduledAt(scheduled_at)
                .streamsList(streams_list)
                .forfeit(forfeit)
                .officialStreamUrl(official_stream_url)
                .id(id)
                .numberOfGames(number_of_games)
                .opponents(opponents)
                .videoGameVersion(videogame_version)
                .tournamentId(tournament_id)
                .name(name)
                .league(league)
                .tournament(tournament)
                .beginAt(begin_at)
                .slug(slug)
                .status(status)
                .match_type(match_type)
                .liveEmbedUrl(live_embed_url)
                .winnerId(winner_id)
                .serie(serie)
                .detailed_stats(detailed_stats)
                .results(results)
                .draw(draw)
                .live(live)
                .endAt(end_at)
                .modifiedAt(modified_at)
                .serieId(serie_id)
                .originalScheduledAt(original_scheduled_at)
                .games(games)
                .leagueId(league_id)
                .build();
    }

}
