package com.server.pandascore.dto.gameDto.sub.player;

import lombok.Getter;

@Getter
public class PhysicalDamage {
    private Long dealt;
    private Long dealt_to_champions;
    private Long taken;
}
