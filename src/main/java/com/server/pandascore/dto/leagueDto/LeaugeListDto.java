package com.server.pandascore.dto.leagueDto;

import com.server.pandascore.dto.leagueDto.sub.Series;
import com.server.pandascore.dto.leagueDto.sub.VideoGame;

import java.time.LocalDateTime;
import java.util.List;

public class LeaugeListDto {
    private Long id;
    private String image_url;
    private LocalDateTime modified_at;
    private String name;
    private List<Series> series;
    private String slug;
    private String url;
    private VideoGame videogame;
}
