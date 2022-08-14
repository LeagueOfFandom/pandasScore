package com.server.pandascore.dto.teamsDetailDto;

import com.server.pandascore.dto.gameDto.sub.player.PlayerDetails;
import com.server.pandascore.dto.teamsDetailDto.sub.Status;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamsDetailDto {
    private String acronym;
    private Long id;
    private String image_url;
    private String name;
    private List<PlayerDetails> players;
    private Status status;
}
