package com.server.pandascore.dto.gameDto;

import com.server.pandascore.dto.gameDto.sub.player.Player;
import com.server.pandascore.dto.gameDto.sub.teams.Team;
import com.server.pandascore.dto.matchDto.sub.Winner;
import com.server.pandascore.entity.MatchDetailEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GameDto {
    private LocalDateTime begin_at;
    private LocalDateTime end_at;
    private Long id;
    private Long length;
    private List<Player> players;
    private String status;
    private Winner winner;
    private List<Team> teams;

    public MatchDetailEntity toMatchDetailEntity(){
        return MatchDetailEntity.builder()
                .beginAt(begin_at)
                .endAt(end_at)
                .id(id)
                .length(length)
                .players(players)
                .status(status)
                .winner(winner)
                .teams(teams)
                .build();
    }

}
