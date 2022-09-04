package com.server.pandascore.entity;

import com.server.pandascore.dto.leagueDto.sub.Series;
import com.server.pandascore.dto.leagueDto.sub.VideoGame;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
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
@Table(name = "league")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class LeagueEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "modified_at")
    private LocalDateTime modified_at;

    @Column(name = "name")
    private String name;

    @Type(type = "json")
    @Column(name = "series", columnDefinition = "json")
    private List<Series> series;

    @Column(name = "slug")
    private String slug;

    @Column(name = "url")
    private String url;

    @Type(type = "json")
    @Column(name = "videogame", columnDefinition = "json")
    private VideoGame videogame;

    public LeagueEntity(Long id, String image_url, LocalDateTime modified_at, String name, List<Series> series, String slug, String url, VideoGame videogame) {
        this.id = id;
        this.image_url = image_url;
        this.modified_at = modified_at;
        this.name = name;
        this.series = series;
        this.slug = slug;
        this.url = url;
        this.videogame = videogame;
    }
}
