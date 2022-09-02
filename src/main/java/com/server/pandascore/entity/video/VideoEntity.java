package com.server.pandascore.entity.video;

import lombok.NoArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name="video")
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long videoId;

    @Column(name="video_resource_id")
    private String videoResourceId;

    @Column(name="video_title")
    private String videoTitle;

    @Column(name="video_description")
    private String videoDescription;

    @Column(name="video_thumbnail")
    private String videoThumbnail;

    @Column(name="video_published_at")
    private String videoPublishedAt;

    @Column(name="video_channel_id")
    private String videoChannelId;

    @Column(name="video_channel_title")
    private String videoChannelTitle;

    @Column(name="video_view_count")
    private Long videoViewCount;

    @Column(name="video_like_count")
    private Long videoLikeCount;

    @Column(name="video_dislike_count")
    private Long videoDislikeCount;
}
