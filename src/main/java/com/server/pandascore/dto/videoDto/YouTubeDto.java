package com.server.pandascore.dto.videoDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter @Setter
@NoArgsConstructor
public class YouTubeDto {

    private String resourceId;

    private String title;

    private String thumbnail;

    private String publishedAt;

    private String channelId;

    private String channelTitle;

    private String description;

}
