package com.server.pandascore.dto.leagueDto;

import com.server.pandascore.dto.leagueDto.sub.Series;
import com.server.pandascore.dto.leagueDto.sub.VideoGame;
import com.server.pandascore.entity.LeagueEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LeagueListDto {
    private Long id;
    private String image_url;
    private LocalDateTime modified_at;
    private String name;
    private List<Series> series;
    private String slug;
    private String url;
    private VideoGame videogame;

    public LeagueEntity toEntity(){
        if(series.size() == 0){
            return new LeagueEntity(id, image_url, modified_at, name, series, slug, url, videogame, null);
        }
        return new LeagueEntity(id,image_url,modified_at,name,series,slug,url,videogame,series.get(series.size() - 1).getId());
    }
}
