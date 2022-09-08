package com.server.pandascore.dto.statsDto;

import com.server.pandascore.dto.gameDto.sub.player.Player;
import com.server.pandascore.dto.statsDto.sub.Stat;
import lombok.Getter;

import java.util.List;

@Getter
public class StatDto {
    private String acronym;
    private Long id;
    private String image_url;
    // private List<LastGame> last_games;
    private String location;
    //private LocalDateTime modified_at;
    //private List<MostBanned> mostBanneds;
    //private List<MostPick> mostPicks;
    private String name;
    //private List<Player> players;
    private String slug;
    private Stat stats;

//    public StatEntity toStatsEntity(){
//        return new StatEntity(id, acrnoym, image_url, location, name, players, slug, stat);
//    }
}
