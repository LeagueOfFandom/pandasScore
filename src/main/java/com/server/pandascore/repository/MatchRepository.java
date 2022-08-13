package com.server.pandascore.repository;

import com.server.pandascore.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
}
