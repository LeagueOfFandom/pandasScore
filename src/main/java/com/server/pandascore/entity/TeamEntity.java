package com.server.pandascore.entity;

import com.server.pandascore.dto.gameDto.sub.player.PlayerDetails;
import com.server.pandascore.dto.teamsDetailDto.sub.Status;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "team_list")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class TeamEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "acronym")
    private String acronym;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "location")
    private String location;

    @Column(name = "name")
    private String name;

    @Type(type = "json")
    @Column(name = "players", columnDefinition = "json")
    private List<PlayerDetails> players;

    @Type(type = "json")
    @Column(name = "status", columnDefinition = "json")
    private Status status;

    @Column(name = "series_id")
    private Long series_id;

    public void setStatus(Status status) {
        this.status = status;
    }


    public TeamEntity(Long id, String acronym, String image_url, String location, String name, List<PlayerDetails> players, Long series_id) {
        this.id = id;
        this.acronym = acronym;
        this.image_url = image_url;
        this.location = location;
        this.name = name;
        this.players = players;
        this.series_id = series_id;
    }
}
