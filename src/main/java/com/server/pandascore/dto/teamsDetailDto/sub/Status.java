package com.server.pandascore.dto.teamsDetailDto.sub;

import lombok.Getter;

@Getter
public class Status {
    private Long games_count;
    private Totals totals;
    private Averages averages;
}
