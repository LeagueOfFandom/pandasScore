package com.server.pandascore.dto.teamDto;

import com.server.pandascore.dto.gameDto.sub.player.PlayerDetails;
import com.server.pandascore.entity.TeamEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamDto {
    private String acronym;
    private Long id;
    private String image_url;
    private String location;
    private String name;
    private List<PlayerDetails> players;

    public TeamEntity toTeamEntity(Long series_id, Long league_id) {
        return new TeamEntity(id, acronym, image_url, location, name, players, series_id, league_id);
    }
}
