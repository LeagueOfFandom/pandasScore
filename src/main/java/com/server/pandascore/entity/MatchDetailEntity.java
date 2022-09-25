package com.server.pandascore.entity;

import com.server.pandascore.dto.gameDto.sub.player.Player;
import com.server.pandascore.dto.gameDto.sub.teams.Team;
import com.server.pandascore.dto.matchDto.sub.Winner;
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
@Table(name = "match_detail_list")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class MatchDetailEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "begin_at")
    private LocalDateTime beginAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "status")
    private String status;

    @Column(name = "length")
    private Long length;

    @Type(type = "json")
    @Column(name = "players", columnDefinition = "json")
    private List<Player> players;

    @Type(type = "json")
    @Column(name = "winner", columnDefinition = "json")
    private Winner winner;

    @Type(type = "json")
    @Column(name = "teams", columnDefinition = "json")
    private List<Team> teams;

    @Column(name = "position")
    private Long position;

    @Builder
    public MatchDetailEntity(Long id, LocalDateTime beginAt, LocalDateTime endAt, String status, Long length, List<Player> players, Winner winner, List<Team> teams, Long position) {
        this.id = id;
        this.beginAt = beginAt;
        this.endAt = endAt;
        this.status = status;
        this.length = length;
        this.players = players;
        this.winner = winner;
        this.teams = teams;
        this.position = position;
    }
}
