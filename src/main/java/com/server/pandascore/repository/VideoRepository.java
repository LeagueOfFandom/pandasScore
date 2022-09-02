package com.server.pandascore.repository;

import com.server.pandascore.entity.video.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
    @Query(value ="select * from video where video_resource_id = ?1", nativeQuery = true)
    VideoEntity findByVideoResourceId(String videoResourceId);
}

