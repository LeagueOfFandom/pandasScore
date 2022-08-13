package com.server.pandascore.dto.gameDto.sub.player;

import lombok.Getter;

@Getter
public class Damage {
    private Long dealt;
    private Long dealt_to_champions;
    private Long taken;
}
