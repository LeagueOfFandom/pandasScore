package com.server.pandascore.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "lof_user")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId; //임의로 생성된 user id. 추후 int가 아닌 타 값으로 변경 필요.(보안)
    @Column(name = "token")
    private String token; //fcm token 값. 변경 가능으로 setter 설정.
    @Column(name = "email")
    private String email; //현재 user 구분을 위한 deviceId. 추후 로그인을 통해 사라질 값.
    @Type(type = "json")
    @Column(name = "team_list" ,columnDefinition = "json")
    private List<Long> teamList = new ArrayList<>(); //user가 등록한 팀 리스트. 변경 가능으로 setter 설정.
    @Type(type = "json")
    @Column(name = "selected", columnDefinition = "json")
    private Map<Long, Boolean> userSelected = new HashMap<>(); //user가 변동한 알람 내역. 임의로 변경하면 안되기에 저장. 변경 가능으로 setter 설정.
}
