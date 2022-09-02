package com.server.pandascore.entity.video;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name="videosource")
public class VideoSourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long videoSourceId;

    @Column(name="channel_title")
    private String ChannelTitle;
    @Column(name="channel_id")
    private String ChannelId;
    @Column(name="source_type")
    private String sourceType;

    public VideoSourceEntity(String ChannelId, String ChannelTitle, String sourceType){
        this.ChannelId = ChannelId;
        this.ChannelTitle = ChannelTitle;
        this.sourceType = sourceType;
    }
}
