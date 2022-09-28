package com.server.pandascore.dto.matchDto.sub;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Winner {
    private Long id;
    private String acronym;
    private String image_url;
    private String name;
    private String location;
    private LocalDateTime modified_at;
    private String slug;
}
